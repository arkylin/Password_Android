package com.arkylin.password;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Search_All extends AppCompatActivity {

    private ListView PASSWD_ALL;
    String[] data;
    ArrayList<String> stringArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final DatabaseHelper databaseHelper = new DatabaseHelper(this,"password.db",null,2);

        PASSWD_ALL = (ListView) findViewById(R.id.viewall);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("password", new String[]{"id","name","content"}, null, null, null, null, null);
        String textview_data = "";
        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来，放到TextView中
        while(cursor.moveToNext()){
            @SuppressLint("Range") String cid = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String cname = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String ccontent = cursor.getString(cursor.getColumnIndex("content"));
            textview_data = cid + "--" + cname +"--" + ccontent;
            stringArrayList.add(textview_data);
        }
        //利用arraylist，保存数据，然后在转换成String[]数组
        String [] stringArray = stringArrayList.toArray(new String[stringArrayList.size()]);
        data = stringArray;
        //多余的一行注释掉ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,R.id.LS);//新建并配置ArrayAapeter
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        PASSWD_ALL.setAdapter(mAdapter);
    }

    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.length;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.small,parent,false);
                holder = new ViewHolder();
                holder.mTextView = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextView.setText(data[position]);
            return convertView;
        }
        class ViewHolder {
            TextView mTextView;
        }
    }
}