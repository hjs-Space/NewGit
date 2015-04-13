package com.DUANGDUANG1;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class BookManage extends JPanel implements ActionListener {
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//�����ָ����, ��ֱ�ָ��ʾ Component �� y ��ָ
	private JPanel jp=new JPanel();//�����������
	//������ǩ����
	private JLabel[] jlArry=new JLabel[]
			{
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("�� �� ��"),
			new JLabel("��������"),
			new JLabel("�� Ԥ Լ"),
			new JLabel("�� �� ��")
			};
	//������ť����
	private JButton[] jbArry=new JButton[]
			{
			new JButton("ͼ�����"),
		    new JButton("ɾ��ͼ��"),
		    new JButton("�޸�ͼ���¼"),
		    new JButton("����ͼ��")
			};
	//�����ı�������
	private JTextField[] jtxtArry=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//����һ�������б��������Լ����������б��
	String[] str={"��","��"};
	private JComboBox jcb1=new JComboBox(str);
	private JComboBox jcb2=new JComboBox(str);
	
	//���ڴ��������JTableģ����ʵ��JTable
	//��������б�������
	Vector<String> head=new Vector<String>();
	{
		//����б���
		head.add("���");
		head.add("����");
		head.add("����");
		head.add("������");
		head.add("��������");
		head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
	}
	//����һ���������������
	Vector<Vector> data=new Vector<Vector>();
	//����һ�����ģ��
	DefaultTableModel dtm=new DefaultTableModel();
	//�ñ��ģ��������һ�����jttable����
	JTable jt=new JTable(dtm);
	//������װ������������ȥ
	JScrollPane jspn=new JScrollPane(jt);
	//����BookManage�ķ���
	public BookManage()
	{
		//��jp������ı���
		for(int i=0;i<5;i++)
		{
			jp.add(jtxtArry[i]);
		}
		//��jp����Ӱ�ť,���ð�ť��λ�ü���С����Ϊ��ť���ü���
		for(int i=0;i<4;i++)
		{
			jp.add(jbArry[i]);
			jbArry[i].setBounds(150+112*i,100,112,25);
			jbArry[i].addActionListener(this);
		}
		//��jp����ӱ�ǩ,�������ı����λ�ü���С�Լ���ǩ��λ��
		for(int i=0;i<7;i++)
		{
			jp.add(jlArry[i]);
			if(i<3)
			{//������š����������ߵı�ǩ�Լ��ı���λ��
				jlArry[i].setBounds(25,10+30*i,100,20);
				jtxtArry[i].setBounds(80,10+30*i,150,20);
			}
			else if(i>2&&i<5)
			{//���ó����硢�������ڵı�ǩ�Լ��ı���λ��
				jlArry[i].setBounds(275,10+30*(i-3),100,20);
				jtxtArry[i].setBounds(340,10+30*(i-3),150,20);
			}
			else
			{//�����ѽ��ġ���ԤԼ�ı�ǩλ��
				jlArry[i].setBounds(525,10+30*(i-5),100,20);
			}
		}
		//���������б���λ�ü���С 
		jcb1.setBounds(595,10,100,20);
		jcb2.setBounds(595,40,100,20);
		//��jp����������б��
		jp.add(jcb1);
		jp.add(jcb2);
		//
		this.add(jsp);
		this.setLayout(new GridLayout(1,1));//����Ϊ1��1�е����񲼾�
		jp.setLayout(null);
		jsp.setDividerLocation(140);//���÷ָ�����λ��
		jsp.setDividerSize(14);//���÷ָ����Ĵ�С
		jsp.setTopComponent(jp);//�����������ӵ��ϲ�
		jsp.setBottomComponent(jspn);//�ѹ���������ӵ��²�
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,600,500);
		this.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jbArry[0])
		{
			this.insertBook();
			System.out.println("������ͼ��");
		}
		if(e.getSource()==jbArry[1])
		{
			this.deleteBook();
		}
		if(e.getSource()==jbArry[2])
		{
			this.updatetBook();
		}
		if(e.getSource()==jbArry[3])
		{
			this.searchBook();
		}
		
	}
	DataBase db;
	String sql;
	String []str1=new String[7];
	public void insertBook()
	{
		for(int i=0;i<5;i++)
		{//���ı����������Ϣ��ŵ�һ�����飬��������
			str1[i]=jtxtArry[i].getText().trim();
		}
		str1[5]=jcb1.getSelectedItem().toString();
		str1[6]=jcb2.getSelectedItem().toString();
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&
				str1[3].equals("")&&str1[4].equals(""))
		{
			JOptionPane.showMessageDialog(this, "�������Ϣ����Ϊ��", "��Ϣ����",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else{//���Ҹ�����Ƿ����
			sql="select bookNO from book where bookNo="+Integer.parseInt(jtxtArry[0].getText().trim());
			db=new DataBase();
			db.selectDb(sql);
			try {
				String bookNo="";
				while(db.rs.next())
				{
					bookNo=db.rs.getString(1).trim();
				}
				//������ڸ���ţ�������ʾ���������ͼ����Ϣ
				if(jtxtArry[0].getText().trim().equals(bookNo))
				{
					JOptionPane.showMessageDialog(this, "������Ѿ����ڣ�", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				}else
				{//�����ݲ��뵽���ݿ���
					sql="insert into BOOK values('"+str1[0]+"','"+str1[1]+"','"+str1[2]+"','"+str1[3]+"'," +
							"'"+str1[4]+"','"+str1[5]+"','"+str1[6]+"')";
					db=new DataBase();
					db.updateDb(sql);//����DataBase�еķ���
					Vector<String> v=new Vector<String>();
					for(int i=0;i<7;i++)
					{
						v.add(str1[i]);
						if(i<5)jtxtArry[i].setText("");//����ı���
					}
					data.add(v);
					//���±��
					dtm.setDataVector(data, head);
					jt.setModel(dtm);
					jt.updateUI();
					jt.repaint();
					return;
				}
				
			} catch (SQLException e) {
				db.dbClose();//�ر����ݿ�����
				e.printStackTrace();
			}
			
		}
		
	}
	public void deleteBook()
	{
		
	}
	public void updatetBook()
	{
		
	}
	public void searchBook()
	{
		
	}
}