import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Mysql {
		/*
		 * 以json实现sql的增删改查方法实现	
		 * name, saver, update_time,num;
		 */

	
	public static JSONArray arr;
	
	Mysql(){
		
		  File file = new File("Mysql");
	      if(!file.exists()){
	      	file.mkdir();
	      }
	      
	      File json_file = new File(file.getAbsolutePath()+"/image.json");
	      if(!json_file.exists()){
		      	try {
					json_file.createNewFile();
					JSONArray arr = new JSONArray();
					helper.write(arr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }
	      
	      arr = helper.read();     
	}
	
	public static void insert(String name, int num, String saver){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		int i = Mysql.select(name);
		if(i==-1){
			JSONObject jb = new JSONObject();
			jb.element("name", name);
			jb.element("num", num);
			jb.element("update", df.format(System.currentTimeMillis()));
			jb.element("saver", saver);
			
			arr.add(jb);
			helper.write(arr);	
		}else{
			int n = arr.getJSONObject(i).getInt("num");
			arr.getJSONObject(i).replace("num", n+num);
			arr.getJSONObject(i).replace("update", df.format(System.currentTimeMillis()));
			
			helper.write(arr);
			
		}

	}
		
	public static void delete(int i){

		if(i<Mysql.arr.size()){
			//此处同时删除对应文件夹
			helper.delFolder(arr.getJSONObject(i).getString("saver"));
			Mysql.arr.remove(i);	
			helper.write(arr);
		}else{
			System.out.println("文件不存在");
		}

	}
	
	public static int select(String name){
		
		
		int i=0;
		if(!name.equals(null)){
			for(;i<Mysql.arr.size(); i++){
				if(Mysql.arr.getJSONObject(i).getString("name").equals(name))
					break;
			}
			if(i<arr.size()){
				return i;	
			}	
		}

		return -1;
	}
	
	// 生成name+update list
	public String[] NametoList(){
		
		String[] list = new String[arr.size()];
		
		for(int i=0;i<arr.size(); i++){
			String name=arr.getJSONObject(i).getString("name");
			String update=arr.getJSONObject(i).getString("update");
			list[i] = name+":"+update;
		}
		
		return list;	
	}		
}
