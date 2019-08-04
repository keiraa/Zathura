package com.zathura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    private String score,name,msg;
    private int val;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        score = intent.getStringExtra("score");
        val = Integer.parseInt(score);
        name = intent.getStringExtra("name");
        TextView hi = (TextView)findViewById(R.id.hi);
        hi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),score,Toast.LENGTH_LONG).show();
            }
        });
        hi.setText("Hey, "+name);
        hi.setTextColor(Color.WHITE);
        TextView result = (TextView)findViewById(R.id.result);
        result.setText("Your score is "+score+" of 10");
        result.setTextColor(Color.WHITE);
        TextView remark = (TextView)findViewById(R.id.msg);
        if(val>=8)
        {
            msg ="WOOO!, You are a born Astronaut";
        }
        else if(val>4)
        {
            msg ="Well, You can work for NASA";
        }
        else
        {
            msg="Get out and Stare at the Sky";
        }
        remark.setText(msg);
        remark.setTextColor(Color.WHITE);
    }
}
