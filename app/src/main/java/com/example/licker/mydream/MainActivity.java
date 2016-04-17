package com.example.licker.mydream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    EditText username;
    EditText password;
    Button load;
    CheckBox remenberpass;
    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        load=(Button)findViewById(R.id.Load);
        remenberpass=(CheckBox)findViewById(R.id.checkBox);
        //--------------------
        username.setText(open());
        dbHelper=new MyDatabaseHelper(this,"users.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor_last=db.query("User",null,null,null,null,null,null,null);
        if(cursor_last.moveToFirst()){
            do {
                if(cursor_last.getString(cursor_last.getColumnIndex("username")).equals(open())==true && cursor_last.getInt(cursor_last.getColumnIndex("whetherRem"))==1)
                {
                    password.setText(cursor_last.getString(cursor_last.getColumnIndex("password")));
                    remenberpass.setChecked(true);
                }

            }while(cursor_last.moveToNext());
        }
    }
    public void Load_Click(View view)
    {
       String user=username.getText().toString();
        String pass=password.getText().toString();
        dbHelper=new MyDatabaseHelper(this,"users.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("User",null,null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do{
                if(cursor.getString(cursor.getColumnIndex("username")).equals(user)==true && cursor.getString(cursor.getColumnIndex("password")).equals(pass)==true)
                {
                    if(remenberpass.isChecked()==true)
                    {
                        ContentValues values=new ContentValues();
                        values.put("whetherRem",1);
                        db.update("User",values,"username=?",new String[]{user});
                    }else
                    {
                        ContentValues values=new ContentValues();
                        values.put("whetherRem",0);
                        db.update("User",values,"username=?",new String[]{user});
                    }
                    sava(user);
                    Intent in=new Intent(MainActivity.this,Flam.class);
                    startActivity(in);
                    cursor.close();
                    MainActivity.this.finish();
                    return ;

                }
            }while(cursor.moveToNext());
        }
        Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_SHORT).show();
        cursor.close();
    }
    public void Regist_Click(View view){
        Intent Regist=new Intent(MainActivity.this, com.example.licker.mydream.Regist.class);
        startActivity(Regist);
        MainActivity.this.finish();
    }
    public void sava(String inputText)
    {
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=openFileOutput("lastuser.txt", Context.MODE_PRIVATE);
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
    public String open()
    {
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=openFileInput("lastuser.txt");
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
        return content.toString();
    }

}
