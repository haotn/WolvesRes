/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wolvesres.model;

import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.ModelAction;
import com.wolvesres.swing.table.ModelProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author FPT
 */
public class ModelLichSuGia {
   private int id;
   private String maSP;
   private String ngayThayDoi;
   private float gia;
   
    @Override
	public String toString() {
		return "ModelLichSuGia [id=" + id + ", maSP=" + maSP + ", ngayThayDoi=" + ngayThayDoi + ", gia=" + gia + "]";
	}

	public ModelLichSuGia() {
    }

    public ModelLichSuGia(int id, String maSP, String ngayThayDoi, float gia) {
        this.id = id;
        this.maSP = maSP;
        this.ngayThayDoi = ngayThayDoi;
        this.gia = gia;
    }
    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the maSP
     */
    public String getMaSP() {
        return maSP;
    }

    /**
     * @param maSP the maSP to set
     */
    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    /**
     * @return the ngayThayDoi
     */
    public String getNgayThayDoi() {
        return ngayThayDoi;
    }

    /**
     * @param ngayThayDoi the ngayThayDoi to set
     */
    public void setNgayThayDoi(String ngayThayDoi) {
        this.ngayThayDoi = ngayThayDoi;
    }

    /**
     * @return the gia
     */
    public float getGia() {
        return gia;
    }

    /**
     * @param gia the gia to set
     */
    public void setGia(float gia) {
        this.gia = gia;
    }
    
    List<ModelSanPham> list = new ArrayList<>();
    SanPhamDAO dao = new SanPhamDAO();
    public String TenSP(String maSP) {
        String ten = "";
        list.addAll(dao.selectAll());
        for (ModelSanPham sp : list) {
            if (maSP.equals(sp.getMaSP())) {
                ten = sp.getTenSP();
            }
        }
        return ten;
    }
   
   public Object[] toRowTable(EventAction event) {
       return new Object[]{TenSP(maSP), getMaSP(), getNgayThayDoi(), getGia(), new ModelAction(this, event)};
    }
}

