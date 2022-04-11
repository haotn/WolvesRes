package com.wolvesres.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

	/**
	 * Male Fristname Vietnamese String Array
	 */
	/*
	 * private String[] maleFirstnameVietnamese = new String[] { "Tuấn", "Nam",
	 * "Trung", "Hùng", "Dũng", "Tiến", "Đạt", "Đức", "Trường", "Quang", "Huy",
	 * "Mạnh", "Minh", "Hoàng", "Kiên", "Nhân", "Hậu", "Kiệt", "Anh" };
	 */
	/**
	 * Male Lastname Vietnamese String Array
	 */
	/*
	 * private String[] maleLastnameVietnamese = new String[] { "Nguyễn Văn",
	 * "Trần Văn", "Lê Văn", "Huỳnh Văn", "Phan Thanh", "Phạm Văn", "Trần Lê Thanh",
	 * "Nguyễn Thanh", "Trần Thanh", "Lê Thanh", "Phan Văn", "Lê Thanh", "Lê Tuấn",
	 * "Trần Tiến", "Trần Tuấn", "Lê Huỳnh", "Phan Huỳnh", "Trần Huỳnh",
	 * "Nguyễn Bảo", "Trần Bảo", "Lê Việt", "Trần Việt" };
	 */
	/**
	 * Female Firstname Vietnamese String Array
	 */
	/*
	 * private String[] femaleFistnameVietnamese = new String[] { "Hoa", "Huỳnh",
	 * "Thúy", "Liễu", "Cúc", "Thanh", "Đào", "Ngân", "Thảo", "Chi", "Hạnh", "Trúc",
	 * "Trân", "Trâm", "Trinh", "An", "Quỳnh", "Loan", "Anh", "Lan" };
	 */
	/**
	 * Female Lastname Vietnamese String Array
	 */
	/*
	 * private String[] femaleLastnameVietnamese = new String[] { "Nguyễn Thị",
	 * "Trần Thị", "Lê Thị", "Phan Thị", "Phạm Thị", "Huỳnh Thị", "Nguyễn Huỳnh",
	 * "Trần Huỳnh", "Phạm Huỳnh", "Lê Huỳnh", "Phan Huỳnh", "Đào Huỳnh Thảo",
	 * "Trần Lê Huỳnh", "Nguyễn Lê Huỳnh", "Trần Nguyễn Huỳnh", "Trần Huyền",
	 * "Phạm Huyền", "Lê Huyền", "Phan Huyền", "Nguyễn Huyền" };
	 */
	/**
	 * EmailExtension
	 */
	/*
	 * private String[] emailExtension = new String[] { "@gmail.com", "@fpt.edu.vn"
	 * };
	 */

	/**
	 * Lorem
	 */
	/*
	 * private String[] lorem = new String[] {
	 * "Video provides a powerful way to help you prove your point. When you click Online Video, you can paste in the embed code for the video you want to add. You can also type a keyword to search online for the video that best fits your document."
	 * ,
	 * "To make your document look professionally produced, Word provides header, footer, cover page, and text box designs that complement each other. For example, you can add a matching cover page, header, and sidebar. Click Insert and then choose the elements you want from the different galleries."
	 * ,
	 * "Themes and styles also help keep your document coordinated. When you click Design and choose a new Theme, the pictures, charts, and SmartArt graphics change to match your new theme. When you apply styles, your headings change to match the new theme."
	 * ,
	 * "Save time in Word with new buttons that show up where you need them. To change the way a picture fits in your document, click it and a button for layout options appears next to it. When you work on a table, click where you want to add a row or a column, and then click the plus sign."
	 * ,
	 * "Reading is easier, too, in the new Reading view. You can collapse parts of the document and focus on the text you want. If you need to stop reading before you reach the end, Word remembers where you left off - even on another device."
	 * ,
	 * "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna. Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus."
	 * ,
	 * "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci. Aenean nec lorem. In porttitor. Donec laoreet nonummy augue."
	 * ,
	 * "Suspendisse dui purus, scelerisque at, vulputate vitae, pretium mattis, nunc. Mauris eget neque at sem venenatis eleifend. Ut nonummy."
	 * ,
	 * "Fusce aliquet pede non pede. Suspendisse dapibus lorem pellentesque magna. Integer nulla."
	 * ,
	 * "Donec blandit feugiat ligula. Donec hendrerit, felis et imperdiet euismod, purus ipsum pretium metus, in lacinia nulla nisl eget sapien. Donec ut est in lectus consequat consequat."
	 * ,
	 * "Etiam eget dui. Aliquam erat volutpat. Sed at lorem in nunc porta tristique."
	 * ,
	 * "Proin nec augue. Quisque aliquam tempor magna. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas."
	 * ,
	 * "Nunc ac magna. Maecenas odio dolor, vulputate vel, auctor ac, accumsan id, felis. Pellentesque cursus sagittis felis."
	 * ,
	 * "Pellentesque porttitor, velit lacinia egestas auctor, diam eros tempus arcu, nec vulputate augue magna vel risus. Cras non magna vel ante adipiscing rhoncus. Vivamus a mi."
	 * , "Morbi neque. Aliquam erat volutpat. Integer ultrices lobortis eros.",
	 * "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin semper, ante vitae sollicitudin posuere, metus quam iaculis nibh, vitae scelerisque nunc massa eget pede. Sed velit urna, interdum vel, ultricies vel, faucibus at, quam."
	 * ,
	 * "Donec elit est, consectetuer eget, consequat quis, tempus quis, wisi. In in nunc. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos."
	 * ,
	 * "Donec ullamcorper fringilla eros. Fusce in sapien eu purus dapibus commodo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus."
	 * , "Cras faucibus condimentum odio. Sed ac ligula. Aliquam at eros.",
	 * "Etiam at ligula et tellus ullamcorper ultrices. In fermentum, lorem non cursus porttitor, diam urna accumsan lacus, sed interdum wisi nibh nec nisl. Ut tincidunt volutpat urna."
	 * ,
	 * "Mauris eleifend nulla eget mauris. Sed cursus quam id felis. Curabitur posuere quam vel nibh."
	 * ,
	 * "Cras dapibus dapibus nisl. Vestibulum quis dolor a felis congue vehicula. Maecenas pede purus, tristique ac, tempus eget, egestas quis, mauris."
	 * ,
	 * "Curabitur non eros. Nullam hendrerit bibendum justo. Fusce iaculis, est quis lacinia pretium, pede metus molestie lacus, at gravida wisi ante at libero."
	 * ,
	 * "Quisque ornare placerat risus. Ut molestie magna at mi. Integer aliquet mauris et nibh."
	 * ,
	 * "Ut mattis ligula posuere velit. Nunc sagittis. Curabitur varius fringilla nisl."
	 * , "Duis pretium mi euismod erat. Maecenas id augue. Nam vulputate.",
	 * "Duis a quam non neque lobortis malesuada. Praesent euismod. Donec nulla augue, venenatis scelerisque, dapibus a, consequat at, leo."
	 * ,
	 * "Pellentesque libero lectus, tristique ac, consectetuer sit amet, imperdiet ut, justo. Sed aliquam odio vitae tortor. Proin hendrerit tempus arcu."
	 * ,
	 * "In hac habitasse platea dictumst. Suspendisse potenti. Vivamus vitae massa adipiscing est lacinia sodales."
	 * ,
	 * "Donec metus massa, mollis vel, tempus placerat, vestibulum condimentum, ligula. Nunc lacus metus, posuere eget, lacinia eu, varius quis, libero. Aliquam nonummy adipiscing augue."
	 * ,
	 * "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna."
	 * , "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.",
	 * "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci."
	 * };
	 */
	private String upperCase = "qwertyuiopasdfghjklzxcvbnm";
	private String lowerCase = "QWERTYUIOPASDFGHJKLZXCVBNM";
	private String number = "0123456789";
	private String symbol = "!@#$^&*<>?/{}[]()=+";
	private Map<String, Character[]> mapUTF8 = new HashMap<String, Character[]>();
	private List<String> maleFirstnameVi;
	private List<String> maleLastnameVi;
	private List<String> femaleFirstnameVi;
	private List<String> femaleLastnameVi;
	private List<String> emailExtension;
	private List<String> lorem;

	public Data() {
		this.generateMapUTF8();
		this.generateEmailExtension();
		this.generateFemaleFirstnameVi();
		this.generateFemaleLastnameVi();
		this.generateLorem();
		this.generateMaleFistnameVi();
		this.generateMaleLastnameVi();
	}

	private void generateMapUTF8() {
		mapUTF8.put("a", new Character[] { 'á', 'à', 'ả', 'ã', 'ạ', 'â', 'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ', 'ă', 'ắ', 'ằ', 'ẳ',
				'ẵ', 'ặ' });
		mapUTF8.put("e", new Character[] { 'é', 'è', 'ẻ', 'ẽ', 'ẹ', 'ê', 'ế', 'ề', 'ể', 'ễ', 'ệ' });
		mapUTF8.put("o", new Character[] { 'ó', 'ò', 'ỏ', 'õ', 'ọ', 'ô', 'ố', 'ồ', 'ổ', 'ỗ', 'ộ', 'ơ', 'ớ', 'ờ', 'ở',
				'ỡ', 'ợ' });
		mapUTF8.put("u", new Character[] { 'ú', 'ù', 'ủ', 'ũ', 'ụ', 'ư', 'ứ', 'ừ', 'ử', 'ữ', 'ự' });
		mapUTF8.put("i", new Character[] { 'í', 'ì', 'ỉ', 'ĩ', 'ị' });
		mapUTF8.put("y", new Character[] { 'ý', 'ỳ', 'ỷ', 'ỹ', 'ỵ' });
		mapUTF8.put("d", new Character[] { 'đ' });
	}

	private void generateMaleFistnameVi() {
		maleFirstnameVi = new ArrayList<String>();
		String firstnames = "Tuấn;Nam;Trung;Hùng;Dũng;Tiến;Đạt;Đức;Trường;Quang;Huy;Mạnh;Minh;Hoàng;Kiên;Nhân;Hậu;Kiệt;Anh";
		for (String item : firstnames.split(";")) {
			maleFirstnameVi.add(item);
		}
	}

	private void generateMaleLastnameVi() {
		maleLastnameVi = new ArrayList<String>();
		String lastnames = "Nguyễn Văn;Trần Văn;Lê Văn;Huỳnh Văn;Phan Thanh;Phạm Văn;Trần Lê Thanh;Nguyễn Thanh;"
				+ "Trần Thanh;Lê Thanh;Phan Văn;Lê Thanh;Lê Tuấn;Trần Tiến;Trần Tuấn;Lê Huỳnh;Phan Huỳnh;"
				+ "Trần Huỳnh;Nguyễn Bảo;Trần Bảo;Lê Việt;Trần Việt";
		for (String item : lastnames.split(";")) {
			maleLastnameVi.add(item);
		}
	}

	private void generateFemaleFirstnameVi() {
		femaleFirstnameVi = new ArrayList<String>();
		String firstnames = "Hoa;Huỳnh;Thúy;Liễu;Cúc;Thanh;Đào;Ngân;Thảo;Chi;Hạnh;Trúc;Trân;Trâm;Trinh;An;Quỳnh;Loan;Anh;Lan";
		for (String item : firstnames.split(";")) {
			femaleFirstnameVi.add(item);
		}
	}

	private void generateFemaleLastnameVi() {
		femaleLastnameVi = new ArrayList<String>();
		String lastnames = "Nguyễn Thị;Trần Thị;Lê Thị;Phan Thị;Phạm Thị;Huỳnh Thị;Nguyễn Huỳnh;"
				+ "Trần Huỳnh;Phạm Huỳnh;Lê Huỳnh;Phan Huỳnh;Đào Huỳnh Thảo;Trần Lê Huỳnh;Nguyễn Lê Huỳnh;"
				+ "Trần Nguyễn Huỳnh;Trần Huyền;Phạm Huyền;Lê Huyền;Phan Huyền;Nguyễn Huyền";
		for (String item : lastnames.split(";")) {
			femaleLastnameVi.add(item);
		}
	}

	private void generateEmailExtension() {
		emailExtension = new ArrayList<String>();
		emailExtension.add("@gmail.com");
		emailExtension.add("@fpt.edu.vn");
	}

	private void generateLorem() {
		lorem = new ArrayList<String>();
		String lorems = "Video provides a powerful way to help you prove your point. "
				+ "When you click Online Video, you can paste in the embed code for the video you "
				+ "want to add. You can also type a keyword to search online for the video that"
				+ " best fits your document.;To make your document look professionally produced, "
				+ "Word provides header, footer, cover page, and text box designs that complement "
				+ "each other. For example, you can add a matching cover page, header, and sidebar. "
				+ "Click Insert and then choose the elements you want from the different galleries.;"
				+ "Themes and styles also help keep your document coordinated. When you click Design "
				+ "and choose a new Theme, the pictures, charts, and SmartArt graphics change to match "
				+ "your new theme. When you apply styles, your headings change to match the new theme.;"
				+ "Save time in Word with new buttons that show up where you need them. To change the "
				+ "way a picture fits in your document, click it and a button for layout options "
				+ "appears next to it. When you work on a table, click where you want to add a "
				+ "row or a column, and then click the plus sign.;Reading is easier, too, in the "
				+ "new Reading view. You can collapse parts of the document and focus on the text "
				+ "you want. If you need to stop reading before you reach the end, Word remembers "
				+ "where you left off - even on another device.;Lorem ipsum dolor sit amet, "
				+ "consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, "
				+ "magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo "
				+ "magna eros quis urna. Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.;"
				+ "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac "
				+ "turpis egestas. Proin pharetra nonummy pede. Mauris et orci. Aenean nec lorem. "
				+ "In porttitor. Donec laoreet nonummy augue.;Suspendisse dui purus, scelerisque at, "
				+ "vulputate vitae, pretium mattis, nunc. Mauris eget neque at sem venenatis eleifend."
				+ " Ut nonummy.;Fusce aliquet pede non pede. Suspendisse dapibus lorem pellentesque magna. "
				+ "Integer nulla.;Donec blandit feugiat ligula. Donec hendrerit, felis et imperdiet euismod, "
				+ "purus ipsum pretium metus, in lacinia nulla nisl eget sapien. Donec ut est in lectus consequat "
				+ "consequat.;Etiam eget dui. Aliquam erat volutpat. Sed at lorem in nunc porta tristique."
				+ "Proin nec augue. Quisque aliquam tempor magna. Pellentesque habitant morbi tristique "
				+ "senectus et netus et malesuada fames ac turpis egestas.;Nunc ac magna. Maecenas odio "
				+ "dolor, vulputate vel, auctor ac, accumsan id, felis. Pellentesque cursus sagittis felis.;"
				+ "Pellentesque porttitor, velit lacinia egestas auctor, diam eros tempus arcu, nec vulputate "
				+ "augue magna vel risus. Cras non magna vel ante adipiscing rhoncus. Vivamus a mi.;Morbi neque. "
				+ "Aliquam erat volutpat. Integer ultrices lobortis eros.;Pellentesque habitant morbi tristique "
				+ "senectus et netus et malesuada fames ac turpis egestas. Proin semper, ante vitae sollicitudin "
				+ "posuere, metus quam iaculis nibh, vitae scelerisque nunc massa eget pede. Sed velit urna, "
				+ "interdum vel, ultricies vel, faucibus at, quam.;Donec elit est, consectetuer eget, consequat quis, "
				+ "tempus quis, wisi. In in nunc. Class aptent taciti sociosqu ad litora torquent per conubia nostra, "
				+ "per inceptos hymenaeos.;Donec ullamcorper fringilla eros. Fusce in sapien eu purus dapibus commodo. "
				+ "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.;Cras faucibus "
				+ "condimentum odio. Sed ac ligula. Aliquam at eros.;Etiam at ligula et tellus ullamcorper ultrices. "
				+ "In fermentum, lorem non cursus porttitor, diam urna accumsan lacus, sed interdum wisi nibh nec nisl. "
				+ "Ut tincidunt volutpat urna.;Mauris eleifend nulla eget mauris. Sed cursus quam id felis. Curabitur "
				+ "posuere quam vel nibh.;Cras dapibus dapibus nisl. Vestibulum quis dolor a felis congue vehicula. "
				+ "Maecenas pede purus, tristique ac, tempus eget, egestas quis, mauris.;Curabitur non eros. Nullam "
				+ "hendrerit bibendum justo. Fusce iaculis, est quis lacinia pretium, pede metus molestie lacus, "
				+ "at gravida wisi ante at libero.;Quisque ornare placerat risus. Ut molestie magna at mi. Integer "
				+ "aliquet mauris et nibh.;Ut mattis ligula posuere velit. Nunc sagittis. Curabitur varius fringilla "
				+ "nisl.;Duis pretium mi euismod erat. Maecenas id augue. Nam vulputate.;Duis a quam non neque lobortis "
				+ "malesuada. Praesent euismod. Donec nulla augue, venenatis scelerisque, dapibus a, consequat at, "
				+ "leo.;Pellentesque libero lectus, tristique ac, consectetuer sit amet, imperdiet ut, justo. "
				+ "Sed aliquam odio vitae tortor. Proin hendrerit tempus arcu.;In hac habitasse platea dictumst. "
				+ "Suspendisse potenti. Vivamus vitae massa adipiscing est lacinia sodales.;Donec metus massa, "
				+ "mollis vel, tempus placerat, vestibulum condimentum, ligula. Nunc lacus metus, posuere eget, "
				+ "lacinia eu, varius quis, libero. Aliquam nonummy adipiscing augue.;Lorem ipsum dolor sit amet, "
				+ "consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar "
				+ "ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.;Nunc viverra "
				+ "imperdiet enim. Fusce est. Vivamus a tellus.;Pellentesque habitant morbi tristique senectus et "
				+ "netus et malesuada fames ac turpis egestas. Proin pharetra nonummy pede. Mauris et orci.";
		for (String item : lorems.split(";")) {
			lorem.add(item);
		}
	}

	public String getUpperCase() {
		return upperCase;
	}

	public void setUpperCase(String upperCase) {
		this.upperCase = upperCase;
	}

	public String getLowerCase() {
		return lowerCase;
	}

	public void setLowerCase(String lowerCase) {
		this.lowerCase = lowerCase;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Map<String, Character[]> getMapUTF8() {
		return mapUTF8;
	}

	public void setMapUTF8(Map<String, Character[]> mapUTF8) {
		this.mapUTF8 = mapUTF8;
	}

	public List<String> getMaleFirstnameVi() {
		return maleFirstnameVi;
	}

	public void setMaleFirstnameVi(List<String> maleFirstnameVi) {
		this.maleFirstnameVi = maleFirstnameVi;
	}

	public List<String> getMaleLastnameVi() {
		return maleLastnameVi;
	}

	public void setMaleLastnameVi(List<String> maleLastnameVi) {
		this.maleLastnameVi = maleLastnameVi;
	}

	public List<String> getFemaleFirstnameVi() {
		return femaleFirstnameVi;
	}

	public void setFemaleFirstnameVi(List<String> femaleFirstnameVi) {
		this.femaleFirstnameVi = femaleFirstnameVi;
	}

	public List<String> getFemaleLastnameVi() {
		return femaleLastnameVi;
	}

	public void setFemaleLastnameVi(List<String> femaleLastnameVi) {
		this.femaleLastnameVi = femaleLastnameVi;
	}

	public List<String> getEmailExtension() {
		return emailExtension;
	}

	public void setEmailExtension(List<String> emailExtension) {
		this.emailExtension = emailExtension;
	}

	public List<String> getLorem() {
		return lorem;
	}

	public void setLorem(List<String> lorem) {
		this.lorem = lorem;
	}

}
