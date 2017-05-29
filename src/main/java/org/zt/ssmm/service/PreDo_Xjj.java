package org.zt.ssmm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class PreDo_Xjj {

	static String fileInpath = "/Users/xiongjunjie/Documents/junjie.xiong/数据集/Geolife Trajectories 1.3/Data/";
	static String outpath = "/Users/xiongjunjie/Downloads/Data3/";
	private static PrintWriter PW;
	private static BufferedReader BUFF;
	
	public  static void main(String arg[])
	{
		int i = 0 , count = 0;
		boolean flag = true;
		String outline;
		
		File dir = new File(fileInpath);
		String[] dirlist = dir.list();
		for( ;  count < dirlist.length; count ++)
		{
			String[] filelist;
			if(dirlist[count].equals(".DS_Store"))
			continue;
				else
			filelist = new File(fileInpath +dirlist[count]+"/Trajectory").list();
			String outputpath = outpath+dirlist[count]+".txt";
			OutputStream fout;
			try {
				fout = new FileOutputStream(new File(outputpath),true);
				PW = new PrintWriter(fout);
				
				for(i=0 ; i < filelist.length; i++)
				{
					try {
						String inputpath = fileInpath +dirlist[count]+"/Trajectory/"+filelist[i];
					    BUFF = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputpath))));
						String str = BUFF.readLine();
						flag = true;
						while((str = BUFF.readLine()) != null)
						{  
							if(flag && str.equals("0"))
							{
								flag = false;
							}
							if(flag)
							{
								continue;
							}
							if(!str.equals("0"))
							{
								String []cor = str.split(",");
								outline =  cor[0] + "," + cor[1] + "," + 
								cor[5] + "," + cor[6] + "\r\n";
								PW.write(outline);
								PW.flush();
								outline = "";
							}
							
						}
				
					}
					catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				} 
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
		}  
	}
		

			
	
	

}