package org.zt.ssmm.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zt.ssmm.core.Article;
import org.zt.ssmm.core.IdTimeInfo;
import org.zt.ssmm.core.Plt1;
import org.zt.ssmm.core.Returntype;
import org.zt.ssmm.core.Spatial;
import org.zt.ssmm.core.User;
import org.zt.ssmm.core.UserTimes;
import org.zt.ssmm.core.UserTimesPercent;
import org.zt.ssmm.core.UserTips;
import org.zt.ssmm.core.Userdata;
import org.zt.ssmm.service.UserService;
import org.zt.ssmm.util.ReturnUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import	java.io.IOException;
import java.io.InputStreamReader;

import	org.apache.http.HttpEntity;
import	org.apache.http.HttpResponse;
import	org.apache.http.client.ResponseHandler;
import	org.apache.http.client.methods.HttpUriRequest;
import	org.apache.http.client.methods.RequestBuilder;
import	org.apache.http.impl.client.CloseableHttpClient;
import	org.apache.http.impl.client.HttpClients;
import	org.apache.http.util.EntityUtils;

@Controller
@RequestMapping("/MarkfulController")
public class MarkfulController 
{
	@Autowired
	private UserService us;
	private UserTimes temp;

	@ResponseBody
	@RequestMapping("/SelectTracByUserId")
	public Object SelectTracByUserId(String id, HttpServletRequest req,HttpSession httpSession)
	{
		List<UserTips> u = us.SelectTracByUserId(id);
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(u);
		return text;  
	}
	
	@ResponseBody
	@RequestMapping("/check")
	public Object check(String id,Double Datapercent,String s,HttpServletRequest req,HttpSession httpSession) throws IOException, ParseException
	{
//		BufferedReader BUFF;
//		BUFF = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/xiongjunjie/Downloads/1.csv"))));
//		String str = BUFF.readLine();
//				String  preStr="";
//				while((str = BUFF.readLine()) != null){
//					String []cor = str.split(",");
//					Predict("0", preStr, cor[1], cor[2], req, httpSession);
//				}
//		String s="196 34 52 150 84 51 196 79 181 150 181 1 181 1 181 1 181 150 181 150 181 116 32";
		String[] p=s.split(" ");	
		boolean check=true;
		String res = "";
		int o=0;
		for(String l:p){
			if(check){
				res=l;
				check=false;
			}
			if(l.equals(p[o>0?o-1:0])&&o!=1){
				
			}else{
				res+=","+l;
			}
			o++;
		}
		System.out.println(res);
		p=res.split(",");
//		String preStr=p[0]+","+p[1]+","+p[2];
		String result="";
		String next_pre="";
		for(int i=2;i<p.length;i++){
			String preStr=(i==2?p[0]+","+p[1]+","+p[2]:next_pre+","+p[i]);
			Returntype result_seq=(Returntype) QuerySeqByUserId(id,preStr,Datapercent,req, httpSession);
			List<Map.Entry<String, Double>> infoIds =(List<Entry<String, Double>>) result_seq.getData();
			Map.Entry<String, Double> infoId=infoIds.get(0);
			result+=","+infoId.getKey();
		    next_pre=preStr.split(",")[1]+","+preStr.split(",")[2];
		    Datapercent=Datapercent<1?Datapercent+0.005:1;
		}
		result=result.substring(1);
		System.out.println(result);
		String[] ins2=res.split(",");
		int q=0;String res2="";
		for(String ins:result.split(",")){
			if(q+3<ins2.length){
				if(ins2[q+3].equals(ins)){
					res2+=","+"1";
				}else{
					res2+=","+"0";
				}
			}
			q++;
		}
		System.out.println(res2);
		String[] res3=res2.split(",");
		int count=0;
		for(String res4:res3){
			if(res4.equals("1"))
				count++;
		}
		System.out.println(((double)count/(double)(res3.length-1)));


		Returntype text=new Returntype();
				ReturnUtil.fix(text,"_KEYS_s01");
				text.setData("");
				return text;  
	}
	
