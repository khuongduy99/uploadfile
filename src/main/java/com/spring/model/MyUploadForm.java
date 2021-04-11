package com.spring.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class MyUploadForm {
	private String urlDestFile;
    private CommonsMultipartFile[] fileDatas;
 
 
    public CommonsMultipartFile[] getFileDatas() {
        return fileDatas;
    }
    
    
 
    public String getUrlDestFile() {
		return urlDestFile;
	}



	public void setUrlDestFile(String urlDestFile) {
		this.urlDestFile = urlDestFile;
	}



	public void setFileDatas(CommonsMultipartFile[] fileDatas) {
        this.fileDatas = fileDatas;
    }
}
