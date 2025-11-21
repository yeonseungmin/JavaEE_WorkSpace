package com.ch.gallery.util;

import javax.swing.AbstractCellEditor;

public class StringUtil {
	//주어진 파일경로의 확장자 추출
	public static String getExtendFrom(String oriName) {
		int lastIndex = oriName.lastIndexOf(".");
		String extend = oriName.substring(lastIndex+1,oriName.length());
		return extend;
	}
}
