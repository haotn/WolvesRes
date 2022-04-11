package com.wolvesres.helper;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

public class XImage {

	public static Image getAppIcon() {
		URL url = XImage.class.getResource("/com/wolvesres/icon/wolf.png");
		return new ImageIcon(url).getImage();
	}

	private static void saveImage(String folderName, File src) {
		File dst = new File(folderName, src.getName());
		if (!dst.getParentFile().exists()) {
			dst.getParentFile().mkdirs();
		}
		try {
			Path from = Paths.get(src.getAbsolutePath());
			Path to = Paths.get(dst.getAbsolutePath());
			Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static ImageIcon readImage(String folderName, String fileName) {
		if (fileName == null) {
			fileName = "";
		}
		File path = new File(folderName, fileName);
		return new ImageIcon(path.getAbsolutePath());
	}

	public static void saveImageNhanVien(File src) {
		saveImage("Pictures", src);
	}

	public static ImageIcon readImageNhanVien(String fileName) {
		return readImage("Pictures", fileName);
	}

	public static void saveImageThucDon(File src) {
		saveImage("ThucDon", src);
	}

	public static ImageIcon readImageThucDon(String fileName) {
		return readImage("ThucDon", fileName);
	}

	public static void saveImageQR(File src) {
		saveImage("QRCode", src);
	}

	public static ImageIcon readImageQRCode(String fileName) {
		return readImage("QRCode", fileName);
	}
}
