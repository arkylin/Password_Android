package com.arkylin.password;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class DatabaseHelper extends SQLiteOpenHelper { //带全部参数的构造函数，name为数据库名称
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  //建表
        db.execSQL("CREATE TABLE password(\n" +
                "             id INTEGER PRIMARY KEY NOT NULL,\n" +
                "             name TEXT NOT NULL,\n" +
                "             content TEXT NOT NULL\n" +
                "             )");
        db.execSQL("INSERT into password (id,name,content) VALUES (1,\"PASSWORD\",\"欢迎使用！\");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//简单demo，就不写这个了

    }
    //只有conCreate()和onUpgrade是抽象方法，所以重写，

}