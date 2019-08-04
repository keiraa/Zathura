package com.zathura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class QuizActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://85.10.205.173:3306/zathura?verifyServerCertificate=false&useSSL=true";
    private static final String user = "zathura";
    private static final String pass = "zathu1234";
    private int id;
    private String name;
    ResultSet rs;
    Connection con = dbConnect();
    static ArrayList<Integer> arr=new ArrayList<Integer>();
    static int j=0;
    int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent quiz = getIntent();
        name=quiz.getStringExtra("name");

        for(int i=1;i<10;i++){
            arr.add(i);

        }

        Collections.shuffle(arr);

         j=Db_GetQuestion(con,arr,j);

    }


    public Connection dbConnect() {

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            return con;


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;


    }
    public int Db_GetQuestion(Connection con, ArrayList<Integer> arr, int j){
        try {

            id=arr.get(j);
            j+=1;


            String st = "SELECT * FROM questions where id=" + id;
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(st);
            ArrayList<String> options = new ArrayList<>();
            rs.absolute(1);
            options.add(rs.getString(3));
            options.add(rs.getString(4));
            options.add(rs.getString(5));
            options.add(rs.getString(6));

            TextView tx = (TextView) findViewById(R.id.question);
            tx.setText(rs.getString(2));
            SetOptions(options);

            return j;
        }
        catch (Exception e){

            e.printStackTrace();
        }
        return 0;
    }







    public void SetOptions(ArrayList<String> options){

            Collections.shuffle(options);

            AppCompatTextView op1 = (AppCompatTextView) findViewById(R.id.op1);
            AppCompatTextView op2 = (AppCompatTextView) findViewById(R.id.op2);
            AppCompatTextView op3 = (AppCompatTextView) findViewById(R.id.op3);
            AppCompatTextView op4 = (AppCompatTextView) findViewById(R.id.op4);
            op1.setText("A. "+options.get(0));
            op2.setText("B. "+options.get(1));
            op3.setText("C. "+options.get(2));
            op4.setText("D. "+options.get(3));


    }


    public void optionsubmit(View view) {
         String ans;
         final TextView tv = (TextView)view;



         ans = tv.getText().toString();
         ans=ans.substring(3);


         try{
            Statement st =con.createStatement();
            rs=st.executeQuery("SELECT * FROM questions where id=" + arr.get(j-1));

            rs.absolute(1);


         }
         catch (Exception e){
             e.printStackTrace();
         }
         score=CheckAns(ans,rs);
        tv.setBackgroundResource(R.drawable.back2);
        tv.setTextColor(Color.BLACK);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setBackgroundResource(R.drawable.back);
                tv.setTextColor(Color.WHITE);
                if (j<9 ) {
                    j = Db_GetQuestion(con, arr, j);
                }
                else {

                    Intent intent = new Intent(QuizActivity.this,ResultActivity.class);
                    intent.putExtra("score",""+score);
                    intent.putExtra("name",name);
                    Toast.makeText(getApplicationContext(),""+score,Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        },50);



    }

    public int CheckAns(String ans,ResultSet rs)
    {

        try {
            String O_ans=rs.getString(3);
            if(O_ans.equals(ans)){
                score++;
                return score;
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
        return score;

    }
}