package com.DUANGDUANG1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;

import javax.swing.table.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class SearchBook extends JPanel implements ActionListener{
	
	String sql;
	DataBase db;
	bookMode bm;
	JTable jt;
	JScrollPane jspn;
	int flag;
   //�����ָ��Ϊ���µ�JSplitePane����
    private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jpJ=new JPanel();//����JPanel����
	//������ʾ�����б������ģ�͵��ַ�������
	private String[] str={"����","������","����","����ʱ��"};
	private JComboBox jcb=new JComboBox(str);//���������б��
	private JButton jb=new JButton("�ύ");	//������ť
	private JLabel[] jlArray=new JLabel[]{
		new JLabel("��    ��"),
		new JLabel("��    ��"),
		new JLabel("������")
	};	
	private JTextField[] jtxtArray=new JTextField[]{//�����ı���
		new JTextField(),new JTextField(),
	    new JTextField(),new JTextField()
	};
	private JRadioButton[] jrbArray={//������ѡ��ť
		new JRadioButton("�߼���ѯ"),
		new JRadioButton("�򵥲�ѯ",true),
	};	
	private ButtonGroup bg=new ButtonGroup();//������ť��
	
	public SearchBook(){
		bm=new bookMode();
		jt=new JTable(bm);
		jspn=new JScrollPane(jt);//��JTable��װ����������
		
		//��������SearchBook�������²��־�Ϊ�ղ��ֹ�����
		jpJ.setLayout(null);
		//���õ�ѡ��Ĵ�С��λ�ã�������¼�������
		jpJ.add(jcb);
		jcb.setBounds(140,80,170,20);	
	    jcb.addActionListener(this);
        //���JButton�������Сλ�ò�����¼�������
		jpJ.add(jb);
		jb.setBounds(565,80,120,20);
		jb.addActionListener(this);
		for(int i=0;i<2;i++){//�Ե�ѡ��ť��������
			jrbArray[i].setBounds(20,30+i*50,100,20);
			jpJ.add(jrbArray[i]);//����ѡ��ť��ӵ����
			bg.add(jrbArray[i]);//����ť��ӵ���ť��
			jrbArray[i].addActionListener(this);//��ע�����
		}
		for(int i=0;i<3;i++){//���ñ�ǩ���ı�������꣬��������ӽ�JPanel
			jlArray[0].setBounds(140,30,80,20);
			jtxtArray[0].setBounds(190,30,120,20);
			jlArray[1].setBounds(325,30,80,20);
			jtxtArray[1].setBounds(370,30,120,20);
			jlArray[2].setBounds(525,30,80,20);
			jtxtArray[2].setBounds(565,30,120,20);
			jpJ.add(jtxtArray[i]);	
			jpJ.add(jlArray[i]);
		}
		for(int i=0;i<3;i++){//�����ı���Ϊ������
			jtxtArray[i].setEditable(false);
		}
    	//�����ı��������,����ӽ�jpJ
		jtxtArray[3].setBounds(325,80,170,20);
		jpJ.add(jtxtArray[3]);
        jsp.setTopComponent(jpJ);//��jpJ���õ�jsp���ϲ�����
        jsp.setBottomComponent(jspn);
        jsp.setDividerSize(10);
    	jsp.setDividerLocation(130);//����jsp�зָ����ĳ�ʼλ��
    	this.setLayout(new GridLayout(1,1));//���ò�ѯͼ�����Ϊ���񲼾�
    	this.add(jsp);
		//���ô���Ĵ�Сλ�ü��ɼ���
        this.setBounds(3,10,600,400);
        this.setVisible(true);
	}
    //Ϊ�¼����صļ��������ϴ����¼�
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if(jrbArray[1].isSelected())//���ѡ�м򵥲�ѯ
		{
			jtxtArray[3].setEditable(true);//���򵥲�ѯ���ı�������Ϊ�ɱ༭
			for(int i=0;i<jtxtArray.length-1;i++)
			{
				jtxtArray[i].setEditable(false);//���߼���ѯ���ı�������Ϊ���ɱ༭
			}
			if(jcb.getSelectedIndex()>=0&&jcb.getSelectedIndex()<4)//ѡ��Ҫ��ѯ������
			{
				jtxtArray[3].requestFocus();//�ı����þ۽�
				if(e.getSource()==jb)
				{//����¼�ԴΪ"�ύ"��ť����ִ�м���
					String str=jtxtArray[3].getText().trim();//��ȡ�ı������������Ϣ
					if(str.equals(""))
					{//�ı�������Ϊ�գ�������ʾ
						JOptionPane.showMessageDialog(this, "���벻��Ϊ�գ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else{
						if(jcb.getSelectedIndex()==0)
						{//ѡ��������ѯ
							sql="select * from book where BookName='"+str+"'";
							jtxtArray[3].setText("");
						}
						else if(jcb.getSelectedIndex()==1)
						{//ѡ�񰴳������ѯ
							sql="select * from book where Publishment='"+str+"'";
							jtxtArray[3].setText("");
						}
						else if(jcb.getSelectedIndex()==2)
						{//ѡ�����߲�ѯ
							sql="select * from book where Author='"+str+"'";
							jtxtArray[3].setText("");
						}
						else if(jcb.getSelectedIndex()==3)
						{//ѡ�񰴹���ʱ���ѯ
							sql="select * from book where BuyTime='"+str+"'";
							jtxtArray[3].setText("");
						}
						try {
							//��һ�����Ҫ�����û����һ�䣬�������Ĳ����оͲ���ʵ�ָù���
							sql=new String(sql.getBytes("gbk"),"gbk");//��ȡ���ֽ���Ϊgbk����ǿ��ת��Ϊgbk
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						try {
							db=new DataBase();
							db.selectDb(sql);//ִ�в��ҹ���
							int flag=0;
							while(db.rs.next()){//������α�����
								flag++;
							}
							if(flag==0){//û�и�����Ϣ��������ʾ
								JOptionPane.showMessageDialog(this, "û�и������Ϣ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					bm=new bookMode(sql);//����bookMode���󣬲���sql��������ȥ
					jt.setModel(bm);//���±��
				}
			}
		}
		else if(jrbArray[0].isSelected())
		{//ѡ�и߼���ѯ��ť
			jtxtArray[0].requestFocus();//��һ���ı����þ۽�
			jtxtArray[3].setEditable(false);//���򵥲�ѯ���ı�������Ϊ���ɱ༭
			for(int i=0;i<jtxtArray.length-1;i++)
			{
				jtxtArray[i].setEditable(true);//���߼���ѯ���ı�������Ϊ�ɱ༭
			}
			if(e.getSource()==jb)
			{//����ı������������
				String str0=jtxtArray[0].getText().trim();
				String str1=jtxtArray[1].getText().trim();
				String str2=jtxtArray[2].getText().trim();
				if(str0.equals("")&&str1.equals("")&&str2.equals(""))
				{
					JOptionPane.showMessageDialog(this, "�������Ϣ����Ϊ�գ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else if(!str0.equals("")&&!str1.equals("")&&str2.equals(""))
				{
					sql="select * from book where bookName='"+str0+"'and Author='"+str1+"'";
					for(int i=0;i<jtxtArray.length-1;i++)
					{
						jtxtArray[i].setText("");
					}
				}
				else if(str0.equals("")&&!str1.equals("")&&!str2.equals(""))
				{
					sql="select * from book where Publishment='"+str2+"'and Author='"+str1+"'";
					for(int i=0;i<jtxtArray.length-1;i++)
					{
						jtxtArray[i].setText("");
					}
				}
				else if(!str0.equals("")&&str1.equals("")&&!str2.equals(""))
				{
					sql="select * from book where bookName='"+str0+"'and Publishment='"+str2+"'";
					for(int i=0;i<jtxtArray.length-1;i++)
					{
						jtxtArray[i].setText("");
					}
				}
				else if(!str0.equals("")&&!str1.equals("")&&!str2.equals(""))
				{
					sql="select * from book where bookName='"+str0+"'and Author='"+str1+"' and Publishment='"+str2+"'";
					for(int i=0;i<jtxtArray.length-1;i++)
					{
						jtxtArray[i].setText("");
					}
				}
				try {
					sql=new String(sql.getBytes("gbk"),"gbk");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				db=new DataBase();
				db.selectDb(sql);
				int flag=0;
				try {
					while(db.rs.next()){
						flag++;
					}
					if(flag==0){//û�и�����Ϣ��������ʾ
						JOptionPane.showMessageDialog(this, "û�и������Ϣ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				bm=new bookMode(sql);
				jt.setModel(bm);
			}
		}
		
	}
	
}