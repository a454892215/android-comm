package com.zb;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Bundle;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class H264Encoder {

    private static final String TAG = "H264Encoder";
    private static final String MIME_TYPE = MediaFormat.MIMETYPE_VIDEO_AVC;

    private MediaCodec mediaCodec;
    private int width;
    private int height;
    private int frameRate;
    private int bitRate;
    private int iFrameInterval;

    private volatile boolean isRunning = false;
    private Thread encodeThread;

    // 输入帧队列（YUV 数据）
    private final BlockingQueue<Frame> frameQueue = new ArrayBlockingQueue<>(10);

    public interface OnEncodedListener {
        void onSpsPps(byte[] sps, byte[] pps);
        void onFrameEncoded(byte[] data, boolean isKeyFrame, long ptsUs);
        void onError(Exception e);
    }

    private OnEncodedListener encodedListener;

    public H264Encoder(int width,
                       int height,
                       int frameRate,
                       int bitRate,
                       int iFrameInterval,
                       OnEncodedListener listener) {
        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.bitRate = bitRate;
        this.iFrameInterval = iFrameInterval;
        this.encodedListener = listener;
    }

    // ===================== 对外 API =====================

    /**
     * 启动编码器
     */
    public void start() {
        try {
            initEncoder();
            isRunning = true;
            startEncodeThread();
        } catch (Exception e) {
            Log.e(TAG, "Start encoder failed", e);
            if (encodedListener != null) {
                encodedListener.onError(e);
            }
        }
    }

    /**
     * 停止编码器
     */
    public void stop() {
        isRunning = false;
        if (encodeThread != null) {
            try {
                encodeThread.join();
            } catch (InterruptedException ignored) {}
            encodeThread = null;
        }
        releaseEncoder();
    }

    /**
     * 输入一帧 YUV 数据（来自 Camera）
     */
    public void offerFrame(byte[] yuvData, long ptsUs) {
        if (!isRunning) return;

        Frame frame = new Frame(yuvData, ptsUs);
        if (!frameQueue.offer(frame)) {
            // 队列满了，丢帧，防止延迟堆积
            Log.w(TAG, "Frame queue full, drop frame");
        }
    }

    /**
     * 请求立刻产生一个 I 帧（新观众进入、切清晰度时用）
     */
    public void requestSyncFrame() {
        if (mediaCodec == null) return;
        try {
            Bundle params = new Bundle();
            params.putInt(MediaCodec.PARAMETER_KEY_REQUEST_SYNC_FRAME, 0);
            mediaCodec.setParameters(params);
        } catch (Exception e) {
            Log.e(TAG, "Request sync frame failed", e);
        }
    }

    // ===================== 初始化编码器 =====================

    private void initEncoder() throws Exception {
        MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, width, height);

        // 颜色格式：支持灵活 YUV420
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible);

        // 码率（非常关键）
        format.setInteger(MediaFormat.KEY_BIT_RATE, bitRate);

        // 帧率
        format.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);

        // I 帧间隔（秒）
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, iFrameInterval);

        // Profile（直播一般用 Baseline / Main）
        format.setInteger(MediaFormat.KEY_PROFILE,
                MediaCodecInfo.CodecProfileLevel.AVCProfileBaseline);

        // 实时优先
        format.setInteger(MediaFormat.KEY_PRIORITY, 0);

        mediaCodec = MediaCodec.createEncoderByType(MIME_TYPE);
        mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mediaCodec.start();

        Log.i(TAG, "Encoder started: " +
                width + "x" + height +
                ", fps=" + frameRate +
                ", bitrate=" + bitRate);
    }

    // ===================== 编码线程 =====================

    private void startEncodeThread() {
        encodeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                encodeLoop();
            }
        }, "H264-Encode-Thread");
        encodeThread.start();
    }

    private void encodeLoop() {
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

        byte[] sps = null;
        byte[] pps = null;

        while (isRunning) {
            try {
                // 1. 取一帧输入
                Frame frame = frameQueue.take();

                // 2. 填充输入缓冲区
                int inputIndex = mediaCodec.dequeueInputBuffer(10_000);
                if (inputIndex >= 0) {
                    ByteBuffer inputBuffer = mediaCodec.getInputBuffer(inputIndex);
                    if (inputBuffer != null) {
                        inputBuffer.clear();
                        inputBuffer.put(frame.data);
                        mediaCodec.queueInputBuffer(
                                inputIndex,
                                0,
                                frame.data.length,
                                frame.ptsUs,
                                0
                        );
                    }
                }

                // 3. 取编码输出
                int outputIndex;
                while ((outputIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0)) >= 0) {
                    ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(outputIndex);
                    if (outputBuffer == null) {
                        mediaCodec.releaseOutputBuffer(outputIndex, false);
                        continue;
                    }

                    byte[] outData = new byte[bufferInfo.size];
                    outputBuffer.get(outData);

                    boolean isKeyFrame =
                            (bufferInfo.flags & MediaCodec.BUFFER_FLAG_KEY_FRAME) != 0;

                    // 4. 处理 SPS / PPS
                    if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                        // 这里通常包含 SPS + PPS
                        byte[][] spsPps = splitSpsPps(outData);
                        if (spsPps != null) {
                            sps = spsPps[0];
                            pps = spsPps[1];
                            if (encodedListener != null) {
                                encodedListener.onSpsPps(sps, pps);
                            }
                        }
                    } else {
                        // 普通视频帧
                        if (encodedListener != null) {
                            encodedListener.onFrameEncoded(
                                    outData,
                                    isKeyFrame,
                                    bufferInfo.presentationTimeUs
                            );
                        }
                    }

                    mediaCodec.releaseOutputBuffer(outputIndex, false);
                }

            } catch (InterruptedException e) {
                // 正常退出
                break;
            } catch (Exception e) {
                Log.e(TAG, "Encode loop error", e);
                if (encodedListener != null) {
                    encodedListener.onError(e);
                }
            }
        }
    }

    // ===================== 工具方法 =====================

    /**
     * 简单拆分 SPS / PPS（演示用，实际项目可更严格解析 NALU）
     */
    private byte[][] splitSpsPps(byte[] configData) {
        // 常见格式: 00 00 00 01 SPS 00 00 00 01 PPS
        try {
            int spsStart = 0;
            int ppsStart = -1;

            for (int i = 4; i < configData.length - 4; i++) {
                if (configData[i] == 0x00 &&
                        configData[i + 1] == 0x00 &&
                        configData[i + 2] == 0x00 &&
                        configData[i + 3] == 0x01) {
                    ppsStart = i;
                    break;
                }
            }

            if (ppsStart > 0) {
                byte[] sps = new byte[ppsStart];
                byte[] pps = new byte[configData.length - ppsStart];
                System.arraycopy(configData, 0, sps, 0, sps.length);
                System.arraycopy(configData, ppsStart, pps, 0, pps.length);
                return new byte[][]{sps, pps};
            }
        } catch (Exception ignored) {}
        return null;
    }

    private void releaseEncoder() {
        try {
            if (mediaCodec != null) {
                mediaCodec.stop();
                mediaCodec.release();
                mediaCodec = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Release encoder error", e);
        }
    }

    // ===================== 内部帧结构 =====================

    private static class Frame {
        byte[] data;
        long ptsUs;

        Frame(byte[] data, long ptsUs) {
            this.data = data;
            this.ptsUs = ptsUs;
        }
    }
}

