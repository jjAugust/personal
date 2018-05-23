package org.zt.ssmm.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
	//数组全排列输出
    public static void fullarrangement(List<List<Integer>> result, int[] nums, int start){
        if(start==nums.length-1){	
            List<Integer> toadd = new ArrayList<Integer>(nums.length);
            for (int i: nums) toadd.add(i);
//            if(!result.contains(toadd)) result.add(toadd);    有重复的情况
            result.add(toadd);
        }
        else{
            for(int i=start; i<nums.length; i++){
                int temp = nums[start];
                nums[start] = nums[i];
                nums[i] = temp;

                fullarrangement(result, nums, start+1);

                temp = nums[start];
                nums[start] = nums[i];
                nums[i] = temp;
            }
        }
    }
    
	}
		

