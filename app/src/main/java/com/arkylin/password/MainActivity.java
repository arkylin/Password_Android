package com.arkylin.password;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText PNAME;
    private EditText PCONTENT;
    private EditText TDELETE;
    private EditText INTSIZE;
    private Button SEARCH;
    private Button ADD;
    private Button ALL;
    private Button DELETE;
    private Button RADOMP;

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,./;'[]=-)(*&^%$#@!~`<>?\":}{|";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(91);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this, "password.db", null, 2);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        PNAME = (EditText) findViewById(R.id.PName);
        PCONTENT = (EditText) findViewById(R.id.PContent);
        TDELETE = (EditText) findViewById(R.id.tdelete);
        INTSIZE = (EditText) findViewById(R.id.intsize);

        RADOMP = (Button) findViewById(R.id.radomp);
        RADOMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String intsize = INTSIZE.getText().toString().trim();
                if (intsize.isEmpty()){
                    Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }else {
                    PCONTENT.setText(getRandomString(Integer.parseInt(intsize)));
                }
            }
        });


        DELETE = (Button) findViewById(R.id.delete);
        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String abcid = TDELETE.getText().toString().trim();
                if (abcid.isEmpty()) {
                    Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }else{
                    final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
                    normalDialog.setTitle("??????");
                    normalDialog.setMessage("??????????????????????");
                    normalDialog.setPositiveButton("???", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.delete("password", "id=?", new String[]{abcid});
                            Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                        }
                    });
                    normalDialog.setNegativeButton("???", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "?????????...", Toast.LENGTH_SHORT).show();
                        }
                    });
                    normalDialog.show();
                }
            }
        });

        ADD = (Button) findViewById(R.id.add);
        AlertDialog.Builder bb = new AlertDialog.Builder(this);
        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = PNAME.getText().toString().trim();
                final String content = PCONTENT.getText().toString().trim();
                if (name.isEmpty() | content.isEmpty()){
                    Toast.makeText(MainActivity.this,"????????????????????????",Toast.LENGTH_SHORT).show();
                }else{
//                    SQLiteDatabase db = databaseHelper.getWritableDatabase();  //??????????????????????????????
                    ContentValues values = new ContentValues();
                    values.put("name",name);
                    values.put("content",content);
                    Cursor cursor = db.query("password", new String[]{"id","name","content"}, "name=?", new String[]{name}, null, null, null);
                    if (cursor.getCount() != 0 ) {
                        if (cursor.moveToFirst()) {
                            @SuppressLint("Range") String pid = cursor.getString(cursor.getColumnIndex("id"));
                            values.put("id",pid);
                            final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
                            normalDialog.setTitle("??????");
                            normalDialog.setMessage("???????????????????????????????????????????");
                            normalDialog.setPositiveButton("???", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.update("password",values,"id=?",new String[]{pid});
                                    Toast.makeText(MainActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
                                }
                            });
                            normalDialog.setNegativeButton("???", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this,"?????????...",Toast.LENGTH_SHORT).show();
                                }
                            });
                            normalDialog.show();
                        }
                    }else{
                        db.insert("password",null,values);
                        Toast.makeText(MainActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        SEARCH = (Button) findViewById(R.id.search);
        SEARCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = PNAME.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = db.query("password", new String[]{"content"}, "name=?", new String[]{name}, null, null, null);
//                    @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
                    if (cursor.moveToFirst()) {
                        PCONTENT.setText(cursor.getString(0));
                    }else{
                        Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ALL = (Button) findViewById(R.id.all);
        ALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????????????????????activity
                startActivity(new Intent(MainActivity.this, Search_All.class));
            }
        });

//        SEARCH = (Button) findViewById(R.id.search);
//        SEARCH.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //????????????????????????activity
//                startActivity(new Intent(MainActivity.this,Search.class));
//            }
//        });

    }
}