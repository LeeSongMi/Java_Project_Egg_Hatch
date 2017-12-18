package com.wooz.song.egg;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    Egg egg = new Egg();    //알 클래스

    Button gallBtn;        //갤러리로 넘어가는 버튼
    ImageView main_img;    //알이 표시될 메인 이미지
    ProgressBar Ex;         //경험치바
    Handler handler;        //메인 UI에 접근하기위한 핸드러
    Thread eat_th;          //알의 동작 스레드
    Thread drink_th;
    Thread sleep_th;
    Thread joy_th;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        egg.get_cont(this);     //context를 egg클래스로 넘겨준다.

        //ID를 연결시켜준다.
        this.main_img = findViewById(R.id.Egg_img);
        this.gallBtn = findViewById(R.id.Gall_btn);
        this.Ex= findViewById(R.id.Ex_gauge);

        //알의 경험치 값대로 progressBar 설정
        Ex.setProgress(egg.get_Ex());


        //이미지 변경을 위해 사용한 핸들러
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                main_img.setImageResource(msg.what);    //핸들러가 보내온 사진 주소로 메인 화면의 이미지를 바꾼다.
            }
        };


        //갤러리 클릭시 서브 액티비티가 열린다.
        gallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터를 주고받는 매개체 Intent
                Intent intentA = new Intent(MainActivity.this, SubActivity.class);
                intentA.putExtra("Egg",egg);    //알 클래스를 서브액티비에 넘겨준다.
                startActivity(intentA);

            }
        });
    };

    //버튼에 따른 동작을 설정
    public void btnMove(View view){
        //스레드를 생성할때 경험치 바와 핸들러의 정보를 넣어준다.
        eat_th = new Thread(new Egg.EatThread(Ex, handler));
        drink_th = new Thread(new Egg.DrinkThread(Ex, handler));
        sleep_th = new Thread(new Egg.SleepThread(Ex, handler));
        joy_th = new Thread(new Egg.JoyThread(Ex, handler));

        //클릭한 버튼의 id값에 따라 알맞은 스레드 시작
        switch (view.getId()) {
            case R.id.Eat_btn:
                eat_th.start();
                break;

            case R.id.Water_btn:
                drink_th.start();
                break;

            case R.id.Sleep_btn:
                sleep_th.start();
                break;

            case R.id.Joy_btn:
                joy_th.start();
                break;

                }

    }
}


