package exceldoing;

import java.io.IOException;

import com.wolvesres.model.ModelNhanVien;

public class WriteResult {
	public static void writeResultNhanVien(Object[][] originData, int rowStart) {
		Object[][] datac = new Object[originData.length][10];
		for (int i = 0; i < originData.length; i++) {
			System.err.println(originData[i][0]);
			if (originData[i][0] instanceof ModelNhanVien) {
				System.out.println("Converted");
				ModelNhanVien nv = (ModelNhanVien) originData[i][0];
				datac[i][0] = nv.getMaNV();
				datac[i][1] = nv.getHoTen();
				datac[i][2] = nv.getChucVu();
				datac[i][3] = nv.getCMND();
				datac[i][4] = nv.getEmail();
				datac[i][5] = nv.getSoDT();
				datac[i][6] = nv.getNgaySinh();
				datac[i][7] = nv.getPathHinhAnh();
				datac[i][8] = nv.isGioiTinh();
				datac[i][9] = nv.isTrangThai();
			}
		}
		try {
			ExcelGo.writeExcelv2("excel-file/asm-temp-demo.xlsx", 2, rowStart, 6,
					"MaNhanVien,HoTen,ChucVu,CCCD/CMND,Email,SDT,NgaySinh,PathHinhAnh,Gender,TrangThai", datac);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
