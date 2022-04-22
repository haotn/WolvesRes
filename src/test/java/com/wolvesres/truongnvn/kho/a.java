package com.wolvesres.truongnvn.kho;

import java.util.List;

 
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.model.ModelSanPham;

public class a {
	public static void main(String[] args) {
		SanPhamDAO spdao = new SanPhamDAO();
		List<ModelSanPham> list = spdao.selectAll();
		for(ModelSanPham sp: list) {
			if (sp.isTrangThai()==true) {
				System.out.println(sp.getMaSP() + "-" + sp.getGiaBan());
			}
		}
	}
}
