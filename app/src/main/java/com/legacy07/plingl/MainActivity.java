package com.legacy07.plingl;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String xda = "https://canvasnitro2roms.blogspot.com/p/blog-page_24.html";
    final Activity activity = this;
    String url = "https://www.pling.com/p/1320337/startdownload?file_id=1566641392&file_name=blog-page_24.html&file_type=text/html&file_size=287529";
    Fragment fragment = null;
    FragmentTransaction ft;
    int i = 0;

    WebView mWebview;

    FrameLayout frameLayout;
    Button changeurl;



    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        i++;

        if (i == 3) {
            i = 0;
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Select an Action")
                    .setMessage("കുറച്ച് കഞ്ഞിയെടുക്കട്ടെ, മാണിക്യാ?")
                    .setPositiveButton("Change Pling", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            prefs.edit().clear().apply();
                            Intent intent = new Intent(getApplicationContext(), AddUrlActivity.class);
                            startActivity(intent);

                            url = prefs.getString("plingurl", "");
                            changeurl.setText(url);


                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .create().show();



        }


    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.wview);
        mWebview = new WebView(this);
        changeurl = findViewById(R.id.changeurl);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
        if (!prefs.getBoolean("pling", false)) {

            Intent intent = new Intent(getApplicationContext(), AddUrlActivity.class);
            startActivity(intent);
            //  Toast.makeText(context, " alarm service started successfully ", Toast.LENGTH_LONG).show();

        } else {
            url = prefs.getString("plingurl", "");
        }


        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "pling!", Toast.LENGTH_SHORT).show();
                //   activity.setTitle(description);

            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Here put your code
                Log.d("My Webview", url);

                // return true; //Indicates WebView to NOT load the url;
                return false; //Allow WebView to load url
            }

        });

        mWebview.loadUrl(url);
        setContentView(mWebview);






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


