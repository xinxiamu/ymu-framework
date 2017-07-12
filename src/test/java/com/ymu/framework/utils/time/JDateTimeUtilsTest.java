package com.ymu.framework.utils.time;

import java.util.Date;

import org.junit.Test;

import com.ymu.framework.utils.PrintUtils;


public class JDateTimeUtilsTest {

//	@Test
	public void testGetCurrentSystemDateTime() {
		String currentDateStr = JDateTimeUtils.getCurrentSystemDateTime(JDateTimeStyle.YYYY_MM_DD_CN.getValue());
		System.out.println(currentDateStr);
	}
	
	@Test
	public void testDateToStr() {
	}
	
	@Test
	public void testStrToDate() {
	}
	
	@Test
	public void testaddDateTime() {
		String a = JDateTimeUtils.addDate("2015-06-02", 1, -2, 4);
		String b = JDateTimeUtils.addTime("2015-06-02", 0, -2, 4);
//		PrintUtils.println(a);
//		PrintUtils.println(b);
	}
	
	@Test
	public void testIsDate1AfterDate2() {
		boolean b = JDateTimeUtils.isDate1AfterDate2("2015-06-02 12:32:23", "2015-06-02 12:32:22");
		boolean c = JDateTimeUtils.isDate1AfterDate2IgnoredTime("2015-06-03 12:32:23", "2015-06-02 12:32:22");
		PrintUtils.println("" + b + "===" + c);
	}
	
	@Test
	public void absDate1ToDate2() {
		int flg = JDateTimeUtils.absDate1ToDate2("2015-06-06 12:32:23", "2015-06-03 14:32:22");
		PrintUtils.println(flg);
	}
	
	@Test
	public void dateForMillsecond() {
		Long l = JDateTimeUtils.dateTimeInMillis(new Date());
		PrintUtils.println(l);
		
		PrintUtils.println(JDateTimeUtils.dateToStr(l, JDateTimeStyle.YYYY_MM_DD_HH_MM_CN.getValue()));
	}
	
	@Test
	public void showDateTimeFriendly() {
		String str = JDateTimeUtils.showDateTimeFriendly("2015-06-9 13:52:23");
		PrintUtils.println("=========" + str);
	}
	
	
	
}
