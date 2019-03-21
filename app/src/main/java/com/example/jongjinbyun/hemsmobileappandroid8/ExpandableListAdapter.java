package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    //2중 배열
    private ArrayList<MyGroup> groupData;
    //레이아웃 붙이기 위한 인플레이터
    private LayoutInflater inflater=null;

    //생성자
    public ExpandableListAdapter(Context context, ArrayList<MyGroup> groupData) {
        this.context = context;
        this.groupData = groupData;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    //부모 리스트 개수
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    //자식 리스트 개수
    public int getChildrenCount(int groupPosition) {
        //부모 해당 포지션의 자식의 사이즈
        return groupData.get(groupPosition).child.size();
    }

    @Override
    //해당 부모 리스트에서의 값
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    //자식 리스트에서의 값
    public Object getChild(int groupPosition, int childPosition) {
        return groupData.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    //부모 리스트 보여주기(실제 붙이는 매소드)
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            //view 붙이기
            convertView=inflater.inflate(R.layout.list_group,null);
        }
        //부모 리스트의 텍스트 가져오기
        TextView textViewGroup=(TextView)convertView.findViewById(R.id.textGroup);
        //부모 리스트의 이미지 가져오기
        ImageView imgViewGroup=(ImageView)convertView.findViewById(R.id.imgGroup);
        textViewGroup.setText(groupData.get(groupPosition).groupName);
        textViewGroup.setTypeface(null,Typeface.BOLD);
        //그룹 펼칠 때, 닫을 때 아이콘 변경
        if(isExpanded)
        {
            //펼칠 때
            imgViewGroup.setImageResource(R.drawable.hems_down_navigation_icon);
        }
        else
        {
            //펼칠 때
            imgViewGroup.setImageResource(R.drawable.hems_right_navigation_icon);

        }
        return convertView;
    }

    @Override
    //자식 리스트 보여주기
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //자식 포지션 가져오기
        String childName=(String)getChild(groupPosition, childPosition);
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_child,null);
        }
        TextView textViewChild=(TextView)convertView.findViewById(R.id.textChild);
        ImageView imgViewChild=(ImageView)convertView.findViewById(R.id.imgChild);
        textViewChild.setText(childName);

        //텍스트에 따라 이미지 바꿔주기
        if(/*childName=="설치 주소"*/childPosition==0)
        {
            imgViewChild.setImageResource(R.drawable.hems_location_icon);
        }
        else if(/*childName=="IP 주소"*/childPosition==1)
        {
            imgViewChild.setImageResource(R.drawable.hems_ip_icon);
        }
        else if(/*childName=="포트 번호"*/childPosition==2)
        {
            imgViewChild.setImageResource(R.drawable.hems_port_icon);
        }
        else if(/*childName=="시리얼번호"*/childPosition==3)
        {
            imgViewChild.setImageResource(R.drawable.hems_serial_key_icon);
        }
        else if(/*childName=="기타"*/childPosition==4)
        {
            imgViewChild.setImageResource(R.drawable.hems_etc_icon);
        }
        else if(/*childName=="자세히"*/childPosition==5)
        {
            imgViewChild.setImageResource(R.drawable.hems_monitoring_detail_icon);
            //다른 자식들에게 이미지가 나타나는 버그 발견->해결 후 주석처리 삭제
            //ImageView tempImage=(ImageView)convertView.findViewById(R.id.imgChildPlus);
            //tempImage.setImageResource(R.drawable.hems_plus_icon);
        }
        else{
            //nothing
        }
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

