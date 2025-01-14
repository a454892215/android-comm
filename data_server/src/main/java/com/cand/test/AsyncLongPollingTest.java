package com.cand.test;

import com.cand.util.LogUtil;

import java.util.concurrent.*;

import jdk.internal.org.jline.utils.Log;

/**
 * Java不通线程挂起和通知用例
 */
public class AsyncLongPollingTest {

    // 客户端的任务映射
    private final ConcurrentHashMap<String, CompletableFuture<String>> clientMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // 定时器线程池

    /**
     * 模拟处理客户端的 GET 请求
     */
    protected void doGet(String clientId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        clientMap.put(clientId, future);
        // 设置超时逻辑
        scheduleTimeout(clientId, future, 10, TimeUnit.SECONDS);
        // 异步响应
        future.thenAccept(data -> {
            try {
                // 模拟返回结果
                LogUtil.d("Response to client " + clientId + ": " + data);
            } catch (Exception e) {
                LogUtil.d(e.toString());
            }
        }).exceptionally(ex -> {
            try {
                // 模拟超时或异常处理
                LogUtil.d("Error for client " + clientId + ": " + ex.getMessage());
            } catch (Exception e) {
                LogUtil.d(e.toString());
            }
            return null;
        });
        LogUtil.d("============doGet  函数执行完毕===================");
    }

    /**
     * 更新数据并唤醒等待的客户端
     *
     * @param clientId 客户端 ID
     * @param data     返回的数据
     */
    public void updateData(String clientId, String data) {
        CompletableFuture<String> future = clientMap.remove(clientId);
        if (future != null) {
            future.complete(data); // 唤醒等待中的客户端
        }
    }

    /**
     * 为 CompletableFuture 设置超时逻辑
     *
     * @param clientId 客户端 ID
     * @param future   客户端对应的 CompletableFuture
     * @param timeout  超时时间
     * @param unit     时间单位
     */
    private void scheduleTimeout(String clientId, CompletableFuture<String> future, long timeout, TimeUnit unit) {
        scheduler.schedule(() -> {
            CompletableFuture<String> removedFuture = clientMap.remove(clientId); // 防止重复处理
            if (removedFuture != null) {
                removedFuture.completeExceptionally(new TimeoutException("Timeout after " + timeout + " " + unit));
            }
        }, timeout, unit);
    }

    /**
     * 关闭资源
     */
    public void shutdown() {
        scheduler.shutdown();
    }

    // 测试代码
    public static void main(String[] args) throws InterruptedException {
        AsyncLongPollingTest server = new AsyncLongPollingTest();

        // 模拟客户端请求
        server.doGet("client1");

        // 模拟异步更新数据
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
            server.updateData("client1", "Hello, Client 1!");
        }, 9, TimeUnit.SECONDS);
        // 等待一会儿以查看结果
        Thread.sleep(1000 * 15);
        // 关闭服务
        scheduledExecutorService.shutdown(); //避免挂起进程
        server.shutdown();//避免挂起进程
        LogUtil.d("=======主线程执行完毕==========");
    }
}
