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
import org.zt.ssmm.core.User;
import org.zt.ssmm.service.PicService;
import org.zt.ssmm.service.TensorFlowInferenceInterface;
import org.zt.ssmm.service.UserService;
import org.zt.ssmm.util.Common;
import org.zt.ssmm.util.ReturnUtil;
@Controller
@RequestMapping("/recog")
public class TensorFlow {
    @Autowired
    private PicService pics;
	@Autowired
	private UserService us;

    @RequestMapping("/toUpload")
    public String toUpload() {
        return "/upload";
    }

    @ResponseBody
	@RequestMapping("/recog")
	public Object recog(String id, HttpServletRequest req,HttpSession httpSession)
	{
    	TensorFlowInferenceInterface recognitionService = new TensorFlowInferenceInterface();
//    	recognitionService.main("/Users/xiongjunjie/Documents/github/personal/src/main/java/org/zt/ssmm/controller/1.jpg");

    return recognitionService.main("/Users/xiongjunjie/Documents/github/personal/src/main/java/org/zt/ssmm/controller/1.jpg");
    } 
    
}
