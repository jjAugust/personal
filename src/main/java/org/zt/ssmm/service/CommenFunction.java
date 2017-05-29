package org.zt.ssmm.service;

import java.util.Calendar;

public class CommenFunction {

	public double Geo_Distance(double lat1, double lng1, double lat2,
			double lng2) {
		double r = 6378.137;
		lat1 = Math.toRadians(lat1);
		lng1 = Math.toRadians(lng1);
		lat2 = Math.toRadians(lat2);
		lng2 = Math.toRadians(lng2);
		double d1 = Math.abs(lat1 - lat2);
		double d2 = Math.abs(lng1 - lng2);
		double p = Math.pow(Math.sin(d1 / 2), 2) + Math.cos(lat1)
				* Math.cos(lat2) * Math.pow(Math.sin(d2 / 2), 2);
		double dis = r * 2 * Math.asin(Math.sqrt(p));
		return dis;
	}
	
	
	public Calendar getCalender(String date, String time) {
		String[] dates = date.split("-");
		String[] times = time.split(":");
		Calendar cale = Calendar.getInstance();
		cale.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]),
				Integer.parseInt(dates[2]), Integer.parseInt(times[0]),
				Integer.parseInt(times[1]), Integer.parseInt(times[2]));
		return cale;
	}

	}
		

