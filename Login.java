package com.DUANGDUANG1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import javax.swing.event.*;import java.sql.*;
import java.util.*;import java.util.Date;
public class Login extends JFrame implements ActionListener{
	private JPanel jp = new ImagePanel();
	//private JPanel jp=new JPanel();//����JPanel����
	private JLabel []jlArray={//������ǩ��
			new JLabel("�û���"),
			new JLabel("��  ��"),new JLabel("")
	};
	private JButton[] jbArray={//������ť����
			new JButton("ѧ����¼"),new JButton("���"),new JButton("����Ա��¼")
	};
	private JTextField[] jtxtArray={ //�����ı���
			new JTextField("110")
	}; 
	private JPasswordField jpassword=new JPasswordField("110"); //���������
	String sql;
	DataBase db;
	public Login(){
		/////////////////////////////////////////////
		jp.setLayout(null); //����JPanel�Ĳ��ֹ�����
		for(int i=0;i<2;i++){ //�Ա�ǩ�밴ť�ؼ�ѭ������		
			jlArray[i].setBounds(160,220+i*50,80,25);//���ñ�ǩ�밴ť�Ĵ�С��λ��	
			jp.add(jlArray[i]);//����ǩ�Ͱ�ť��ӽ�JPanel������
		}
		for(int i=0;i<1;i++){//�����ı���Ĵ�Сλ�ò�Ϊ������¼�������
			jtxtArray[i].setBounds(210,220+50*i,180,25);
			jp.add(jtxtArray[i]);
			jtxtArray[i].addActionListener(this);
		}
		for(int i=0;i<3;i++){//���ð�ť�Ĵ�Сλ�ò�Ϊ������¼�������
			jbArray[i].setBounds(80+i*120,330,100,25);
			jp.add(jbArray[i]);	
			jbArray[i].addActionListener(this);      
		}
		jpassword.setBounds(210,270,180,25);//���������Ĵ�Сλ��
		jp.add(jpassword);//���������ӽ�JPanel����
		jpassword.setEchoChar('*');//���������Ļ����ַ�
		jpassword.addActionListener(this);//Ϊ�����ע�������
		jlArray[2].setBounds(10,280,300,25);//����������ʾ��¼״̬�ı�ǩ�Ĵ�Сλ��
		jp.add(jlArray[2]);//����ǩ��ӽ�JPanel����
		///////////////
		//this.add(jp);	
		///////////////
		Image image=new ImageIcon("./res/login.jpg").getImage();//��logoͼƬ���г�ʼ��  
		this.setIconImage(image);
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setTitle("ͼ�����ϵͳ��ӭ��!");
		this.setResizable(false);
		//���ô����״γ��ֵĴ�С��λ��--�Զ�����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;//��������
		int h=400;//������߶�
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//���ô����������Ļ����
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
		// ���ͼƬ��frame�ڶ���,������ͼ��ǩ��ӵ�jframe��LayeredPane�����??
		jp.setOpaque(false);
		jpp.setOpaque(false);
		jp.setLayout(null);
	}	

	//ʵ��ActionListener�ӿ��еķ���
	public void actionPerformed(ActionEvent e)
	{//�¼�ԴΪ�ı���
		String mgno=jtxtArray[0].getText().trim();
		DataBase.login=this;
		if(e.getSource()==jtxtArray[0]){
			jpassword.requestFocus();//�л����뽹�㵽�����
		}
		else if(e.getSource()==jbArray[1]){//�¼�ԴΪ��հ�ť
			//���������Ϣ
			jlArray[2].setText("");
			jtxtArray[0].setText("");
			jpassword.setText("");
			//�����뽹�����õ��ı���
			jtxtArray[2].requestFocus();
		}
		else if(e.getSource()==jbArray[2]){//�¼�ԴΪ����Ա��¼��ť
			//�ж��û����������Ƿ�ƥ��
			if(!mgno.matches("\\d+"))//������б�ܴ���������ʽ,/d����Ϊ����
			{//����û�����ʽ��������
				JOptionPane.showMessageDialog(this,"�û�����ʽ���󣡣���","��Ϣ",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sql="select mgNo,password from manager where mgNo="+Integer.parseInt(mgno);
			db=new DataBase();
			db.selectDb(sql);//���������Ƕ��û�����������в�ѯ����֤���
			try{
				String mgNo="";
				String password="";
				while(db.rs.next()){//ȡ������������ݲ���ֵ
					mgNo=db.rs.getString(1).trim();
					password=db.rs.getString(2).trim();					
				}
				if(jtxtArray[0].getText().trim().equals(mgNo)&&
						String.valueOf(jpassword.getPassword()).equals(password)){//��¼�ɹ�
					jlArray[2].setText("��ϲ������¼�ɹ�������");
					new Root(mgNo);
					this.dispose();   			
				}
				else{//��¼ʧ��
					jlArray[2].setText("�Բ��𣬵�¼ʧ�ܣ�����");    
				}
			}
			catch(Exception e1){e1.printStackTrace();}
			db.dbClose();//�ر����ݿ�����
		}
		else if(e.getSource()==jbArray[0]){//�¼�ԴΪѧ����¼��ť
			if(!jtxtArray[0].getText().trim().matches("\\d+")){
				//��ѧ�Ÿ�ʽ���������ʾ�Ի���
				JOptionPane.showMessageDialog(this,"��������,ѧ��ֻ��Ϊ����!!!",
						"��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//��ѯѧ���ı�������ѧ���Ƿ������STUDENT����
			sql="select StuNO,Password from STUDENT where StuNO="
					+Integer.parseInt(jtxtArray[0].getText().trim());
			db=new DataBase();
			db.selectDb(sql);
			try{
				if(!(db.rs.next())){//��ѧ�Ŵ��������ʾ�Ի���
					JOptionPane.showMessageDialog(this,"�����˴����ѧ�ţ���",
							"��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				}
				else{//�õ�����ѧ�ŵ�ѧ����ѧ�ź�����
					String stuNO=db.rs.getString(1).trim(),
							password=db.rs.getString(2).trim();
					if(jtxtArray[0].getText().trim().equals(stuNO)&&
							String.valueOf(jpassword.getPassword()).equals(password)){//��¼�ɹ�
						jlArray[2].setText("��ϲ������¼�ɹ�������");
						new StudentSystem();
						this.dispose();
					}
					else{//��¼ʧ��
						jlArray[2].setText("�Բ��𣬵�¼ʧ�ܣ�����");    
					}
				}
			}
			catch(Exception ex) { ex.printStackTrace();}
			db.dbClose(); //�ر����ݿ�����	
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
