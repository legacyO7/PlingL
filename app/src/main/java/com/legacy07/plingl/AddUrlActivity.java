package com.legacy07.plingl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddUrlActivity extends AppCompatActivity {

    Button submit;
    EditText urlstr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);

        submit=findViewById(R.id.submit);
        urlstr=findViewById(R.id.urltext);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (urlstr.getText().toString().trim() != null) {
                    SharedPreferences prefs = getSharedPreferences("plingpref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("pling", true);
                    editor.putString("plingurl", urlstr.getText().toString().trim());
                    editor.apply();

                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

                else
                {
                    urlstr.setHint("PLEASE ENTER THE URL");
                }

            }
        });
    }
}
