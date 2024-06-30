package com.fpt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class MethodUtil {

	public static String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	public static String DATE_PATTERN = "yyyy-MM-dd";

	@SuppressWarnings("deprecation")
	public static boolean checkRegularExpression(String input, String regular) {
		// validate input
		if (StringUtils.isEmpty(input)) {
			return false;
		}

		// return validation result
		return Pattern.compile(regular).matcher(input).matches();
	}

	public static Date convertStringToDateTime(String input) throws ParseException {
		return convertStringToDateObject(input, DATE_TIME_PATTERN);
	}

	public static Date convertStringToDate(String input) throws ParseException {
		return convertStringToDateObject(input, DATE_PATTERN);
	}

	public static Date convertStringToDateObject(String input, String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(input);
	}
}
