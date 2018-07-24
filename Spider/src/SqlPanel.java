
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.json.JSONObject;

public class SqlPanel implements ActionListener {

	DefaultListModel listmodel = new DefaultListModel();
	Mysql mysql = new Mysql();
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JList list;
	JTextArea jta;

	JButton btn1 = new JButton("查询");
	JButton btn2 = new JButton("删除");
	JButton btn3 = new JButton("更新");
	JButton btn5 = new JButton("打开文件夹");

	SqlPanel() {

		this.pane1();
		this.pane2();
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn5.addActionListener(this);
	}

	private void pane1() {
		/*
		 * 容器1
		 */

		jp1.setSize(100, 50);
		jp1.setBounds(20, 20, 200, 450);
		jp1.setLayout(null);
		
		listmodel = new DefaultListModel();
		listmodel.addElement("关键字：最后更新时间");
		
		list = new JList<String>(listmodel);
		for(int i=0; i<mysql.NametoList().length; i++)
			listmodel.addElement(mysql.NametoList()[i]);
		
		System.out.println(list.getSelectedIndex());
		
		list.setFont(new Font("微软雅黑", 0, 15));
		list.setBackground(Color.white);
		list.setBounds(0, 0, 450, 300);
		list.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		list.setSelectedIndex(0);
		
		System.out.println(list.getSelectedIndex());
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				
				if(arg0.getValueIsAdjusting()){
					return;
				}
				int cho = list.getSelectedIndex();
				System.out.println(cho);

				if(cho>-1){
					if (cho == 0) {
						jta.setText("helper tips:\n"
								+ "查询：查询指定记录；\n"
								+ "删除：删除记录(同时删除文件)；\n"
								+ "更新：刷新记录；\n"
								+ "打开文件夹：打开图片所在文件夹。\n"
								);
					}
					else{
						
						JSONObject jb = Mysql.arr.getJSONObject(cho-1);
						jta.setText("关键字(name):"+jb.getString("name")+";\n"
								+ "数量(num):"+jb.getString("num")+";\n"
								+ "存储位置(saver):"+jb.getString("saver")+";\n"
								+ "最后更新时间(update)"+jb.getString("update")+";\n");
					}
				}
			}

		});

		JScrollPane js = new JScrollPane(list);
		js.setBounds(0, 0, 200, 300);
		jp1.add(js);


		btn1.setBounds(0, 310, 80, 25);
		btn1.setFont(new Font("微软雅黑", 0, 15));
		jp1.add(btn1);

		btn2.setBounds(120, 310, 80, 25);
		btn2.setFont(new Font("微软雅黑", 0, 15));
		jp1.add(btn2);

		btn3.setBounds(0, 360, 80, 25);
		btn3.setFont(new Font("微软雅黑", 0, 15));
		jp1.add(btn3);

		btn5.setBounds(0, 410, 150, 25);
		btn5.setFont(new Font("微软雅黑", 0, 15));
		jp1.add(btn5);
	}
	


	private void pane2() {
		/*
		 * 容器2
		 */

		jp2.setSize(100, 50);
		jp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "详细数据"));
		jp2.setBounds(250, 250, 430, 150);
		jp2.setLayout(null);

		jta = new JTextArea();
		jta.setBounds(20, 20, 410, 180);
		jta.setOpaque(false);
		jta.setEditable(false);
		jta.setFont(new Font("微软雅黑", 0, 15));
		jta.setText("helper tips:\n"
				+ "查询：查询指定记录；\n"
				+ "删除：删除记录(同时删除文件)；\n"
				+ "更新：刷新记录；\n"
				+ "打开文件夹：打开图片所在文件夹\n"
				);

		jp2.add(jta);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		
		if (arg0.getSource() == btn1) {
			/*
			 * 查询
			 */
			String s1 = null;
			s1 = JOptionPane.showInputDialog("请输入要查询的关键字:");
			if(!s1.equals(null)){
				int i = mysql.select(s1);
				if(i==-1)
					JOptionPane.showMessageDialog(this.jp1, "未找到该关键字对应图片");
				else
					list.setSelectedIndex(i+1);
			}

		}
		if (arg0.getSource() == btn2) {
			/*
			 * 删除
			 */
			int i = list.getSelectedIndex();
			Mysql.delete(i-1);
			
		}
		if (arg0.getSource() == btn3) {
			/*
			 * 更新
			 */
			Mysql.arr = helper.read();
			listmodel.clear();
			listmodel.addElement("关键字：最后更新时间");
			for(int i=0; i<mysql.NametoList().length; i++)
				listmodel.addElement(mysql.NametoList()[i]);
			
			list.setSelectedIndex(0);
			jta.setText("helper tips:\n"
					+ "查询：查询指定记录；\n"
					+ "删除：删除记录(同时删除文件)；\n"
					+ "更新：刷新记录；\n"
					+ "打开文件夹：打开图片所在文件夹。\n"
					);

		}

		
		if (arg0.getSource() == btn5) {
			/*
			 * 打开文件夹
//			 */	
			
			int i = list.getSelectedIndex();
			
			if(i>0){
				String saver = mysql.arr.getJSONObject(i-1).getString("saver");
				System.out.println(saver);
				try {
					Desktop.getDesktop().open(new File(saver));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					Desktop.getDesktop().open(new File("image/"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
