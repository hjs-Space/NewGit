package com.DUANGDUANG1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import javax.swing.event.*;import java.sql.*;
import java.util.*;import java.util.Date;
public class Login extends JFrame implements ActionListener{
	private JPanel jp = new ImagePanel();
	//private JPanel jp=new JPanel();//创建JPanel对象
	private JLabel []jlArray={//创建标签组
			new JLabel("用户名"),
			new JLabel("密  码"),new JLabel("")
	};
	private JButton[] jbArray={//创建按钮数组
			new JButton("学生登录"),new JButton("清空"),new JButton("管理员登录")
	};
	private JTextField[] jtxtArray={ //创建文本框
			new JTextField("110")
	}; 
	private JPasswordField jpassword=new JPasswordField("110"); //创建密码框
	String sql;
	DataBase db;
	public Login(){
		/////////////////////////////////////////////
		jp.setLayout(null); //设置JPanel的布局管理器
		for(int i=0;i<2;i++){ //对标签与按钮控件循环处理		
			jlArray[i].setBounds(160,220+i*50,80,25);//设置标签与按钮的大小和位置	
			jp.add(jlArray[i]);//将标签和按钮添加进JPanel容器中
		}
		for(int i=0;i<1;i++){//设置文本框的大小位置并为其添加事件监听器
			jtxtArray[i].setBounds(210,220+50*i,180,25);
			jp.add(jtxtArray[i]);
			jtxtArray[i].addActionListener(this);
		}
		for(int i=0;i<3;i++){//设置按钮的大小位置并为其添加事件监听器
			jbArray[i].setBounds(80+i*120,330,100,25);
			jp.add(jbArray[i]);	
			jbArray[i].addActionListener(this);      
		}
		jpassword.setBounds(210,270,180,25);//设置密码框的大小位置
		jp.add(jpassword);//将密码框添加进JPanel容器
		jpassword.setEchoChar('*');//设置密码框的回显字符
		jpassword.addActionListener(this);//为密码框注册监听器
		jlArray[2].setBounds(10,280,300,25);//设置用于显示登录状态的标签的大小位置
		jp.add(jlArray[2]);//将标签添加进JPanel容器
		///////////////
		//this.add(jp);	
		///////////////
		Image image=new ImageIcon("./res/login.jpg").getImage();//对logo图片进行初始化  
		this.setIconImage(image);
		//设置窗体的大小位置及可见性
		this.setTitle("图书管理系统欢迎您!");
		this.setResizable(false);
		//设置窗体首次出现的大小和位置--自动居中
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;//本窗体宽度
		int h=400;//本窗体高度
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//设置窗体出现在屏幕中央
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		/////////////////////////////////////
		this.setContentPane(jp);
		ImageIcon ii = new ImageIcon("./res/login.jpg");
		JLabel lab1 = new JLabel(ii);
		this.getLayeredPane().add(lab1, new Integer(Integer.MIN_VALUE));
		lab1.setBounds(0, -2, ii.getIconWidth(), ii.getIconHeight());
		JPanel jpp = (JPanel) this.getContentPane();
		jp = new ImagePanel();
		// 添加图片到frame第二层,将背景图标签添加到jframe的LayeredPane面板里??
		jp.setOpaque(false);
		jpp.setOpaque(false);
		jp.setLayout(null);
	}	

	//实现ActionListener接口中的方法
	public void actionPerformed(ActionEvent e)
	{//事件源为文本框
		String mgno=jtxtArray[0].getText().trim();
		DataBase.login=this;
		if(e.getSource()==jtxtArray[0]){
			jpassword.requestFocus();//切换输入焦点到密码框
		}
		else if(e.getSource()==jbArray[1]){//事件源为清空按钮
			//清空所有信息
			jlArray[2].setText("");
			jtxtArray[0].setText("");
			jpassword.setText("");
			//将输入焦点设置到文本框
			jtxtArray[2].requestFocus();
		}
		else if(e.getSource()==jbArray[2]){//事件源为管理员登录按钮
			//判断用户名和密码是否匹配
			if(!mgno.matches("\\d+"))//两个反斜杠代表正则表达式,/d代表为数字
			{//如果用户名格式输入有误
				JOptionPane.showMessageDialog(this,"用户名格式错误！！！","信息",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sql="select mgNo,password from manager where mgNo="+Integer.parseInt(mgno);
			db=new DataBase();
			db.selectDb(sql);//以上三行是对用户名和密码进行查询，验证身份
			try{
				String mgNo="";
				String password="";
				while(db.rs.next()){//取出结果集中数据并赋值
					mgNo=db.rs.getString(1).trim();
					password=db.rs.getString(2).trim();					
				}
				if(jtxtArray[0].getText().trim().equals(mgNo)&&
						String.valueOf(jpassword.getPassword()).equals(password)){//登录成功
					jlArray[2].setText("恭喜您，登录成功！！！");
					new Root(mgNo);
					this.dispose();   			
				}
				else{//登录失败
					jlArray[2].setText("对不起，登录失败！！！");    
				}
			}
			catch(Exception e1){e1.printStackTrace();}
			db.dbClose();//关闭数据库链接
		}
		else if(e.getSource()==jbArray[0]){//事件源为学生登录按钮
			if(!jtxtArray[0].getText().trim().matches("\\d+")){
				//若学号格式错误，输出提示对话框
				JOptionPane.showMessageDialog(this,"输入有误,学号只能为数字!!!",
						"消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//查询学号文本中所输学号是否存在于STUDENT表中
			sql="select StuNO,Password from STUDENT where StuNO="
					+Integer.parseInt(jtxtArray[0].getText().trim());
			db=new DataBase();
			db.selectDb(sql);
			try{
				if(!(db.rs.next())){//若学号错误，输出提示对话框
					JOptionPane.showMessageDialog(this,"输入了错误的学号！！",
							"消息", JOptionPane.INFORMATION_MESSAGE);
				}
				else{//得到输入学号的学生的学号和密码
					String stuNO=db.rs.getString(1).trim(),
							password=db.rs.getString(2).trim();
					if(jtxtArray[0].getText().trim().equals(stuNO)&&
							String.valueOf(jpassword.getPassword()).equals(password)){//登录成功
						jlArray[2].setText("恭喜您，登录成功！！！");
						new StudentSystem();
						this.dispose();
					}
					else{//登录失败
						jlArray[2].setText("对不起，登录失败！！！");    
					}
				}
			}
			catch(Exception ex) { ex.printStackTrace();}
			db.dbClose(); //关闭数据库链接	
		}
	}
	public static void main(String[]args)
	{
		new Login();
		//JFrame frame = new JFrame();
		
	}
}
class ImagePanel extends JPanel {

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon icon = new ImageIcon("./res/login.jpg");
		g.drawImage(icon.getImage(), 0, 0, null);

	}

}
