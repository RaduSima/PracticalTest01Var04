package ro.pub.cs.systems.eim.practicaltest01var04;

import static java.lang.Math.sqrt;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread{
    private String text1, text2;
    private Context context;

    boolean isRunning = true;

    public ProcessingThread(Context context, String text1, String text2) {
        this.context = context;
        this.text1 = text1;
        this.text2 = text2;
    }

    @Override
    public void run() {
        Log.d(Constants.THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());

        while (isRunning) {
            sendMessage(text1, Constants.actionTypes[0]);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

            sendMessage(text2, Constants.actionTypes[0]);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        Log.d(Constants.THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage(String text, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(Constants.BROADCAST_KEY, text);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}
