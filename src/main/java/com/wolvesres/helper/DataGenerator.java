package com.wolvesres.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelSanPham;

public class DataGenerator {
	private Data data;
	private SimpleDateFormat formater;
	private static Long userMinId;
	private static Long userMaxId;
	private static Long videoMinId;
	private static Long videoMaxId;
	private Long commentMinId;
	private Long commentMaxId;

	public DataGenerator() {
		formater = new SimpleDateFormat("yyyy-MM-dd");
		data = new Data();
	}

	public int randomMinMax(int min, int max) {
		Random random = new Random();
		int numb = random.nextInt(max + 1 - min) + min;
		return numb;
	}

	public Long randomMinMax(Long min, Long max) {
		Random random = new Random();
		Long numb = random.nextLong(max + 1 - min) + min;
		return numb;
	}

	public double randomMinMax(double min, double max) {
		Random random = new Random();
		double numb = random.nextDouble(max + 1 - min) + min;
		return numb;
	}

	public String generateTextAndNumber(double min, double max) {
		String text = "";
		int countLetter = 1;
		double price = randomMinMax(min, max);
		text = String.valueOf(price);
		for (int i = 0; i < countLetter; i++) {
			char[] arr = text.toCharArray();
			text = "";
			int index = randomMinMax(0, String.valueOf(price).length() - 1);
			char letter = data.getUpperCase().charAt(randomMinMax(0, data.getUpperCase().length() - 1));
			arr[index] = letter;
			for (int j = 0; j < arr.length; j++) {
				text += String.valueOf(arr[j]);
			}
		}
		return text;
	}

	public Long randVideoId() {
		Long id = randomMinMax(videoMinId, videoMaxId);
		while (id == null) {
			id = randomMinMax(videoMinId, videoMaxId);
		}
		return id;
	}

	public Long randUserId() {
		Long id = randomMinMax(userMinId, userMaxId);
		while (id == null) {
			id = randomMinMax(userMinId, userMaxId);
		}
		return id;
	}

	public Long randCommentId() {
		Long id = randomMinMax(commentMinId, commentMaxId);
		while (id == null) {
			id = randomMinMax(commentMinId, commentMaxId);
		}
		return id;
	}

	public String generateCookieID() {
		String cookieId = "";
		for (int i = 0; i < 100; i++) {
			int random = (int) (3 * Math.random());
			switch (random) {
			case 0:
				cookieId += String
						.valueOf(data.getLowerCase().charAt(randomMinMax(0, data.getLowerCase().length() - 1)));
				break;
			case 1:
				cookieId += String
						.valueOf(data.getUpperCase().charAt(randomMinMax(0, data.getUpperCase().length() - 1)));
				break;
			case 2:
				cookieId += String.valueOf(data.getNumber().charAt(randomMinMax(0, data.getNumber().length() - 1)));
				break;
			}
		}
		return cookieId;
	}

