package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Monitoring_2_3_Activity extends AppCompatActivity {

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

    //realTime 출력 kw(공유 변수임)
    private int realTime_kw;

    //realTIme으로 계속 값 세팅하는스레드
    Thread thread;
    TextView nowTIme;
    TextView volt;
    TextView ampere;
    TextView kw;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_monitoring_2_3_ );
        //현재시간, 전압, 전류, 전력 가져오기
        nowTIme=(TextView ) findViewById ( R.id.textNowDate );
        volt=(TextView)findViewById ( R.id.textVolt );
        ampere=(TextView)findViewById ( R.id.textAmpere );
        kw=(TextView)findViewById (  R.id.text_kW );


        //HOmE 버튼
        findViewById(R.id.ImgBtn_Home_monitoring_2_3).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent=new Intent(getApplicationContext (),MainActivity.class);
                startActivity ( intent );
            }
        } );

        //5초마다 화면 값 갱신
        handler.sendEmptyMessage ( 0 );

        //그래프 그리기
        initChart ();
        doRealTimeThread ();

    }
    Handler handler=new Handler (  ){
        public void handleMessage(Message msg){
            setTextValue ();

            //5초에 한 번씩 update
            handler.sendEmptyMessageDelayed ( 0,5000 );
        }
    } ;
    //값 뿌려주는 함수
    private void setTextValue()
    {
        try
        {
            //현재 시간 넣어주기
            long nowTime= System.currentTimeMillis ( );
            Date date=new Date(nowTime);
            SimpleDateFormat sdf=new SimpleDateFormat ( "hh:mm:ss" );
            String strDate=sdf.format ( date );
            nowTIme.setText ( strDate);
            //전압 값 임의로 넣어주기
            int nVolt=(int)(Math.random ()*220)+1;
            volt.setText (String.valueOf (  nVolt ) );
            //전류 값 임의로 넣어주기
            int nAmpere=(int)(Math.random ()*20)+1;
            ampere.setText (String.valueOf (   nAmpere ));
            //전력 값 임의로 넣어주기

            realTime_kw=(int)(Math.random ()*50)+10;
            int nKw=realTime_kw;
            kw.setText (String.valueOf ( nKw  ) );
        }catch(Exception ex){
            ex.getMessage ();
        }

    }

    //차트 초기화
    private void initChart()
    {
        //라인 차트 가져오기
        lineChart=findViewById ( R.id.lineChartDetail );
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
        x.setPosition (XAxisPosition.BOTTOM);

        x.setValueFormatter ( new IAxisValueFormatter () {

            private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss");
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long currentTime=System.currentTimeMillis ();
                long millis = TimeUnit.SECONDS.toMillis((long) value)+currentTime;

                return mFormat.format(new Date(millis));

            }
        });

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
                        Thread.sleep ( 5100 );
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

            data.addEntry ( new Entry ( set.getEntryCount (),(float)(realTime_kw)),0 );
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
        set.setAxisDependency ( AxisDependency.LEFT);
        set.setColor(Color.RED);
        //set.setLineWidth ( 2f );
        //set.setFillAlpha ( 65 );
        //set.setValueTextColor ( Color.BLACK);
        //set.setValueTextSize ( 9f );
        set.setDrawValues ( false );
        return set;
    }


}
