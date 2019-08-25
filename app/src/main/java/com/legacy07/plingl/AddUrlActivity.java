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
    EditText urlstr;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);

        submit = findViewById(R.id.submit);
        urlstr = findViewById(R.id.urltext);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.pling_layout);
        textView=findViewById(R.id.tvTitle);
        textView.setText("Give the DAMN URL");


        final SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();



            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!urlstr.getText().toString().trim().equals("")) {
                        SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear().apply();

                        editor.putBoolean("pling", true);
                        editor.putString("plingurl", urlstr.getText().toString().trim());
                        editor.apply();
                        gotoMain();
                    } else {
                        urlstr.setHint("PLEASE ENTER THE URL YOU IDIOT");
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
