package com.example.hueic_sqlite_qlns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DATABASE_NAME ="QLNS.db";
    SQLiteDatabase database;
    ListView lstDSNV;
    ArrayList<NhanVien>list;
    AdapterNhanVien adapterNhanVien;
    Button btnThemNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThemNV = (Button)findViewById(R.id.buttonThemNV);

        addEvent();
        lstDSNV =(ListView) findViewById(R.id.listViewDSNV);
        list = new ArrayList<>();
        adapterNhanVien = new AdapterNhanVien(MainActivity.this,list);
        lstDSNV.setAdapter(adapterNhanVien);

        database = Database.initDatabase(MainActivity.this,DATABASE_NAME);

        Cursor cursor = database.rawQuery("Select * from NhanVien",null);
        for (int i=0; i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int manv = cursor.getInt(0);
            String tennv = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new NhanVien(manv, tennv,sdt,anh));
        }
        adapterNhanVien.notifyDataSetChanged();
    }
    private void addEvent(){
        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });
    }
}
