package com.wooz.song.egg;

import android.widget.ImageView;

/**
 * Created by samsung on 2017-12-15.
 */

public class Chick {
    private int num = 0;		//부화시킨 병아리의 수

    int sound = R.raw.bird_sound;   //병아리 소리정보
    int img = R.drawable.chick;     //병아리 이미지 정보

    public void set_Num() { this.num++; }   //부화시킨 동물 수를 +1 해주는 함수
    public int get_Num() { return  num; }   //부화시킨 동물 수를 반환해주는 함수
}
