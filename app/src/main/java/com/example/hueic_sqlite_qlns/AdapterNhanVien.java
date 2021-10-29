package com.example.hueic_sqlite_qlns;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterNhanVien extends BaseAdapter {
    Context context;
    ArrayList<NhanVien> list;
    SQLiteDatabase database;
    String DATABASE_NAME ="QLNS.db";

    public AdapterNhanVien(Context context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_nhanvien,null);

        TextView txtMaSV = (TextView)row.findViewById(R.id.textViewMaNV);
        TextView txtTenNV = (TextView)row.findViewById(R.id.textViewTenNV);
        TextView txtSDT = (TextView)row.findViewById(R.id.textViewSDT);
        ImageView txtAnh = (ImageView) row.findViewById(R.id.imageViewAnhThem);
        Button btnSua = (Button)row.findViewById(R.id.buttonSua);
        Button btnXoa = (Button)row.findViewById(R.id.buttonXoa);




        final NhanVien nhanVien = list.get(i);
        txtMaSV.setText(nhanVien.MaNV+"");
        txtTenNV.setText(nhanVien.TenNV);
        txtSDT.setText(nhanVien.SDT);
        Bitmap Bitmap = BitmapFactory.decodeByteArray(nhanVien.Anh,0,nhanVien.Anh.length);
        txtAnh.setImageBitmap(Bitmap);


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("ID",nhanVien.MaNV);
                context.startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa hay không ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(nhanVien.MaNV);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return row;
    }

    private void delete(int maNV) {
        database = Database.initDatabase((Activity) context, DATABASE_NAME);
        database.delete("NhanVien","manv =?", new String[] {maNV+""});

        Cursor cursor = database.rawQuery("Select * from NhanVien",null);
        list.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tennv = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            list.add(new NhanVien(id,tennv,sdt,anh));
        }
        notifyDataSetChanged();

    }
}
