package com.wooz.song.egg;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;

import junit.framework.Test;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by samsung on 2017-12-15.
 */

//서브액티비티로 보내기 위해 에그 클래스를 직렬화
public class Egg implements Serializable {
    //부화할 동물들 클래스 생성
    public static Chick chick = new Chick();
    public static Dog dog = new Dog();
    public static Cat cat = new Cat();
    public static Unicorn unicorn = new Unicorn();

    private static Random r = new Random();    //생성될 동물을 랜덤으로 뽑을 때 사용
    private static int Max_animal = 4;         //부화할 동물의 종 수

    //MainActivity에서 받아올 것들
    public static  Context context;     //MainActivity의 Context -> this를 받을 필드
    private static ProgressBar ex_pro;  //MainActivity의 ProgressBar를 사용하기 위해 선언한 필드
    private static Handler th_handler;  //스레드를 통해 이미지를 변경시킬 때 사용될 핸들러

    //MainActivity의 Context -> this를 Egg의 context에 대입
    void get_cont(Context context) {
        this.context = context;
    }


    //경험치 설정
    static int experience = 0;    //경험치
    public int get_Ex() { return experience; } //경험치 값 리턴
    static public void set_Ex(int ex) {
        experience += ex;
    } //ex만큼 경험치를 증가 시켜준다
    static public void reset_Ex() { experience = 0; }       //경험치를 0으로 초기화 시켜준다.
    private static  int Max_Ex = 100;   //경험치 최대값

    //ProgressBar의 값을 경험치로 설정해주는 함수
    static void update_Ex(ProgressBar pro){
        pro.setProgress(experience);
    }

