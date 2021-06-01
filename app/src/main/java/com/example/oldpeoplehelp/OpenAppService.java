package com.example.oldpeoplehelp;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OpenAppService extends Service {
    private Intent intentRecognizer;
    private SpeechRecognizer speechRecognizer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.startListening(intentRecognizer);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                //speechRecognizer.stopListening();
            }

            @Override
            public void onError(int i) {

            }

            private Boolean isActivated = false;
            private String activationKeyword = "gold";

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String string = "";
                if(matches != null){
                    string = matches.get(0);
                    if(string.contains(activationKeyword)){
                        Log.e("SERIVCEEEEEEEEEEEE", "YESSSSS");
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.oldpeoplehelp");
                        if (launchIntent != null) {
                            Log.e("SERIVCEEEEEEEEEEEE", "PACKAGE FOUND");
                            //startActivity(launchIntent);
                            Intent intent = new Intent(getApplicationContext(), MenuNavigationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent);
                        } else {
                        }

                    }

                }
                else{

                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        speechRecognizer.stopListening();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
