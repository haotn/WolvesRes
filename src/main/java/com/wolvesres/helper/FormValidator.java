package com.wolvesres.helper;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wolvesres.dao.GhiNhoDAO;
import com.wolvesres.model.ModelGhiNho;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelVouCher;

public class FormValidator {

	public static final Pattern VALID_IDNATIONAL_CMND = Pattern.compile("(([0-2]{1}[0-9]{1})|(3[0-8]{1}))[0-9]{7}");// !T

	private static final Pattern VALID_PHONE_NUMBER = Pattern.compile("0[3789]{1}[\\d]{8}");
	public static final Pattern VALID_IDNATIONAL = Pattern.compile(
			"0((0(1|2|4|6|8))|(1(0|1|2|4|5|7|9))|(2(0|2|4|5|6|7))|(3(0|1|3|4|5|6|7|8))|(4(0|2|4|5|6|8|9))|(5(1|2|4|6|8))|(6(0|2|4|6|7|8))|(7(0|2|4|5|7|9))|(8(0|2|3|4|6|7|9))|(9[1-6]{1}))[0-9]{1}[0-9]{2}[0-9]{6}");
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Check if id national is valid
	 * 
	 * @param idNational
	 * @return is valid
	 */
	public static boolean isValidIdNational(String idNational) {
		Matcher matcher = VALID_IDNATIONAL.matcher(idNational);
		return matcher.find();
	}

