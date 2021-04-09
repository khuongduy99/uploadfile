package com.spring.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.spring.model.MyUploadForm;

@Controller
public class MyFileUploadController {
	// Phương thức này được gọi mỗi lần có Submit.
	   @InitBinder
	   public void initBinder(WebDataBinder dataBinder) {
	       Object target = dataBinder.getTarget();
	       if (target == null) {
	           return;
	       }
	       System.out.println("Target=" + target);
	 
	       if (target.getClass() == MyUploadForm.class) {
	  
	           // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
	           dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	       }
	   }
	  
	   // GET: Hiển thị trang form upload
	   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
	   public String uploadOneFileHandler(Model model) {
	 
	       MyUploadForm myUploadForm = new MyUploadForm();
	       model.addAttribute("myUploadForm", myUploadForm);
	 
	       // Forward to "/WEB-INF/pages/uploadOneFile.jsp".
	       return "uploadOneFile";
	   }
	  
	   // POST: Sử lý Upload
	   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
	   public String uploadOneFileHandlerPOST(HttpServletRequest request, //
	           Model model, //
	           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	 
	       return this.doUpload(request, model, myUploadForm);
	 
	   }
	  
	   // GET: Hiển thị trang form upload
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
	   public String uploadMultiFileHandler(Model model) {
	 
	       MyUploadForm myUploadForm = new MyUploadForm();
	       model.addAttribute("myUploadForm", myUploadForm);
	 
	       // Forward to "/WEB-INF/pages/uploadMultiFile.jsp".
	       return "uploadMultiFile";
	   }
	   
	// GET: Hiển thị trang form upload
	   @RequestMapping(value = "/thuvienanh", method = RequestMethod.GET)
	   public String getImages(Model model) {
	 
	       model.addAttribute("myUploadForm", getDB());
	 
	       // Forward to "/WEB-INF/pages/uploadMultiFile.jsp".
	       return "thuvienanh";
	   }
	  
	   // POST: Sử lý Upload
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
	   public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
	           Model model, //
	           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	 
	       return this.doUpload(request, model, myUploadForm);
	 
	   }
	 
	   private String doUpload(HttpServletRequest request, Model model, //
	           MyUploadForm myUploadForm) {
	 
	       String description = myUploadForm.getDescription();
	       System.out.println("Description: " + description);
	 
	       // Thư mục gốc upload file.
	       String uploadRootPath = request.getServletContext().getRealPath("images");
	       System.out.println("uploadRootPath=" + uploadRootPath);
	 
	       File uploadRootDir = new File(uploadRootPath);
	      
	       //
	       // Tạo thư mục gốc upload nếu nó không tồn tại.
	       if (!uploadRootDir.exists()) {
	           uploadRootDir.mkdirs();
	       }
	       CommonsMultipartFile[] fileDatas = myUploadForm.getFileDatas();
	       
	       List<String> uploadedFiles = new ArrayList<String>();
	       for (CommonsMultipartFile fileData : fileDatas) {
	    	   
	           // Tên file gốc tại Client.
	           String name = fileData.getOriginalFilename();
	           System.out.println("Client File Name = " + name);
	 
	           if (name != null && name.length() > 0) {
	               try {
	                   // Tạo file tại Server.
	                   File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
	 
	                   // Luồng ghi dữ liệu vào file trên Server.
	                   BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                   stream.write(fileData.getBytes());
	                   stream.close();
	                   saveDB("/images/" + name);
	                   uploadedFiles.add("/images/" + name);
	                   System.out.println("Write file: " + serverFile);
	               } catch (Exception e) {
	                   System.out.println("Error Write file: " + name);
	               }
	           }
	       }
	      
	       
	       model.addAttribute("description", description);
	       model.addAttribute("uploadedFiles", uploadedFiles);
	       return "uploadResult";
	   }
	   
	   public static void saveDB(String data) {
		    BufferedWriter bw = null;
			FileWriter fw = null;
			
			Resource resource = new ClassPathResource("urlimage.txt");
			try {
				File file = resource.getFile();
				if (!file.exists())
					file.createNewFile();
				fw = new FileWriter(file.getAbsoluteFile(), true);
				bw = new BufferedWriter(fw);
				bw.write(data + "\r\n");
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					if (bw != null)
						bw.close();
					if (fw != null)
						fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
	   }
	   
	   public static List<String> getDB() {
		   FileInputStream fileInputStream = null;
			BufferedReader bufferedReader = null;
			List<String> list = null;
			try {
				Resource resource = new ClassPathResource("urlimage.txt");
				
				fileInputStream = new FileInputStream(resource.getFile());
				System.out.println(fileInputStream.toString());
				bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
				
				String line = bufferedReader.readLine();
				list = new ArrayList<String>();
				while (line != null) {
					line = bufferedReader.readLine();
					list.add(line);
				}
				return list;
			} catch (Exception e) {
				return null;
			}
	   }
	   
	   public static void main(String[] args) {
		List<String> res = getDB();
		System.out.println(res.size());
	}
}
