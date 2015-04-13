package com.DUANGDUANG1;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class BookManage extends JPanel implements ActionListener {
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//创建分割面板, 垂直分割表示 Component 沿 y 轴分割。
	private JPanel jp=new JPanel();//创建面板容器
	//创建标签数组
	private JLabel[] jlArry=new JLabel[]
			{
			new JLabel("书    号"),
			new JLabel("书    名"),
			new JLabel("作    者"),
			new JLabel("出 版 社"),
			new JLabel("购买日期"),
			new JLabel("已 预 约"),
			new JLabel("已 借 阅")
			};
	//创建按钮数组
	private JButton[] jbArry=new JButton[]
			{
			new JButton("图书入库"),
		    new JButton("删除图书"),
		    new JButton("修改图书记录"),
		    new JButton("查找图书")
			};
	//创建文本框数组
	private JTextField[] jtxtArry=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//定义一个下拉列表框的数组以及创建下拉列表框
	String[] str={"是","否"};
	private JComboBox jcb1=new JComboBox(str);
	private JComboBox jcb2=new JComboBox(str);
	
	//现在创建下面的JTable模型来实现JTable
	//创建表格列标题向量
	Vector<String> head=new Vector<String>();
	{
		//添加列标题
		head.add("书号");
		head.add("书名");
		head.add("作者");
		head.add("出版社");
		head.add("购买日期");
		head.add("是否借阅");
		head.add("是否预约");
	}
	//创建一个向量来存放数据
	Vector<Vector> data=new Vector<Vector>();
	//创建一个表格模型
	DefaultTableModel dtm=new DefaultTableModel();
	//用表格模型来创建一个表格jttable对象
	JTable jt=new JTable(dtm);
	//将表格封装到滚动窗格上去
	JScrollPane jspn=new JScrollPane(jt);
	//构造BookManage的方法
	public BookManage()
	{
		//向jp中添加文本框
		for(int i=0;i<5;i++)
		{
			jp.add(jtxtArry[i]);
		}
		//向jp中添加按钮,设置按钮的位置及大小，并为按钮设置监听
		for(int i=0;i<4;i++)
		{
			jp.add(jbArry[i]);
			jbArry[i].setBounds(150+112*i,100,112,25);
			jbArry[i].addActionListener(this);
		}
		//向jp中添加标签,并设置文本框的位置及大小以及标签的位置
		for(int i=0;i<7;i++)
		{
			jp.add(jlArry[i]);
			if(i<3)
			{//设置书号、书名、作者的标签以及文本框位置
				jlArry[i].setBounds(25,10+30*i,100,20);
				jtxtArry[i].setBounds(80,10+30*i,150,20);
			}
			else if(i>2&&i<5)
			{//设置出版社、购买日期的标签以及文本框位置
				jlArry[i].setBounds(275,10+30*(i-3),100,20);
				jtxtArry[i].setBounds(340,10+30*(i-3),150,20);
			}
			else
			{//设置已借阅、已预约的标签位置
				jlArry[i].setBounds(525,10+30*(i-5),100,20);
			}
		}
		//设置下拉列表框的位置及大小 
		jcb1.setBounds(595,10,100,20);
		jcb2.setBounds(595,40,100,20);
		//向jp中添加下拉列表框
		jp.add(jcb1);
		jp.add(jcb2);
		//
		this.add(jsp);
		this.setLayout(new GridLayout(1,1));//设置为1行1列的网格布局
		jp.setLayout(null);
		jsp.setDividerLocation(140);//设置分割条的位置
		jsp.setDividerSize(14);//设置分割条的大小
		jsp.setTopComponent(jp);//将面板容器添加到上部
		jsp.setBottomComponent(jspn);//把滚动窗格添加到下部
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,600,500);
		this.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jbArry[0])
		{
			this.insertBook();
			System.out.println("点击添加图书");
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
		{//将文本框输入的信息存放到一个数组，声明变量
			str1[i]=jtxtArry[i].getText().trim();
		}
		str1[5]=jcb1.getSelectedItem().toString();
		str1[6]=jcb2.getSelectedItem().toString();
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&
				str1[3].equals("")&&str1[4].equals(""))
		{
			JOptionPane.showMessageDialog(this, "输入的信息不能为空", "消息提醒",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else{//查找该书号是否存在
			sql="select bookNO from book where bookNo="+Integer.parseInt(jtxtArry[0].getText().trim());
			db=new DataBase();
			db.selectDb(sql);
			try {
				String bookNo="";
				while(db.rs.next())
				{
					bookNo=db.rs.getString(1).trim();
				}
				//如果存在该书号，弹出提示，否则插入图书信息
				if(jtxtArry[0].getText().trim().equals(bookNo))
				{
					JOptionPane.showMessageDialog(this, "该书号已经存在！", "消息", JOptionPane.INFORMATION_MESSAGE);
				}else
				{//将数据插入到数据库中
					sql="insert into BOOK values('"+str1[0]+"','"+str1[1]+"','"+str1[2]+"','"+str1[3]+"'," +
							"'"+str1[4]+"','"+str1[5]+"','"+str1[6]+"')";
					db=new DataBase();
					db.updateDb(sql);//调用DataBase中的方法
					Vector<String> v=new Vector<String>();
					for(int i=0;i<7;i++)
					{
						v.add(str1[i]);
						if(i<5)jtxtArry[i].setText("");//清空文本框
					}
					data.add(v);
					//更新表格
					dtm.setDataVector(data, head);
					jt.setModel(dtm);
					jt.updateUI();
					jt.repaint();
					return;
				}
				
			} catch (SQLException e) {
				db.dbClose();//关闭数据库连接
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