	public Date generateDate(int from, int to) {
		Date date = new Date();
		int year = randomMinMax(from, to);
		int month = randomMinMax(1, 12);
		int day = 0;
		Integer[] m31 = new Integer[] { 1, 3, 5, 7, 8, 10, 12 };
		if (month == 2) {
			day = randomMinMax(1, 28);
		} else {
			boolean exist = false;
			for (Integer m : m31) {
				if (month == m.longValue()) {
					day = randomMinMax(1, 31);
					exist = true;
					break;
				}
			}
			if (!exist) {
				day = randomMinMax(1, 31);
			}
		}
		try {
			date = formater.parse(String.valueOf(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public String generateFullname(boolean threeWord, boolean randUpperCase, boolean symbol) {
		String fullname = "";
		int gender = randomMinMax(0, 1);
		List<String> firstnames = data.getMaleFirstnameVi();
		List<String> lastnames = data.getMaleLastnameVi();
		if (gender != 1) {
			firstnames = data.getFemaleFirstnameVi();
			lastnames = data.getFemaleLastnameVi();
		}
		String firstname = firstnames.get(randomMinMax(0, firstnames.size() - 1));
		String lastname = lastnames.get(randomMinMax(0, lastnames.size() - 1));

		if (!threeWord) {
			lastname = lastname.substring(0, lastname.indexOf(" "));
		}
		fullname = lastname + firstname;
		Double countUpper = Math.floor(fullname.length() / 2);
		if (randUpperCase) {
			for (int i = 0; i < countUpper.intValue(); i++) {
				int index = randomMinMax(0, fullname.length() - 1);
				char[] arr = fullname.toCharArray();
				fullname = "";
				for (int j = 0; j < arr.length; j++) {
					if (j == index) {
						fullname += String.valueOf(arr[j]).toUpperCase();
					}
					fullname += String.valueOf(arr[j]);
				}
			}
		}
		if (symbol) {
			for (int i = 0; i < countUpper.intValue(); i++) {
				int index = randomMinMax(0, fullname.length() - 1);
				char[] arr = fullname.toCharArray();
				fullname = "";
				for (int j = 0; j < arr.length; j++) {
					if (j == index) {
						fullname += String
								.valueOf(data.getSymbol().charAt(randomMinMax(0, data.getSymbol().length() - 1)));
					}
					fullname += String.valueOf(arr[j]);
				}
			}
		}
		return lastname + " " + firstname;
	}

	public List<String> generateListFullname(int amount, boolean allowDuplicate, boolean isValid) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < amount; i++) {
			int upper = randomMinMax(0, 1);
			String fullname = generateFullname(true, true, true);
			if (upper == 0) {
				fullname = generateFullname(true, false, true);
			}

			if (!isValid) {
				fullname = generateFullname(true, false, false);
			}
			if (allowDuplicate) {
				list.add(fullname);
			} else {
				if (list.size() > 0) {
					String exist = null;
					for (String n : list) {
						if (fullname.equals(n)) {
							exist = n;
							i = i - 1;
							break;
						}
					}
					if (exist != null) {
						while (fullname.equals(exist)) {
							fullname = generateFullname(true, false, false);
							if (!isValid) {
								fullname = generateFullname(true, true, false);
							}
						}
						list.add(fullname);
					} else {
						list.add(fullname);
					}
				} else {
					list.add(fullname);
				}
			}
		}
		return list;
	}

	public String generateEmailName(String fullname) {
		String emailname = "";
		String[] fullnameArray = fullname.split(" ");
		String firstname = fullnameArray[fullnameArray.length - 1];
		emailname = firstname.toLowerCase();
		for (int i = 0; i < fullnameArray.length - 1; i++) {
			emailname += fullnameArray[i].toLowerCase().charAt(0);
		}
		char[] emailnameCharArray = emailname.toCharArray();
		emailname = "";
		for (char letter : emailnameCharArray) {
			boolean exist = false;
			for (String key : data.getMapUTF8().keySet()) {
				for (char value : data.getMapUTF8().get(key)) {
					if (String.valueOf(letter).equalsIgnoreCase(String.valueOf(value))) {
						emailname += key;
						exist = true;
						break;
					}
				}
			}
			if (!exist) {
				emailname += String.valueOf(letter);
			}
		}
		return emailname;
	}

	public String generateEmail(String emailname) {
		String email = "";
		int rand = randomMinMax(0, data.getEmailExtension().size() - 1);
		email = emailname + data.getEmailExtension().get(rand);
		return email;
	}

	public Map<String, String> generateMapEmail(List<String> listFullname) {
		Map<String, String> map = new HashMap<String, String>();
		for (String fullname : listFullname) {
			String emailname = generateEmailName(fullname);
			if (!map.isEmpty()) {
				String emailnameExist = "";
				for (String key : map.keySet()) {
					if (emailname.equals(key.substring(0, key.lastIndexOf("@") - 1))) {
						emailnameExist = key.substring(0, key.lastIndexOf("@") - 1);
						break;
					}
				}
				if (emailnameExist != "") {
					int i = 1;
					while (true) {
						String termp = emailname;
						termp += "0" + i;
						i++;
						if (!termp.equals(emailnameExist)) {
							emailname = termp;
							break;
						}
					}
					map.put(generateEmail(emailname), fullname);
				} else {
					map.put(generateEmail(emailname), fullname);
				}
			} else {
				map.put(generateEmail(emailname), fullname);
			}
		}
		return map;
	}

	public String generatePassword(int minLength, int maxLength, boolean alowSpace) {
		String password = "";
		int length = randomMinMax(minLength, maxLength);
		int spaceIndex = -1;
		if (alowSpace) {
			spaceIndex = randomMinMax(0, length - 1);
		}

		for (int i = 0; i < length; i++) {
			if (i == spaceIndex && alowSpace) {
				password += " ";
				continue;
			}
			int random = (int) (4 * Math.random());
			switch (random) {
			case 0:
				password += String
						.valueOf(data.getLowerCase().charAt(randomMinMax(0, data.getLowerCase().length() - 1)));
				break;
			case 1:
				password += String
						.valueOf(data.getUpperCase().charAt(randomMinMax(0, data.getUpperCase().length() - 1)));
				break;
			case 2:
				password += String.valueOf(data.getNumber().charAt(randomMinMax(0, data.getNumber().length() - 1)));
				break;
			case 3:
				password += String.valueOf(data.getSymbol().charAt(randomMinMax(0, data.getSymbol().length() - 1)));
			}
		}
		data.setLowerCase(data.getLowerCase().trim());
		return password;
	}

	public String generateUsename(String email) {
		String username = "";
		username = email.substring(0, email.lastIndexOf("@"));
		int length = randomMinMax(8, 13);
		if (username.length() < length) {
			length -= username.length();
			for (int i = 0; i < length; i++) {
				username += data.getNumber().charAt(randomMinMax(0, data.getNumber().length() - 1));
			}
		}
		return username;
	}

	public Map<String, String> generateMapUsername(Map<String, String> map) {
		Map<String, String> usernames = new HashMap<String, String>();
		for (String key : map.keySet()) {
			String username = generateUsename(key);
			if (usernames.size() > 0) {
				String exist = null;
				for (String un : usernames.keySet()) {
					if (usernames.get(un).equals(exist)) {
						exist = usernames.get(un);
						break;
					}
				}
				if (exist != null) {
					while (username.equals(exist)) {
						username = generateUsename(key);
					}
				}
				usernames.put(key, username);
			} else {
				usernames.put(key, username);
			}
		}
		return usernames;
	}

	public String generateDescription(int length) {
		String description = "";
		description = data.getLorem().get(randomMinMax(0, data.getLorem().size() - 1));
		while (description.length() < length) {
			description += data.getLorem().get(randomMinMax(0, data.getLorem().size() - 1));
		}
		return description;
	}

	public List<String> listTinh() {
		List<String> list = new ArrayList<String>();
		String[] listIsNotValidStrings = new String[] { "013", "016", "018", "021", "023", "028", "029", "032", "039",
				"041", "043", "047", "050", "053", "055", "057", "059", "061", "063", "065", "066", "069", "071", "073",
				"078", "081", "085", "088", "090" };

		for (int i = 1; i < 97; i++) {
			String nubString = "";
			if (i < 10) {
				if (i != 3 && i != 5 && i != 7 && i != 9) {
					nubString = "00" + i;
					list.add(nubString);
				}
			} else if (i >= 10) {
				boolean exist = false;
				for (int j = 0; j < listIsNotValidStrings.length; j++) {
					nubString = "0" + i;
					if (nubString.equals(listIsNotValidStrings[j])) {
						exist = true;
					}
				}
				if (!exist) {
					list.add(nubString);
				}
			}
		}
		return list;
	}

	/**
	 * Generate data
	 */
	public String generateSDT() {

		String dau = "";
		int d = ThreadLocalRandom.current().nextInt(1, 5);
		if (d == 1) {
			dau = "3";
		} else if (d == 2) {
			dau = "7";
		} else if (d == 3) {
			dau = "8";
		} else {
			dau = "9";
		}

		int sdt1 = ThreadLocalRandom.current().nextInt(0, 10);
		int sdt2 = ThreadLocalRandom.current().nextInt(0, 10);
		int sdt3 = ThreadLocalRandom.current().nextInt(0, 10);
		int sdt4 = ThreadLocalRandom.current().nextInt(0, 10);
		int sdt5 = ThreadLocalRandom.current().nextInt(0, 10);
		int sdt6 = ThreadLocalRandom.current().nextInt(0, 10);
		int sdt7 = ThreadLocalRandom.current().nextInt(0, 10);
		int sdt8 = ThreadLocalRandom.current().nextInt(0, 10);

		String sdt = "0" + dau + String.valueOf(sdt1) + String.valueOf(sdt2) + String.valueOf(sdt3)
				+ String.valueOf(sdt4) + String.valueOf(sdt5) + String.valueOf(sdt6) + String.valueOf(sdt7)
				+ String.valueOf(sdt8);
		return sdt;
	}

	public String generateIdNational(Date dateOfBirth, Boolean isMale) {
		String date = XDate.toString(dateOfBirth, "dd-MM-yyyy");
		String year = date.substring(date.lastIndexOf("-"));
		// System.out.println(nam);
		String gt = "";
		if (isMale == true) {
			gt = "0";
		} else {
			gt = "1";
		}

		if (Integer.valueOf(year) < 2000) {
			if (Integer.valueOf(gt) == 0) {
				gt = "0";
			} else {
				gt = "1";
			}
		} else {
			if (Integer.valueOf(gt) == 0) {
				gt = "2";
			} else {
				gt = "3";
			}
		}
		year = year.substring(2, 4);
		int tinh = ThreadLocalRandom.current().nextInt(0, 63);
		int tk;
		int testnam = ThreadLocalRandom.current().nextInt(0, 2);
		if (testnam == 0) {
			tk = ThreadLocalRandom.current().nextInt(0, 2);
		} else {
			tk = ThreadLocalRandom.current().nextInt(2, 4);
		}
		int ranNum1 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum2 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum3 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum4 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum5 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum6 = ThreadLocalRandom.current().nextInt(0, 10);
		String cccd = listTinh().get(tinh) + gt + year + String.valueOf(ranNum1) + String.valueOf(ranNum2)
				+ String.valueOf(ranNum3) + String.valueOf(ranNum4) + String.valueOf(ranNum5) + String.valueOf(ranNum6);
		return cccd;
	}

	public String generateIdNational() {
		Date dateOfBirth;
		Boolean isMale;
 
		int so2=0;
		int rany1 = ThreadLocalRandom.current().nextInt(1, 3);
		if(rany1==1) {
			so2=9;
		}else {
			so2=0;
		}
		int rany3 = ThreadLocalRandom.current().nextInt(0, 10);
		int rany4 = ThreadLocalRandom.current().nextInt(0, 10);
		String year=""+rany1+so2+rany3+rany4;
		int gioitinh = ThreadLocalRandom.current().nextInt(1, 3);
		if(gioitinh==1) {
			isMale =true;
		}else {
			isMale= false;
		}
		// System.out.println(nam);
		String gt = "";
		if (isMale == true) {
			gt = "0";
		} else {
			gt = "1";
		}

		if (Integer.valueOf(year) < 2000) {
			if (Integer.valueOf(gt) == 0) {
				gt = "0";
			} else {
				gt = "1";
			}
		} else {
			if (Integer.valueOf(gt) == 0) {
				gt = "2";
			} else {
				gt = "3";
			}
		}
		year = year.substring(2, 4);
		int tinh = ThreadLocalRandom.current().nextInt(0, 63);
		int tk;
		int testnam = ThreadLocalRandom.current().nextInt(0, 2);
		if (testnam == 0) {
			tk = ThreadLocalRandom.current().nextInt(0, 2);
		} else {
			tk = ThreadLocalRandom.current().nextInt(2, 4);
		}
		int ranNum1 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum2 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum3 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum4 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum5 = ThreadLocalRandom.current().nextInt(0, 10);
		int ranNum6 = ThreadLocalRandom.current().nextInt(0, 10);
		String cccd = listTinh().get(tinh) + gt + year + String.valueOf(ranNum1) + String.valueOf(ranNum2)
				+ String.valueOf(ranNum3) + String.valueOf(ranNum4) + String.valueOf(ranNum5) + String.valueOf(ranNum6);
		return cccd;
	}

	public String generateIdNationalNotValid(Date dateOfBirth, Boolean isMale) {
		String idNational = generateIdNational(dateOfBirth, isMale);
		int ranNumb = randomMinMax(1, 4);
		for (int j = 0; j < ranNumb; j++) {
			char symbol = data.getSymbol().charAt(randomMinMax(0, data.getSymbol().length() - 1));
			char[] arr = idNational.toCharArray();
			idNational = "";
			arr[j] = symbol;
			for (int k = 0; k < arr.length; k++) {
				idNational += String.valueOf(arr[k]);
			}
		}
		return idNational;
	}

	public List<ModelNhanVien> generateListNhanVien(Boolean isFullnameValid, Boolean isIdNationalValid,
			Boolean isEmailValid, Boolean isPhoneNumberValid, Boolean isBirthDateValid, int amount) {
		List<ModelNhanVien> list = new ArrayList<ModelNhanVien>();
		List<String> fullnames = generateListFullname(amount, true, true);
		if (!isFullnameValid) {
			fullnames = generateListFullname(amount, true, false);
		}
		Map<String, String> mapEmails = generateMapEmail(fullnames);
		int i = 0;
		for (String key : mapEmails.keySet()) {
			ModelNhanVien nhanVien = new ModelNhanVien();
			int role = randomMinMax(2, 4);
			nhanVien.setChucVu(role);
			int gender = randomMinMax(0, 1);
			if (gender == 1) {
				nhanVien.setGioiTinh(true);
			} else {
				nhanVien.setGioiTinh(false);
			}
			nhanVien.setNgaySinh(XDate.toString(generateDate(1990, 2003), "dd-MM-yyyy"));

			nhanVien.setCMND(
					generateIdNational(XDate.toDate(nhanVien.getNgaySinh(), "dd-MM-yyyy"), nhanVien.isGioiTinh()));
			if (!isIdNationalValid) {
				nhanVien.setCMND(generateIdNationalNotValid(XDate.toDate(nhanVien.getNgaySinh(), "dd-MM-yyyy"),
						nhanVien.isGioiTinh()));
			}
			nhanVien.setMaNV("NV" + String.valueOf(i + 20));
			nhanVien.setPathHinhAnh("anhNhanVien" + String.valueOf(i + 20));
			nhanVien.setSoDT(generateSDT());
			nhanVien.setTrangThai(true);
			nhanVien.setEmail(key);
			nhanVien.setHoTen(mapEmails.get(key));
			for (int j = 0; j < list.size(); j++) {
				while (nhanVien.getSoDT().equals(list.get(j).getSoDT())) {
					nhanVien.setSoDT(generateSDT());
				}
				while (nhanVien.getCMND().equals(list.get(j).getCMND())) {
					nhanVien.setCMND(generateIdNational(XDate.toDate(nhanVien.getNgaySinh(), "dd-MM-yyyy"),
							nhanVien.isGioiTinh()));
					if (!isIdNationalValid) {
						nhanVien.setCMND(generateIdNationalNotValid(XDate.toDate(nhanVien.getNgaySinh(), "dd-MM-yyyy"),
								nhanVien.isGioiTinh()));
					}
				}
			}
			list.add(nhanVien);
			i++;
		}
		return list;
	}

	public List<ModelSanPham> generateListSanPham(Boolean pdNameValid, Boolean priceNegative, String cagoreValid,
			String pathImg, int unitValid, Boolean statusValid, int amount) {
		DanhMucDAO dmDao = new DanhMucDAO();
		DonViTinhDAO dvtDao = new DonViTinhDAO();

		List<ModelSanPham> list = new ArrayList<ModelSanPham>();
		for (int i = 0; i < amount; i++) {
			ModelSanPham sp = new ModelSanPham();
			sp.setMaSP("SP00000" + (i + 1));
			sp.setGiaBan(randomMinMax(500000, 200000));
		}

		return list;
	}
}
