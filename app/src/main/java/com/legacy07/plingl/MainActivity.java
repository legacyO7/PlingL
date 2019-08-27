package com.legacy07.plingl;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    final Activity activity = this;
    int i = 0, count = 0, sec = 0, min = 0, hr = 0, j = 1;
    String myurl1 = "", myurl2 = "", myurl3 = "", myurl4 = "", myurl5 = "", myurl = "", display = "1.1 Pling L";
    String appPackageName = "com.autoclicker.clicker";
    WebView mWebview;
    TextView textView, meter;
    PackageManager pm;

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alert.setTitle("Select an action")
                .setMessage("കുറച്ച് കഞ്ഞിയെടുക്കട്ടെ, മാണിക്യാ?")
                .setNegativeButton("Change Pling", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        myurl1 = myurl2 = myurl3 = myurl4 = myurl5 = "";
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

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);

        mWebview = findViewById(R.id.wview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            activity.setImmersive(true);
        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.pling_layout);


        builder.setIcon(R.drawable.plogo)
                .setTitle("Disclaimer")
                .setMessage("This app is meant for educational purpose only. Developers wont be responsible for any unethical action done using this app. Proceed under yourown will.\n\nActual results might vary from the readings. Sometimes you wont get the half of it! ")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                        if (launchIntent != null) {
                            if (isAppRunning(getApplicationContext(), appPackageName)) {
                                startActivity(launchIntent);
                                Toast.makeText(getApplicationContext(), "Opening autoclicker", Toast.LENGTH_LONG).show();
                            }
                        } else
                            Toast.makeText(getApplicationContext(), "Please open the autoclicker to automate", Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create().show();

        textView = findViewById(R.id.tvTitle);
        meter = findViewById(R.id.meter);
        textView.setText(display);

        mWebview = new WebView(this);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
        if (!prefs.getBoolean("pling", false)) {

            Intent intent = new Intent(getApplicationContext(), AddUrlActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
            myurl1 = prefs.getString("plingurl1", "");
            myurl2 = prefs.getString("plingurl2", "");
            myurl3 = prefs.getString("plingurl3", "");
            myurl4 = prefs.getString("plingurl4", "");
            myurl5 = prefs.getString("plingurl5", "");

        }

        webviewdisplay(mWebview);

        pm = getApplicationContext().getPackageManager();

        if (!isPackageInstalled(appPackageName, pm)) {
            builder.setMessage("AutoClicker isn't installed")
                    .setNeutralButton("Install", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }


                        }
                    })
                    .setIcon(R.drawable.dodo)
                    .setNegativeButton("It's Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();

        }

        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sec = count;
                        meter.setText("Clicks : " + i / 4 + "    Amount : Rs " + Math.round((i * 72) / 4) / 100 + "/-    RunTime=" + hr + ":" + min + ":" + sec);
                        count++;
                        if (count > 59) {
                            min++;
                            count = 0;
                            sec = 0;
                            if (min > 59) {
                                min = 0;
                                hr++;
                            }


                        }
                    }
                });
            }
        }, 1000, 1000);



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

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {

        boolean found = true;

        try {

            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }

    public void webviewdisplay(WebView webView) {

        webView.setWebViewClient(new WebViewClient() {

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
                } else {
                    //Toast.makeText(MainActivity.this, "Tail", Toast.LENGTH_SHORT).show();
                    textView.setText(display + "  |  URL < " + j + " >");
                    if (j == 1) {
                        url = myurl1;
                        j = 2;
                    } else if (j == 2) {
                        url = myurl2;
                        j = 3;
                    } else if (j == 3) {
                        url = myurl3;
                        j = 4;
                    } else if (j == 4) {
                        url = myurl4;
                        j = 5;
                    } else if (j == 5) {
                        url = myurl5;
                        j = 1;
                    }

                    myurl = url;


                    view.loadUrl(url);
                    i++;


                }

                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(myurl);
        setContentView(webView);

    }

}


