package com.ymu.framework.utils;


/**
 * 
 * @类描述：数学辅助类
 * 
 * @创建人：mt
 * @创建时间：2014年7月4日上午11:03:08
 * @修改人：Administrator
 * @修改时间：2014年7月4日上午11:03:08
 * @修改备注：
 * @version v1.0
 * @Copyright
 * @mail 932852117@qq.com
 */
public class MathUtils {

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * http://www.hkww.org/weather/chinese/index_main.html
	 * 
	 * 公式：
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double GetDistance(double lng1, double lat1, double lng2,
			double lat2) {
		// 地球半径
		final double EARTH_RADIUS = 6378137;

		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		System.out.println("a:" + a);
		System.out.println("b:" + b);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 保留double数后面digit位小数
	 * 
	 * @param valuse
	 *            原值
	 * @param digit
	 *            保留小数的位数,一位10，两位100，三位1000，四位10000……
	 * @return
	 * @author mutian
	 * @createTime:2014年7月4日下午3:18:25
	 * @updateAuthor:
	 * @updateTime:
	 */
	public static double retainDecimals(Double valuse, int digit) {
		int a = (int) Math.round(valuse * digit); // 移位并四舍五入
		double k = digit;
		double b = a / k;
		return b;
	}

	/**
	 * 已知点a经纬度，根据给定的距离计算纬度为点a的纬度的点b的经度。（两点纬度相同）
	 * 
	 * @param s
	 *            给定的距离
	 * @param lng1
	 *            点a的经度
	 * @param lat1
	 *            点a的纬度
	 * @return
	 * @author mutian
	 * @createTime:2014年7月4日下午3:47:20
	 * @updateAuthor:
	 * @updateTime:
	 */
	public static Double[] getLng2(Double s, double lng1, double lat1) {
		// 地球半径
		final double EARTH_RADIUS = 6378137;

		double a = 360
				/ Math.PI
				* (Math.asin(Math.sqrt(Math.pow(Math.sin(s / (2 * EARTH_RADIUS)), 2)
						/ Math.pow(Math.cos(lat1), 2))));

		Double[] lng2 = new Double[2];
		lng2[0] = retainDecimals(lng1 - a,10000);
		lng2[1] = retainDecimals(lng1 + a,10000);
		return lng2;

	}

	/**
	 * 已知点a经纬度，根据给定的距离计算经度为a的经度的点b的纬度。(两点经度相同)
	 * 
	 * @param s
	 * @param lat1
	 * @return
	 * @author mutian
	 * @createTime:2014年7月4日下午3:50:04
	 * @updateAuthor:
	 * @updateTime:
	 */
	public static Double[] getLat2(Double s, double lat1) {
		final double EARTH_RADIUS = 6378137;
		double a = 360/Math.PI * Math.asin(Math.sqrt(Math.pow(Math.sin(s/(2*EARTH_RADIUS)), 2)));
		Double[] lat2 = new Double[2];
		lat2[0] = retainDecimals(lat1 - a,10000);
		lat2[1] = retainDecimals(lat1 + a, 10000);
		return lat2;

	}

	public static void main(String[] args) {
		final double EARTH_RADIUS = 6378137;

		double distance = GetDistance(114.3265, 24.8345, 117.0307, 24.8345);
		System.out.println("两点距离是:" + distance / 1000);

		 System.out.println(getLat2(111100.0, 0.0000)[0] + "//////"
		 + getLat2(111000.0, 0.0000)[1]);

		Double[] c = getLng2(distance, 114.3265, 24.8345);
		System.out.println(c[0] + "//////" + c[1]);

	}
}
