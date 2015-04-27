/**
 * ����ԤԼ����
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

	//�����ָ��Ϊ���µ�JSplitePane����
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jp=new JPanel();//����JPanel����
	private JButton jb=new JButton("�ύ");	//������ť
	private JLabel[] jlArray=new JLabel[]{
			new JLabel("��Ҫ���Ļ�ԤԼ�����"),
			new JLabel("����ѧ����ѧ��")
	};
	private JTextField[] jtxtArray=new JTextField[]{//�����ı���
			new JTextField(),new JTextField()
	};
	private JRadioButton[] jrbArray={//������ѡ��ť
			new JRadioButton("����ͼ��",true),
			new JRadioButton("ԤԼͼ��")
	};	
	private ButtonGroup bg=new ButtonGroup();//������ť��

	public BorrowBook()
	{
		bm=new bookMode();
		jt=new JTable(bm);
		jspn=new JScrollPane(jt);//��JTable��װ����������
		//��������SearchBook�������²��־�Ϊ�ղ��ֹ�����
		jp.setLayout(null);
		jp.add(jb);
		jb.setBounds(325,80,120,20);
		jb.addActionListener(this);
		for(int i=0;i<2;i++){//�Ե�ѡ��ť��������
			jrbArray[0].setBounds(40,80,100,20);//����ͼ�鵥ѡ��ť
			jrbArray[1].setBounds(190,80,100,20);//ԤԼͼ�鵥ѡ��ť
			jp.add(jrbArray[i]);//����ѡ��ť��ӵ����
			bg.add(jrbArray[i]);//����ť��ӵ���ť��
			jrbArray[i].addActionListener(this);//��ע�����
		}
		for(int i=0;i<2;i++){//���ñ�ǩ���ı�������꣬��������ӽ�JPanel
			jlArray[0].setBounds(40,30,150,20);
			jtxtArray[0].setBounds(180,30,120,20);
			jlArray[1].setBounds(325,30,150,20);
			jtxtArray[1].setBounds(430,30,120,20);
			jp.add(jtxtArray[i]);	
			jp.add(jlArray[i]);
		}
		jsp.setTopComponent(jp);//��jp���õ�jsp���ϲ�����
		jsp.setBottomComponent(jspn);//�����������ӵ�jsp�����²�
		jsp.setDividerSize(10);//���÷ָ����Ĵ�С
		jsp.setDividerLocation(130);//����jsp�зָ����ĳ�ʼλ��
		this.setLayout(new GridLayout(1,1));//���ò�ѯͼ�����Ϊ���񲼾�
		this.add(jsp);
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(3,10,600,400);
		this.setVisible(true);
	}
	//Ϊ�¼����صļ��������ϴ����¼�
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//��ť�����¼�����
		if(e.getSource()==jb)
		{
			String str0=jtxtArray[0].getText().trim();//��ȡ�ı��������ͼ���
			String str1=jtxtArray[1].getText().trim();//��ȡ�ı��������ѧ����
			if(str0.equals("")||str1.equals(""))
			{
				JOptionPane.showMessageDialog(this, "�������Ϣ����Ϊ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if(!str0.equals("")&&!str1.equals(""))
			{//1�����Ȳ鿴��ѧ���Ƿ���н���Ȩ��
				sql="select * from student where stuNo= "+Integer.parseInt(str1);
				db=new DataBase();
				db.selectDb(sql);
				try {
					if(!(db.rs.next()))
					{//���ѧ������û�м�¼����ʾ
						JOptionPane.showMessageDialog(this, "�����ѧ������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else{
						String stuName=db.rs.getString(2).trim();//��ȡѧ��������
						String Class=db.rs.getString(4).trim();//��ȡѧ���İ༶
						stuName=new String(stuName.getBytes("gbk"));//����ת��
						Class=new String(Class.getBytes("gbk"));//����ת��
						if(db.rs.getString(7).trim().equals("��"))
						{//û�н���Ȩ�ޣ���ʾ
							JOptionPane.showMessageDialog(this, "��ѧ��û�н���Ȩ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						else{//����н���Ȩ�ޣ�����ͼ����в��Ҹ����Ƿ񱻽������ԤԼ
							sql="select * from book where BookNo="+Integer.parseInt(str0);
							db.selectDb(sql);//ִ�в��Ҳ���
							if(!(db.rs.next()))
							{
								JOptionPane.showMessageDialog(this, "û�и������Ϣ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
							}else{
								String borrow=db.rs.getString(6).trim();//��ȡ�Ƿ����
								borrow=new String(borrow.getBytes("gbk"));//�����ʽΪgbk
								String ordered=db.rs.getString(7).trim();//��ȡ�Ƿ�ԤԼ
								ordered=new String(ordered.getBytes("gbk"));
								String bookName=db.rs.getString(2).trim();//��ȡͼ����
								bookName=new String(bookName.getBytes("gbk"));
								String author=db.rs.getString(3).trim();//��ȡͼ������
								author=new String(author.getBytes("gbk"));
								/*sql="select * from book where BookNo="+Integer.parseInt(str0);
								bookMode bm=new bookMode(sql);//�������ģ��
								jt.setModel(bm);//ˢ�±��
*/								
								//��ѡ�����ͼ��ĵ�ѡ��ťʱ
								if(jrbArray[0].isSelected())
								{
									//�ж��Ƿ񱻽���
									if(borrow.trim().equals("��"))
									{//�Ѿ�������
										JOptionPane.showMessageDialog(this, "��ͼ���Ѿ������ģ���ԤԼ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
									}
									//�ж��Ƿ�ԤԼ
									else if(ordered.trim().equals("��"))
									{//�Ѿ���ԤԼ,��ʾ
										JOptionPane.showMessageDialog(this, "�����Ѿ���ԤԼ", 
												"��ʾ", JOptionPane.INFORMATION_MESSAGE);
									}
									else{//����ͽ���
										//����ͼ���ļ�¼
										sql="update book set Borrowed='��' where BookNo="+Integer.parseInt(str0);
										db=new DataBase();
										db.updateDb(sql);
										JOptionPane.showMessageDialog(this, "����ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
										/***ʱ������ã���ȡϵͳ�ĵ�ǰʱ��  ***************�黹ʱ��****************
										 * ����Ϊ�˲��ԣ���ʱ������Ϊ������2���Ҫ���飬�������
										 * **********8**/
										Date date=new Date();
										sql="insert into Record values("+Integer.parseInt(str0)+"," +
												"'"+bookName+"',"+Integer.parseInt(str1)+"," +
													"'"+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+"'," +
															"'"+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+(date.getDate()+2)+"','��','��')";
										db.updateDb(sql);
										sql="select * from book";
										bookMode bm=new bookMode(sql);
										jt.setModel(bm);//���±��
									}
									
								}
								else if(jrbArray[1].isSelected())
								{//ѡ��ԤԼ��ѡ��ť
									//�ж��Ƿ�ԤԼ
									if(ordered.trim().equals("��"))
									{//�Ѿ���ԤԼ,��ʾ
										JOptionPane.showMessageDialog(this, "�����Ѿ���ԤԼ", 
												"��ʾ", JOptionPane.INFORMATION_MESSAGE);
									}else
									{//û�б�ԤԼ������ͼ���ļ�¼
										sql="update Book set Ordered='��' where BookNo="+Integer.parseInt(str0);
										db=new DataBase();
										db.updateDb(sql);
										//ԤԼ�ɹ�������ʾ
										JOptionPane.showMessageDialog(this, "ԤԼ�ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
										//����¼��ӵ�ԤԼ����
										String sql1="insert into Orderreport values("+Integer.parseInt(jtxtArray[0].getText().trim())+",'"+stuName+"','"+Class+"','"+bookName+"',"+Integer.parseInt(jtxtArray[1].getText().trim())+",'"+author+"')";
										db.updateDb(sql1);
										sql="select * from book";
										//����jt���
										bm=new bookMode(sql);
										jt.setModel(bm);
									}
								}
								
							}
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					db.dbClose();//�ر�
				}
			}
		}
	}

}
