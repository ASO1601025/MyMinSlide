package com.example.motoyama.myminslide;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;
    int[] mImageResources = {R.drawable.s01,R.drawable.s02,R.drawable.s03,R.drawable.s04,
            R.drawable.s05,R.drawable.s06,R.drawable.s07,R.drawable.s08,R.drawable.s09,R.drawable.s10};
    int mPosition=0;

    boolean mIsSlideshow = false;

    MediaPlayer mMediaPlayer;

    public class MainTimaerTask extends TimerTask {
        @Override
        public void run(){
            if(mIsSlideshow){
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        movePosition(1);
                    }
                });
            }
        }
    }

    Timer mTimer = new Timer();
    TimerTask mTimerTask = new MainTimaerTask();
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory(){
            @Override
            public View makeView(){
                ImageView imageview =
                        new ImageView(getApplicationContext());
                return imageview;
            }
        });
        mImageSwitcher.setImageResource(mImageResources[0]);
        mTimer.schedule(mTimerTask,0,3000);
        mMediaPlayer = MediaPlayer.create(this,R.raw.getdown);
        mMediaPlayer.setLooping(true);
    }


    public void movePosition(int move){
        mPosition = mPosition + move;
        if(mPosition >= mImageResources.length){
            mPosition=0;
        } else if(mPosition<0){
            mPosition=mImageResources.length-1;
        }
        mImageSwitcher.setImageResource(mImageResources[mPosition]);
    }



    public void onstartbuttoTapped(View view){
        mIsSlideshow = !mIsSlideshow;
        mMediaPlayer.start();
    }

    public void onstopbuttonTapped(View view){
        mIsSlideshow = !mIsSlideshow;
        mMediaPlayer.pause();
    }
}