	@ResponseBody
	@RequestMapping("/Predict")
	public Object Predict(String id,String preStr,String time,String date,Double Datapercent, HttpServletRequest req,HttpSession httpSession) throws ParseException
	{
		Double a=0.9;
		Double b=1-a;
		Returntype result_time=(Returntype) SelectPercentByTime(id,time,date, date,"", req, httpSession);
		Returntype result_seq=(Returntype) QuerySeqByUserId(id,preStr,Datapercent,req, httpSession);
		 Map<String,Double> result_new=new HashMap<String, Double>();
		@SuppressWarnings("unchecked")
		List<UserTimesPercent> UserTimesPercent=(List<UserTimesPercent>) result_time.getData();
		@SuppressWarnings("unchecked")
		List<Map.Entry<String, Double>> infoIds =(List<Entry<String, Double>>) result_seq.getData();
		for(UserTimesPercent userTimesPercent:UserTimesPercent){
			String substr=userTimesPercent.getSub_str();
			for(Map.Entry<String, Double> i:infoIds){//先处理公共部分
				if(i.getKey().equals(substr)){
					result_new.put(substr, userTimesPercent.getPercent()*b+i.getValue()*a);
				}
			}
			if(!result_new.containsKey(substr)){//处理时间预测出的剩余部分
				result_new.put(substr, userTimesPercent.getPercent()*b);
			}
		}
		for(Map.Entry<String, Double> i:infoIds){//处理序列预测出的剩余部分
			 if(!result_new.containsKey(i.getKey())){
					result_new.put(i.getKey(), i.getValue()*a);
				}
		}
		Double max=0.0;
		String result="";
		List<Map.Entry<String, Double>> n =
			    new ArrayList<Map.Entry<String, Double>>(result_new.entrySet());
		 Collections.sort(n, new Comparator<Map.Entry<String, Double>>() {   

				@Override
				public int compare(Entry<String, Double> o1,
						Entry<String, Double> o2) {
					if(o2.getValue() > o1.getValue())
					 return 1;
					else if(o2.getValue() < o1.getValue()){
					 return -1;
					}
					else
					return 0; 
				}
			 }); 
		for(String m:result_new.keySet()){
			result=result_new.get(m)>max?m:result;
			max=result_new.get(m)>max?result_new.get(m):max;
		}
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(n);
		return text;  
	}
	
	//	List<UserTimesPercent>  SelectPercentByTime(IdTimeInfo idTimeInfo);
	@ResponseBody
	@RequestMapping("/SelectPercentByTime")
	public Object SelectPercentByTime(String id,String time,String date,String place, String string, HttpServletRequest req,HttpSession httpSession) throws ParseException
	{
		IdTimeInfo idTimeInfo=new IdTimeInfo();
		idTimeInfo.setId(id);
		idTimeInfo.setSdate(date);
        DateFormat time_formate = new SimpleDateFormat("hh:mm:ss");
        Date datet = time_formate.parse(time);
        Date datet1=new Date(datet.getTime()-2*60*1000);
        Date datet2=new Date(datet.getTime()+2*60*1000);
		String s_time = datet1.toString().split(" ")[3],e_time = datet2.toString().split(" ")[3];
        idTimeInfo.setS_time(s_time);
		idTimeInfo.setE_time(e_time);
		List<UserTimesPercent> u= us.SelectPercentByTime(idTimeInfo);
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(u);
		return text;  
	}
	
