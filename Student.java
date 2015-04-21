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
{   //创建一个上下方向分割的JSplitPane对象
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpAdd=new JPanel();//创建添加学生面板
	private JPanel jpDelete=new JPanel();//创建删除学生面板
	private JPanel jpUpdate=new JPanel();//创建更新面板
	private JPanel jpSearch=new JPanel();//创建查询面板
	private JTabbedPane jtp=new JTabbedPane();//创建选项卡对象
	JTable jt;
	JScrollPane jspn;
	String[]str1=new String[7];//声明字符串数组
	String sql;
	DataBase db;
	stuMode sm;
	private JLabel[] jlArray=new JLabel[]{//添加学生声明标签数组
			new JLabel("学    号"),
			new JLabel("姓    名"),
			new JLabel("性    别"),
			new JLabel("班    级"),
			new JLabel("院    系"),
			new JLabel("密    码"),
			new JLabel("借书权限")   
	};
	private JLabel[] jlArrayDe=new JLabel[]{//创建删除学生的标签
			new JLabel("学    号"),
			new JLabel("姓    名"),
			new JLabel("性    别"),
			new JLabel("班    级"),
			new JLabel("院    系"),
			new JLabel("密    码"),
			new JLabel("借书权限")   
	};
	private JLabel[] jlArrayUp=new JLabel[]{//修改面板声明标签数组
			new JLabel("学    号"),
			new JLabel("姓    名"),
			new JLabel("性    别"),
			new JLabel("班    级"),
			new JLabel("院    系"),
			new JLabel("密    码"),
			new JLabel("借书权限")   
	};
	private JLabel[] jlArraySe=new JLabel[]{//修改面板声明标签数组
			new JLabel("学    号"),
			new JLabel("姓    名"),
			new JLabel("性    别"),
			new JLabel("班    级"),
			new JLabel("院    系"),
			new JLabel("密    码"),
			new JLabel("借书权限")   
	};
	private JTextField[] jtxtArray=new JTextField[]{//添加学生面板的声明文本框数组
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private JTextField[] jtxtArrayDe=new JTextField[]{//删除学生面板的文本框
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private JTextField[] jtxtArrayUp=new JTextField[]{//更新面板的声明文本框数组
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private JTextField[] jtxtArraySe=new JTextField[]{//chaxun面板的声明文本框数组
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField(),
			new JTextField(),new JTextField()
	};
	private String[] str={"是","否"};//创建下拉列表框数据模型的字符串数组
	private JComboBox jcp=new JComboBox(str);//创建下拉列表框
	private JComboBox jcpUp=new JComboBox(str);//创建下拉列表框
	private JComboBox jcpDe=new JComboBox(str);//创建下拉列表框
	private JComboBox jcpSe=new JComboBox(str);//创建下拉列表框
	private JButton[] jbArray={//设置JButton按钮的文本
			new JButton("确认添加"),
			new JButton("确认修改"),
			new JButton("确认删除"),
			new JButton("查       询")
	};

	public Student()
	{

		stuMode sm=new stuMode();//创建一个数据模型对象
		jt=new JTable(sm);//初始化JTable
		jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
		this.setLayout(new GridLayout(1,1));//声明本界面为网格布局
		jpAdd.setLayout(null);//设置面板的上部分为空布局管理器
		jpDelete.setLayout(null);
		jpUpdate.setLayout(null);
		jpSearch.setLayout(null);
		jsp.setDividerLocation(200);//设置jp中分割条的初始位置
		jsp.setDividerSize(14);//设置分隔条的宽度
		jsp.setTopComponent(jtp);
		jtp.addTab("添加学生信息",jpAdd);
		jtp.addTab("删除学生信息",jpDelete);
		jtp.addTab("修改学生信息",jpUpdate);
		jtp.addTab("查找学生信息",jpSearch);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<6;i++){//将文本框添加进上部面板
			jpAdd.add(jtxtArray[i]);
		}
		for(int i=0;i<6;i++){//将文本框添加进上部面板
			jpUpdate.add(jtxtArrayUp[i]);
			jtxtArrayUp[0].setEditable(false);
		}
		for(int i=0;i<6;i++){//将文本框添加进上部面板
			jpDelete.add(jtxtArrayDe[i]); 
			for(int j=1;j<6;j++)
			{
				jtxtArrayDe[j].setEditable(false);
			}

		}
		for(int i=0;i<6;i++){//将文本框添加进上部面板
			jpSearch.add(jtxtArraySe[i]);
			for(int j=1;j<6;j++)
			{
				jtxtArraySe[j].setEditable(false);
			}
		}
		//添加面板的标签
		for(int i=0;i<7;i++){
			jpAdd.add(jlArray[i]);
			if(i<3)
			{//对界面上的第一行标签和文本框大小位置进行设置
				jlArray[i].setBounds(25+i*220,80,100,20);
				jtxtArray[i].setBounds(105+i*200,80,120,20);
				jtxtArray[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{//对第二行标签和文本框大小位置进行设置
				jlArray[i].setBounds(25+(i-3)*220,120,100,20);
				jtxtArray[i].setBounds(105+(i-3)*200,120,120,20);
				jtxtArray[i].addActionListener(this);
			}
			else
			{//对最下面的显示标签进行设置
				jlArray[i].setBounds(650,80,100,20);
			}
		}
		//删除面板标签及文本框
		for(int i=0;i<7;i++){
			jpDelete.add(jlArrayDe[i]);
			if(i<3)
			{//对界面上的第一行标签和文本框大小位置进行设置
				jlArrayDe[i].setBounds(25+i*220,80,100,20);
				jtxtArrayDe[i].setBounds(105+i*200,80,120,20);
				jtxtArrayDe[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{//对第二行标签和文本框大小位置进行设置
				jlArrayDe[i].setBounds(25+(i-3)*220,120,100,20);
				jtxtArrayDe[i].setBounds(105+(i-3)*200,120,120,20);
				jtxtArrayDe[i].addActionListener(this);
			}
			else
			{//对最下面的显示标签进行设置
				jlArrayDe[i].setBounds(650,80,100,20);
			}
		}
		//更新面板的标签及文本框
		for(int i=0;i<7;i++){
			jpUpdate.add(jlArrayUp[i]);
			if(i<3)
			{//对界面上的第一行标签和文本框大小位置进行设置
				jlArrayUp[i].setBounds(25+i*220,80,100,20);
				jtxtArrayUp[i].setBounds(105+i*200,80,120,20);
				jtxtArrayUp[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{//对第二行标签和文本框大小位置进行设置
				jlArrayUp[i].setBounds(25+(i-3)*220,120,100,20);
				jtxtArrayUp[i].setBounds(105+(i-3)*200,120,120,20);
				jtxtArrayUp[i].addActionListener(this);
			}
			else
			{//对最下面的显示标签进行设置
				jlArrayUp[i].setBounds(650,80,100,20);
			}
		}
		//查询面板的标签及文本框
		for(int i=0;i<7;i++){
			jpSearch.add(jlArraySe[i]);
			if(i<3)
			{//对界面上的第一行标签和文本框大小位置进行设置
				jlArraySe[i].setBounds(25+i*220,80,100,20);
				jtxtArraySe[i].setBounds(105+i*200,80,120,20);
				jtxtArraySe[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{//对第二行标签和文本框大小位置进行设置
				jlArraySe[i].setBounds(25+(i-3)*220,120,100,20);
				jtxtArraySe[i].setBounds(105+(i-3)*200,120,120,20);
				jtxtArraySe[i].addActionListener(this);
			}
			else
			{//对最下面的显示标签进行设置
				jlArraySe[i].setBounds(650,80,100,20);
			}
		}
		this.add(jsp);
		jsp.setBottomComponent(jspn);//设置下部子窗格
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
		{//将JButton添加进jpAdd

			jbArray[i].addActionListener(this);	//设置监听器
		}
		jt.addMouseListener(new MouseAdapter() {
			JTable jt;

			public void mouseClicked(MouseEvent e){
				if(e.getClickCount()==2){
					int row = ((JTable)e.getSource()).getSelectedRow();//声明所选行号
					if(row>=0){
						//选择了一行
						for(int i=0;i<6;i++){
							jtxtArrayUp[i].setText(((JTable)e.getSource()).getValueAt(row, i).toString());
						}
					}
				}
			}
		});
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,600,500);
		this.setVisible(true);			
	}
	public void actionPerformed(ActionEvent e)
	{
		//当点击"添加学生信息"按钮是将执行添加功能，将文本框的学生信息添加进STUDENT表中
		if(e.getSource()==jbArray[0])
		{
			this.insertStudent();
			sm =new stuMode();
			jt.setModel(sm);
		}
		//当点击"删除学生信息"按钮是将执行删除功能，将学号为学号框的学生信息从STUDENT表中删除	
		if(e.getSource()==jbArray[2])
		{
			this.deleteStudent();
			sm =new stuMode();
			jt.setModel(sm);
		}
		//当点击"修改学生信息"按钮是将执行修改功能，将信息为学号框的学生信息在STUDENT表中更新
		if(e.getSource()==jbArray[1])
		{
			this.updateStudent();
			System.out.println("点击修改");
			sm=new stuMode();
			jt.setModel(sm);

		}
		//当点击"查找学生信息"按钮是将执行查找功能，将从STUDENT表中查找学号为学号框的学生信息
		if(e.getSource()==jbArray[3])
		{
			System.out.println("点击确认查询按钮");
			sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxtArraySe[0].getText().trim());
			//			this.searchStudent();
			sm=new stuMode(sql);
			jt.setModel(sm);
		}
	}
	public void searchStudent(){
		if(jtxtArraySe[0].getText().equals("")){//
			JOptionPane.showMessageDialog(this,"学号不能为空，请重新输入！！！",
					"信息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxtArraySe[0].getText().trim());
		db=new DataBase();
		db.selectDb(sql);//查询学号文本中所输学号是否存在于STUDENT表中				   
		try{//对结果集进行异常处理
			int k=0;
			while(db.rs.next()){
				k++;
				for(int i=1;i<=7;i++){//顺序达到所搜到的结果中的各项记录
					if(i<6){jtxtArraySe[i].setText("");}
				}
			}
			if(k==0){//若Book表中没有该书号，则输出提示对话框
				JOptionPane.showMessageDialog(this,	"没有该学生信息！",
						"消息",JOptionPane.INFORMATION_MESSAGE);
			}	
			for(int i=0;i<=6;i++){//将文本框信息清空
				if(i<6){jtxtArraySe[i].setText("");}
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally{db.dbClose();}
	}
	public void insertStudent(){
		for(int i=0;i<6;i++){//声明文本框输入信息
			str1[i]=jtxtArray[i].getText().trim();		
		}
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")
				&&str1[3].equals("")&&str1[4].equals("")&&str1[5].equals(""))
		{//当各文本框为空提示
			JOptionPane.showMessageDialog(this,	"学生信息不能为空！！！",
					"消息",JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")
				&&!str1[3].equals("")&&!str1[4].equals("")&&!str1[5].equals(""))
		{//当在文本框输入信息
			sql="select StuNo from student where StuNo="+Integer.parseInt(jtxtArray[0].getText().trim());
			db=new DataBase();
			db.selectDb(sql);//以上三行是对用户名和密码进行查询，验证身份
			try{
				String StuNo="";
				while(db.rs.next()){//取出结果集中数据并赋值
					StuNo=db.rs.getString(1).trim();				
				}
				if(jtxtArray[0].getText().trim().equals(StuNo)){//消息提示，学号存在
					JOptionPane.showMessageDialog(this, "该学号已存在学生！", "消息", JOptionPane.INFORMATION_MESSAGE);
				}
				else{//添加学生
					
					str1[6]=jcp.getSelectedItem().toString();
					sql="insert into student(StuNO,StuName,StuSex,Class,Department,"
							+"Password,Permitted) values('"+str1[0]+"','"+str1[1]+"','"
							+ str1[2] + "',' "+str1[3]+"','"+
							str1[4]+"','"+str1[5]+"','"+str1[6]+"')";
					db=new DataBase();
					db.updateDb(sql);//插入学生信息
					for(int i=0;i<=6;i++){//将每列添加到临时数组v
						if(i<6){jtxtArray[i].setText("");}	
					}
					return;   
				}
			}
			catch(Exception e1){e1.printStackTrace();}
			db.dbClose();//关闭数据库链接

		}
	}
	public void deleteStudent(){
		String stuno = jtxtArrayDe[0].getText().trim();
		for(int j=1;j<6;j++){
			jtxtArrayDe[j].setEditable(false);
		}
		if(stuno.equals("")){//当学号输入为空提示
			JOptionPane.showMessageDialog(this,	"请输入要删除的学生的学号！然后点击删除！！",
					"消息",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select * from RECORD where StuNO="+Integer.parseInt(stuno);
		db=new DataBase();
		db.selectDb(sql);//查询学号为输入的学生验证其身份
		try{//对结果集进行异常处理
			if(db.rs.next()){
				//若Record借阅表中有该学号，则输出提示对话框
				JOptionPane.showMessageDialog(this,	"不能删除该生信息，他有书未还！",
						"消息",JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				sql="delete from STUDENT where StuNO="+Integer.parseInt(stuno);
				db=new DataBase();
				int rr=db.updateDb(sql);//删除学号为输入内容的学生的信息
				JOptionPane.showMessageDialog(this,	"成功删除该生信息记录！！",
						"消息",JOptionPane.INFORMATION_MESSAGE);
				for(int j=1;j<6;j++){
					jtxtArrayDe[j].setEditable(false);
				}
				for(int i=0;i<=6;i++){
					if(i<6){//将文本框清空
						jtxtArrayDe[i].setText("");}	
				}
				return;	
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	public void updateStudent(){
		String str[]=new String[7];
		for(int i=0;i<6;i++){//声明文本框输入信息
			str[i]=jtxtArrayUp[i].getText().trim();		
		}
		str[6]=jcpUp.getSelectedItem().toString();
		if(str[0].equals("")&&str[1].equals("")&&str[2].equals("")
				&&str[3].equals("")&&str[4].equals("")&&str[5].equals(""))
		{//当各文本框为空提示
			JOptionPane.showMessageDialog(this,	"学生信息不能为空！！！",
					"消息",JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		if(!str[0].equals("")&&!str[1].equals("")&&!str[2].equals("")
				&&!str[3].equals("")&&!str[4].equals("")&&!str[5].equals(""))
		{//当在文本框输入信息
			sql="update STUDENT set StuName='"+str[1]+"',StuSex='"+str[2]+"',Class='"
					+str[3]+"',Department='"+str[4]+"',Permitted='"+str[6]+"',Password='"+str[5]
							+"' where StuNO='"+str[0]+"'";
			db=new DataBase();
			db.updateDb(sql);//更新学生信息
			if(db.updateDb(sql)==-1){
				JOptionPane.showMessageDialog(this,"修改失败！！",
						"消息!!",JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(this,"修改成功！！",
						"消息!!",JOptionPane.INFORMATION_MESSAGE);
				for(int i=0;i<=6;i++){//将每列添加到临时数组v
					if(i<6){jtxtArrayUp[i].setText("");}	
				}
				return;
			}
		}
		db.dbClose();//关闭
	}
}