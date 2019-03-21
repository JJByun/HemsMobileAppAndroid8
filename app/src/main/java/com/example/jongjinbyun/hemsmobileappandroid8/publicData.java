package com.example.jongjinbyun.hemsmobileappandroid8;

import android.app.Application;

import java.util.ArrayList;

//전역 변수를 담는 클래스
//모든 액티비티는 Application 클래스를 상속
public class publicData extends Application {
    public static int monitoringCount;
    public static ArrayList<String> HemsID=new ArrayList<String> ( 100 );
    //prefernce를 사용하기위한 명칭
    public static String PREFERENCE="HemsInfoPreference";
    //등록 HEMS 개수
    public static String HEMSCOUNT="HemsCount";

    //HEMS 등록 개수
    public static int getData(){
        return monitoringCount;
    }
    public static void setData(int i)
    {
        monitoringCount=i;
    }

    //HEMS ID 넣기
    //HEMS 등록한 총 개수 가져오기
    public static int getHemsCount(){return HemsID.size ();}
    //해당 index의 HEMS 아이디 가져오기
    public static String getHemsID(int i){return HemsID.get (i );}
    //HEMS ID 넣기
    public static void setHemsID(String hemsID){HemsID.add ( hemsID );}


}
