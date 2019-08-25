package com.legacy07.plingl;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //String url = "https://www.canvasnitro2roms.blogspot.com/p/287529";
    //final String xda ="https://canvasnitro2roms.blogspot.com/p/blog-page_24.html";

    final Activity activity = this;

    String myurl="";
    WebView mWebview;
    TextView textView;

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Select an action")
                .setMessage("\nകുറച്ച് കഞ്ഞിയെടുക്കട്ടെ, മാണിക്യാ?")
                .setNegativeButton("Change Pling", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        myurl="";
                        Intent intent = new Intent(getApplicationContext(), AddUrlActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
          
                    }
                })
                .setPositiveButton("Exit App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create().show();
    }
    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.wview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            activity.setImmersive(true);
        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.pling_layout);

        textView=findViewById(R.id.tvTitle);
        textView.setText("1.0 Pling L");

        mWebview = new WebView(this);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
        if (!prefs.getBoolean("pling", false)) {

            Intent intent = new Intent(getApplicationContext(), AddUrlActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
            myurl = prefs.getString("plingurl", "");
        }

        mWebview.setWebViewClient(new WebViewClient() {

            // BELOW 2 FUNCTIONS FOR DETECTING ERROR DURING PAGE LOAD
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
               //Toast.makeText(getApplicationContext(), "Some loading Error! " + description, Toast.LENGTH_LONG).show();
               //activity.setTitle(description);

            }

            // Didn't think it was needed, so had removed this onRecievedError earlier. Even the above method, if there is nothing in it then no use...
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
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
                    //Toast.makeText(MainActivity.this, "Pling", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(MainActivity.this, "Tail", Toast.LENGTH_SHORT).show();
                    url = myurl;
                    view.loadUrl(url);
                }

                super.onPageFinished(view, url);
            }
        });

        mWebview.loadUrl(myurl);
        setContentView(mWebview);



/*
        mWebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                activity.setTitle("i got clicked");
                return true;
            }
        });
        */

        //mWebview.loadUrl("javascript:document.getElementsByClassName('btn btn-success btn-lg')[0].click();");

        //mWebview.loadUrl("javascript:document.getElementsByClassName('btn btn-success btn-lg')[0].click()"‌​);



        //mWebview.setOnTouchListener(handleTouch);


        /*
        // Obtain MotionEvent object
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 500;
        float x= (float) 540, y= (float) 858;
        // List of meta states found here:developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
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
        mWebview.dispatchTouchEvent(motionEvent2);

        */

    }

    /*
    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            activity.setTitle("I got clicked");
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //Log.i("TAG", "touched down");
                    Toast.makeText(MainActivity.this, "touched down", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    Toast.makeText(MainActivity.this, "moving: (" + x + ", " + y + ")", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_UP:
                    //Log.i("TAG", "touched up");
                    Toast.makeText(MainActivity.this, "touched up", Toast.LENGTH_SHORT).show();
                    break;
            }

            return true;
        }
    };
    */
}