    //시간을 time만큼 딜레이 시켜주는 함수
    public  static void time_delay(int time){
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e){ return; }
    }

    //부화 스레드 실행 함수
    public static void hatch_egg(ProgressBar progress_ex) {
        if (progress_ex.getProgress() >= Max_Ex){      //현재 경험치가 최대 경험치 이상일 경우 부화 스레드 실행
            Thread hatch_th = new Thread(new Egg.HatchThread(th_handler));
            hatch_th.start();
        }
    }

    //알에게 밥을 주는 동작 스레드
    public static class EatThread implements Runnable {
        //스레드를 생성할 때 MainActivity에서 progressbar와 handler정보를 받는다.
        EatThread (ProgressBar pro, Handler handler){
            ex_pro = pro;
            th_handler = handler;
        }

        @Override
        public void run() {
            final MediaPlayer eat_sound = MediaPlayer.create(context, R.raw.eat_sound); //eat_sound 생성
            eat_sound.start();      //사운드 재생
            set_Ex(25);             //경험치 25 증가
            update_Ex(ex_pro);      //증가한 경험치를 ProgressBar에 새로 업데이트해준다.

            //mainUI를 여기서 변경할 수 없기 때문에 핸들러를 이용하였다.
            //핸들러로 변경할 사진을 mainUI에 보내고 main UI에서는 핸들러로 받은 사진에 따라 변경하는 동작을 취한다.
            //알의 이미지 정보를 메세지로 설정하여 mainUI에 보냄
            th_handler.sendEmptyMessage(R.drawable.eat1);   //핸들러를 이용해서 메인 이미지를 변경
            time_delay(500);                                 //이미지가 변경된 채로 시간을 딜레이 시켜준다
            th_handler.sendEmptyMessage(R.drawable.eat2);
            time_delay(500);
            th_handler.sendEmptyMessage(R.drawable.eat3);
            time_delay(500);
            th_handler.sendEmptyMessage(R.drawable.eat4);
            time_delay(500);
            //^알이 음식을 먹는 동작

            th_handler.sendEmptyMessage(R.drawable.egg);    //동작이 끝나고 원래의 알 이미지로 돌아간다.
            hatch_egg(ex_pro);      //경험치가 다 찼는지 확인 후, 다 찬 경우 부화 스레드를 실행하는 함수
        }
    }

    //물주기 스레드
    public static class DrinkThread implements Runnable {;
        DrinkThread (ProgressBar pro, Handler handler){
            ex_pro = pro;
            th_handler = handler;
        }
        @Override
        public void run() {
            final MediaPlayer drink_sound = MediaPlayer.create(context, R.raw.drink_sound);
            drink_sound.start();    //물의 마시는 소리 재생
            set_Ex(25);             //경험치 증가
            update_Ex(ex_pro);

            //알이 물을 마시는 이미지
            th_handler.sendEmptyMessage(R.drawable.drink0);
            time_delay(700);
            th_handler.sendEmptyMessage(R.drawable.drink1);
            time_delay(700);
            th_handler.sendEmptyMessage(R.drawable.drink2);
            time_delay(800);
            th_handler.sendEmptyMessage(R.drawable.drink3);
            time_delay(800);
            th_handler.sendEmptyMessage(R.drawable.drink4);
            time_delay(1100);

            th_handler.sendEmptyMessage(R.drawable.egg);
            hatch_egg(ex_pro);
        }
    }

    //재우기 스레드
    public static class SleepThread implements Runnable {;
        SleepThread (ProgressBar pro, Handler handler){
            ex_pro = pro;
            th_handler = handler;
        }
        @Override
        public void run() {
            final MediaPlayer snoring_sound = MediaPlayer.create(context, R.raw.snoring_sound);
            snoring_sound.start();      //소리재생
            set_Ex(25);                 //경험치증가
            update_Ex(ex_pro);

            //알의 이미지 동작
            th_handler.sendEmptyMessage(R.drawable.sleep1);//1
            time_delay(2200);
            th_handler.sendEmptyMessage(R.drawable.sleep2);//3
            time_delay(2300);
            th_handler.sendEmptyMessage(R.drawable.sleep1);//5
            time_delay(2000);
            th_handler.sendEmptyMessage(R.drawable.sleep3);//6
            time_delay(2000);

            th_handler.sendEmptyMessage(R.drawable.egg);
            hatch_egg(ex_pro);
        }
    }

    //놀아주기 스레드
    public static class JoyThread implements Runnable {;
        JoyThread (ProgressBar pro, Handler handler){
            ex_pro = pro;
            th_handler = handler;
        }
        @Override
        public void run() {
            final MediaPlayer joy_sound = MediaPlayer.create(context, R.raw.joy_sound);
            joy_sound.start();      //소리재생
            set_Ex(25);             //경험치 증가
            update_Ex(ex_pro);

            //알의 동작 시작
            th_handler.sendEmptyMessage(R.drawable.joy1);
            time_delay(200);
            th_handler.sendEmptyMessage(R.drawable.joy2);
            time_delay(400);
            th_handler.sendEmptyMessage(R.drawable.joy3);
            time_delay(500);

            th_handler.sendEmptyMessage(R.drawable.egg);
            hatch_egg(ex_pro);
        }
    }

    //알 부화 스레드
    public static class HatchThread implements Runnable {;
        HatchThread (Handler handler){
            th_handler = handler;
        }
        @Override
        public void run() {
            int what_animal = r.nextInt(Max_animal);       //랜덤으로 부화시킬 동물을 정한다.

            switch (what_animal) {
                case 0:
                    chick_hatch();  //병아리가 부화하는 경우
                    break;

                case 1:
                    cat_hatch();    //고양이가 부화하는 경우
                    break;

                case 2:
                    dog_hatch();    //강아지가 부화하는 경우
                    break;

                case 3:
                    unicorn_hatch();    //유니콘이 부화하는 경우
                    break;
            }
        }
    }

    //알이 깨지는 이미지를 함수로 만들었다.
    public static void crack_egg() {

        th_handler.sendEmptyMessage(R.drawable.crack1);
        time_delay(500);
        th_handler.sendEmptyMessage(R.drawable.egg);
        time_delay(500);
        th_handler.sendEmptyMessage(R.drawable.crack2);
        time_delay(500);
        th_handler.sendEmptyMessage(R.drawable.crack_egg);
        time_delay(800);

    }

    //병아리가 부화하는 함수
    private static void chick_hatch() {
        chick.set_Num();    //부화시킨 병아리 수를 +1해준다.
        crack_egg();        //알이 깨지는 과정 이미지

        final MediaPlayer bird_sound = MediaPlayer.create(context, R.raw.bird_sound);   //새소리 설정
        bird_sound.start();             //새소리 재생

        th_handler.sendEmptyMessage(R.drawable.chick);      //부화한 병아리의 모습을 보여준다.

        //3초가 지나고 다시 알의 형태로 돌아간다 -> 새로 알키우기 시작
        time_delay(3000);
        th_handler.sendEmptyMessage(R.drawable.egg);


        reset_Ex();          //경험치도 다시 0으로 초기화 시켜준다.
        update_Ex(ex_pro);   //초기화한 경험치 업데이트
    }

    //고양이가 부화하는 함수
    private static void cat_hatch() {
        cat.set_Num();    //부화시킨 동물 수를 +1해준다.
        crack_egg();        //알이 깨지는 과정 이미지

        final MediaPlayer cat_sound = MediaPlayer.create(context, R.raw.cat_sound);   //소리 설정
        cat_sound.start();             //소리 재생

        th_handler.sendEmptyMessage(R.drawable.cat);      //부화한 동물의 모습을 보여준다.

        time_delay(3000);      //3초 딜레이

        th_handler.sendEmptyMessage(R.drawable.egg);

        //경험치도 다시 0으로 초기화 시켜준다.
        reset_Ex();
        update_Ex(ex_pro);
    }

    //유니콘이 부화하는 함수
    private static void unicorn_hatch() {
        unicorn.set_Num();    //부화시킨 동물 수를 +1해준다.
        crack_egg();        //알이 깨지는 과정 이미지

        final MediaPlayer unicorn_sound = MediaPlayer.create(context, R.raw.horse_sound);   //소리 설정
        unicorn_sound.start();             //소리 재생

        th_handler.sendEmptyMessage(R.drawable.unicorn);      //부화한 동물의 모습을 보여준다.

        time_delay(3000);      //3초 딜레이

        th_handler.sendEmptyMessage(R.drawable.egg);
        //경험치도 다시 0으로 초기화 시켜준다.
        reset_Ex();
        update_Ex(ex_pro);
    }

    //강이지가 부화하는 함수
    private static void dog_hatch() {
        dog.set_Num();    //부화시킨 동물 수를 +1해준다.
        crack_egg();        //알이 깨지는 과정 이미지

        final MediaPlayer dog_sound = MediaPlayer.create(context, R.raw.dog_sound);   //소리 설정
        dog_sound.start();             //소리 재생


        th_handler.sendEmptyMessage(R.drawable.dog);      //부화한 동물의 모습을 보여준다.

        time_delay(3000);      //3초 딜레이

        th_handler.sendEmptyMessage(R.drawable.egg);
        //경험치도 다시 0으로 초기화 시켜준다.
        reset_Ex();
        update_Ex(ex_pro);
    }



}
