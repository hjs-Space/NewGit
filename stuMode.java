package com.DUANGDUANG1;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class stuMode extends AbstractTableModel{

	Vector<String> head;
	Vector<Vector> data;
	DataBase db;
	String sql;
	private void Init(String sql) {
		if(sql.equals(""))
		{
			sql="select * from student";
		}
		data=new Vector();
		head = new Vector<String>();{//��������
			head.add("ѧ��");head.add("����");
			head.add("�Ա�");head.add("�༶");
			head.add("Ժϵ");head.add("����");
			head.add("����Ȩ��");
		}
		try {
			db=new DataBase();
			db.selectDb(sql);
			while(db.rs.next()){
					Vector head=new Vector();
					head.add(db.rs.getString(1).trim());
					head.add(db.rs.getString(2).trim());
					head.add(db.rs.getString(3).trim());
					head.add(db.rs.getString(4).trim());
					head.add(db.rs.getString(5).trim());
					head.add(db.rs.getString(6).trim());
					head.add(db.rs.getString(7).trim());
					//str = new String(str.getBytes(),"gbk");
					data.add(head);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(db.rs!=null)db.rs.close();
				if(db.stat!=null)db.stat.close();
				if(db.con!=null)db.con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	//ͨ�����ݵ�sql������õ�����ģ��
	public stuMode (String sql) {
		this.Init(sql);
	}
	//��һ�����캯�������ڳ�ʼ�����ǵ�����ģ��
	public stuMode(){
		this.Init("");
	}

	//�õ���������
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.data.size();

	}

	//�õ���������
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.head.size();

	}

	//�õ�ĳ��ĳ�е�����
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector)this.data.get(rowIndex)).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.head.get(column);
	}

}
