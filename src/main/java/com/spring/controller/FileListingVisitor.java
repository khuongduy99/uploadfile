package com.spring.controller;

import java.io.File;
import java.io.IOException;

/** Recursive listing with SimpleFileVisitor in JDK 7. */
public final class FileListingVisitor {
	public static final String ROOT = "E:\\MyProjectEclipse\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\SpringMVCUploadFile\\images\\";
	
	public static void main(String args[]) throws IOException {
//		// Creating a File object for directory
//		File directoryPath = new File(ROOT);
//		// List of all files and directories
//		File files[] = directoryPath.listFiles();
//		//System.out.println("List of files and directories in the specified directory:");
//		for(File content : files) {
//			if(content.isFile()) {
//				
//				System.out.println("file: " + content.getName());
//			} 
//			if(content.isDirectory()) {
//				System.out.println("folder: " + content.getName());
//			}
//			System.out.println(content.getParentFile());
//		}
		File file = new File("E:\\MyProjectEclipse\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\SpringMVCUploadFile\\images\\product");
		System.out.println(file.getParent());
		file = new File(file.getParent());
		System.out.println(file.getParent());
		String path = "/a/b/c";
		
		System.out.println(path.replace("/a/b", ""));
	}
}