package com.example.jongjinbyun.hemsmobileappandroid8;

import java.util.ArrayList;

public class MyGroup {
    //자식 리스트 생성을 위한 임시 클래스
    public ArrayList<String>child;
    public String groupName;
    MyGroup(String name)
    {
        groupName=name;
        child=new ArrayList<String>();
    }

}
