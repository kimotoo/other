import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Spider {
	
	
	private static String url_1 = "http://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=";
	private static String url_2 = "&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=&z=&ic=&word=";
	private static String url_3 = "&s=&se=&tab=&width=&height=&face=&istype=&qc=&nc=1&fr=&pn=";
	private static String url_4 = "&rn=30&gsm=1e&1516436651147="; 
	
	static AtomicInteger ai = new AtomicInteger(0);
	
	String data;
	String saver;
	int num;
	public boolean end=false;

	public Spider(String data, int num, String saver){
		
		this.data = data;
		this.num = num;
		this.saver = saver;
	}
	
	public void run(){
		
		ai.set(0);
		int page=30;
		ExecutorService exec = Executors.newFixedThreadPool(10);
		System.out.println(exec.isTerminated());
		System.out.println(exec.isShutdown());
		
		while(Spider.ai.get()<this.num){
			
			//判断线程池中子线程是否全部结束
			if(!exec.isTerminated()){
				
				//判断线程池是否被关闭
				if(!exec.isShutdown()){
					
					int min = (this.num-Spider.ai.get())>30 ? 30 : (this.num-Spider.ai.get());
					//System.out.println(min);
		    		//请求json并转换为json数组
		    		String output = sendGet(this.data, page);
		    		System.out.println(output);
					JSONObject json=JSONObject.fromObject(output);
					JSONArray arr;
		            try{
		                arr=json.getJSONArray("data");
		                page+=30;
		            }catch (Exception e){
		                break;
		            }            
		                   
		            //下载json数组中图片
		            for(int j=0;j<min;j++){
		                String imgUrl=arr.getJSONObject(j).getString("thumbURL");
		                //System.out.println(imgUrl);
		                //启动多线程
		                exec.execute(new Download(imgUrl, this.saver));       
		            }
		            
		            exec.shutdown();
				}
			}
			else{
				if(ai.get()==this.num)
	            	break;
				exec = Executors.newFixedThreadPool(10);
			}	
			
		}
		this.end = true;
		System.out.println(ai.get());
		
	}
	

	private static String sendGet(String s,int i) {
		
		String result = "";
	    BufferedReader in = null;
	    String url = url_1 + s + url_2 + s + url_3 + i + url_4;
	    
	    try{
	    	
	    	// 建立连接
	    	java.net.URL rlurl = new URL(url);
	    	URLConnection con = rlurl.openConnection();
        	con.setConnectTimeout(5000);
        	con.setReadTimeout(10000);
        	con.setRequestProperty("Accept", "text/plain, */*; q=0.01");
        	con.setRequestProperty("Connection", "keep-alive");
        	con.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");
        	con.connect();
	    	
	    	// 接受数据流
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			
	    	String line;
	    	while((line = in.readLine()) != null) {
	    		result += line;
	    	}
	    	System.out.println(result);
		}catch(Exception e1){
			System.out.println("读取输入流时发生错误");
		}finally {
			
			try {
				//关闭输入流
	    		if(in != null) {
	    			in.close();
	    		}
	    	}catch(Exception e1) {
	    		e1.printStackTrace();
	    	}
		} 	
	    
	    return result;
	}
	
	


	//下载图片
	class Download implements Runnable {
		
		private String imgUrl, saver;
		
	    public Download(String imgUrl, String saver){
	        this.imgUrl=imgUrl;
	        this.saver = saver;    
	    }
	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try{
				
				URL url=new URL(imgUrl);
	            URLConnection conn = url.openConnection();
	
	            
	            //参数设置
	            conn.setReadTimeout(10000);
	            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
	            conn.setRequestProperty("Connection", "keep-alive");
	            conn.setRequestProperty("Referer", "http://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&word=%E8%BD%A6%E7%89%8C");
	            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
	        	conn.setRequestProperty("user-agent",
	                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");
	            conn.connect();
	
	            // 获取图片后缀，多为.jgp
	            String type[] = imgUrl.split("\\.",10);
	            
	            InputStream in=conn.getInputStream();
	            
	            String name = Thread.currentThread().getName()+System.currentTimeMillis();
	            		
	            FileOutputStream out=new FileOutputStream(this.saver+"/"+name+"."+type[type.length-1]);
	            
	            
	            // 图像数据写入
	            byte[] buf = new byte[1024];
	            int len=0;
	            while ((len=in.read(buf))!=-1){
	                out.write(buf,0,len);
	            }
	            
	            in.close();
	            out.close();
	            System.out.println(name+"-"+ai.get());            
	            ai.getAndIncrement();
	            Main.pm.setProgress(ai.get());
	                  	
			}catch(Exception e1){
				System.out.println("图像下载失败");
			} 		
		}
	}
}
