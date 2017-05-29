package org.zt.ssmm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zt.ssmm.core.Article;
import org.zt.ssmm.core.Plt1;
import org.zt.ssmm.core.Returntype;
import org.zt.ssmm.core.Spatial;
import org.zt.ssmm.core.User;
import org.zt.ssmm.core.UserTimes;
import org.zt.ssmm.core.UserTimesPercen;
import org.zt.ssmm.core.UserTips;
import org.zt.ssmm.core.Userdata;
import org.zt.ssmm.service.UserService;
import org.zt.ssmm.util.ReturnUtil;

import	java.io.IOException;

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
	@RequestMapping("/QuerySeqByUserId")
	public Object QuerySeqByUserId(String id, HttpServletRequest req,HttpSession httpSession)
	{
		Set<UserTimesPercen> a=new HashSet<UserTimesPercen>();
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
	    String find_str="1";
		 LinkedList<String> ab= new LinkedList<String>();
		 ab=FindPart(res,find_str);
		Map<String,Double> percent=FindPercent(ab,res);
//	    String[] rest=res.split(",");
//	    List<String> restu1=new LinkedList<String>();
//	    List<String> restu_1=new LinkedList<String>();
//	    List<String> restu2=new LinkedList<String>();
//	    List<String> restu_2=new LinkedList<String>();
//	    String s1="",s2="";
//	    int i=0;
//	    for(String s:rest){
//	    	if(s.equals("1")){
//	    		s1=s+','+rest[i+1];
//	    		if(!restu1.contains(s1))
//	    		restu1.add(s1);
//	    		restu_1.add(s1);
//	    		a = FindPercent(restu1,restu_1);
//	    		if(i>1){
//	    			s2=rest[i-1]+','+s+','+rest[i+1];
//	    			if(!restu2.contains(s2))
//	    			restu2.add(s2);
//	    			restu_2.add(s2);
//	    			a = FindPercent(restu2,restu_2);
//	    		}
//	    	}
//	    	s1=s2="";
//	    	i++;
//	    }
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(percent);
		return text;  
	}
	
	@ResponseBody
	@RequestMapping("/QuerySeqByUserId2")
	public Object QuerySeqByUserId2(String id, HttpServletRequest req,HttpSession httpSession)
	{
		Set<UserTimesPercen> a=new HashSet<UserTimesPercen>();
		String res="";
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		return text;  
	}
	
	private Set<UserTimesPercen> FindPercent(List<String> A,List<String> B) {
		Set<UserTimesPercen> result = new HashSet<UserTimesPercen>();
        double count=0,total=B.size();
		for(String c:A){
			for(String s:B){
				if(c.equals(s))
				count++;
			}
			UserTimesPercen userTimesPercen=new UserTimesPercen();
			userTimesPercen.setSub_str(c);
			userTimesPercen.setPercent( (count/total));
			result.add(userTimesPercen);
			count=0;
		}
		return result;
	}
	private  Set<UserTimesPercen> FindPercent(String in) {
		Set<UserTimesPercen> result = new HashSet<UserTimesPercen>();
        double count=0,total=in.split(",").length;
        int index=-1;
        double i=0;
		UserTimesPercen userTimesPercen=new UserTimesPercen();
		for(String c:in.split(",")){
			for(;i<total;i++){
	        	if(in.indexOf(c,index+1)!=-1){
	        		index=in.indexOf(c,index+1);
	        		count++;
	        	}
	        }
			userTimesPercen.setSub_str(String.valueOf(c));
			userTimesPercen.setPercent( (count/total));
			result.add(userTimesPercen);
			count=0;
			i=0;
		}
		return result;
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
				if(index!=-1&&in.substring(index-1, index).equals(",")){

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
