package com.legacy07.plingl;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String url = "https://www.pling.com/p/1320337/startdownload?file_id=1566641392&file_name=blog-page_24.html&file_type=text/html&file_size=287529";
    //final String xda ="https://canvasnitro2roms.blogspot.com/p/blog-page_24.html";

    WebView mWebview;
    //Button button;

   // View view;
    //String url ="https://www.google.com";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.wview);
        mWebview = new WebView(this);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript



        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "Some Error!" + description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Here put your code

                return false; //Allow WebView to load url
                //return true; //Indicates WebView to NOT load the url;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                if ("www.pling.com".equals(Uri.parse(url).getHost())) {
                    Toast.makeText(MainActivity.this, "Pling website", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "CN2R website", Toast.LENGTH_SHORT).show();
                    url = "https://www.pling.com/p/1320337/startdownload?file_id=1566641392&file_name=blog-page_24.html&file_type=text/html&file_size=287529";
                    view.loadUrl(url);
                }

                super.onPageFinished(view, url);
            }
        });

        mWebview.loadUrl(url);
        setContentView(mWebview);

        /*

        long time=SystemClock.uptimeMillis();

        while (SystemClock.uptimeMillis()!=time+100)
        {
            if (mWebview.getUrl().trim().equals(xda)){
                mWebview.loadUrl(url);
                break;
            }


        }
            Log.d("hi", mWebview.getUrl()+" is the url");





/*
        mWebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                activity.setTitle("i got clicked");
                return true;
            }
        });*/

       // mWebview.loadUrl("javascript:document.getElementsByClassName('btn btn-success btn-lg')[0].click();");

      //  mWebview.loadUrl("javascript:document.getElementsByClassName('btn btn-success btn-lg')[0].click()"‌​);


     // mWebview.setOnTouchListener(handleTouch);



  /*      // Obtain MotionEvent object
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 500;
        float x= (float) 556.48474,y= (float) 536.6646;

// List of meta states found here:     developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_MOVE,
                x,
                y,
                metaState
        );

        MotionEvent motionEvent2 = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                x,
                y,
                metaState
        );
// Dispatch touch event to viewm

        mWebview.dispatchTouchEvent(motionEvent);
        mWebview.dispatchTouchEvent(motionEvent2);*/
    }


/*
    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            float x = event.getX();
            float y = event.getY();


activity.setTitle("I got clicked");
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("TAG", "touched down");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("TAG", "touched up");
                    break;
            }

            return true;
        }
    };*/
}


