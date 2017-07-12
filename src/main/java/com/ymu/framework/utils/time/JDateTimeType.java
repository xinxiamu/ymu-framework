package com.ymu.framework.utils.time;

/**
 *  YYYY	yes	year
	MM	yes	month
	DD	yes	day of month
	D	 	day of week
	MML	 	month name long
	MMS	 	month name short
	DL	 	day of week name long
	DS	 	day of week name short
	hh	yes	hour
	mm	yes	minute
	ss	yes	seconds
	mss	yes	milliseconds
	DDD	 	day of year
	WW	 	week of year
	WWW	 	week of year with 'W' prefix
	W	 	week of month
	E	 	era (AD or BC)
	TZL	 	time zone name long
	TZS	 	time zone name short
 * @author mutou
 *
 */
public enum JDateTimeType {
	YEAR("YYYY"), MONTH("MM"), DAY("DD"), HOUR("hh"), MINUTE("mm"), SECONDS(
			"ss"), MILLISECONDS("mss");

	private String value;

	JDateTimeType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}