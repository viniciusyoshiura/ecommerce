package com.mycompany.ecommerce.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class UrlUtils {

	public static List<Integer> decodeToListInteger(String s) {

		String[] arr = s.split(",");
		List<Integer> resultList = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			resultList.add(Integer.parseInt(arr[i]));
		}
		return resultList;

		// ---------- Alternative using lambda
		// return Arrays.asList(s.split(",")).stream().map(x ->
		// Integer.parseInt(x)).collect(Collectors.toList());
	}

	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

}
