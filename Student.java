package com.DUANGDUANG1;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.table.*;

import java.sql.*;
import java.util.*;
import java.util.Date;
public class Student extends JPanel implements ActionListener
{   //����һ�����·���ָ��JSplitPane����
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpAdd=new JPanel();//�������ѧ�����
	private JPanel jpDelete=new JPanel();//����ɾ��ѧ�����
	private JPanel jpUpdate=new JPanel();//�����������
	private JPanel jpSearch=new JPanel();//������ѯ���
	private JTabbedPane jtp=new JTabbedPane();//����ѡ�����
	JTable jt;
	JScrollPane jspn;
	String[]str1=new String[7];//�����ַ�������
	String sql;
	DataBase db;
	stuMode sm;
	private JLabel[] jlArray=new JLabel[]{//���ѧ��������ǩ����
			new JLabel("ѧ    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("Ժ    ϵ"),
			new JLabel("��    ��"),
			new JLabel("����Ȩ��")   
	};
	private JLabel[] jlArrayDe=new JLabel[]{//����ɾ��ѧ���ı�ǩ
			new JLabel("ѧ    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("Ժ    ϵ"),
			new JLabel("��    ��"),
			new JLabel("����Ȩ��")   
	};
	private JLabel[] jlArrayUp=new JLabel[]{//�޸����������ǩ����
			new JLabel("ѧ    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("Ժ    ϵ"),
			new JLabel("��    ��"),
			new JLabel("����Ȩ��")   
	};
	private JLabel[] jlArraySe=new JLabel[]{//�޸����������ǩ����
			new JLabel("ѧ    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("Ժ    ϵ"),
			new JLabel("��    ��"),
			new JLabel("����Ȩ��")   
	};
	private JTextField[] jtxtArray=new JTextField[]{//���ѧ�����������ı�������
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private JTextField[] jtxtArrayDe=new JTextField[]{//ɾ��ѧ�������ı���
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private JTextField[] jtxtArrayUp=new JTextField[]{//�������������ı�������
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private JTextField[] jtxtArraySe=new JTextField[]{//chaxun���������ı�������
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private String[] str={"��","��"};//���������б������ģ�͵��ַ�������
	private JComboBox jcp=new JComboBox(str);//���������б��
	private JComboBox jcpUp=new JComboBox(str);//���������б��
	private JComboBox jcpDe=new JComboBox(str);//���������б��
	private JComboBox jcpSe=new JComboBox(str);//���������б��
	private JButton[] jbArray={//����JButton��ť���ı�
			new JButton("ȷ�����"),
			new JButton("ȷ���޸�"),
			new JButton("ȷ��ɾ��"),
			new JButton("��       ѯ")
	};

	public Student()
	{

		stuMode sm=new stuMode();//����һ������ģ�Ͷ���
		jt=new JTable(sm);//��ʼ��JTable
		jspn=new JScrollPane(jt);//��JTable��װ����������
		this.setLayout(new GridLayout(1,1));//����������Ϊ���񲼾�
		jpAdd.setLayout(null);//���������ϲ���Ϊ�ղ��ֹ�����
		jpDelete.setLayout(null);
		jpUpdate.setLayout(null);
		jpSearch.setLayout(null);
		jsp.setDividerLocation(200);//����jp�зָ����ĳ�ʼλ��
		jsp.setDividerSize(14);//���÷ָ����Ŀ��
		jsp.setTopComponent(jtp);
		jtp.addTab("���ѧ����Ϣ",jpAdd);
		jtp.addTab("ɾ��ѧ����Ϣ",jpDelete);
		jtp.addTab("�޸�ѧ����Ϣ",jpUpdate);
		jtp.addTab("����ѧ����Ϣ",jpSearch);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<6;i++){//���ı�����ӽ��ϲ����
			jpAdd.add(jtxtArray[i]);
		}
		for(int i=0;i<6;i++){//���ı�����ӽ��ϲ����
			jpUpdate.add(jtxtArrayUp[i]);
			jtxtArrayUp[0].setEditable(false);
		}
		for(int i=0;i<6;i++){//���ı�����ӽ��ϲ����
			jpDelete.add(jtxtArrayDe[i]); 
			for(int j=1;j<6;j++)
			{
				jtxtArrayDe[j].setEditable(false);
			}

		}
		for(int i=0;i<6;i++){//���ı�����ӽ��ϲ����
			jpSearch.add(jtxtArraySe[i]);
			for(int j=1;j<6;j++)
			{
				jtxtArraySe[j].setEditable(false);
			}
		}
		//������ı�ǩ
		for(int i=0;i<7;i++){
			jpAdd.add(jlArray[i]);
			if(i<3)
			{//�Խ����ϵĵ�һ�б�ǩ���ı����Сλ�ý�������
				jlArray[i].setBounds(25+i*220,80,100,20);
				jtxtArray[i].setBounds(105+i*200,80,120,20);
				jtxtArray[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{//�Եڶ��б�ǩ���ı����Сλ�ý�������
				jlArray[i].setBounds(25+(i-3)*220,120,100,20);
				jtxtArray[i].setBounds(105+(i-3)*200,120,120,20);
				jtxtArray[i].addActionListener(this);
			}
			else
			{//�����������ʾ��ǩ��������
				jlArray[i].setBounds(650,80,100,20);
			}
		}
		//ɾ������ǩ���ı���
		for(int i=0;i<7;i++){
			jpDelete.add(jlArrayDe[i]);
			if(i<3)
			{//�Խ����ϵĵ�һ�б�ǩ���ı����Сλ�ý�������
				jlArrayDe[i].setBounds(25+i*220,80,100,20);
				jtxtArrayDe[i].setBounds(105+i*200,80,120,20);
				jtxtArrayDe[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{//�Եڶ��б�ǩ���ı����Сλ�ý�������
				jlArrayDe[i].setBounds(25+(i-3)*220,120,100,20);
				jtxtArrayDe[i].setBounds(105+(i-3)*200,120,120,20);
				jtxtArrayDe[i].addActionListener(this);
			}
			else
			{//�����������ʾ��ǩ��������
				jlArrayDe[i].setBounds(650,80,100,20);
			}
		}
		//�������ı�ǩ���ı���
		for(int i=0;i<7;i++){
			jpUpdate.add(jlArrayUp[i]);
			if(i<3)
			{//�Խ����ϵĵ�һ�б�ǩ���ı����Сλ�ý�������
				jlArrayUp[i].setBounds(25+i*220,80,100,20);
				jtxtArrayUp[i].setBounds(105+i*200,80,120,20);
				jtxtArrayUp[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{//�Եڶ��б�ǩ���ı����Сλ�ý�������
				jlArrayUp[i].setBounds(25+(i-3)*220,120,100,20);
				jtxtArrayUp[i].setBounds(105+(i-3)*200,120,120,20);
				jtxtArrayUp[i].addActionListener(this);
			}
			else
			{//�����������ʾ��ǩ��������
				jlArrayUp[i].setBounds(650,80,100,20);
			}
		}
		//��ѯ���ı�ǩ���ı���
		for(int i=0;i<7;i++){
			jpSearch.add(jlArraySe[i]);
			if(i<3)
			{//�Խ����ϵĵ�һ�б�ǩ���ı����Сλ�ý�������
				jlArraySe[i].setBounds(25+i*220,80,100,20);
				jtxtArraySe[i].setBounds(105+i*200,80,120,20);
				jtxtArraySe[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{//�Եڶ��б�ǩ���ı����Сλ�ý�������
				jlArraySe[i].setBounds(25+(i-3)*220,120,100,20);
				jtxtArraySe[i].setBounds(105+(i-3)*200,120,120,20);
				jtxtArraySe[i].addActionListener(this);
			}
			else
			{//�����������ʾ��ǩ��������
				jlArraySe[i].setBounds(650,80,100,20);
			}
		}
		this.add(jsp);
		jsp.setBottomComponent(jspn);//�����²��Ӵ���
		jcp.setBounds(730,80,100,20);
		jcpUp.setBounds(730,80,100,20);
		jcpDe.setBounds(730,80,100,20);
		jcpSe.setBounds(730,80,100,20);
		jpAdd.add(jcp);
		jpUpdate.add(jcpUp);
		jpDelete.add(jcpDe);
		jpSearch.add(jcpSe);
		jcpDe.setEnabled(false);
		jcpSe.setEnabled(false);
		jbArray[0].setBounds(730, 120, 100, 20);
		jbArray[1].setBounds(730, 120, 100, 20);
		jbArray[2].setBounds(730, 120, 100, 20);
		jbArray[3].setBounds(730, 120, 100, 20);
		jpAdd.add(jbArray[0]);
		jpUpdate.add(jbArray[1]);
		jpDelete.add(jbArray[2]);
		jpSearch.add(jbArray[3]);
		for(int i=0;i<4;i++)
		{//��JButton��ӽ�jpAdd

			jbArray[i].addActionListener(this);	//���ü�����
		}
		jt.addMouseListener(new MouseAdapter() {
			JTable jt;

			public void mouseClicked(MouseEvent e){
				if(e.getClickCount()==2){
					int row = ((JTable)e.getSource()).getSelectedRow();//������ѡ�к�
					if(row>=0){
						//ѡ����һ��
						for(int i=0;i<6;i++){
							jtxtArrayUp[i].setText(((JTable)e.getSource()).getValueAt(row, i).toString());
						}
					}
				}
			}
		});
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,600,500);
		this.setVisible(true);			
	}
	public void actionPerformed(ActionEvent e)
	{
		//�����"���ѧ����Ϣ"��ť�ǽ�ִ����ӹ��ܣ����ı����ѧ����Ϣ��ӽ�STUDENT����
		if(e.getSource()==jbArray[0])
		{
			this.insertStudent();
			sm =new stuMode();
			jt.setModel(sm);
		}
		//�����"ɾ��ѧ����Ϣ"��ť�ǽ�ִ��ɾ�����ܣ���ѧ��Ϊѧ�ſ��ѧ����Ϣ��STUDENT����ɾ��	
		if(e.getSource()==jbArray[2])
		{
			this.deleteStudent();
			sm =new stuMode();
			jt.setModel(sm);
		}
		//�����"�޸�ѧ����Ϣ"��ť�ǽ�ִ���޸Ĺ��ܣ�����ϢΪѧ�ſ��ѧ����Ϣ��STUDENT���и���
		if(e.getSource()==jbArray[1])
		{
			this.updateStudent();
			System.out.println("����޸�");
			sm=new stuMode();
			jt.setModel(sm);

		}
		//�����"����ѧ����Ϣ"��ť�ǽ�ִ�в��ҹ��ܣ�����STUDENT���в���ѧ��Ϊѧ�ſ��ѧ����Ϣ
		if(e.getSource()==jbArray[3])
		{
			System.out.println("���ȷ�ϲ�ѯ��ť");
			sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxtArraySe[0].getText().trim());
			//			this.searchStudent();
			sm=new stuMode(sql);
			jt.setModel(sm);
		}
	}
	public void searchStudent(){
		if(jtxtArraySe[0].getText().equals("")){//
			JOptionPane.showMessageDialog(this,"ѧ�Ų���Ϊ�գ����������룡����",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxtArraySe[0].getText().trim());
		db=new DataBase();
		db.selectDb(sql);//��ѯѧ���ı�������ѧ���Ƿ������STUDENT����				   
		try{//�Խ���������쳣����
			int k=0;
			while(db.rs.next()){
				k++;
				for(int i=1;i<=7;i++){//˳��ﵽ���ѵ��Ľ���еĸ����¼
					if(i<6){jtxtArraySe[i].setText("");}
				}
			}
			if(k==0){//��Book����û�и���ţ��������ʾ�Ի���
				JOptionPane.showMessageDialog(this,	"û�и�ѧ����Ϣ��",
						"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			}	
			for(int i=0;i<=6;i++){//���ı�����Ϣ���
				if(i<6){jtxtArraySe[i].setText("");}
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally{db.dbClose();}
	}
	public void insertStudent(){
		for(int i=0;i<6;i++){//�����ı���������Ϣ
			str1[i]=jtxtArray[i].getText().trim();		
		}
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")
				&&str1[3].equals("")&&str1[4].equals("")&&str1[5].equals(""))
		{//�����ı���Ϊ����ʾ
			JOptionPane.showMessageDialog(this,	"ѧ����Ϣ����Ϊ�գ�����",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")
				&&!str1[3].equals("")&&!str1[4].equals("")&&!str1[5].equals(""))
		{//�����ı���������Ϣ
			sql="select StuNo from student where StuNo="+Integer.parseInt(jtxtArray[0].getText().trim());
			db=new DataBase();
			db.selectDb(sql);//���������Ƕ��û�����������в�ѯ����֤���
			try{
				String StuNo="";
				while(db.rs.next()){//ȡ������������ݲ���ֵ
					StuNo=db.rs.getString(1).trim();				
				}
				if(jtxtArray[0].getText().trim().equals(StuNo)){//��Ϣ��ʾ��ѧ�Ŵ���
					JOptionPane.showMessageDialog(this, "��ѧ���Ѵ���ѧ����", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				}
				else{//���ѧ��
					
					str1[6]=jcp.getSelectedItem().toString();
					sql="insert into student(StuNO,StuName,StuSex,Class,Department,"
							+"Password,Permitted) values('"+str1[0]+"','"+str1[1]+"','"
							+ str1[2] + "',' "+str1[3]+"','"+
							str1[4]+"','"+str1[5]+"','"+str1[6]+"')";
					db=new DataBase();
					db.updateDb(sql);//����ѧ����Ϣ
					for(int i=0;i<=6;i++){//��ÿ����ӵ���ʱ����v
						if(i<6){jtxtArray[i].setText("");}	
					}
					return;   
				}
			}
			catch(Exception e1){e1.printStackTrace();}
			db.dbClose();//�ر����ݿ�����

		}
	}
	public void deleteStudent(){
		String stuno = jtxtArrayDe[0].getText().trim();
		for(int j=1;j<6;j++){
			jtxtArrayDe[j].setEditable(false);
		}
		if(stuno.equals("")){//��ѧ������Ϊ����ʾ
			JOptionPane.showMessageDialog(this,	"������Ҫɾ����ѧ����ѧ�ţ�Ȼ����ɾ������",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select * from RECORD where StuNO="+Integer.parseInt(stuno);
		db=new DataBase();
		db.selectDb(sql);//��ѯѧ��Ϊ�����ѧ����֤�����
		try{//�Խ���������쳣����
			if(db.rs.next()){
				//��Record���ı����и�ѧ�ţ��������ʾ�Ի���
				JOptionPane.showMessageDialog(this,	"����ɾ��������Ϣ��������δ����",
						"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				sql="delete from STUDENT where StuNO="+Integer.parseInt(stuno);
				db=new DataBase();
				int rr=db.updateDb(sql);//ɾ��ѧ��Ϊ�������ݵ�ѧ������Ϣ
				JOptionPane.showMessageDialog(this,	"�ɹ�ɾ��������Ϣ��¼����",
						"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				for(int j=1;j<6;j++){
					jtxtArrayDe[j].setEditable(false);
				}
				for(int i=0;i<=6;i++){
					if(i<6){//���ı������
						jtxtArrayDe[i].setText("");}	
				}
				return;	
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	public void updateStudent(){
		String str[]=new String[7];
		for(int i=0;i<6;i++){//�����ı���������Ϣ
			str[i]=jtxtArrayUp[i].getText().trim();		
		}
		str[6]=jcpUp.getSelectedItem().toString();
		if(str[0].equals("")&&str[1].equals("")&&str[2].equals("")
				&&str[3].equals("")&&str[4].equals("")&&str[5].equals(""))
		{//�����ı���Ϊ����ʾ
			JOptionPane.showMessageDialog(this,	"ѧ����Ϣ����Ϊ�գ�����",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		if(!str[0].equals("")&&!str[1].equals("")&&!str[2].equals("")
				&&!str[3].equals("")&&!str[4].equals("")&&!str[5].equals(""))
		{//�����ı���������Ϣ
			sql="update STUDENT set StuName='"+str[1]+"',StuSex='"+str[2]+"',Class='"
					+str[3]+"',Department='"+str[4]+"',Permitted='"+str[6]+"',Password='"+str[5]
							+"' where StuNO='"+str[0]+"'";
			db=new DataBase();
			db.updateDb(sql);//����ѧ����Ϣ
			if(db.updateDb(sql)==-1){
				JOptionPane.showMessageDialog(this,"�޸�ʧ�ܣ���",
						"��Ϣ!!",JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(this,"�޸ĳɹ�����",
						"��Ϣ!!",JOptionPane.INFORMATION_MESSAGE);
				for(int i=0;i<=6;i++){//��ÿ����ӵ���ʱ����v
					if(i<6){jtxtArrayUp[i].setText("");}	
				}
				return;
			}
		}
		db.dbClose();//�ر�
	}
}