import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpiderPanel extends JPanel {
	/**
	 * Spider GUIÊµÏÖ
	 */
	private static final long serialVersionUID = 1L;
	
	
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	JLabel label3 = new JLabel();
	JButton btn = new JButton("È·¶¨");
	JCheckBox cb = new JCheckBox("ÐÞ¸ÄÄ¿Â¼");

	public SpiderPanel(){
		
		this.setSize(100,50);
		this.setBorder(BorderFactory.createTitledBorder("SpiderÆô¶¯"));
		this.setBounds(250, 20, 430, 200);
		this.setLayout(null);
		
		label1.setText("Í¼Æ¬¹Ø¼ü×Ö");
		label1.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 15));
		label1.setBounds(20,40,100,25);
		JTextField tf1 = new JTextField();
		tf1.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 15));
		tf1.setBounds(105,40,250,25);
		
		this.add(label1);
		this.add(tf1);
		
		label2.setBounds(20,80,100,25);
		label2.setText("   Í¼Æ¬ÊýÄ¿");
		label2.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 15));
		JTextField tf2 = new JTextField();
		tf2.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 15));
		tf2.setBounds(105,80,250,25);
		
		this.add(label2);
		this.add(tf2);
		
		label3.setText("´æ´¢¸ùÄ¿Â¼");
		label3.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 15));
		label3.setBounds(20,120,100,25);
		JTextField tf3 = new JTextField();
		tf3.setText("image/");
		tf3.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 15));
		tf3.setBounds(105,120,250,25);
		tf3.setEditable(false);
		
		this.add(label3);
		this.add(tf3);
		
		cb.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				tf3.setEditable(!tf3.isEditable());
			}
			
		});
		
		cb.setBounds(105,160,100,25);
		cb.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 15));
		this.add(cb);
		
		

		JButton btn = new JButton("È·¶¨");
		btn.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 15));
		btn.setBounds(250,160,100,25);
		
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String name=null;
				int num=0;
				try{
					name = tf1.getText();
					num = Integer.parseInt(tf2.getText());
				}catch(Exception e2){
					JOptionPane.showMessageDialog(SpiderPanel.this, "Çë¼ì²é²ÎÊý");
				}

				String saver = tf3.getText();
				String str="";
				
				System.out.println(name);
				
				byte[] utf8Decode;
				try {
					utf8Decode = name.getBytes("utf-8");
			        for (byte b: utf8Decode) 
			        	str += "%"+Integer.toHexString(b & 0xFF);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				str = str.toUpperCase();

				
				if(!str.equals(null) && num!=0){
					
					File file = new File(saver+name);
			        if(!file.exists()){
			        	file.mkdirs();
			        }
			        
			        Spider sp = new Spider(str, num, file.getAbsolutePath());
			        sp.run();
			        
			        while(true){
			        	if(sp.end){
			        		Mysql.insert(name, sp.num, sp.saver);
			        		JOptionPane.showMessageDialog(SpiderPanel.this, "ÏÂÔØ½áÊø");
			        		break;
			        	}
			        }
				}
				
			}
			
		});
		this.add(btn);	
	}
	
	

}
