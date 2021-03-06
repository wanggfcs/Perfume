package main.resources.perfume.util;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {
	
	private static final String FILES_COUNT = "!!!!! files count: %s";
	private static final String DIR_NOT_EXIST = "????? directory %s not exist";
	
	private static ArrayList<String> filePathList;
	
	public static ArrayList<String> getAllTestJavaFilePath(String dirName) {
		filePathList = new ArrayList<String>();
		getTestDirPath(dirName);
		return filePathList;
	}
	
	private static void getTestDirPath(String dirName){ 
    	File dir = new File(dirName);
    	
    	if (!dir.exists()) {
    		LogUtil.print(
    				String.format(
    						DIR_NOT_EXIST, dirName));    		
    		return;
    	}
    	
    	File[] fileList = dir.listFiles();
    	
    	for (File file: fileList) {
    		String path = file.getAbsolutePath();
    		if (file.isDirectory()){
    			if (file.getName().contains("test")){
    				filePathList.add(file.getAbsolutePath());
    			}
    			else {
    				getTestDirPath(path);
    			}
    		}
    	}
    }
	
	public static ArrayList<String> getAllJavaFilePath(String dirName) {
		filePathList = new ArrayList<String>();
		getJavaFile(dirName);
		return filePathList;
	}
	
	private static void getJavaFile(String dirName){ 
    	File dir = new File(dirName);
    	
    	if (!dir.exists()) {
    		LogUtil.print(
    				String.format(
    						DIR_NOT_EXIST, dirName));    		
    		return;
    	}
    	
    	File[] fileList = dir.listFiles();
    	
    	for (File file: fileList) {
    		String path = file.getAbsolutePath();
    		if (file.isDirectory()){
    			getJavaFile(path);
    		}
    		else if (path.endsWith(".java")) {
    			filePathList.add(file.getAbsolutePath());
            }
    	}
    }
    
	public static ArrayList<String> getAllProjectName(String dirName) {
		File dir = new File(dirName);
		File[] fileList = dir.listFiles();
		ArrayList<String> projectNameList = new ArrayList<>();
    	
    	for (File file: fileList) {
    		if (file.isDirectory()){
    			projectNameList.add(file.getName());
    		}
    	}
    	
    	return projectNameList;
	}
	
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} 
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//鍏堝垹闄ゆ枃浠跺す閲岄潰鐨勬枃浠�
				delFolder(path + "/" + tempList[i]);//鍐嶅垹闄ょ┖鏂囦欢澶�
				flag = true;
			}
		}
		return flag;
	}
}
