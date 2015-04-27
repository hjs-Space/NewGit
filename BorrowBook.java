/**
 * 借阅预约界面
 */
package com.DUANGDUANG1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import com.db.DataBase;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class BorrowBook extends JPanel implements ActionListener{
	String sql;
	DataBase db;
	bookMode bm;
	JTable jt;
	JScrollPane jspn;
	int flag;

	//创建分割方向为上下的JSplitePane对象
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jp=new JPanel();//创建JPanel对象
	private JButton jb=new JButton("提交");	//创建按钮
	private JLabel[] jlArray=new JLabel[]{
			new JLabel("您要借阅或预约的书号"),
			new JLabel("输入学生的学号")
	};
	private JTextField[] jtxtArray=new JTextField[]{//创建文本框
			new JTextField(),new JTextField()
	};
	private JRadioButton[] jrbArray={//创建单选按钮
			new JRadioButton("借阅图书",true),
			new JRadioButton("预约图书")
	};	
	private ButtonGroup bg=new ButtonGroup();//创建按钮组

	public BorrowBook()
	{
		bm=new bookMode();
		jt=new JTable(bm);
		jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
		//设置整个SearchBook界面上下部分均为空布局管理器
		jp.setLayout(null);
		jp.add(jb);
		jb.setBounds(325,80,120,20);
		jb.addActionListener(this);
		for(int i=0;i<2;i++){//对单选按钮进行设置
			jrbArray[0].setBounds(40,80,100,20);//借阅图书单选按钮
			jrbArray[1].setBounds(190,80,100,20);//预约图书单选按钮
			jp.add(jrbArray[i]);//将单选按钮添加到面板
			bg.add(jrbArray[i]);//将按钮添加到按钮组
			jrbArray[i].addActionListener(this);//并注册监听
		}
		for(int i=0;i<2;i++){//设置标签和文本框的坐标，并将其添加进JPanel
			jlArray[0].setBounds(40,30,150,20);
			jtxtArray[0].setBounds(180,30,120,20);
			jlArray[1].setBounds(325,30,150,20);
			jtxtArray[1].setBounds(430,30,120,20);
			jp.add(jtxtArray[i]);	
			jp.add(jlArray[i]);
		}
		jsp.setTopComponent(jp);//把jp设置到jsp的上部窗格
		jsp.setBottomComponent(jspn);//将滚动面板添加到jsp面板的下部
		jsp.setDividerSize(10);//设置分割条的大小
		jsp.setDividerLocation(130);//设置jsp中分割条的初始位置
		this.setLayout(new GridLayout(1,1));//设置查询图书界面为网格布局
		this.add(jsp);
		//设置窗体的大小位置及可见性
		this.setBounds(3,10,600,400);
		this.setVisible(true);
	}
	//为事件加载的监听器加上处理事件
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//按钮触发事件处理
		if(e.getSource()==jb)
		{
			String str0=jtxtArray[0].getText().trim();//获取文本框输入的图书号
			String str1=jtxtArray[1].getText().trim();//获取文本框输入的学生号
			if(str0.equals("")||str1.equals(""))
			{
				JOptionPane.showMessageDialog(this, "输入的信息不能为空", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if(!str0.equals("")&&!str1.equals(""))
			{//1、首先查看该学生是否具有借阅权限
				sql="select * from student where stuNo= "+Integer.parseInt(str1);
				db=new DataBase();
				db.selectDb(sql);
				try {
					if(!(db.rs.next()))
					{//如果学生表中没有记录，提示
						JOptionPane.showMessageDialog(this, "输入的学号有误！", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else{
						String stuName=db.rs.getString(2).trim();//获取学生的姓名
						String Class=db.rs.getString(4).trim();//获取学生的班级
						stuName=new String(stuName.getBytes("gbk"));//编码转换
						Class=new String(Class.getBytes("gbk"));//编码转换
						if(db.rs.getString(7).trim().equals("否"))
						{//没有借书权限，提示
							JOptionPane.showMessageDialog(this, "该学生没有借书权限", "提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						else{//如果有借阅权限，则在图书表中查找该书是否被借出或者预约
							sql="select * from book where BookNo="+Integer.parseInt(str0);
							db.selectDb(sql);//执行查找操作
							if(!(db.rs.next()))
							{
								JOptionPane.showMessageDialog(this, "没有该书的信息！", "提示", JOptionPane.INFORMATION_MESSAGE);
							}else{
								String borrow=db.rs.getString(6).trim();//获取是否借阅
								borrow=new String(borrow.getBytes("gbk"));//编码格式为gbk
								String ordered=db.rs.getString(7).trim();//获取是否预约
								ordered=new String(ordered.getBytes("gbk"));
								String bookName=db.rs.getString(2).trim();//获取图书名
								bookName=new String(bookName.getBytes("gbk"));
								String author=db.rs.getString(3).trim();//获取图书作者
								author=new String(author.getBytes("gbk"));
								/*sql="select * from book where BookNo="+Integer.parseInt(str0);
								bookMode bm=new bookMode(sql);//创建表格模型
								jt.setModel(bm);//刷新表格
*/								
								//当选择借阅图书的单选按钮时
								if(jrbArray[0].isSelected())
								{
									//判断是否被借阅
									if(borrow.trim().equals("是"))
									{//已经被借阅
										JOptionPane.showMessageDialog(this, "该图书已经被借阅！请预约！", "提示", JOptionPane.INFORMATION_MESSAGE);
									}
									//判断是否被预约
									else if(ordered.trim().equals("是"))
									{//已经被预约,提示
										JOptionPane.showMessageDialog(this, "该书已经被预约", 
												"提示", JOptionPane.INFORMATION_MESSAGE);
									}
									else{//否则就借书
										//更新图书表的记录
										sql="update book set Borrowed='是' where BookNo="+Integer.parseInt(str0);
										db=new DataBase();
										db.updateDb(sql);
										JOptionPane.showMessageDialog(this, "借书成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
										/***时间的设置，获取系统的当前时间  ***************归还时间****************
										 * 这里为了测试，把时间设置为：借书2天就要还书，否则过期
										 * **********8**/
										Date date=new Date();
										sql="insert into Record values("+Integer.parseInt(str0)+"," +
												"'"+bookName+"',"+Integer.parseInt(str1)+"," +
													"'"+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+"'," +
															"'"+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+(date.getDate()+2)+"','否','否')";
										db.updateDb(sql);
										sql="select * from book";
										bookMode bm=new bookMode(sql);
										jt.setModel(bm);//更新表格
									}
									
								}
								else if(jrbArray[1].isSelected())
								{//选择预约单选按钮
									//判断是否被预约
									if(ordered.trim().equals("是"))
									{//已经被预约,提示
										JOptionPane.showMessageDialog(this, "该书已经被预约", 
												"提示", JOptionPane.INFORMATION_MESSAGE);
									}else
									{//没有被预约，更新图书表的记录
										sql="update Book set Ordered='是' where BookNo="+Integer.parseInt(str0);
										db=new DataBase();
										db.updateDb(sql);
										//预约成功弹出提示
										JOptionPane.showMessageDialog(this, "预约成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
										//将记录添加到预约表中
										String sql1="insert into Orderreport values("+Integer.parseInt(jtxtArray[0].getText().trim())+",'"+stuName+"','"+Class+"','"+bookName+"',"+Integer.parseInt(jtxtArray[1].getText().trim())+",'"+author+"')";
										db.updateDb(sql1);
										sql="select * from book";
										//更新jt表格
										bm=new bookMode(sql);
										jt.setModel(bm);
									}
								}
								
							}
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					db.dbClose();//关闭
				}
			}
		}
	}

}
