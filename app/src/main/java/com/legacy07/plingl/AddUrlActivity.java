package com.legacy07.plingl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUrlActivity extends AppCompatActivity {

    Button submit;
    EditText urlstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);

        submit = findViewById(R.id.submit);
        urlstr = findViewById(R.id.urltext);

        final SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        if (prefs.getBoolean("pling", false)){
            Toast.makeText(AddUrlActivity.this, "Using previously saved Pling URL", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                if (!urlstr.getText().toString().trim().equals("")) {
                    SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
           
                        editor.putBoolean("pling", true);
                        editor.putString("plingurl", urlstr.getText().toString().trim());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                else
                {
                    urlstr.setHint("PLEASE ENTER THE URL YOU IDIOT");
                }
            });
        }
    }

    //Code for Back Press again to exit app
    private Toast toast;
    private long lastBackPressTime = 0;
    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 2000) {
            toast = Toast.makeText(this, "Press back again to close this app", Toast.LENGTH_LONG);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }
    }
}
