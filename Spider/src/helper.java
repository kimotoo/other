import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import net.sf.json.JSONArray;

public class helper {
	
	// json文件读取
	public static JSONArray read(){
		
		StringBuffer sb = new StringBuffer();
		BufferedReader reader=null;
		try{
			FileInputStream in = new FileInputStream("Mysql/image.json");
			InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(inputStreamReader); 
			String tempString = null;  
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            
            reader.close();  
			
		}catch(IOException e1){
			e1.printStackTrace();
		}finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }
		
		JSONArray array = JSONArray.fromObject(sb.toString());
		
		return array;
	}
	
	// json文件写入
	public static void write(JSONArray array){
		
      StringBuffer sb = new StringBuffer(array.toString());
      
      BufferedWriter writer = null;
      
      File file = new File("Mysql/image.json");
      if(!file.exists()){
    	  try{
    		  file.createNewFile();
    	  }catch(IOException e){
    		  e.printStackTrace();
    	  }
      }
      
      try{
    	  
    	  writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
    	  writer.write(sb.toString());
    	  
      }catch(IOException e){
    	  e.printStackTrace();
      }finally{
    	  try{
    		  if(writer != null){
    			  writer.close();
    		  }
    	  }catch(IOException e1){
    		  e1.printStackTrace();
    	  }	  
      }
	}
	
	//删除文件
	public static void delFolder(String folderPath) {
		
	     try {  
		        delAllFile(folderPath); //删除完里面所有内容  
		        String filePath = folderPath;  
		        filePath = filePath.toString();  
		        java.io.File myFilePath = new java.io.File(filePath);  
		        myFilePath.delete(); //删除空文件夹  
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
	          } else {  
	              temp = new File(path + File.separator + tempList[i]);  
	          }  
	          if (temp.isFile()) {  
	             temp.delete();  
	          }  
	          if (temp.isDirectory()) {  
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件  
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹  
	             flag = true;  
	          }  
	       }  
	       return flag;  
	}  
}
