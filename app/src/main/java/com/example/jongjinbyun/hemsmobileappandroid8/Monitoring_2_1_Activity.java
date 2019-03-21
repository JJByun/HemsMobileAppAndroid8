package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;

public class Monitoring_2_1_Activity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<MyGroup>DataList=new ArrayList<MyGroup>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_2_1_);

        //홈버튼 이벤트 핸들러
        findViewById(R.id.ImgBtn_monitoring_2_1).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent=new Intent(getApplicationContext (),MainActivity.class);
                startActivity ( intent );
            }
        } );


        //리스트 가져오기
        listView=(ExpandableListView)findViewById(R.id.exListView);
        //데이터 넣기
        initData();
        //어댑터에 데이터 붙이기
        listAdapter=new ExpandableListAdapter(getApplicationContext(),DataList);
        //어뎁터에 리스트뷰 붙이기
        listView.setAdapter(listAdapter);

        //처음 생성시 모든 그룹 닫기
        int groupCount=(int)listAdapter.getGroupCount();
        for(int i=0; i<groupCount;i++)
        {
            listView.collapseGroup(i);
        }

        //그룹이 닫힐 경우 이벤트
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ImageView imgView=(ImageView)findViewById(R.id.imgGroup);
                imgView.setImageResource(R.drawable.hems_right_navigation_icon);
            }
        });

        //그룹이 열릴 경우 이벤트
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ImageView imgView=(ImageView)findViewById(R.id.imgGroup);
                imgView.setImageResource(R.drawable.hems_down_navigation_icon);
            }
        });

        //자세히보기 아이콘 클릭 이벤트
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //자세히보기 눌렀을때 이벤트
                if(childPosition==5)
                {
                    //여기 눌렀을 때 monitoring 2_2로 화면 전환
                    Intent intent=new Intent(getApplicationContext (),Monitoring_2_2_Activity.class);
                    startActivity ( intent );
                }/*
                else if(childPosition==4)
                {
                //삭제 기능 구현?
                    String strHems=getPreferenceString ( "HEMS"+String.valueOf ( groupPosition ) );
                    setPreferenceRemove ( strHems );
                    setPreferenceRemove (  );
                }*/
                return false;
            }
        });
    }
     //데이터를 2차원 배열에 넣는 함수
    public void initData()
    {
        //ListView에 데이터 넣기

        int hemsCount=0;
        hemsCount=getPreferenceInt ( publicData.HEMSCOUNT );
        MyGroup temp;
        for(int i=1;i<=hemsCount;i++){
            //공유 데이터에서 HEMS ID 가져오기
            String strHems=getPreferenceString ( "HEMS"+String.valueOf ( i ) );
            //HEMS ID로 부모 리스트뷰 만들기
            temp=new MyGroup ( strHems );
            //주소 ->자식 리스트 추가
            temp.child.add ( getPreferenceString ( strHems+"_address" ) );
            //IP 주소->자식 리스트 추가
            temp.child.add ( getPreferenceString ( strHems+"_IP" ) );
            //port ->자식 리스트 추가
            temp.child.add ( getPreferenceString ( strHems+"_port" ) );
            //serialNo->자식 리스트 추가
            temp.child.add ( getPreferenceString ( strHems+"_serial" ) );
            //기타 (추후 추가되는 데이터)
            temp.child.add ( "삭제" );
            //자세히보기
            temp.child.add ( "자세히" );
            DataList.add ( temp );
        }

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
