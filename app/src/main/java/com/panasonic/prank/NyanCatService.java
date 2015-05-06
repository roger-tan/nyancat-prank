package com.panasonic.prank;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

public class NyanCatService extends Service {
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private GifView mView;
    private MediaPlayer mMediaPlayer;
    private boolean mIsBound = false;

    public NyanCatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isRunning = intent.getBooleanExtra("run", false);
        if (isRunning) {
            createComponents();
        } else {
            if (mIsBound) {
                mWindowManager.removeView(mView);
                mMediaPlayer.stop();
                mIsBound = false;
            }
        }
        return START_STICKY;
    }

    private void initialize() {
        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                ,
                PixelFormat.TRANSLUCENT);

        mLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
    }

    private void createComponents() {
        try {
            if (!mIsBound) {
                mMediaPlayer = new MediaPlayer();
                mView = new GifView(this, "file:///android_asset/nyan.gif");
                mView.setMinimumHeight(100);
                mView.setMinimumWidth(200);
                mView.setBackgroundColor(Color.TRANSPARENT);
                mWindowManager.addView(mView, mLayoutParams);
                
                AssetFileDescriptor afd = getAssets().openFd("nyancat.mp3");
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.setVolume(1.0f, 1.0f);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                mIsBound = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
