package com.wolvesres.model;

import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.ModelAction;
import com.wolvesres.swing.table.ModelProfile;
import java.util.ArrayList;
import java.util.List;
/**
 * Thêm các hàm Thêm sửa xóa 
 * @author huynh
 *
 */
public class ModelDonViTinh {

    private int maDVT;
    private String tenDVT;

    @Override
	public String toString() {
		return "ModelDonViTinh [maDVT=" + maDVT + ", tenDVT=" + tenDVT + "]";
	}

    public ModelDonViTinh() {
    }

    public ModelDonViTinh(int maDVT, String tenDVT) {
        this.maDVT = maDVT;
        this.tenDVT = tenDVT;
    }

    /**
     * @return the maDVT
     */
    public int getMaDVT() {
        return maDVT;
    }

    /**
     * @param maDVT the maDVT to set
     */
    public void setMaDVT(int maDVT) {
        this.maDVT = maDVT;
    }

    /**
     * @return the tenDVT
     */
    public String getTenDVT() {
        return tenDVT;
    }

    /**
     * @param tenDVT the tenDVT to set
     */
    public void setTenDVT(String tenDVT) {
        this.tenDVT = tenDVT;
    }
    
        public Object[] toRowTable(EventAction event) {
        return new Object[]{getMaDVT(), getTenDVT(), new ModelAction(this, event)};
    }
        
}
