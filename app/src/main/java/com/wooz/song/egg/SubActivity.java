package com.wooz.song.egg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    Egg egg_inf = new Egg();        //메인 액티비티의 에그 클래스를 저장하기 위해 선언

    //부화시킨 동물들의 수를 표시할 텍스트 뷰
    TextView chick_num;
    TextView dog_num;
    TextView cat_num;
    TextView unicorn_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //ID를 연결시켜준다.
        chick_num = findViewById(R.id.chick_num);
        dog_num = findViewById(R.id.dog_num);
        cat_num = findViewById(R.id.cat_num);
        unicorn_num = findViewById(R.id.unicorn_num);

        //메인 액티비티에서 보낸 egg 데이터를 받는다.
        Intent intentB = getIntent();
        egg_inf = (Egg)intentB.getSerializableExtra("Egg");

        //받은 정보대로 텍스트뷰를 변환시켜준다.
        chick_num.setText("부화시킨 병아리: "+egg_inf.chick.get_Num()+" 마리");
        dog_num.setText("부화시킨 강아지: "+egg_inf.dog.get_Num()+" 마리");
        cat_num.setText("부화시킨 고양이: "+egg_inf.cat.get_Num()+" 마리");
        unicorn_num.setText("부화시킨 유니콘: "+egg_inf.unicorn.get_Num()+" 마리");


    }
}
