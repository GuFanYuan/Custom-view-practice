package com.example.gufan.myview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    private CircleView TestCircle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestCircle = (CircleView) findViewById(R.id.circleView);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(TestCircle,"translationX",0,90),
                ObjectAnimator.ofFloat(TestCircle,"translationY",0,90),
                ObjectAnimator.ofFloat(TestCircle,"alpha",1,0.25f,1)
        );
        set.setDuration(1*1000).start();
    }
}
