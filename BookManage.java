package com.DUANGDUANG1;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.jar.JarOutputStream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class BookManage extends JPanel implements ActionListener {
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//创建分割面板, 垂直分割表示 Component 沿 y 轴分割。
	private JPanel jp=new JPanel();//创建面板容器
	private JPanel jpAdd=new JPanel();//创建添加学生面板
	private JPanel jpDelete=new JPanel();//创建删除学生面板
	private JPanel jpUpdate=new JPanel();//创建更新面板
	private JPanel jpSearch=new JPanel();//创建查询面板
	private JTabbedPane jtp=new JTabbedPane();//创建选项卡对象
	bookMode bm;
	JScrollPane jspn;
	JTable jt;
	DataBase db;
	String sql;
	String []str1=new String[7];

	//创建图书入库标签数组
	private JLabel[] jlArryAd=new JLabel[]
			{
			new JLabel("书    号"),
			new JLabel("书    名"),
			new JLabel("作    者"),
			new JLabel("出 版 社"),
			new JLabel("购买日期"),
			new JLabel("已 预 约"),
			new JLabel("已 借 阅")
			};
	//创建图书删除标签数组
	private JLabel[] jlArryDe=new JLabel[]
			{
			new JLabel("书    号"),
			new JLabel("书    名"),
			new JLabel("作    者"),
			new JLabel("出 版 社"),
			new JLabel("购买日期"),
			new JLabel("已 预 约"),
			new JLabel("已 借 阅")
			};
	//创建图书修改标签数组
	private JLabel[] jlArryUp=new JLabel[]
			{
			new JLabel("书    号"),
			new JLabel("书    名"),
			new JLabel("作    者"),
			new JLabel("出 版 社"),
			new JLabel("购买日期"),
			new JLabel("已 预 约"),
			new JLabel("已 借 阅")
			};
	//创建图书查找标签数组
	private JLabel[] jlArrySe=new JLabel[]
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
	private JTextField[] jtxtArryAd=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//创建文本框数组
	private JTextField[] jtxtArryDe=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//创建文本框数组
	private JTextField[] jtxtArryUp=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//创建文本框数组
	private JTextField[] jtxtArrySe=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//定义一个下拉列表框的数组以及创建下拉列表框
	String[] str={"是","否"};
	private JComboBox[] jcb=new JComboBox[]{
			new JComboBox(str),
			new JComboBox(str),
			new JComboBox(str),
			new JComboBox(str),
			new JComboBox(str),
			new JComboBox(str),
			new JComboBox(str),
			new JComboBox(str)
	};

	//现在创建下面的JTable模型来实现JTable
	//创建表格列标题向量
	//创建一个向量来存放数据
	//创建一个表格模型
	//用表格模型来创建一个表格jttable对象
	//将表格封装到滚动窗格上去
	//构造BookManage的方法
	public BookManage()
	{
		bookMode bm=new bookMode();
		jt=new JTable(bm);
		jspn=new JScrollPane(jt);
		//向jp中添加文本框
		for(int i=0;i<5;i++)
		{
			jpAdd.add(jtxtArryAd[i]);
		}
		for(int i=0;i<5;i++)
		{
			jpDelete.add(jtxtArryDe[i]);
		}
		for(int i=0;i<5;i++)
		{
			jpUpdate.add(jtxtArryUp[i]);
		}
		for(int i=0;i<5;i++)
		{
			jpSearch.add(jtxtArrySe[i]);
		}
		for(int i=1;i<5;i++){
			jtxtArrySe[i].setEditable(false);
			jtxtArryDe[i].setEditable(false);
		}
		//向jp中添加按钮,设置按钮的位置及大小，并为按钮设置监听
		for(int i=0;i<4;i++)
		{
			jpAdd.add(jbArry[0]);
			jpDelete.add(jbArry[1]);
			jpUpdate.add(jbArry[2]);
			jpSearch.add(jbArry[3]);
			jbArry[i].addActionListener(this);
		}
		//向jpAdd中添加标签,并设置文本框的位置及大小以及标签的位置
		for(int i=0;i<7;i++)
		{
			jpAdd.add(jlArryAd[i]);
			if(i<3)
			{//设置书号、书名、作者的标签以及文本框位置
				jlArryAd[i].setBounds(25,60+30*i,100,20);
				jtxtArryAd[i].setBounds(80,60+30*i,150,20);
			}
			else if(i>2&&i<5)
			{//设置出版社、购买日期的标签以及文本框位置
				jlArryAd[i].setBounds(275,60+30*(i-3),100,20);
				jtxtArryAd[i].setBounds(340,60+30*(i-3),150,20);
			}
			else
			{//设置已借阅、已预约的标签位置
				jlArryAd[i].setBounds(525,60+30*(i-5),100,20);
				jbArry[0].setBounds(595, 120, 100,20);
			}
		}

		//向jpDelete中添加标签,并设置文本框的位置及大小以及标签的位置
		for(int i=0;i<7;i++)
		{
			jpDelete.add(jlArryDe[i]);
			if(i<3)
			{//设置书号、书名、作者的标签以及文本框位置
				jlArryDe[i].setBounds(25,60+30*i,100,20);
				jtxtArryDe[i].setBounds(80,60+30*i,150,20);
			}
			else if(i>2&&i<5)
			{//设置出版社、购买日期的标签以及文本框位置
				jlArryDe[i].setBounds(275,60+30*(i-3),100,20);
				jtxtArryDe[i].setBounds(340,60+30*(i-3),150,20);
			}
			else
			{//设置已借阅、已预约的标签位置
				jlArryDe[i].setBounds(525,60+30*(i-5),100,20);
				jbArry[1].setBounds(595, 120, 100,20);
			}
		}
		//向jpUpdate中添加标签,并设置文本框的位置及大小以及标签的位置
		for(int i=0;i<7;i++)
		{
			jpUpdate.add(jlArryUp[i]);
			if(i<3)
			{//设置书号、书名、作者的标签以及文本框位置
				jlArryUp[i].setBounds(25,60+30*i,100,20);
				jtxtArryUp[i].setBounds(80,60+30*i,150,20);
				jtxtArryUp[0].setEditable(false);
			}
			else if(i>2&&i<5)
			{//设置出版社、购买日期的标签以及文本框位置
				jlArryUp[i].setBounds(275,60+30*(i-3),100,20);
				jtxtArryUp[i].setBounds(340,60+30*(i-3),150,20);
			}
			else
			{//设置已借阅、已预约的标签位置
				jlArryUp[i].setBounds(525,60+30*(i-5),100,20);
				jbArry[2].setBounds(595, 120, 100,20);
			}
		}
		//向jpSearch中添加标签,并设置文本框的位置及大小以及标签的位置
		for(int i=0;i<7;i++)
		{
			jpSearch.add(jlArrySe[i]);
			if(i<3)
			{//设置书号、书名、作者的标签以及文本框位置
				jlArrySe[i].setBounds(25,60+30*i,100,20);
				jtxtArrySe[i].setBounds(80,60+30*i,150,20);
			}
			else if(i>2&&i<5)
			{//设置出版社、购买日期的标签以及文本框位置
				jlArrySe[i].setBounds(275,60+30*(i-3),100,20);
				jtxtArrySe[i].setBounds(340,60+30*(i-3),150,20);
			}
			else
			{//设置已借阅、已预约的标签位置
				jlArrySe[i].setBounds(525,60+30*(i-5),100,20);
				jbArry[3].setBounds(595, 120, 100,20);
			}
		}

		//设置下拉列表框的位置及大小 
		for(int i=0;i<4;i++){
			jcb[i].setBounds(595,60,100,20);
			jcb[1].setEnabled(false);
			jcb[3].setEnabled(false);
		}
		for(int i=4;i<8;i++){
			jcb[i].setBounds(595,90,100,20);
			jcb[5].setEnabled(false);
			jcb[7].setEnabled(false);
		}
		//向jp中添加下拉列表框
		jpAdd.add(jcb[0]);
		jpAdd.add(jcb[4]);
		jpDelete.add(jcb[1]);
		jpDelete.add(jcb[5]);
		jpUpdate.add(jcb[2]);
		jpUpdate.add(jcb[6]);
		jpSearch.add(jcb[3]);
		jpSearch.add(jcb[7]);

		//鼠标触发事件
		jt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				JTable jt;
				if(e.getClickCount()==2){
					int row=((JTable)e.getSource()).getSelectedRow();
					if(row>=0){
						for(int i=0;i<5;i++){
							jtxtArryUp[i].setText(((JTable)e.getSource()).getValueAt(row, i).toString());
						}
					}
				}
			}
		});

		//
		this.add(jsp);
		this.setLayout(new GridLayout(1,1));//设置为1行1列的网格布局
		jpAdd.setLayout(null);//设置面板的上部分为空布局管理器
		jpDelete.setLayout(null);
		jpUpdate.setLayout(null);
		jpSearch.setLayout(null);
		jsp.setDividerLocation(200);//设置分割条的位置
		jsp.setDividerSize(14);//设置分割条的大小
		jsp.setTopComponent(jtp);//将面板容器添加到上部
		jtp.addTab("图书入库",jpAdd);
		jtp.addTab("删除图书",jpDelete);
		jtp.addTab("修改图书信息",jpUpdate);
		jtp.addTab("查找图书",jpSearch);
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
			bm=new bookMode();
			jt.setModel(bm);
		}
		if(e.getSource()==jbArry[1])
		{
			this.deleteBook();
			System.out.println("点击删除图书");
			bm=new bookMode();
			jt.setModel(bm);
		}
		if(e.getSource()==jbArry[2])
		{
			this.updatetBook();
			System.out.println("点击修改图书");
			bm=new bookMode();
			jt.setModel(bm);
		}
		if(e.getSource()==jbArry[3])
		{
//			this.searchBook();
			sql="select * from book where BookNO="+Integer.parseInt(jtxtArrySe[0].getText().trim());
			System.out.println("点击查找图书");
			bm=new bookMode(sql);
			jt.setModel(bm);
		}

	}

	public void insertBook()
	{
		for(int i=0;i<5;i++)
		{//将文本框输入的信息存放到一个数组，声明变量
			str1[i]=jtxtArryAd[i].getText().trim();
		}
		str1[5]=jcb[0].getSelectedItem().toString();
		str1[6]=jcb[1].getSelectedItem().toString();
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&
				str1[3].equals("")&&str1[4].equals(""))
		{
			JOptionPane.showMessageDialog(this, "输入的信息不能为空", "消息提醒",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else{//查找该书号是否存在
			sql="select bookNO from book where bookNo="+Integer.parseInt(jtxtArryAd[0].getText().trim());
			db=new DataBase();
			db.selectDb(sql);
			try {
				String bookNo="";
				while(db.rs.next())
				{
					bookNo=db.rs.getString(1).trim();
				}
				//如果存在该书号，弹出提示，否则插入图书信息
				if(jtxtArryAd[0].getText().trim().equals(bookNo))
				{
					JOptionPane.showMessageDialog(this, "该书号已经存在！", "温馨提示", 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}else
				{//将数据插入到数据库中
					sql="insert into BOOK values('"+str1[0]+"','"+str1[1]+"','"+str1[2]+"','"+str1[3]+"'," +
							"'"+str1[4]+"','"+str1[5]+"','"+str1[6]+"')";
					db=new DataBase();
					db.updateDb(sql);//调用DataBase中的方法
					for(int i=0;i<7;i++)
					{
						if(i<5)jtxtArryAd[i].setText("");//清空文本框
					}
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
		String BookNo=jtxtArryDe[0].getText().trim();
		if(BookNo.equals(""))
		{//若输入的图书号为空，则弹出信息提示
			JOptionPane.showMessageDialog(this, "输入的图书号不能为空", "温馨提醒", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select BookNo from record where bookNo="+Integer.parseInt(BookNo);
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next())//若借阅图书信息表中有该书的记录，则弹出消息提示框
			{
				JOptionPane.showMessageDialog(this, "该书被借出，不能删除！", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else{//若借阅图书信息表中没有该书的记录，则从图书信息表中删除该图书
				sql="delete from book where bookNo="+Integer.parseInt(BookNo);
				int flag=db.updateDb(sql);
				if(flag>0){JOptionPane.showMessageDialog(this, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);}
				else {JOptionPane.showMessageDialog(this, "删除失败！", "提示", JOptionPane.INFORMATION_MESSAGE);}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updatetBook()
	{
		db=new DataBase();
		String str[]=new String[7];
		for(int i=0;i<5;i++)
		{
			str[i]=jtxtArryUp[i].getText().trim();
		}
		str[5]=jcb[2].getSelectedItem().toString();
		str[6]=jcb[6].getSelectedItem().toString();
		if(str[0].equals("")&&str[1].equals("")&&str[2].equals("")
				&&str[3].equals("")&&str[4].equals("")&&str[5].equals(""))
		{//当各文本框为空提示
			JOptionPane.showMessageDialog(this,	"图书信息不能为空！！！",
					"消息",JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		if(!str[0].equals("")&&!str[1].equals("")&&!str[2].equals("")
				&&!str[3].equals("")&&!str[4].equals("")&&!str[5].equals(""))
		{//更新除书名以外的所有信息
			sql="update book set BookName='"+str[1]+"', Author='"+str[2]+"'," +
					"Publishment='"+str[3]+"'," +
					"BuyTime='"+str[4]+"'," +
					"Borrowed='"+str[5]+"'," +
					"Ordered='"+str[6]+"' " +
					"where BookNo="+str[0];

			db.updateDb(sql);
			if(db.updateDb(sql)>0){
				JOptionPane.showMessageDialog(this, "修改成功！", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(this, "修改失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
	}
	public void searchBook()
	{
		
		/*if(jtxtArrySe[0].getText().equals("")){//
			JOptionPane.showMessageDialog(this,"书号不能为空，请重新输入！！！",
					"信息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select * from book where BookNO="+Integer.parseInt(jtxtArrySe[0].getText().trim());
		bm=new bookMode(sql);
		
		db=new DataBase();
		db.selectDb(sql);//查询学号文本中所输学号是否存在于STUDENT表中				   
		try{//对结果集进行异常处理
			int k=0;
			while(db.rs.next()){
				k++;
				jtxtArrySe[0].setText("");
			}
			if(k==0){//若Book表中没有该书号，则输出提示对话框
				JOptionPane.showMessageDialog(this,	"没有该书的信息！",
						"消息",JOptionPane.INFORMATION_MESSAGE);
			}	
		}
		catch(Exception e){e.printStackTrace();}
		finally{db.dbClose();}*/
	}
}