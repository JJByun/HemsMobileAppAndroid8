package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Setting_3_1_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_3_1_);

        findViewById(R.id.ImgBtn_Home_setting_3_1).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        findViewById(R.id.imgBtnRefresh).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Toast.makeText ( getApplicationContext (),"최신 버전입니다",Toast.LENGTH_SHORT ).show ();
                    }
                }
        );
    }
}
