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
   //创建分割方向为上下的JSplitePane对象
    private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jpJ=new JPanel();//创建JPanel对象
	//创建表示下拉列表框数据模型的字符串数组
	private String[] str={"书名","出版社","作者","购买时间"};
	private JComboBox jcb=new JComboBox(str);//创建下拉列表框
	private JButton jb=new JButton("提交");	//创建按钮
	private JLabel[] jlArray=new JLabel[]{
		new JLabel("书    名"),
		new JLabel("作    者"),
		new JLabel("出版社")
	};	
	private JTextField[] jtxtArray=new JTextField[]{//创建文本框
		new JTextField(),new JTextField(),
	    new JTextField(),new JTextField()
	};
	private JRadioButton[] jrbArray={//创建单选按钮
		new JRadioButton("高级查询"),
		new JRadioButton("简单查询",true),
	};	
	private ButtonGroup bg=new ButtonGroup();//创建按钮组
	
	public SearchBook(){
		bm=new bookMode();
		jt=new JTable(bm);
		jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
		
		//设置整个SearchBook界面上下部分均为空布局管理器
		jpJ.setLayout(null);
		//设置单选框的大小、位置，并添加事件监听器
		jpJ.add(jcb);
		jcb.setBounds(140,80,170,20);	
	    jcb.addActionListener(this);
        //添加JButton设置其大小位置并添加事件监听器
		jpJ.add(jb);
		jb.setBounds(565,80,120,20);
		jb.addActionListener(this);
		for(int i=0;i<2;i++){//对单选按钮进行设置
			jrbArray[i].setBounds(20,30+i*50,100,20);
			jpJ.add(jrbArray[i]);//将单选按钮添加到面板
			bg.add(jrbArray[i]);//将按钮添加到按钮组
			jrbArray[i].addActionListener(this);//并注册监听
		}
		for(int i=0;i<3;i++){//设置标签和文本框的坐标，并将其添加进JPanel
			jlArray[0].setBounds(140,30,80,20);
			jtxtArray[0].setBounds(190,30,120,20);
			jlArray[1].setBounds(325,30,80,20);
			jtxtArray[1].setBounds(370,30,120,20);
			jlArray[2].setBounds(525,30,80,20);
			jtxtArray[2].setBounds(565,30,120,20);
			jpJ.add(jtxtArray[i]);	
			jpJ.add(jlArray[i]);
		}
		for(int i=0;i<3;i++){//设置文本框为不可用
			jtxtArray[i].setEditable(false);
		}
    	//设置文本框的坐标,并添加进jpJ
		jtxtArray[3].setBounds(325,80,170,20);
		jpJ.add(jtxtArray[3]);
        jsp.setTopComponent(jpJ);//把jpJ设置到jsp的上部窗格
        jsp.setBottomComponent(jspn);
        jsp.setDividerSize(10);
    	jsp.setDividerLocation(130);//设置jsp中分割条的初始位置
    	this.setLayout(new GridLayout(1,1));//设置查询图书界面为网格布局
    	this.add(jsp);
		//设置窗体的大小位置及可见性
        this.setBounds(3,10,600,400);
        this.setVisible(true);
	}
    //为事件加载的监听器加上处理事件
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if(jrbArray[1].isSelected())//如果选中简单查询
		{
			jtxtArray[3].setEditable(true);//将简单查询的文本框设置为可编辑
			for(int i=0;i<jtxtArray.length-1;i++)
			{
				jtxtArray[i].setEditable(false);//将高级查询的文本框设置为不可编辑
			}
			if(jcb.getSelectedIndex()>=0&&jcb.getSelectedIndex()<4)//选择要查询的索引
			{
				jtxtArray[3].requestFocus();//文本框获得聚焦
				if(e.getSource()==jb)
				{//如果事件源为"提交"按钮，则执行检索
					String str=jtxtArray[3].getText().trim();//获取文本框中输入的信息
					if(str.equals(""))
					{//文本框输入为空，弹出提示
						JOptionPane.showMessageDialog(this, "输入不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else{
						if(jcb.getSelectedIndex()==0)
						{//选择按书名查询
							sql="select * from book where BookName='"+str+"'";
							jtxtArray[3].setText("");
						}
						else if(jcb.getSelectedIndex()==1)
						{//选择按出版社查询
							sql="select * from book where Publishment='"+str+"'";
							jtxtArray[3].setText("");
						}
						else if(jcb.getSelectedIndex()==2)
						{//选择按作者查询
							sql="select * from book where Author='"+str+"'";
							jtxtArray[3].setText("");
						}
						else if(jcb.getSelectedIndex()==3)
						{//选择按购买时间查询
							sql="select * from book where BuyTime='"+str+"'";
							jtxtArray[3].setText("");
						}
						try {
							//这一句很重要，如果没有这一句，则在中文查找中就不能实现该功能
							sql=new String(sql.getBytes("gbk"),"gbk");//获取的字节流为gbk，并强制转换为gbk
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						try {
							db=new DataBase();
							db.selectDb(sql);//执行查找功能
							int flag=0;
							while(db.rs.next()){//结果集游标下移
								flag++;
							}
							if(flag==0){//没有该书信息，弹出提示
								JOptionPane.showMessageDialog(this, "没有该书的信息", "提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					bm=new bookMode(sql);//创建bookMode对象，并把sql参数传进去
					jt.setModel(bm);//更新表格
				}
			}
		}
		else if(jrbArray[0].isSelected())
		{//选中高级查询按钮
			jtxtArray[0].requestFocus();//第一个文本框获得聚焦
			jtxtArray[3].setEditable(false);//将简单查询的文本框设置为不可编辑
			for(int i=0;i<jtxtArray.length-1;i++)
			{
				jtxtArray[i].setEditable(true);//将高级查询的文本框设置为可编辑
			}
			if(e.getSource()==jb)
			{//获得文本框输入的内容
				String str0=jtxtArray[0].getText().trim();
				String str1=jtxtArray[1].getText().trim();
				String str2=jtxtArray[2].getText().trim();
				if(str0.equals("")&&str1.equals("")&&str2.equals(""))
				{
					JOptionPane.showMessageDialog(this, "输入的信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
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
					if(flag==0){//没有该书信息，弹出提示
						JOptionPane.showMessageDialog(this, "没有该书的信息", "提示", JOptionPane.INFORMATION_MESSAGE);
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