	@ResponseBody
	@RequestMapping("/QuerySeqByUserId")
	public Object QuerySeqByUserId(String id,String preStr,Double Datapercent, HttpServletRequest req,HttpSession httpSession)
	{
		List<UserTimes> u = us.QuerySeqByUserId(id);
		temp = new UserTimes();
		String res = "";
    	boolean cont = true;
	    for(UserTimes us:u){
			if(cont==true){
		    	temp=us;
		    	res+=us.getClusterId();
		    	cont=false;
	    		continue;
	    	}	    
			if(temp.getClusterId().equals(us.getClusterId())){
				
			}else{
				res+=','+us.getClusterId();
			}
			temp=us;
	    }
	    //按比例选取训练集
	    String[] res_sz=res.split(",");
	    String res_temp="";
	    int datacount=(int) (res_sz.length*Datapercent);
	    for(int datanumber=0;datanumber<datacount;datanumber++){
	    	res_temp+=","+res_sz[datanumber];
	    }
	    res_temp=res_temp.substring(1);
	    //训练集选取完成
	    res=res_temp;
	     String find_str=preStr;
		 LinkedList<String> ab= new LinkedList<String>();
			String[] findStr=find_str.split(",");
			double[] temp = null;
			//复合马尔科夫模型的参数设置
			//一阶参数
			//二阶参数
			//三阶参数等等
			if(findStr.length==1){
				 temp=new double[]{0.6,0.4};
			}
			else if(findStr.length==2){
				 temp=new double[]{0.5,0.3,0.2};
			}
			else{
				 temp=new double[]{0.4,0.3,0.1,0.2};
			}
			
			List<UserTimesPercent> a=us.SelectZeroPhase(id);
			Map<String,Double> percentTotal=new HashMap<String, Double>();
			for(UserTimesPercent b : a){
				percentTotal.put(b.getSub_str(), b.getPercent()*temp[0]);
			}
			
			find_str=findStr[findStr.length-1];
			ab=FindPart(res,find_str);
//			System.out.println(res);
			Map<String,Double> percent=FindPercent(ab,res);
			for(String subStr:percent.keySet()){
				String sStr[]=subStr.split(",");
				String lastStr=sStr[sStr.length-1];
				double tempTotal=percentTotal.get(lastStr)+percent.get(subStr)*temp[sStr.length-1];
				percentTotal.put(lastStr, tempTotal);
			}
			
		 for(int i=2;i<=findStr.length;i++){
			 find_str=findStr[findStr.length-i]+","+find_str;
			  ab=FindPart(res,find_str);
			  percent=FindPercent(ab,res);
			  for(String subStr:percent.keySet()){
					String sStr[]=subStr.split(",");
					String lastStr=sStr[sStr.length-1];
					double tempTotal=percentTotal.get(lastStr)+percent.get(subStr)*temp[sStr.length-1];
					percentTotal.put(lastStr, tempTotal);
				}
		 }
		 
		   
		
		 List<Map.Entry<String, Double>> infoIds =
				    new ArrayList<Map.Entry<String, Double>>(percentTotal.entrySet());
		//排序
		 Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {   

			@Override
			public int compare(Entry<String, Double> o1,
					Entry<String, Double> o2) {
				if(o2.getValue() > o1.getValue())
				 return 1;
				else if(o2.getValue() < o1.getValue()){
				 return -1;
				}
				else
				return 0; 
			}
		 }); 

		
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(infoIds);
		return text;  
	}
	
	@ResponseBody
	@RequestMapping("/SelectZeroPhase")
	public Object QuerySeqByUserId2(String id, HttpServletRequest req,HttpSession httpSession)
	{
		List<UserTimesPercent> a=us.SelectZeroPhase(id);
		Map<String,Double> percent=new HashMap<String, Double>();
		for(UserTimesPercent b : a){
			percent.put(b.getSub_str(), b.getPercent());
		}
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");		
		text.setData(percent);
		return text;  
	}
	
	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}

	private  LinkedList<String> FindPart(String in,String find_str) {
		LinkedList<String> result = new LinkedList<String>();
        int in_len=in.length(),findstr_len=find_str.length()+1;
        int index=-1;
        String discover="";
		for(int i=0;i<in_len;i++){
			if((index+findstr_len+1)<in_len){
				index=in.indexOf(find_str+",", index+1);
				if(index!=-1&&in.substring(index-1<0?0:index-1, index).equals(",")){

						int lastindex=index+findstr_len;		
						if(lastindex+1<in_len){
						while(!in.substring(lastindex, lastindex+1).equals(",")){
							lastindex++;
							if(lastindex==in_len)
									break;
						}
						if(lastindex<=in_len)
						discover=(String) in.substring(index, lastindex);
						result.add(discover);
					}
				}
				index++;
			}
		}
		return result;
	}
	
	private  Map<String,Double> FindPercent(LinkedList<String> in,String ini){
		Map<String,Integer> result=new HashMap<String,Integer>();
		Map<String,Double> percent=new HashMap<String,Double>();
		int total=0;
		for(String c:in){
			int count=0;
			for(String temp:in){
				if(c.equals(temp))
					count++;
			}
			if(!result.containsKey(c)){
				total+=count;
			result.put(c, count);
			}
		}
		for(String Key:result.keySet()){
			percent.put(Key,  ((double)result.get(Key)/(double)total));
		}
		return percent;
	}
	
}
