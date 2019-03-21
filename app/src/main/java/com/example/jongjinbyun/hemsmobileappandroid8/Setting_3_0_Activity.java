package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Setting_3_0_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_3_0_);

        //공지사항 스위치 이벤트 핸들러
        Switch switch1=(Switch)findViewById(R.id.switchNotice);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Toast.makeText(Setting_3_0_Activity.this,"공지사항 push On",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Setting_3_0_Activity.this,"공지사항 push Off",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //경고 알림 스위치 이벤트 핸들러

        Switch switch2=(Switch)findViewById(R.id.switchWarning);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Toast.makeText(Setting_3_0_Activity.this,"경고알림 push On",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Setting_3_0_Activity.this,"경고알림 push Off",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //장애 알림 스위치 이벤트 핸들러

        Switch switch3=(Switch)findViewById(R.id.switchFail);
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Toast.makeText(Setting_3_0_Activity.this,"장애알림 push On",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Setting_3_0_Activity.this,"장애알림 push Off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //홈버튼 이벤트 핸들러
        findViewById(R.id.ImgBtn_Home).setOnClickListener(
                new  Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        //앱 정보 버튼 이벤트 핸들러
        findViewById(R.id.btnInfo).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),Setting_3_1_Activity.class);
                        startActivity(intent);
                    }
                }
        );
        //앱 사용방법 버튼 이벤트 핸들러
        findViewById(R.id.btnHowToUse).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),Setting_3_2_Activity.class);
                        startActivity(intent);
                    }
                }
        );
        //오픈소스 라이센스 버튼 이벤트 핸들러
        findViewById(R.id.btnLicense).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),Setting_3_3_Activity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
