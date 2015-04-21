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
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//�����ָ����, ��ֱ�ָ��ʾ Component �� y ��ָ
	private JPanel jp=new JPanel();//�����������
	private JPanel jpAdd=new JPanel();//�������ѧ�����
	private JPanel jpDelete=new JPanel();//����ɾ��ѧ�����
	private JPanel jpUpdate=new JPanel();//�����������
	private JPanel jpSearch=new JPanel();//������ѯ���
	private JTabbedPane jtp=new JTabbedPane();//����ѡ�����
	bookMode bm;
	JScrollPane jspn;
	JTable jt;
	DataBase db;
	String sql;
	String []str1=new String[7];

	//����ͼ������ǩ����
	private JLabel[] jlArryAd=new JLabel[]
			{
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("�� �� ��"),
			new JLabel("��������"),
			new JLabel("�� Ԥ Լ"),
			new JLabel("�� �� ��")
			};
	//����ͼ��ɾ����ǩ����
	private JLabel[] jlArryDe=new JLabel[]
			{
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("�� �� ��"),
			new JLabel("��������"),
			new JLabel("�� Ԥ Լ"),
			new JLabel("�� �� ��")
			};
	//����ͼ���޸ı�ǩ����
	private JLabel[] jlArryUp=new JLabel[]
			{
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("��    ��"),
			new JLabel("�� �� ��"),
			new JLabel("��������"),
			new JLabel("�� Ԥ Լ"),
			new JLabel("�� �� ��")
			};
	//����ͼ����ұ�ǩ����
	private JLabel[] jlArrySe=new JLabel[]
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
	private JTextField[] jtxtArryAd=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//�����ı�������
	private JTextField[] jtxtArryDe=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//�����ı�������
	private JTextField[] jtxtArryUp=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//�����ı�������
	private JTextField[] jtxtArrySe=new JTextField[]
			{
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField(),
			new JTextField()
			};
	//����һ�������б��������Լ����������б��
	String[] str={"��","��"};
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

	//���ڴ��������JTableģ����ʵ��JTable
	//��������б�������
	//����һ���������������
	//����һ�����ģ��
	//�ñ��ģ��������һ�����jttable����
	//������װ������������ȥ
	//����BookManage�ķ���
	public BookManage()
	{
		bookMode bm=new bookMode();
		jt=new JTable(bm);
		jspn=new JScrollPane(jt);
		//��jp������ı���
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
		//��jp����Ӱ�ť,���ð�ť��λ�ü���С����Ϊ��ť���ü���
		for(int i=0;i<4;i++)
		{
			jpAdd.add(jbArry[0]);
			jpDelete.add(jbArry[1]);
			jpUpdate.add(jbArry[2]);
			jpSearch.add(jbArry[3]);
			jbArry[i].addActionListener(this);
		}
		//��jpAdd����ӱ�ǩ,�������ı����λ�ü���С�Լ���ǩ��λ��
		for(int i=0;i<7;i++)
		{
			jpAdd.add(jlArryAd[i]);
			if(i<3)
			{//������š����������ߵı�ǩ�Լ��ı���λ��
				jlArryAd[i].setBounds(25,60+30*i,100,20);
				jtxtArryAd[i].setBounds(80,60+30*i,150,20);
			}
			else if(i>2&&i<5)
			{//���ó����硢�������ڵı�ǩ�Լ��ı���λ��
				jlArryAd[i].setBounds(275,60+30*(i-3),100,20);
				jtxtArryAd[i].setBounds(340,60+30*(i-3),150,20);
			}
			else
			{//�����ѽ��ġ���ԤԼ�ı�ǩλ��
				jlArryAd[i].setBounds(525,60+30*(i-5),100,20);
				jbArry[0].setBounds(595, 120, 100,20);
			}
		}

		//��jpDelete����ӱ�ǩ,�������ı����λ�ü���С�Լ���ǩ��λ��
		for(int i=0;i<7;i++)
		{
			jpDelete.add(jlArryDe[i]);
			if(i<3)
			{//������š����������ߵı�ǩ�Լ��ı���λ��
				jlArryDe[i].setBounds(25,60+30*i,100,20);
				jtxtArryDe[i].setBounds(80,60+30*i,150,20);
			}
			else if(i>2&&i<5)
			{//���ó����硢�������ڵı�ǩ�Լ��ı���λ��
				jlArryDe[i].setBounds(275,60+30*(i-3),100,20);
				jtxtArryDe[i].setBounds(340,60+30*(i-3),150,20);
			}
			else
			{//�����ѽ��ġ���ԤԼ�ı�ǩλ��
				jlArryDe[i].setBounds(525,60+30*(i-5),100,20);
				jbArry[1].setBounds(595, 120, 100,20);
			}
		}
		//��jpUpdate����ӱ�ǩ,�������ı����λ�ü���С�Լ���ǩ��λ��
		for(int i=0;i<7;i++)
		{
			jpUpdate.add(jlArryUp[i]);
			if(i<3)
			{//������š����������ߵı�ǩ�Լ��ı���λ��
				jlArryUp[i].setBounds(25,60+30*i,100,20);
				jtxtArryUp[i].setBounds(80,60+30*i,150,20);
				jtxtArryUp[0].setEditable(false);
			}
			else if(i>2&&i<5)
			{//���ó����硢�������ڵı�ǩ�Լ��ı���λ��
				jlArryUp[i].setBounds(275,60+30*(i-3),100,20);
				jtxtArryUp[i].setBounds(340,60+30*(i-3),150,20);
			}
			else
			{//�����ѽ��ġ���ԤԼ�ı�ǩλ��
				jlArryUp[i].setBounds(525,60+30*(i-5),100,20);
				jbArry[2].setBounds(595, 120, 100,20);
			}
		}
		//��jpSearch����ӱ�ǩ,�������ı����λ�ü���С�Լ���ǩ��λ��
		for(int i=0;i<7;i++)
		{
			jpSearch.add(jlArrySe[i]);
			if(i<3)
			{//������š����������ߵı�ǩ�Լ��ı���λ��
				jlArrySe[i].setBounds(25,60+30*i,100,20);
				jtxtArrySe[i].setBounds(80,60+30*i,150,20);
			}
			else if(i>2&&i<5)
			{//���ó����硢�������ڵı�ǩ�Լ��ı���λ��
				jlArrySe[i].setBounds(275,60+30*(i-3),100,20);
				jtxtArrySe[i].setBounds(340,60+30*(i-3),150,20);
			}
			else
			{//�����ѽ��ġ���ԤԼ�ı�ǩλ��
				jlArrySe[i].setBounds(525,60+30*(i-5),100,20);
				jbArry[3].setBounds(595, 120, 100,20);
			}
		}

		//���������б���λ�ü���С 
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
		//��jp����������б��
		jpAdd.add(jcb[0]);
		jpAdd.add(jcb[4]);
		jpDelete.add(jcb[1]);
		jpDelete.add(jcb[5]);
		jpUpdate.add(jcb[2]);
		jpUpdate.add(jcb[6]);
		jpSearch.add(jcb[3]);
		jpSearch.add(jcb[7]);

		//��괥���¼�
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
		this.setLayout(new GridLayout(1,1));//����Ϊ1��1�е����񲼾�
		jpAdd.setLayout(null);//���������ϲ���Ϊ�ղ��ֹ�����
		jpDelete.setLayout(null);
		jpUpdate.setLayout(null);
		jpSearch.setLayout(null);
		jsp.setDividerLocation(200);//���÷ָ�����λ��
		jsp.setDividerSize(14);//���÷ָ����Ĵ�С
		jsp.setTopComponent(jtp);//�����������ӵ��ϲ�
		jtp.addTab("ͼ�����",jpAdd);
		jtp.addTab("ɾ��ͼ��",jpDelete);
		jtp.addTab("�޸�ͼ����Ϣ",jpUpdate);
		jtp.addTab("����ͼ��",jpSearch);
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
			bm=new bookMode();
			jt.setModel(bm);
		}
		if(e.getSource()==jbArry[1])
		{
			this.deleteBook();
			System.out.println("���ɾ��ͼ��");
			bm=new bookMode();
			jt.setModel(bm);
		}
		if(e.getSource()==jbArry[2])
		{
			this.updatetBook();
			System.out.println("����޸�ͼ��");
			bm=new bookMode();
			jt.setModel(bm);
		}
		if(e.getSource()==jbArry[3])
		{
//			this.searchBook();
			sql="select * from book where BookNO="+Integer.parseInt(jtxtArrySe[0].getText().trim());
			System.out.println("�������ͼ��");
			bm=new bookMode(sql);
			jt.setModel(bm);
		}

	}

	public void insertBook()
	{
		for(int i=0;i<5;i++)
		{//���ı����������Ϣ��ŵ�һ�����飬��������
			str1[i]=jtxtArryAd[i].getText().trim();
		}
		str1[5]=jcb[0].getSelectedItem().toString();
		str1[6]=jcb[1].getSelectedItem().toString();
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&
				str1[3].equals("")&&str1[4].equals(""))
		{
			JOptionPane.showMessageDialog(this, "�������Ϣ����Ϊ��", "��Ϣ����",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else{//���Ҹ�����Ƿ����
			sql="select bookNO from book where bookNo="+Integer.parseInt(jtxtArryAd[0].getText().trim());
			db=new DataBase();
			db.selectDb(sql);
			try {
				String bookNo="";
				while(db.rs.next())
				{
					bookNo=db.rs.getString(1).trim();
				}
				//������ڸ���ţ�������ʾ���������ͼ����Ϣ
				if(jtxtArryAd[0].getText().trim().equals(bookNo))
				{
					JOptionPane.showMessageDialog(this, "������Ѿ����ڣ�", "��ܰ��ʾ", 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}else
				{//�����ݲ��뵽���ݿ���
					sql="insert into BOOK values('"+str1[0]+"','"+str1[1]+"','"+str1[2]+"','"+str1[3]+"'," +
							"'"+str1[4]+"','"+str1[5]+"','"+str1[6]+"')";
					db=new DataBase();
					db.updateDb(sql);//����DataBase�еķ���
					for(int i=0;i<7;i++)
					{
						if(i<5)jtxtArryAd[i].setText("");//����ı���
					}
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
		String BookNo=jtxtArryDe[0].getText().trim();
		if(BookNo.equals(""))
		{//�������ͼ���Ϊ�գ��򵯳���Ϣ��ʾ
			JOptionPane.showMessageDialog(this, "�����ͼ��Ų���Ϊ��", "��ܰ����", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select BookNo from record where bookNo="+Integer.parseInt(BookNo);
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next())//������ͼ����Ϣ�����и���ļ�¼���򵯳���Ϣ��ʾ��
			{
				JOptionPane.showMessageDialog(this, "���鱻���������ɾ����", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else{//������ͼ����Ϣ����û�и���ļ�¼�����ͼ����Ϣ����ɾ����ͼ��
				sql="delete from book where bookNo="+Integer.parseInt(BookNo);
				int flag=db.updateDb(sql);
				if(flag>0){JOptionPane.showMessageDialog(this, "ɾ���ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);}
				else {JOptionPane.showMessageDialog(this, "ɾ��ʧ�ܣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);}
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
		{//�����ı���Ϊ����ʾ
			JOptionPane.showMessageDialog(this,	"ͼ����Ϣ����Ϊ�գ�����",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		if(!str[0].equals("")&&!str[1].equals("")&&!str[2].equals("")
				&&!str[3].equals("")&&!str[4].equals("")&&!str[5].equals(""))
		{//���³����������������Ϣ
			sql="update book set BookName='"+str[1]+"', Author='"+str[2]+"'," +
					"Publishment='"+str[3]+"'," +
					"BuyTime='"+str[4]+"'," +
					"Borrowed='"+str[5]+"'," +
					"Ordered='"+str[6]+"' " +
					"where BookNo="+str[0];

			db.updateDb(sql);
			if(db.updateDb(sql)>0){
				JOptionPane.showMessageDialog(this, "�޸ĳɹ���", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(this, "�޸�ʧ�ܣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
	}
	public void searchBook()
	{
		
		/*if(jtxtArrySe[0].getText().equals("")){//
			JOptionPane.showMessageDialog(this,"��Ų���Ϊ�գ����������룡����",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select * from book where BookNO="+Integer.parseInt(jtxtArrySe[0].getText().trim());
		bm=new bookMode(sql);
		
		db=new DataBase();
		db.selectDb(sql);//��ѯѧ���ı�������ѧ���Ƿ������STUDENT����				   
		try{//�Խ���������쳣����
			int k=0;
			while(db.rs.next()){
				k++;
				jtxtArrySe[0].setText("");
			}
			if(k==0){//��Book����û�и���ţ��������ʾ�Ի���
				JOptionPane.showMessageDialog(this,	"û�и������Ϣ��",
						"��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			}	
		}
		catch(Exception e){e.printStackTrace();}
		finally{db.dbClose();}*/
	}
}