package com.example.jongjinbyun.hemsmobileappandroid8;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Enroll_1_1_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_1_1_);


        //홈 버튼
        findViewById(R.id.ImgBtn_Home_enroll).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        //등록 버튼
        findViewById(R.id.btnEnrollToHems).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v)
                    {
                        //변수 가져오기
                        EditText editTextHemsID=(EditText)findViewById(R.id.editHemsID);
                        EditText editTextIP=(EditText)findViewById ( R.id.editIPAddress );
                        EditText editTextPort=(EditText)findViewById ( R.id.editPort );
                        EditText editTextSerial=(EditText)findViewById (R.id.editSerialNo );
                        Spinner spinnerMetro =(Spinner)findViewById ( R.id.spinnerMetro );
                        Spinner spinnerCity=(Spinner)findViewById ( R.id.spinnerCity );


                        if( editTextHemsID.getText().toString().length() == 0)
                        {
                            Toast.makeText(Enroll_1_1_Activity.this,"HEMS ID 를 입력해주세요",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(editTextIP.getText().toString().length() == 0)
                        {
                            Toast.makeText ( Enroll_1_1_Activity.this,"IP 주소를 입력하세요" ,Toast.LENGTH_SHORT).show ();
                            return;
                        }
                        else if(editTextPort.getText ().toString ().length () ==0)
                        {
                            Toast.makeText ( Enroll_1_1_Activity.this,"Port 번호를 입력하세요",Toast.LENGTH_SHORT ).show ();
                        }
                        else if(editTextSerial.getText ().toString ().length () ==0)
                        {
                            Toast.makeText ( Enroll_1_1_Activity.this,"시리얼번호를 입력하세요",Toast.LENGTH_SHORT ).show ();
                        }
                        else
                        {
                            try
                            {
                                Intent intent = new Intent(getApplicationContext(),Enroll_1_2_Activity.class);
                                //intent로 값 전달
                                intent.putExtra ( "HemsID",editTextHemsID.getText ().toString () );
                                intent.putExtra ( "IPAddress",editTextIP.getText ().toString () );
                                intent.putExtra ( "port",editTextPort.getText ().toString () );
                                intent.putExtra ( "serialNo",editTextSerial.getText ().toString () );
                                intent.putExtra ( "metro",spinnerMetro.getSelectedItem ().toString () );
                                intent.putExtra ( "city",spinnerCity.getSelectedItem ().toString () );
                                startActivity(intent);
                            }catch(Exception ex)
                            {
                                Toast.makeText ( getApplicationContext (),ex.getMessage (), Toast.LENGTH_SHORT ).show ();
                            }

                        }

                    }
                }
        );


        Spinner spinner=(Spinner)findViewById(R.id.spinnerMetro);
        ArrayAdapter metroAdapter=ArrayAdapter.createFromResource(this,R.array.metro,android.R.layout.simple_spinner_item);
        metroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(metroAdapter);
        //글자색 변경
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.rgb(44,44,44));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner2=(Spinner)findViewById(R.id.spinnerCity);
        ArrayAdapter cityAdapter=ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(cityAdapter);

        //글자색 변경
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.rgb(44,44,44));
                //TextView tv= (TextView)convertView.findViewById(R.id.spinnerCity);
                //tv.setTextcolor(Color.argb(0,ee,ef,f0));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

    }
}