	/**
	 * Check if string is not empty
	 * 
	 * @param phoneNumber
	 * @return is phone number not empty
	 */
	public static Boolean isTextIsNotEmpty(String text) {
		if (text == null) {
			return false;
		} else if (text.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Check if text length is valid
	 * 
	 * @param phoneNumber
	 * @return is valid
	 */
	public static Boolean isValidTextLength(String text, int length) {
		if (text.trim().length() != length) {
			return false;
		}
		return true;
	}

	/**
	 * Check if text min length is valid
	 * 
	 * @param phoneNumber
	 * @return is valid
	 */
	public static Boolean isValidTextMinLength(String text, int length) {
		if (text.trim().length() < length) {
			return false;
		}
		return true;
	}

	/**
	 * Check if age is >= 18
	 * 
	 * @param birthDay
	 * @return is valid
	 */
	public static Boolean isValidAge(Date birthDay) {
		Date now = new Date();
		System.out.println(XDate.toString(XDate.addDays(birthDay, 6570), "dd/MM/yyyy") + " is before "
				+ XDate.toString(now, "dd/MM/yyyy"));
		if (XDate.addDays(birthDay, 6570).before(now)) {
			return false;
		}
		return true;
	}

	/**
	 * Valid phome number
	 * 
	 * @param phoneNumber
	 * @return is valid
	 */
	public static Boolean isValidPhoneNumber(String phoneNumber) {
		Matcher matcher = VALID_PHONE_NUMBER.matcher(phoneNumber);
		return matcher.find();
	}

	/**
	 * Valid Fullname
	 * 
	 * @param hoTen
	 * @return is valid
	 */
	public static Boolean isValidFullname(String hoTen) {
		if (!hoTen.contains(" ")) {
			return false;
		}
		return true;
	}

	/**
	 * Valid Email
	 * 
	 * @param emailStr
	 * @return is valid
	 */
	public static Boolean isValidEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	/**
	 * Check if gender code in id national is valid with year of birth between 1900
	 * nad 1999
	 * 
	 * @param isMale
	 * @param genderCode
	 * @return is valid
	 */

	public static Boolean isValidGenderCodeInCentury(Boolean isMale, int genderCode, int century) {
		if (isMale && genderCode != ((century - 20) * 2)) {
			return false;
		} else if (!isMale && genderCode != (((century - 20) * 2) + 1)) {
			return false;
		}
		return true;
	}

	/**
	 * Check if year in century
	 * 
	 * @param yearOfBirth
	 * @return is in century
	 */
	public static Boolean isYearOfBirthInCentury(int yearOfBirth, int century) {

		if (yearOfBirth >= ((century - 1) * 100) && yearOfBirth <= ((century * 100) - 1)) {
			return true;
		}
		return false;
	}

	/**
	 * Get gender code in id national
	 * 
	 * @param idNational
	 * @return gender code
	 */
	public static Integer getGenderCodeInIdNational(String idNational) {
		return Integer.parseInt(idNational.substring(3, 4));
	}

	/**
	 * Get year of birth code in id national
	 * 
	 * @param idNational
	 * @return year of birth code
	 */
	public static Integer getYearOfbirthCodeInIdNational(String idNational) {
		return Integer.parseInt(idNational.substring(4, 6));
	}

	/**
	 * Valid Gender Code in Id National
	 * 
	 * @param idNational
	 * @param isMale
	 * @param yearOfBirth
	 * @return is valid
	 */
	public static Boolean isValidGenderCode(String idNational, Boolean isMale, int yearOfBirth) {
		int genderCode = getGenderCodeInIdNational(idNational);
		if (isYearOfBirthInCentury(yearOfBirth, 20)) {
			if (!isValidGenderCodeInCentury(isMale, genderCode, 20)) {
				return false;
			}
		} else if (isYearOfBirthInCentury(yearOfBirth, 21)) {
			if (!isValidGenderCodeInCentury(isMale, genderCode, 21)) {
				return false;
			}
		} else if (isYearOfBirthInCentury(yearOfBirth, 22)) {
			if (!isValidGenderCodeInCentury(isMale, genderCode, 22)) {
				return false;
			}
		} else if (isYearOfBirthInCentury(yearOfBirth, 23)) {
			if (!isValidGenderCodeInCentury(isMale, genderCode, 23)) {
				return false;
			}
		} else if (isYearOfBirthInCentury(yearOfBirth, 24)) {
			if (!isValidGenderCodeInCentury(isMale, genderCode, 24)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Valid Year of Birth Code in Id Naioinal
	 * 
	 * @param idNational
	 * @param yearOfBirth
	 * @return is valid
	 */
	public static Boolean isValidYearOfBirthCode(String idNational, int yearOfBirth) {
		int yearOfBirthCode = getYearOfbirthCodeInIdNational(idNational);
		int yearOfBirthST = Integer.parseInt(String.valueOf(yearOfBirth).substring(2, 4));
		if (yearOfBirthCode != yearOfBirthST) {
			return false;
		}
		return true;
	}

	/**
	 * Valid CMND (9 number)
	 * 
	 * @param cmnd
	 * @return is valid
	 */

	public static Boolean isValidCMND(String cmnd) {
		if (cmnd.length() != 9) {
			return false;
		}
		Matcher matcher = VALID_IDNATIONAL_CMND.matcher(cmnd);
		return matcher.find();
	}

	/**
	 * Valid Id National is number
	 * 
	 * @param idNational
	 * @return is number
	 */
	public static Boolean isNumber(String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Valid CMND
	 * 
	 * @param idNational
	 * @return is valid
	 */

	public static Boolean isValidIdCMND(String idNational) {
		Matcher matcher = VALID_IDNATIONAL_CMND.matcher(idNational);
		return matcher.find();
	}

	/**
	 * Check if id national is not duplicate
	 * 
	 * @param idNational
	 * @param list
	 * @param isInsert
	 * @return
	 */
	public static Boolean isIdNationalNotDuplicate(String idNational, List<ModelNhanVien> list, Boolean isInsert,
			String idEmp) {
		for (ModelNhanVien item : list) {
			String lastIdNational = "";
			String lastIdNationalExists = "";
			if (item.getCMND().length() == 12) {
				lastIdNational = idNational.substring(6, 12);
				lastIdNationalExists = item.getCMND().substring(6, 12);
				if (lastIdNational.equals(lastIdNationalExists) && isInsert) {
					return false;
				} else if (lastIdNational.equals(lastIdNationalExists) && !isInsert && !item.getMaNV().equals(idEmp)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check if cmnd is not duplicate
	 * 
	 * @param cmnd
	 * @param list
	 * @param isInsert
	 * @return
	 */
	public static Boolean isCMNDNotDuplicate(String cmnd, List<ModelNhanVien> list, Boolean isInsert, String idEmp) {
		for (ModelNhanVien item : list) {
			if (item.getCMND().length() == 9) {
				if (cmnd.equals(item.getCMND()) && isInsert) {
					return false;
				} else if (cmnd.equals(item.getCMND()) && !isInsert && !item.getMaNV().equals(idEmp)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check if phone number is not duplicate
	 * 
	 * @param phoneNumber
	 * @param list
	 * @param isInsert
	 * @param idEmp
	 * @return
	 */
	public static Boolean isPhoneNumberNotDuplicate(String phoneNumber, List<ModelNhanVien> list, Boolean isInsert,
			String idEmp) {
		for (ModelNhanVien item : list) {
			if (item.getSoDT().equals(phoneNumber) && isInsert) {
				return false;
			} else if (item.getSoDT().equals(phoneNumber) && !isInsert && !item.getMaNV().equals(idEmp)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if email is not duplicate
	 * 
	 * @param email
	 * @param list
	 * @param isInsert
	 * @param idEmp
	 * @return is not duplicate
	 */
	public static Boolean isEmailNotDuplicate(String email, List<ModelNhanVien> list, Boolean isInsert, String idEmp) {
		for (ModelNhanVien item : list) {
			if (item.getEmail().equals(email) && isInsert) {
				return false;
			} else if (item.getEmail().equals(email) && !isInsert && !item.getMaNV().equals(idEmp)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if user have checked remember ever
	 * 
	 * @return is remember
	 */
	public static Boolean isRemember() {
		GhiNhoDAO gnDao = new GhiNhoDAO();
		ModelGhiNho ghiNho = null;
		ghiNho = gnDao.selectById(XIpAddress.getIPAddres());
		if (ghiNho != null) {
			return true;
		}
		return false;
	}

	/**
	 * Compare 2 String is equal
	 * 
	 * @param text1
	 * @param text2
	 * @return
	 */
	public static Boolean isTextEqual(String text1, String text2) {
		if (!text1.trim().equals(text2.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * Compare two number int
	 * 
	 * @param number
	 * @param compareTo
	 * @return is greater than
	 */
	public static Boolean isGreaterThan(int number, int compareTo) {
		if (number < compareTo) {
			return false;
		}
		return true;
	}

	/**
	 * Compare two number int
	 * 
	 * @param number
	 * @param compareTo
	 * @return is less than
	 */
	public static Boolean isLessThan(int number, int compareTo) {
		if (number > compareTo) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Compare two number int**
	 * 
	 * @param number
	 * 
	 * @param compareTo
	 * 
	 * @return is greater than
	 */

	public static Boolean isGreaterThan(double number, double compareTo) {
		if (number < compareTo) {
			return false;
		}
		return true;
	}

	/**
	 * Compare two number int
	 * 
	 * @param number
	 * @param compareTo
	 * @return is less than
	 */
	public static Boolean isLessThan(double number, double compareTo) {
		if (number > compareTo) {
			return false;
		}
		return true;
	}

	/**
	 * Check if date is after compareTo
	 * 
	 * @param date
	 * @param compareTo
	 * @return is after
	 */
	public static Boolean isDateAfter(Date date, Date compareTo) {
		if (date.before(compareTo)) {
			return false;
		}
		return true;
	}

	/**
	 * Check if date is before compareTo
	 * 
	 * @param date
	 * @param compareTo
	 * @return is before
	 */
	public static Boolean isDateBefore(Date date, Date compareTo) {
		if (date.after(compareTo)) {
			return false;
		}
		return true;
	}

	/**
	 * Valid voucher begin date
	 * 
	 * @param beginDate
	 * @return is valid
	 */
	public static Boolean isBeginDateValid(Date beginDate) {
		if (!isDateBefore(beginDate, beginDate)) {
			return false;
		}
		return true;
	}

	/**
	 * Valid voucher end date
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return is valid
	 */
	public static Boolean isEndDayValid(Date beginDate, Date endDate) {
		if (!isDateAfter(endDate, beginDate)) {
			return false;
		}
		return true;
	}

	/**
	 * Date is equals
	 * 
	 * @param date1
	 * @param date2
	 * @return is equals
	 */
	public static Boolean isDateEquals(Date date1, Date date2) {
		if (!date1.equals(date2)) {
			return false;
		}
		return true;
	}

	public static Boolean isVoucherNotDuplicate(String mavoucher, List<ModelVouCher> list, Boolean isInsert) {
		for (ModelVouCher item : list) {
			if (item.getMaVoucher().equals(mavoucher) && isInsert) {
				return false;
			} else if (item.getMaVoucher().equals(mavoucher) && !isInsert && !item.getMaVoucher().equals(mavoucher)) {
				return false;
			}
		}
		return true;
	}
}
