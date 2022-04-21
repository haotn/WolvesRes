package com.wolvesres.model;

import java.util.ArrayList;
import java.util.List;

import com.wolvesres.dao.KhuBanDAO;
import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.ModelAction;
import com.wolvesres.swing.table.ModelProfile;
/**
 * Thêm các hàm thêm sửa xóa
 * @author huynh
 *
 */
public class ModelKhuBan {

    private String maKhuBan;
    private String tenKhuBan;
    private String ghiChu;



    @Override
	public String toString() {
		return maKhuBan + " - " + tenKhuBan + " - " + ghiChu;
	}

	public ModelKhuBan() {
    }

    public ModelKhuBan(String maKhuBan, String tenKhuBan, String ghiChu) {
        this.maKhuBan = maKhuBan;
        this.tenKhuBan = tenKhuBan;
        this.ghiChu = ghiChu;
    }

    /**
     * @return the tenKhuBan
     */
    public String getTenKhuBan() {
        return tenKhuBan;
    }

    /**
     * @param tenKhuBan the tenKhuBan to set
     */
    public void setTenKhuBan(String tenKhuBan) {
        this.tenKhuBan = tenKhuBan;
    }

    /**
     * @return the ghiChu
     */
    public String getGhiChu() {
        return ghiChu;
    }

    /**
     * @param ghiChu the ghiChu to set
     */
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    /**
     * @return the maKhuBan
     */
    public String getMaKhuBan() {
        return maKhuBan;
    }

    /**
     * @param maKhuBan the maKhuBan to set
     */
    public void setMaKhuBan(String maKhuBan) {
        this.maKhuBan = maKhuBan;
    }

    public Object[] toRowTable(EventAction event) {
        return new Object[]{getMaKhuBan(), getTenKhuBan(), getGhiChu(), new ModelAction(this, event)};
    }

    public Object[] toRowModel() {
        return new Object[]{getMaKhuBan(), getTenKhuBan(), getGhiChu()};
    }
    
    private KhuBanDAO daokb = new KhuBanDAO();
    
    
    
}
