package com.example.jongjinbyun.hemsmobileappandroid8;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Enroll_1_2_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_1_2_);

        //변수 할당
        final TextView textHemsID=findViewById ( R.id.textHemsID_enroll_1_2 );
        final TextView textIP=findViewById ( R.id.textIP_enroll_1_2 );
        final TextView textPort=findViewById ( R.id.textPort_enroll_1_2 );
        final TextView textSerial=findViewById ( R.id.textSerialNo_enroll_1_2 );
        final TextView textAddress=findViewById ( R.id.textAddress_enroll_1_2 );

        try
        {
            //값 받기
            Intent intent=getIntent ();
            textHemsID.setText ( intent.getStringExtra ( "HemsID" ) );
            textIP.setText ( intent.getStringExtra ( "IPAddress" ) );
            textPort.setText ( intent.getStringExtra ( "port" ) );
            textSerial.setText ( intent.getStringExtra ( "serialNo" ) );
            String metro=intent.getStringExtra ( "metro" );
            String city=intent.getStringExtra ( "city" );
            String address=metro+" " +city;
            textAddress.setText ( address );

        }catch(Exception ex)
        {
            Toast.makeText ( this , ex.getMessage () , Toast.LENGTH_SHORT ).show ( );
        }


        //인증 요청 버튼 이벤트 핸들러
        findViewById(R.id.btnEnrollToHems).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        //뒤의 화면 흐리게 하기
                        WindowManager.LayoutParams lpWindow=new WindowManager.LayoutParams();
                        lpWindow.flags=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        //70% 적용
                        lpWindow.dimAmount=0.7f;

                        //스플래시 이미지 보여주기
                        initLoadingView();
                        //3초 로딩 대기 후 빠져나오기 위한 핸들러 생성
                        Handler handler=new Handler();
                        //4초간 보여준다
                        Runnable runnable=new Runnable() {
                            @Override
                            public void run() {

                                //애니메이션 다시 없애기
                                stopLoadingView();

                                //다이얼로그 생성
                                Dialog dialog=new Dialog(Enroll_1_2_Activity.this);
                                //커스터마이징 화면 붙이기
                                dialog.setContentView(R.layout.layout_alter_view_ok);
                                //다이얼로그 뒷 배경은 선택 불가
                                dialog.setCancelable(false);
                                if(webSocket())
                                {
                                    //등록된 햄스 숫자 가져오기
                                    int HemsCount=getPreferenceInt ( publicData.HEMSCOUNT );

                                    //HEMS 추가 시켰으니 +1
                                    HemsCount+=1;
                                    //HEMS 아이디 저장(primeKey로 사용)
                                    setPreference ( "HEMS"+ String.valueOf ( HemsCount ),textHemsID.getText ().toString ());
                                    //증가시킨 키 카운트 등록
                                    setPreference ( publicData.HEMSCOUNT,HemsCount );
                                    setPreference ( textHemsID.getText ().toString ()+"_IP",textIP.getText ().toString () );
                                    setPreference ( textHemsID.getText ().toString ()+"_port",textPort.getText ().toString () );
                                    setPreference ( textHemsID.getText ().toString ()+"_serial",textSerial.getText ().toString () );
                                    setPreference ( textHemsID.getText ().toString ()+"_address",textAddress.getText ().toString () );
                                    /*
                                    //인텐트 보내기
                                    Intent intent = new Intent(getApplicationContext(),Monitoring_2_1_Activity.class);
                                    //intent로 모니터링 화면에 값 전달
                                    intent.putExtra ( "HemsID",textHemsID.getText ().toString () );
                                    intent.putExtra ( "IPAddress",textIP.getText ().toString () );
                                    intent.putExtra ( "port",textPort.getText ().toString () );
                                    intent.putExtra ( "serialNo",textSerial.getText ().toString () );
                                    intent.putExtra ( "address",textAddress.getText ().toString ());
                                    startActivity ( intent );*/

                                    //텍스트 출력
                                    try
                                    {
                                        TextView text= (TextView)dialog.findViewById(R.id.textPopup);
                                        text.setText("인증 성공"); //여기를 바꿔서 실패일 때 수정
                                    }
                                    catch(Exception ex)
                                    {
                                        ex.getStackTrace();
                                    }

                                    //이미지 붙이기
                                    try
                                    {
                                        ImageView image=(ImageView)dialog.findViewById(R.id.ImgPopup);
                                        image.setImageResource(R.drawable.hems_enroll_enroll_ok); //여기를 바꿔서 실패일 떄 수정
                                    }
                                    catch(Exception ex)
                                    {
                                        ex.getMessage();
                                    }

                                    //버튼 이벤트 핸들러
                                    try
                                    {
                                        Button button=(Button)dialog.findViewById(R.id.btnPopup);
                                        button.setOnClickListener(
                                                new Button.OnClickListener(){
                                                    public void onClick(View v){
                                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                        startActivity(intent); //여기를 바꿔 실패일 때 수정
                                                    }
                                                }
                                        );
                                    }
                                    catch(Exception ex)
                                    {
                                        ex.getMessage();
                                    }
                                }
                                else
                                {
                                    //텍스트 출력
                                    try
                                    {
                                        TextView text= (TextView)dialog.findViewById(R.id.textPopup);
                                        text.setText("인증 실패"); //여기를 바꿔서 실패일 때 수정
                                    }
                                    catch(Exception ex)
                                    {
                                        ex.getStackTrace();
                                    }

                                    //이미지 붙이기
                                    try
                                    {
                                        ImageView image=(ImageView)dialog.findViewById(R.id.ImgPopup);
                                        image.setImageResource(R.drawable.hems_enroll_enroll_fail); //여기를 바꿔서 실패일 떄 수정
                                    }
                                    catch(Exception ex)
                                    {
                                        ex.getMessage();
                                    }

                                    //버튼 이벤트 핸들러
                                    try
                                    {
                                        Button button=(Button)dialog.findViewById(R.id.btnPopup);
                                        button.setOnClickListener(
                                                new Button.OnClickListener(){
                                                    public void onClick(View v){
                                                        Intent intent=new Intent(getApplicationContext(),Enroll_1_1_Activity.class);
                                                        startActivity(intent); //여기를 바꿔 실패일 때 수정
                                                    }
                                                }
                                        );
                                    }
                                    catch(Exception ex)
                                    {
                                        ex.getMessage();
                                    }
                                }

                                //제목 붙이기
                                dialog.setTitle("결과");
                                dialog.create();

                                //실제 띄우는 함수
                                //없으면 화면에 출력 x
                                dialog.show();


                            }
                        };
                        //4초간 로딩 이미지 보여주기
                        handler.postDelayed(runnable,4000);

                        /*
                        //AlterDialog 일 때만 사용 가능
                        //확인 버튼 눌렀을 때 이벤트 -> enroll1_1로 이동
                        dialog.button("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent intent=new Intent(getApplicationContext(),Enroll_1_1_Activity.class);
                                startActivity(intent);
                            }
                        });*/

                    }

        }
        );
    }

    //전송 로딩 이미지 애니메이션
    private void initLoadingView()
    {
        ImageView img= (ImageView)findViewById(R.id.imgSendLoading);
        img.setVisibility(View.VISIBLE);
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.loading);
        img.setAnimation(animation);
        ConstraintLayout constraintLayout=(ConstraintLayout)findViewById(R.id.constraintLayout);
        //버튼 비활성화
        Button btn=(Button)findViewById(R.id.btnEnrollToHems);
        btn.setClickable(false);

    }
    //전송 로딩 이미지 숨기기
    private void stopLoadingView()
    {
        ImageView img=(ImageView)findViewById(R.id.imgSendLoading);
        img.setVisibility(View.INVISIBLE);
        //애니메이션을 제거해야 안 보이기 시작한다
        img.clearAnimation();
    }
    //웹소켓을 통해 인증 절차
    public boolean webSocket()
    {
        /*
        if(true//성공)
        {
            return true;
        }
        else if(false//실패)
        {
            return false;
        }
        */

        //HEMS 등록 숫자 카운트
        int count=publicData.getData ();
        count+=1;
        publicData.setData ( count );

        return true;
    }

    // 데이터 저장 함수
    //bool type
    public void setPreference(String key, boolean value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    //string type
    public void setPreference(String key, String value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    //int type
    public void setPreference(String key, int value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    //float type
    public void setPreference(String key, float value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    //long type
    public void setPreference(String key, long value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    // 데이터 불러오기 함수
    public boolean getPreferenceBoolean(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }
    public String getPreferenceString(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getString(key, "");
    }
    public int getPreferenceInt(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getInt(key, 0);
    }
    public float getPreferenceFloat(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getFloat(key, 0f);
    }
    public long getPreferenceLong(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getLong(key, 0l);
    }

    // 데이터 한개씩 삭제하는 함수
    public void setPreferenceRemove(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
    // 모든 데이터 삭제
    public void setPreferenceClear(){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
