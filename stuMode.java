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
		head = new Vector<String>();{//创建标题
			head.add("学号");head.add("姓名");
			head.add("性别");head.add("班级");
			head.add("院系");head.add("密码");
			head.add("借书权限");
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
	//通过传递的sql语句来得到数据模型
	public stuMode (String sql) {
		this.Init(sql);
	}
	//做一个构造函数，用于初始化我们的数据模型
	public stuMode(){
		this.Init("");
	}

	//得到共多少行
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.data.size();

	}

	//得到共多少列
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.head.size();

	}

	//得到某行某列的数据
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
