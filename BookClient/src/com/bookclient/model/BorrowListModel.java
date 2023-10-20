package com.bookclient.model;

import javax.swing.table.DefaultTableModel;

import com.common.BookModel;

public class BorrowListModel extends DefaultTableModel{
	public static String[] colnam={"书号","书名","出版社","出版日期","作者","借书日期"};
	public static Object[][] cell={};
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;	
    }
	public BorrowListModel(){
		super(BorrowListModel.cell,BorrowListModel.colnam);
	}
}
