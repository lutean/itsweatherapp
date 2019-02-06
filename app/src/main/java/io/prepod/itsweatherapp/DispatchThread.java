package io.prepod.itsweatherapp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.CountDownLatch;

public class DispatchThread extends Thread {

    private volatile Handler handler = null;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DispatchThread() {
        start();
    }

    public void postRunnable(Runnable runnable) {
        postRunnable(runnable, 0);
    }

    public void postRunnable(Runnable runnable, long delay) {
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