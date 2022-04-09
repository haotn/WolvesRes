package com.wolvesres.test.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataGenerator {
	private Data data;
	private SimpleDateFormat formater;
	private Long userMinId;
	private Long userMaxId;
	private Long videoMinId;
	private Long videoMaxId;
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

	public String generateFullname() {
		int gender = randomMinMax(0, 1);
		List<String> firstnames = data.getMaleFirstnameVi();
		List<String> lastnames = data.getMaleLastnameVi();
		if (gender != 1) {
			firstnames = data.getFemaleFirstnameVi();
			lastnames = data.getFemaleLastnameVi();
		}
		String firstname = String.valueOf(firstnames.get(randomMinMax(0, firstnames.size() - 1)));
		String lastname = String.valueOf(lastnames.get(randomMinMax(0, lastnames.size() - 1)));
		return lastname + " " + firstname;
	}

	public List<String> generateListFullname(int amount, boolean allowDuplicate) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < amount; i++) {
			String fullname = generateFullname();
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
							fullname = generateFullname();
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

	public String generatePassword(int length, boolean alowSpace) {
		String password = "";
		if (alowSpace) {
			data.setLowerCase(data.getLowerCase() + " ");
		}
		for (int i = 0; i < length; i++) {
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
		data.setLowerCase(data.getLowerCase().replace(" ", ""));
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

}
