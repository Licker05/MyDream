package com.example.licker.mydream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
/**
 * Created by Licker on 2016/4/16.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_User="create table User ("
            + "id integer primary key autoincrement,"
            + "username text, "
            + "password text, "
            + "whetherRem integer) ";
    private Context mContext;
    public MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_User);
        Toast.makeText(mContext, "检测到你是第一次使用，将转到注册页面", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
        db.execSQL("drop table if exists User");
        onCreate(db);

    }
}
