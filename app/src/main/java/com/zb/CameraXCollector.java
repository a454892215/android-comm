package com.zb;


import android.content.Context;
import android.util.Log;
import android.util.Size;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraXCollector {
    private static final String TAG = "CameraXCollector";

    private final Context context;
    private final LifecycleOwner lifecycleOwner;
    private final OnFrameListener frameListener;

    private ProcessCameraProvider cameraProvider;
    private ExecutorService cameraExecutor;

    public interface OnFrameListener {
        void onFrame(byte[] yuvData, int width, int height, long timestamp);
        void onError(Exception e);
    }

    public CameraXCollector(Context context,
                            LifecycleOwner lifecycleOwner,
                            OnFrameListener listener) {
        this.context = context.getApplicationContext();
        this.lifecycleOwner = lifecycleOwner;
        this.frameListener = listener;
    }

    /**
     * 启动采集
     */
    public void start() {
        cameraExecutor = Executors.newSingleThreadExecutor();

        ListenableFuture<ProcessCameraProvider> future =
                ProcessCameraProvider.getInstance(context);

        future.addListener(() -> {
            try {
                cameraProvider = future.get();
                bindUseCases();
            } catch (Exception e) {
                Log.e(TAG, "Get CameraProvider failed", e);
                if (frameListener != null) {
                    frameListener.onError(e);
                }
            }
        }, ContextCompat.getMainExecutor(context));
    }

    /**
     * 绑定预览 + 分析
     */
    private void bindUseCases() {
        if (cameraProvider == null) {
            return;
        }

        try {
            // 解绑旧的
            cameraProvider.unbindAll();

            // 选择前置或后置摄像头
            CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

            // 预览（如果你有 SurfaceView / PreviewView 可接入）
            Preview preview = new Preview.Builder()
                    .setTargetResolution(new Size(1280, 720))
                    .build();
            // 图像分析，用于拿 YUV 数据
            ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                    .setTargetResolution(new Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build();
            imageAnalysis.setAnalyzer(cameraExecutor, image -> {
                try {
                    processImage(image);
                } catch (Exception e) {
                    Log.e(TAG, "Analyze error", e);
                    if (frameListener != null) {
                        frameListener.onError(e);
                    }
                } finally {
                    // 一定要关闭，否则会阻塞
                    image.close();
                }
            });

            // 绑定到生命周期
            cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis
            );

        } catch (Exception e) {
            Log.e(TAG, "Bind use cases failed", e);
            if (frameListener != null) {
                frameListener.onError(e);
            }
        }
    }

    /**
     * 处理每一帧图像，提取 YUV 数据
     */
    private void processImage(ImageProxy image) {
        if (image.getFormat() != android.graphics.ImageFormat.YUV_420_888) {
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        long timestamp = image.getImageInfo().getTimestamp();

        byte[] yuvData = yuv420ToNv21(image);

        if (frameListener != null && yuvData != null) {
            frameListener.onFrame(yuvData, width, height, timestamp);
        }
    }

    /**
     * 将 YUV_420_888 转成 NV21（常用于送 MediaCodec）
     */
    private byte[] yuv420ToNv21(ImageProxy image) {
        ImageProxy.PlaneProxy[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];

        // Y
        yBuffer.get(nv21, 0, ySize);

        // VU 交错
        int uvOffset = ySize;
        byte[] uBytes = new byte[uSize];
        byte[] vBytes = new byte[vSize];
        uBuffer.get(uBytes);
        vBuffer.get(vBytes);

        for (int i = 0; i < vSize; i++) {
            nv21[uvOffset++] = vBytes[i];
            nv21[uvOffset++] = uBytes[i];
        }

        return nv21;
    }

    /**
     * 停止采集
     */
    public void stop() {
        try {
            if (cameraProvider != null) {
                cameraProvider.unbindAll();
            }
            if (cameraExecutor != null) {
                cameraExecutor.shutdown();
                cameraExecutor = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Stop error", e);
        }
    }

    public static void ApiUseSample(Context context, LifecycleOwner lifecycleOwner) {
        ;
        CameraXCollector collector = new CameraXCollector(
                context,
                lifecycleOwner,
                new CameraXCollector.OnFrameListener() {
                    @Override
                    public void onFrame(byte[] yuvData, int width, int height, long timestamp) {
                        // 这里可以直接送入 MediaCodec 编码器
                    }

                    @Override
                    public void onError(Exception e) {
                        // 处理采集异常
                    }
                }
        );

        collector.start();
    }
}

