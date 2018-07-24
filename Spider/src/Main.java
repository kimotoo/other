import java.io.File;
import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;

public class Main extends JFrame{
	
	String path = new File("").getAbsolutePath();

	public static ProgressMonitor pm;
	SqlPanel sql_jp = new SqlPanel();
	SpiderPanel jp2 = new SpiderPanel();
	
	public Main(){
		
		super();
		try{
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception ex){
		ex.printStackTrace();
		}	
		
		
		this.add(sql_jp.jp1);
		this.add(sql_jp.jp2);
		this.add(jp2);
		
		
		this.setLayout(null);
		this.setSize(700, 516);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("java大作业：百度Spider");
		
		pm = new ProgressMonitor(Main.this, "正在下载", "", 0, 100);
		
	}
			
	public static void main(String[] args) {
		new Main();
	}

}
