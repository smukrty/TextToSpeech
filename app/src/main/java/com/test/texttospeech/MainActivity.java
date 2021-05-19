package com.test.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final int ActivityResult_CODE = 0;

    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check whether TTS resources are available on the device
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, ActivityResult_CODE);


        mButtonSpeak = findViewById(R.id.button_speak);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //監聽創建是否成功
                // status : TextToSpeech.SUCCESS=0 , TextToSpeech.ERROR=-1
                Log.e("TAG", "TextToSpeech onInit status = " + status);
                if (status == TextToSpeech.SUCCESS) {
                    //setLanguage設置語言
                    int result = mTTS.setLanguage(Locale.CHINA);
                    int result2 = mTTS.setLanguage(Locale.CHINESE);
                    Log.e("tts：", "語言設置結果："+result+":"+result2);

                    // TextToSpeech.LANG_MISSING_DATA：表示语言的資料遺失
                    // TextToSpeech.LANG_NOT_SUPPORTED：不支持
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        //不支持中文就設為英文
                        mTTS.setLanguage(Locale.US);
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ActivityResult_CODE == requestCode) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Log.e("TAG", " android有tts: " + resultCode);

            } else {
                //系统没有，網路下载
                Log.e("TAG", " android没有tts: "+resultCode );
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    private void speak() {
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        mTTS.setPitch(pitch);// 音調，值越大聲音越尖（女生），值越小則變成男聲，1.0是常規
        mTTS.setSpeechRate(speed); // 速度
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        //TextToSpeech.QUEUE_ADD 為目前的念完才念
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 不管是否正在朗读TTS都被打断
        mTTS.stop();
        // 关闭，释放资源
        mTTS.shutdown();
    }

    @Override
    protected void onDestroy() {
        //關閉TTS，回收資源
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}