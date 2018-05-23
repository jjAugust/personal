package org.zt.ssmm.controller;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;










import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zt.ssmm.core.Article;
import org.zt.ssmm.core.Returntype;
import org.zt.ssmm.core.Spatial;
import org.zt.ssmm.core.Uploadpic;
import org.zt.ssmm.service.PicService;
import org.zt.ssmm.service.UserService;
import org.zt.ssmm.util.Common;
import org.zt.ssmm.util.ReturnUtil;
@Controller
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private PicService pics;
	@Autowired
	private UserService us;

    @RequestMapping("/toUpload")
    public String toUpload() {
        return "/upload";
    }

    /***
     * 保存文件
     *
     * @param file
     * @return
     */
    private boolean saveFile(HttpServletRequest request, MultipartFile file,String filename,String type) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
               
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中  )
              String filePath = Common.runClassPath+"images/upload/"+filename+".jpg";

                Uploadpic temp=new Uploadpic();
                temp.setName(request.getSession().getAttribute("id").toString());
                temp.setBelong(type);
                temp.setUrl(filename);
                pics.insertUploadPic(temp);

                //	                    file.getOriginalFilename();
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();

                // 转存文件
                file.transferTo(saveDir);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean saveFile2(HttpServletRequest request, MultipartFile file,String filename,String type) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
               
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中  )
              String filePath = Common.runClassPath+"../default/images/upload/"+filename+".jpg";

                Uploadpic temp=new Uploadpic();
                temp.setName("容我再认认哦！");
                temp.setBelong(type);
                temp.setUrl(filename);
                pics.insertUploadPic(temp);

                //	                    file.getOriginalFilename();
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();

                // 转存文件
                file.transferTo(saveDir);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 上传图片
     *
     * @param files
     * @param request
     * @return
     */
    @RequestMapping("/filesUpload")
    @ResponseBody
    public Object filesUpload(@RequestParam("myfiles") MultipartFile[] files,String type,
            HttpServletRequest request) {
        boolean j = false;
        String filename="";
        Returntype text=new Returntype();
        if(request.getSession().getAttribute("id")==null){
            ReturnUtil.fix(text,"_KEYS_f08");
            return text;
        }
        else{
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    // 保存文件
                    Date ii= new Date();
                    DateFormat jj= new SimpleDateFormat("yyyyMMddhhmmss");
                    int p=(int)Math.round(Math.random()*9000+1000);
                    filename=jj.format(ii)+""+p;
                    j= saveFile(request, file,filename,type);
                    System.out.println(j);
                }
            }

            if(j==true){
            	text.setData(filename);
                ReturnUtil.fix(text,"_KEYS_s06");
                return text;
            }

            else{
                ReturnUtil.fix(text,"_KEYS_f08");
                return text;
            }
        }
    }

    
    @RequestMapping("/picUpload")
    @ResponseBody
    public Object filesUpload2(@RequestParam("myfiles") MultipartFile[] files,String type,
            HttpServletRequest request) {
        boolean j = false;
        String filename="";
        Returntype text=new Returntype();
     
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    // 保存文件
                    Date ii= new Date();
                    DateFormat jj= new SimpleDateFormat("yyyyMMddhhmmss");
                    int p=(int)Math.round(Math.random()*9000+1000);
                    filename=jj.format(ii)+""+p;
                    j= saveFile2(request, file,filename,type);
                    System.out.println(j);
                }
            }

            if(j==true){
            	text.setData(filename);
                ReturnUtil.fix(text,"_KEYS_s06");
                return text;
            }

            else{
                ReturnUtil.fix(text,"_KEYS_f08");
                return text;
            }
        }
    @RequestMapping(value="/querypicinfo" )  
	@ResponseBody  
	public Object querypicinfo(HttpServletRequest req,String id){  
		String pic_name = us.querypicinfo(id);

		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(pic_name);
		return text;  
	} 
    
    @RequestMapping(value="/addpicinfo" )  
   	@ResponseBody  
   	public Object addpicinfo(HttpServletRequest req,String id,String name){  
    	Uploadpic pic=new Uploadpic();
    	pic.setName(name);
    	pic.setId(id);
   		us.addpicinfo(pic);
   		Returntype text=new Returntype();
   		ReturnUtil.fix(text,"_KEYS_s01");
   		return text;  
   	} 
       
    @RequestMapping(value="/querylastpicinfo" )  
   	@ResponseBody  
   	public Object querylastpicinfo(HttpServletRequest req){  
    	
   		String picid=us.querylastpicinfo();
   		Returntype text=new Returntype();
   		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(picid);
   		return text;  
   	}   
}
