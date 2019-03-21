package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class ConfigureData extends AppCompatActivity {

    public static Context mContext;
    public ConfigureData()
    {
        mContext=this;
        //constructor
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
