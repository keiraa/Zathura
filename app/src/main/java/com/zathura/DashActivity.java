package com.zathura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.BatchUpdateException;

public class DashActivity extends AppCompatActivity {

    private String uname,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        Intent intent = getIntent();
        uname = intent.getStringExtra("name");
        phone = intent.getStringExtra("phn");

        TextView txt = (TextView) findViewById(R.id.welcome);
        txt.setText("Hello, "+uname);
        txt.setTextColor(Color.WHITE);

        Button journey = (Button) findViewById(R.id.ButtonGo);
        journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quiz=new Intent(DashActivity.this,QuizActivity.class);
                quiz.putExtra("name",uname);
                startActivity(quiz);
            }
        });
    }

}
