package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Monitoring_2_4_Activity extends AppCompatActivity {

    //barChart 변수
    BarChart barChart;
    //날짜 출력 배열
    ArrayList<String>labels;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_monitoring_2_4_ );

        //차트 제목
        final TextView textChartTitle=(TextView )findViewById ( R.id.textBarChartTitle );
        //차트 단위
        final TextView textChartUnit=(TextView )findViewById ( R.id.textUnit );
        //HOmE 버튼
        findViewById(R.id.ImgBtn_Home_monitoring_2_4).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent=new Intent(getApplicationContext (),MainActivity.class);
                startActivity ( intent );
            }
        } );

        //전력량 버튼
        findViewById(R.id.imgBtnKw).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                textChartTitle.setText ( "전력량" );
                textChartUnit.setText ( "(KW)" );
                chartKwShow ();
            }
        } );

        //수익
        findViewById(R.id.imgBtnProfits).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                textChartTitle.setText ( "수익" );
                textChartUnit.setText ( "(원)" );
                chartProfitsShow ();
            }
        } );

        //장애 발생
        findViewById(R.id.imgBtnFail).setOnClickListener ( new OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                textChartTitle.setText ( "장애발생" );
                textChartUnit.setText ( "(횟수)" );
                chartFailShow ();
            }
        } );

        //초기화
        textChartTitle.setText ( "전력량" );
        textChartUnit.setText ( "(KW)" );
        initChart ();
        chartKwShow ();
    }

    //전력량 bar 차트 보여주는 함수
    private void chartKwShow(){
        barChart=(BarChart)findViewById ( R.id.barChart );

        try{
            ArrayList<BarEntry>barEntries=new ArrayList<BarEntry> (  );
            for(int i=1;i<=3;i++){
                barEntries.add ( new BarEntry (i,(int)(Math.random ()*100)+400) );
            }

            final BarDataSet barDataSet=new BarDataSet ( barEntries,"" );

            BarData data=new BarData(barDataSet);
            barDataSet.setColors ( ColorTemplate.COLORFUL_COLORS );
            data.setBarWidth ( 0.5f );

            barChart.setData ( data );
            barChart.animateY ( 1000 );
            barChart.invalidate ();
        }catch(Exception ex){
            ex.getMessage ();
        }

    }
    //수익 bar 차트 보여주는 함수
    private void chartProfitsShow(){
        barChart=(BarChart)findViewById ( R.id.barChart );

        try{
            ArrayList<BarEntry>barEntries=new ArrayList<BarEntry> (  );
            for(int i=1;i<=3;i++){
                barEntries.add ( new BarEntry(i,(int)(Math.random ()*10000)+200000) );
            }
            BarDataSet barDataSet=new BarDataSet ( barEntries,"" );
            BarData data=new BarData ( barDataSet );
            barDataSet.setColors ( ColorTemplate.COLORFUL_COLORS );
            data.setBarWidth ( 0.5f );

            barChart.setData ( data );
            barChart.animateY ( 1000 );
            barChart.invalidate ();

        }catch(Exception ex){
            ex.getMessage ();
        }

    }
    //장애발생 bar 차트 보여주는 함수
    private void chartFailShow(){
        barChart=(BarChart)findViewById ( R.id.barChart );

        try{
            ArrayList<BarEntry>barEntries=new ArrayList<BarEntry> (  );
            for(int i=1;i<=3;i++){
                barEntries.add ( new BarEntry(i,(int)(Math.random ()*10)) );
            }
            BarDataSet barDataSet=new BarDataSet ( barEntries,"" );
            BarData data=new BarData ( barDataSet );
            barDataSet.setColors ( ColorTemplate.COLORFUL_COLORS );
            data.setBarWidth ( 0.5f );

            barChart.setData ( data );
            barChart.animateY ( 1500 );
            barChart.invalidate ();

        }catch(Exception ex){
            ex.getMessage ();
        }

    }

    private void initChart()
    {
        barChart=findViewById ( R.id.barChart);

        //자동으로 y축 최대 최소값 바꾸기
        barChart.setAutoScaleMinMaxEnabled ( true );
        //enable value hilighting
        barChart.setDefaultFocusHighlightEnabled(false);
        //touch enable
        barChart.setTouchEnabled(true);

        //enable scaling and dragging
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawGridBackground(false);

        //enable pinch Zoom to avoid scaling x and y axis separately
        barChart.setPinchZoom(false);

        //alternative background color
        barChart.setBackgroundColor(Color.TRANSPARENT);

        //get legend object
        Legend legend =barChart.getLegend();

        //custom legend
        legend.setForm(LegendForm.LINE);
        legend.setTextColor(Color.BLACK);

        //x축 세팅
        XAxis x=barChart.getXAxis();
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxisPosition.BOTTOM);

        //배경에 격자무늬 넣기
        x.setDrawGridLines(false);
        x.setAvoidFirstLastClipping(true);

        //x축 '월 붙이기'
        labels=new ArrayList<String> ( );
        labels.add ( "" );
        labels.add ( "10월" );
        labels.add ( "11월" );
        labels.add ( "12월" );
        //인덱스에 따라 string 포맷으로 라벨 붙이기
        x.setValueFormatter ( new IndexAxisValueFormatter ( labels ) );

        //왼쪽 y축 보이게 하기
        YAxis y= barChart.getAxisLeft();
        y.setTextColor(Color.BLACK);

        //배경에 격자무늬 넣기
        y.setDrawGridLines(true);

        //오른쪽 y축 안보이게 하기
        YAxis y2=barChart.getAxisRight();
        y2.setEnabled(false);

        // Add a limit line
        //LimitLine ll = new LimitLine(LIMIT_MAX_MEMORY, "Upper Limit");
        //ll.setLineWidth(2f);
        //ll.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        //ll.setTextSize(10f);
        //ll.setTextColor(Color.WHITE);
        // reset all limit lines to avoid overlapping lines
        //leftAxis.removeAllLimitLines();
        //leftAxis.addLimitLine(ll);
        // limit lines are drawn behind data (and not on top)
        //leftAxis.setDrawLimitLinesBehindData(true);

        //설명 넣기(안 넣음)
        barChart.getDescription().setEnabled(false);

        //출력
        //barChart.invalidate();
    }
}
