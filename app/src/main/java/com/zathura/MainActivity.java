 package com.zathura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.Statement;

 public class MainActivity extends AppCompatActivity {


    private static final String  url = "jdbc:mysql://85.10.205.173:3306/zathura?verifyServerCertificate=false&useSSL=true";
    private static final String user = "zathura";
    private static final String pass = "zathu1234";
    private String uname="Name",phone="12345";
     TextInputLayout tx,ph;
     Connection con;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tx = (TextInputLayout) findViewById(R.id.name);
         tx.setSelected(false);
         ph = (TextInputLayout) findViewById(R.id.phone);
         ph.setSelected(false);
         con = dbConnect();


         Button butt = (Button) findViewById(R.id.enter);

         butt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 tx.setErrorEnabled(false);
                 ph.setErrorEnabled(false);
                 uname= String.valueOf(tx.getEditText().getText());

                 phone= String.valueOf(ph.getEditText().getText());
                 boolean flag=true;
//
                 if(uname.equals("")){

                     tx.setError("Name should not be empty");
                     flag=false;


                 }
                 if(phone.equals("")) {

                     ph.setError("Phone number should not be empty");
                     flag=false;

                 }
                 if(flag){


                 DbCRUD(con);
                 Intent intent = new Intent(MainActivity.this,DashActivity.class);
                 intent.putExtra("name",uname);
                 intent.putExtra("phn",phone);
                 ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                 startActivity(intent,options.toBundle());

                 }
             }
         });


    }


    public Connection dbConnect(){

            try {



                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url,user,pass);

                return con;

            }
            catch (Exception e){
                e.printStackTrace();

            }
                return null;

        }

       public void DbCRUD(Connection con){
         try {
             String st2 = "SELECT * FROM users WHERE name = '"+uname+"'";
             Statement stmt = con.createStatement();
             boolean flag =true;
             try {
                 ResultSet rs = stmt.executeQuery(st2);
                 rs.absolute(1);
                 rs.getString(1);
                 flag=false;
             }
             catch (Exception e){
                 e.printStackTrace();
             }

             if(flag) {
                 Statement stmt2 = con.createStatement();
                 String st = "INSERT INTO `users`(`name`,`phone`) VALUES ('" + uname + "','" + phone + "')";
                 int val = stmt2.executeUpdate(st);
             }
             else
             {
                 Toast.makeText(getApplicationContext(),"Logged in as: " +uname,Toast.LENGTH_LONG).show();
             }
         }
         catch (Exception e){
             e.printStackTrace();
             e.getStackTrace();
         }


       }

 }
