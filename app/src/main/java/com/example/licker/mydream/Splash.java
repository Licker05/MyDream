package com.example.licker.mydream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Splash extends Activity {
    private final int SPLASH_DISPLAY_LENGTH=3000;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        File Check= new File("/data/data/com.example.licker.mydream/files/IsFirstOpen.txt");
        try {
            if(Check.exists()==false){
                sava("Yes");
                dbHelper=new MyDatabaseHelper(this,"users.db",null,1);
                dbHelper.getWritableDatabase();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent RegiInter = new Intent(Splash.this, Regist.class);
                        Splash.this.startActivity(RegiInter);
                        Splash.this.finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }else
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent MainInter=new Intent(Splash.this,MainActivity.class);
                        Splash.this.startActivity(MainInter);
                        Splash.this.finish();
                    }
                },SPLASH_DISPLAY_LENGTH);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sava(String inputText)
    {
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=openFileOutput("IsFirstOpen.txt", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null)
                {
                    writer.close();
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void open()
    {
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=openFileInput("IsFirstOpen.txt");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null)
            {
                content.append(line);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();

                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


}
