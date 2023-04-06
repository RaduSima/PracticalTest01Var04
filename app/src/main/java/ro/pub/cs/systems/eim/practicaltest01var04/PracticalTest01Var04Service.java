package ro.pub.cs.systems.eim.practicaltest01var04;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class PracticalTest01Var04Service extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String text1 = intent.getStringExtra(Constants.TEXT1_KEY);
            String text2 = intent.getStringExtra(Constants.TEXT2_KEY);

            if (processingThread != null) {
                processingThread.stopThread();
            }
            processingThread = new ProcessingThread(this, text1, text2);
            processingThread.start();
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (processingThread != null) {
            processingThread.stopThread();
        }
    }
}
