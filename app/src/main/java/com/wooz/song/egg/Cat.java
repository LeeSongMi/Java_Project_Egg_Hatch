package com.wooz.song.egg;

/**
 * Created by samsung on 2017-12-17.
 */

public class Cat {
    private int num = 0;		//부화시킨 고양이 수

    int sound = R.raw.cat_sound; //고양이 소리 정보
    int img = R.drawable.cat;    //고양이 이미지 정보

    public void set_Num() { this.num++; }   //부화시킨 동물 수를 +1 해주는 함수
    public int get_Num() { return  num; }   //부화시킨 동물 수를 반환해주는 함수
}
