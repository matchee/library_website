package com.common;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class BookModel extends DefaultTableModel{
	public static String[] colnam={"书号","书名","出版社","出版日期","作者","总数","在馆总数"};
	public static Object[][] cell={};
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;	
    }
	public BookModel(){
		super(BookModel.cell,BookModel.colnam);
	}
}
