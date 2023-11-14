package com.example.tijori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_screen extends AppCompatActivity {

    Animation topanim,bottomanim;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image =findViewById(R.id.imageView2);
        logo=findViewById(R.id.director);
        image.setAnimation(topanim);
        logo.setAnimation(bottomanim);
        int SPLASH_SCREEN = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_screen.this,BiometricActivity.class);
                // Pair[] pairs = new Pair[2];
                // pairs[0] = new Pair<View,String>(image,"logo_image");
                // pairs[1] = new Pair<View,String>(logo,"logo_text");

                // ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(splashscreen.this,pairs);
                startActivity(intent//options.toBundle()//
                );
                finish();
            }
        }, SPLASH_SCREEN);
    }

    public static class NewsAdapter {
    }
}