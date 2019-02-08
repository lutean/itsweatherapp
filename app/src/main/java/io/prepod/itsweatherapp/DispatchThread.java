package io.prepod.itsweatherapp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.CountDownLatch;

public class DispatchThread extends Thread {

    private volatile Handler handler = null;
    private volatile Handler mainThreadHandler = null;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DispatchThread() {
        start();
        mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public void execute(Runnable runnable) {
        execute(runnable, 0);
    }

    public void execute(Runnable runnable, long delay) {
        try {
            countDownLatch.await();
            if (delay <= 0)
                handler.post(runnable);
            else
                handler.postDelayed(runnable, delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void executeInMainThread(Runnable runnable){
        mainThreadHandler.post(runnable);
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };
        countDownLatch.countDown();
        Looper.loop();
    }
}