package org.zt.ssmm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;

public class StayPoint_Xjj {
	static String filepath = "/Users/xiongjunjie/Downloads/Data2/";
	static String outputpath = "/Users/xiongjunjie/Downloads/Data5.0/data.txt";
	static double k_currdis = 0.2; //间距
	static double t_time = 5*1000*60; //时间间隔
	static double t2_time = 10*1000*60; //处理尾行的时间间隔
	private static BufferedReader buff;
	private static PrintWriter pw;

	public static void main(String args[]) throws FileNotFoundException
	{
		CommenFunction commenFunction = new CommenFunction();
		DecimalFormat ft = new DecimalFormat("#0.00000000");
		String[] filelist =new File(filepath).list();
		int  count =0,id = 0,sum = 0;
		double	currdis = 0.0;
		for(int i=0;i< filelist.length;i++){
			try{
			    pw = new PrintWriter(new FileOutputStream(new File(outputpath),true));;
				buff = new BufferedReader(
						new InputStreamReader(
								new FileInputStream(
										new File(filepath+filelist[i]))));
				String str = buff.readLine();
				if(str == null)
					return;
				String []info = str.split(",");
				String x1 = info[0],y1 = info[1],x2 = null,y2 = null;
			double	totalX = Double.valueOf(info[0]).doubleValue();
			double	totalY = Double.valueOf(info[1]).doubleValue();
			String	startDate = info[2]+","+info[3],endDate = null;
			Calendar	caleB = null,caleA = commenFunction.getCalender(info[2],info[3]);
			while((str = buff.readLine()) != null)
			{  
					info = str.split(",");
						x2 = info[0];
					    y2 = info[1];
					currdis = commenFunction.Geo_Distance(Double.valueOf(x1).doubleValue(),Double.valueOf(y1).doubleValue(),Double.valueOf(x2).doubleValue(),Double.valueOf(y2).doubleValue());
					if(currdis > k_currdis && caleB != null && caleA != null && ((caleB.getTimeInMillis() - caleA.getTimeInMillis()) >t_time))
					{
						x1 = x2;
						y1 = y2;
						if(count > 1)
						{
							pw.write(id+","+ft.format(totalX/count)+","+ft.format(totalY/count)+","+startDate+","+endDate+"\r\n");
							pw.flush();
							sum++;
						}
						startDate = info[2]+","+info[3];
						caleA = commenFunction.getCalender(info[2],info[3]);
						count = 0;
						totalX = totalY = 0;
					}
					totalX += Double.valueOf(x2).doubleValue();
					totalY += Double.valueOf(y2).doubleValue();
					endDate = info[2]+","+info[3];
					caleB = commenFunction.getCalender(info[2],info[3]);
					count++;
					x1 = x2;
					y1 = y2;
					x2 = y2 = "";
			}
			//尾行的处理
			if(totalX != 0 || totalY != 0 && (!x2.isEmpty() && y2.isEmpty()) && 
					((caleB.getTimeInMillis() - caleA.getTimeInMillis()) > t2_time))
			{
				pw.write(id+","+ft.format(totalX/count)+","+ft.format(totalY/count)+","+startDate+","+endDate+"\r\n");
				pw.flush();
				sum++;
			}
			id++;
			System.out.println(sum);
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}finally
			{

				try {
					if(buff != null)
						buff.close();
					if(pw != null)
						pw.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		} 
	}
		

