package com.jxau.jf.englishstudy.mainContent;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jxau.jf.englishstudy.R;
import com.jxau.jf.englishstudy.utils.HttpUtils;


public class SpokenActivity extends AppCompatActivity {
    private TextView title;
    private Button playVoice;
    private MediaPlayer mMediaPlayer;
    private Boolean isPause = true;//是否为播放状态，true为暂停状态
    private String voiceURL = "girl.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spoken);
        playVoice = findViewById(R.id.spo_voice_button);
        title = findViewById(R.id.spo_title);
        title.setText(getIntent().getStringExtra("title"));
        //直接创建，不需要设置setDataSource
        String path= HttpUtils.URL + HttpUtils.SPO + voiceURL;     //这里给一个歌曲的网络地址就行了
        Uri uri  =  Uri.parse(path);
        mMediaPlayer = MediaPlayer.create(SpokenActivity.this, uri);
        playVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPause) {
                    mMediaPlayer.start();
                    isPause = false;
                } else {
                    mMediaPlayer.pause();
                    isPause = true;
                }
                //监听音频播放完毕事件
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReleasePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer.start();
        isPause = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.pause();
        isPause = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    /**
     * 释放播放器资源
     */
    private void ReleasePlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
