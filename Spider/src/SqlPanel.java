
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

	JButton btn1 = new JButton("��ѯ");
	JButton btn2 = new JButton("ɾ��");
	JButton btn3 = new JButton("����");
	JButton btn5 = new JButton("���ļ���");

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
		 * ����1
		 */

		jp1.setSize(100, 50);
		jp1.setBounds(20, 20, 200, 450);
		jp1.setLayout(null);
		
		listmodel = new DefaultListModel();
		listmodel.addElement("�ؼ��֣�������ʱ��");
		
		list = new JList<String>(listmodel);
		for(int i=0; i<mysql.NametoList().length; i++)
			listmodel.addElement(mysql.NametoList()[i]);
		
		System.out.println(list.getSelectedIndex());
		
		list.setFont(new Font("΢���ź�", 0, 15));
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
								+ "��ѯ����ѯָ����¼��\n"
								+ "ɾ����ɾ����¼(ͬʱɾ���ļ�)��\n"
								+ "���£�ˢ�¼�¼��\n"
								+ "���ļ��У���ͼƬ�����ļ��С�\n"
								);
					}
					else{
						
						JSONObject jb = Mysql.arr.getJSONObject(cho-1);
						jta.setText("�ؼ���(name):"+jb.getString("name")+";\n"
								+ "����(num):"+jb.getString("num")+";\n"
								+ "�洢λ��(saver):"+jb.getString("saver")+";\n"
								+ "������ʱ��(update)"+jb.getString("update")+";\n");
					}
				}
			}

		});

		JScrollPane js = new JScrollPane(list);
		js.setBounds(0, 0, 200, 300);
		jp1.add(js);


		btn1.setBounds(0, 310, 80, 25);
		btn1.setFont(new Font("΢���ź�", 0, 15));
		jp1.add(btn1);

		btn2.setBounds(120, 310, 80, 25);
		btn2.setFont(new Font("΢���ź�", 0, 15));
		jp1.add(btn2);

		btn3.setBounds(0, 360, 80, 25);
		btn3.setFont(new Font("΢���ź�", 0, 15));
		jp1.add(btn3);

		btn5.setBounds(0, 410, 150, 25);
		btn5.setFont(new Font("΢���ź�", 0, 15));
		jp1.add(btn5);
	}
	


	private void pane2() {
		/*
		 * ����2
		 */

		jp2.setSize(100, 50);
		jp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "��ϸ����"));
		jp2.setBounds(250, 250, 430, 150);
		jp2.setLayout(null);

		jta = new JTextArea();
		jta.setBounds(20, 20, 410, 180);
		jta.setOpaque(false);
		jta.setEditable(false);
		jta.setFont(new Font("΢���ź�", 0, 15));
		jta.setText("helper tips:\n"
				+ "��ѯ����ѯָ����¼��\n"
				+ "ɾ����ɾ����¼(ͬʱɾ���ļ�)��\n"
				+ "���£�ˢ�¼�¼��\n"
				+ "���ļ��У���ͼƬ�����ļ���\n"
				);

		jp2.add(jta);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		
		if (arg0.getSource() == btn1) {
			/*
			 * ��ѯ
			 */
			String s1 = null;
			s1 = JOptionPane.showInputDialog("������Ҫ��ѯ�Ĺؼ���:");
			if(!s1.equals(null)){
				int i = mysql.select(s1);
				if(i==-1)
					JOptionPane.showMessageDialog(this.jp1, "δ�ҵ��ùؼ��ֶ�ӦͼƬ");
				else
					list.setSelectedIndex(i+1);
			}

		}
		if (arg0.getSource() == btn2) {
			/*
			 * ɾ��
			 */
			int i = list.getSelectedIndex();
			Mysql.delete(i-1);
			
		}
		if (arg0.getSource() == btn3) {
			/*
			 * ����
			 */
			Mysql.arr = helper.read();
			listmodel.clear();
			listmodel.addElement("�ؼ��֣�������ʱ��");
			for(int i=0; i<mysql.NametoList().length; i++)
				listmodel.addElement(mysql.NametoList()[i]);
			
			list.setSelectedIndex(0);
			jta.setText("helper tips:\n"
					+ "��ѯ����ѯָ����¼��\n"
					+ "ɾ����ɾ����¼(ͬʱɾ���ļ�)��\n"
					+ "���£�ˢ�¼�¼��\n"
					+ "���ļ��У���ͼƬ�����ļ��С�\n"
					);

		}

		
		if (arg0.getSource() == btn5) {
			/*
			 * ���ļ���
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
