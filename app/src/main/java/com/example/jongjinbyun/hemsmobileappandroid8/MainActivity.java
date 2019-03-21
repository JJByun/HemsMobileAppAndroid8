package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //변수 가져오기
        ImageView imgMonitoring=(ImageView) findViewById ( R.id.ImgBtn_Monitoring);

        //인증 버튼
        findViewById(R.id.ImgBtn_Enroll).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),Enroll_1_1_Activity.class);
                        startActivity(intent);
                    }
                }
        );
        //모니터링 버튼
        findViewById(R.id.ImgBtn_Monitoring).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),Monitoring_2_1_Activity.class);
                        startActivity(intent);
                    }
                }
        );
        //장비 설정 버튼
        findViewById(R.id.ImgBtn_Setting).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),Setting_3_0_Activity.class);
                        startActivity(intent);
                    }
                }
        );
        //모니터링 버튼 활성화, 비활성화

        if(getPreferenceInt ( publicData.HEMSCOUNT )==0)
        {
            //인증 전, 비활성화
            imgMonitoring.setImageResource ( R.drawable.hems_main_monitoring_unclickable_icon );
            imgMonitoring.setClickable ( false );
        }
        else
        {
            //인증 후, 활성화
            imgMonitoring.setImageResource ( R.drawable.hems_main_monitoring_icon );
            imgMonitoring.setClickable ( true );
        }

    }

    // 모든 데이터 삭제
    public void setPreferenceClear(){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
    //값 가져오기
    public int getPreferenceInt(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    @Override
    //메인 화면에서 뒤로가기 버튼 막기
    public void onBackPressed ( ) {
        //super.onBackPressed ( );
    }
}
