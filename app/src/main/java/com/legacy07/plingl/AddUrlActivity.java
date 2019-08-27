package com.legacy07.plingl;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddUrlActivity extends AppCompatActivity {

    Button submit;
    EditText urlstr1,urlstr2,urlstr3,urlstr4,urlstr5;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);

        submit = findViewById(R.id.submit);
        urlstr1 = findViewById(R.id.urltext1);
        urlstr2 = findViewById(R.id.urltext2);
        urlstr3 = findViewById(R.id.urltext3);
        urlstr4 = findViewById(R.id.urltext4);
        urlstr5 = findViewById(R.id.urltext5);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.pling_layout);
        textView=findViewById(R.id.tvTitle);
        textView.setText("Give the DAMN URL");


        final SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();



            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   String u1= urlstr1.getText().toString().trim();
                    String u2= urlstr2.getText().toString().trim();
                    String u3= urlstr3.getText().toString().trim();
                    String u4= urlstr4.getText().toString().trim();
                    String u5= urlstr5.getText().toString().trim();
                    if (!urlstr1.getText().toString().trim().equals("")&&!urlstr2.getText().toString().trim().equals("")&&!urlstr3.getText().toString().trim().equals("")&&!urlstr4.getText().toString().trim().equals("")&&!urlstr5.getText().toString().trim().equals("")) {
                        if (u1.startsWith("https://www.pling.")&&u2.startsWith("https://www.pling.")&&u3.startsWith("https://www.pling.")&&u4.startsWith("https://www.pling.")&&u5.startsWith("https://www.pling.")) {
                            SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.clear().apply();

                            editor.putBoolean("pling", true);
                            editor.putString("plingurl1", u1);
                            editor.putString("plingurl2", u2);
                            editor.putString("plingurl3", u3);
                            editor.putString("plingurl4", u4);
                            editor.putString("plingurl5", u5);
                            editor.apply();
                            gotoMain();
                        }
                        else{
                            submit.setText("Pling urls ONLY\nLoot it");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"PLEASE ENTER THE URL YOU IDIOT", Toast.LENGTH_LONG).show();
                    }
                }
            });



    }

    void gotoMain(){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        gotoMain();
        /*if (this.lastBackPressTime < System.currentTimeMillis() - 2000) {
            toast = Toast.makeText(this, "Press back again to close this app", Toast.LENGTH_LONG);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }*/
    }
}
