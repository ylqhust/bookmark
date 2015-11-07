package com.ylqhust.bookmarks.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylqhust.bookmarks.R;

public class HelloActivity extends AppCompatActivity {
    private RelativeLayout rela;
    private boolean execute = false;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        rela = (RelativeLayout) findViewById(R.id.activity_hello_rela);

        rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (execute == true)
                    return;
                Execute();
            }
        });

        rela.animate().
                setDuration(1000).
                scaleX(1.0f).
                setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (execute == true)
                            return;
                        Execute();
                    }
                });
    }

    public void Execute(){
        Intent intent = new Intent();
        intent.setClass(HelloActivity.this,MainActivity.class);
        HelloActivity.this.startActivity(intent);
        HelloActivity.this.finish();
    }

}
