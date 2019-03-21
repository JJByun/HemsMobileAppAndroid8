package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Monitoring_2_2_Activity extends AppCompatActivity {

    //line chart Object
    private LineChart lineChart;
    //보여줄 데이터 개수
    private final float X_RANGE_MAX=10f;
    private final float X_RANGE_MIN=1f;
    //발전량 최소 값
    private final float MIN_DATA=0f;
    //발전량 최대 값
    private final float MAX_DATA=60f;
    //lineChart에 넣을 데이터 배열
    List<Entry> entries=new ArrayList<> ( );
    //x축 라벨
    List<String>labels=new ArrayList<> (  );
    //리얼타임 출력 스레드
    private Thread thread;
    //SMP 변수
    private Document doc;
    private Elements content;
    private final String strPageUrl="https://www.kpx.or.kr/";
    private String strSMP="";

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_monitoring_2_2_ );

        //HOmE 버튼
        findViewById(R.id.ImgBtn_Home_monitoring_2_2).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent=new Intent(getApplicationContext (),MainActivity.class);
                startActivity ( intent );
            }
        } );

        //수익 비교 클릭 이벤트 핸들러
        findViewById(R.id.btnProfitCompare).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent=new Intent(getApplicationContext (),Monitoring_2_4_Activity.class);
                startActivity ( intent );
                finish ();
            }
        } );
        //수익 비교 클릭 이벤트 핸들러
        findViewById ( R.id. textProfitCompare).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent=new Intent(getApplicationContext (),Monitoring_2_4_Activity.class);
                startActivity ( intent );
                finish ();
            }
        } );
        //상세보기 버튼 클릭 이벤트 핸들러
        findViewById(R.id.btnSeeDetail).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent=new Intent(getApplicationContext (),Monitoring_2_3_Activity.class);
                startActivity ( intent );
                finish ();
            }
        } );
        //상세보기 텍스트 클릭 이벤트 핸들러
        findViewById(R.id.textSeeDetail).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent=new Intent(getApplicationContext (),Monitoring_2_3_Activity.class);
                startActivity ( intent );
                finish ();
            }
        } );

        //이번달로 '월' 설정해주기
        TextView textKwTag=(TextView)findViewById ( R.id.textKwTag );
        TextView textProfitsTag=(TextView)findViewById ( R.id.textProfitsTag );
        long nowTime= System.currentTimeMillis ( );
        Date date=new Date(nowTime);
        SimpleDateFormat sdf=new SimpleDateFormat ( "MM" );
        String strMonth=sdf.format ( date );
        textKwTag.setText ( strMonth+"월 누적발전량: " );
        textProfitsTag.setText ( strMonth+"월 예상 수익: " );

        //smp 값 비동기 불러오기
        final TextView txtSmp=(TextView )findViewById ( R.id.textSMP );
        //실제 백그라운드 실행
        new AsyncTask (){

            protected void onPostExecute(Object o){
                txtSmp.setText ("SMP: "+ strSMP +"원");
            }

            @Override
            protected Object doInBackground ( Object[] objects ) {
                try{
                    doc=Jsoup.connect ( strPageUrl ).get ();
                    //smp 해더 가져오기
                    content=doc.select ( "#m_container #smp_01" );
                }catch(Exception e){
                    e.printStackTrace ();
                }
                for( Element element:content){
                    Node value=element.childNode ( 3 ).childNode ( 5 ).childNode ( 7 ).childNode ( 3 ).childNode ( 0 );
                    strSMP=value.toString ();
                }
                return null;
            }

            protected void onPreExecute(){
                super.onPreExecute ();
            }
        }.execute (  );

        //그래프 그리기
        initChart ();
        doRealTimeThread ();
    }

    //차트 초기화
    private void initChart()
    {
        //라인 차트 가져오기
        lineChart=findViewById ( R.id.lineChart );
        //enable value hilighting
        lineChart.setDefaultFocusHighlightEnabled(true);
        //touch enable
        lineChart.setTouchEnabled(true);

        //enable scaling and dragging
        //드래그
        lineChart.setDragEnabled(false);
        //확대기능
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);

        //enable pinch Zoom to avoid scaling x and y axis separately
        lineChart.setPinchZoom(true);

        //alternative background color
        lineChart.setBackgroundColor(Color.TRANSPARENT);

        LineData data=new LineData ( );
        data.setValueTextColor ( Color.BLACK );
        lineChart.setData ( data );

        //get legend object
        Legend legend =lineChart.getLegend();

        //custom legend
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.BLACK);

        //x축 세팅
        XAxis x=lineChart.getXAxis();
        x.setTextColor(Color.BLACK);

        //배경에 격자무늬 넣기
        x.setDrawGridLines(false);
        x.setAvoidFirstLastClipping(true);
        x.setEnabled ( true );
        x.setPosition (XAxisPosition.BOTTOM );

        //왼쪽 y축 보이게 하기
        YAxis y= lineChart.getAxisLeft();
        y.setTextColor(Color.BLACK);
        y.setAxisMaximum(MAX_DATA);
        y.setAxisMinimum(MIN_DATA);

        //배경에 격자무늬 넣기
        y.setDrawGridLines(true);

        //오른쪽 y축 안보이게 하기
        YAxis y2=lineChart.getAxisRight();
        y2.setEnabled(false);

        //설명 넣기(안 넣음)
        lineChart.getDescription().setEnabled(false);

        //x축 최대, 최소 보여주는 갯수 세팅
        lineChart.setVisibleXRange ( MIN_DATA,MAX_DATA );
    }

    //리얼 타임 출력 스레드
    private void doRealTimeThread(){
        if(thread!=null){
            thread.interrupt ();
        }
        final Runnable runnable=new Runnable ( ) {
            @Override
            public void run ( ) {
                addEntry();
            }
        };
        thread =new Thread ( new Runnable ( ) {
            @Override
            public void run ( ) {
                for(int i=0 ;i<1000;i++){
                    runOnUiThread ( runnable );
                    try{
                        Thread.sleep ( 1000 );
                    }catch(InterruptedException ex){
                        ex.printStackTrace ();
                    }
                }
            }
        } );
        thread.start ();
    }

    //스레드로 값 추가시키기
    private void addEntry(){
        LineData data=lineChart.getData ();
        if(data !=null){
            ILineDataSet set=data.getDataSetByIndex ( 0 );
            //set.addEntry(...) // can be called as well

            if(set == null){
                set=createSet();
                data.addDataSet ( set );
            }

            data.addEntry ( new Entry ( set.getEntryCount (),(float)(Math.random ()*60)+1f ),0 );
            //데이터 바뀌었음을 알리기
            data.notifyDataChanged ();
            lineChart.notifyDataSetChanged ();
            lineChart.setVisibleXRange(X_RANGE_MIN,X_RANGE_MAX);

            //마지막 엔트리로 이동
            lineChart.moveViewToX ( data.getEntryCount () );
        }
    }

    //환경설정 값 만들기
    private LineDataSet createSet(){
        LineDataSet set=new LineDataSet ( null,"발전량" );
        set.setAxisDependency ( AxisDependency.LEFT );
        set.setColor(Color.RED);
        //set.setLineWidth ( 2f );
        //set.setFillAlpha ( 65 );
        //set.setValueTextColor ( Color.BLACK);
        //set.setValueTextSize ( 9f );
        set.setDrawValues ( false );
        return set;
    }

}
