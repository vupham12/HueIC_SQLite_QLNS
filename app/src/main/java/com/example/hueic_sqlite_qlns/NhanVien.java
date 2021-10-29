package com.example.hueic_sqlite_qlns;

public class NhanVien {
    public int MaNV;
    public String TenNV;
    public String SDT;
    public byte[] Anh;

    public NhanVien(int maSV, String tenNV, String SDT, byte[] anh) {
        MaNV = maSV;
        TenNV = tenNV;
        this.SDT = SDT;
        Anh = anh;
    }
}
