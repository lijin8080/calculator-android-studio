package com.lj.calc;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {

    private ListView lv;
    private List<Equal> list;
    private MyAdapter adapter;
    private TextView tv1;
    private TextView tv2;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_listview);
        queryAll();
        lv = (ListView) findViewById(R.id.lv1);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
            View view = View.inflate(HistoryActivity.this, R.layout.history_item, null);
            tv1 = (TextView) view.findViewById(R.id.tv1);
            tv2 = (TextView) view.findViewById(R.id.tv2);
            final Equal a = list.get(arg0);
            tv1.setText(a.getA());
            tv2.setText(a.getResult());
            return view;
        }
    }

    //查询所有数据，并添加到list中
    public void queryAll() {
        db = MainActivity.cal.getWritableDatabase();
        Cursor cursor = db.query("a", null, null, null, null, null, null);
        list = new ArrayList<Equal>();
        while (cursor.moveToNext()) {
            String a = cursor.getString(0);
            String result = cursor.getString(1);
            list.add(new Equal(a, result));
        }
        cursor.close();
        db.close();
    }

    //清楚历史记录
    public void clean(View view) {
        db = MainActivity.cal.getWritableDatabase();
        db.execSQL("delete from a");
        list.clear();
        db.close();
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }
}
