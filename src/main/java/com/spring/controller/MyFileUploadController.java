package com.spring.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.spring.model.FileImage;
import com.spring.model.MyUploadForm;

@Controller
public class MyFileUploadController {
	
	private static final String FOLDER_ROOT = "/images";
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
	   public String getImages(HttpServletRequest request, Model model) {
		   	String path = request.getParameter("path");
		   	if(path == null) path = "";
		   	path = path.replace("1408dt2410", File.separator);
		   	String rootSever = FOLDER_ROOT + File.separator + path;
		   	String root = request.getServletContext().getRealPath(rootSever);
			File directoryPath = new File(root);
			// List of all files and directories
			File files[] = directoryPath.listFiles();
			List<FileImage> fileImage = new ArrayList<FileImage>();
			int id = 0;
			for(File file : files) {
				id++;
				fileImage.add(new FileImage(id, file.getName(), rootSever + File.separator + file.getName(), file.isDirectory() ? true : false));
			}
	       model.addAttribute("Files", fileImage);
	       String prePath = directoryPath.getParent().replace(request.getServletContext().getRealPath(FOLDER_ROOT), "");
	       prePath = prePath.replace(File.separator, "1408dt2410");
	       model.addAttribute("pre", prePath);
	       
	       String nowPath = directoryPath.getPath().replace(request.getServletContext().getRealPath(FOLDER_ROOT), "");
	       nowPath = nowPath.replace(File.separator, "1408dt2410");
	       model.addAttribute("now", nowPath);
	       MyUploadForm myUploadForm = new MyUploadForm();
	       model.addAttribute("myUploadForm", myUploadForm);
	       return "thuvienanh";
	   }
	  
	   // POST: Sử lý Upload
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
	   public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
	           Model model, //
	           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	 
	       return this.doUpload(request, model, myUploadForm);
	 
	   }
	   
	// POST: Sử lý Upload
	   @RequestMapping(value = "/themfolder", method = RequestMethod.POST)
	   public String createFolder(HttpServletRequest request, Model model) {
		  	String nameFolder = request.getParameter("nameFolder");
		   	String path = request.getParameter("nowPath");
		   	String newpath = path.replace("1408dt2410", File.separator);
		   	String newFolder = FOLDER_ROOT + File.separator + newpath + File.separator + nameFolder;
		   	String pathNewFolder = request.getServletContext().getRealPath(newFolder);
			File folder = new File(pathNewFolder);
			if(!folder.exists()) folder.mkdirs();
			
		   return "redirect:thuvienanh?path=" + path;
	 
	   }
	   
	
	   
	// POST: Sử lý Upload
	   @RequestMapping(value = "/xoaanh", method = RequestMethod.GET)
	   public String deletefile(HttpServletRequest request, Model model) {
		   String nowpath = request.getParameter("path");
		   String name = request.getParameter("name");
			String newpath = nowpath.replace("1408dt2410", File.separator);
			String fileDelete = FOLDER_ROOT + File.separator + newpath + File.separator + name;
			String pathFileDelete = request.getServletContext().getRealPath(fileDelete);
		   try {
	            File file = new File(pathFileDelete);
	            if (file.delete()) {
	                System.out.println(file.getName() + " is deleted!");
	            } else {
	                System.out.println("Delete operation is failed.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		   return "redirect:thuvienanh?path=" + nowpath;
	 
	   }
	 
	   private String doUpload(HttpServletRequest request, Model model, MyUploadForm myUploadForm) {
		   
		   String path = myUploadForm.getUrlDestFile().replace("1408dt2410", File.separator);
	       // Thư mục gốc upload file.
	       String uploadRootPath = request.getServletContext().getRealPath(FOLDER_ROOT + File.separator + path);
	 
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
	                  
	                   uploadedFiles.add("/images/" + name);
	                   System.out.println("Write file: " + serverFile);
	               } catch (Exception e) {
	                   System.out.println("Error Write file: " + name);
	               }
	           }
	       }
	      
	       return "redirect:thuvienanh?path=" + myUploadForm.getUrlDestFile();
	   }
	   
	   
	   
	  
		
}
