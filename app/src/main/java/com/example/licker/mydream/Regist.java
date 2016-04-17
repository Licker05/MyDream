package com.example.licker.mydream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

public class Regist extends Activity {
    Button Regist;
    EditText RegistUsername;
    EditText Registpassword;
    private MyDatabaseHelper dbHelper;
    private  ContentValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regist);
        Regist=(Button)findViewById(R.id.Regist);
        RegistUsername=(EditText)findViewById(R.id.RegistUsername);
        Registpassword=(EditText)findViewById(R.id.RegistPassword);
        dbHelper=new MyDatabaseHelper(this,"users.db",null,1);
        Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db_Regist_vali=dbHelper.getWritableDatabase();
                Cursor cursor_vali=db_Regist_vali.query("User", null, null, null, null, null, null, null);
                if(cursor_vali.moveToFirst())
                {
                    do{
                        if(cursor_vali.getString(cursor_vali.getColumnIndex("username")).equals(RegistUsername.getText().toString())==true)
                        {
                            Toast.makeText(getApplicationContext(),"注册失败,该用户名已被注册",Toast.LENGTH_SHORT).show();
                            return ;
                        }

                    }while(cursor_vali.moveToNext());
                }
                if(Registpassword.length()<6){
                    Toast.makeText(getApplicationContext(),"注册失败，密码最少为6位数",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String name = RegistUsername.getText().toString();
                            String pass = Registpassword.getText().toString();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("username", name);
                            values.put("password", pass);
                            values.put("whetherRem", 0);
                            db.insert("User", null, values);
                            Intent Load = new Intent(Regist.this, MainActivity.class);
                            startActivity(Load);
                            Regist.this.finish();
                        }
                    }, 1000);

                }
            }
        });
    }
}
