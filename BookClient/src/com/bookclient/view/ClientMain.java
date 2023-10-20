package com.bookclient.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.bookclient.control.ConnectServer;
import com.bookclient.model.BorrowListModel;
import com.bookclient.model.UserModel;
import com.common.BookModel;
import com.common.Message;
import com.common.MyTable;

import java.awt.CardLayout ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ClientMain extends JFrame implements TreeSelectionListener,ActionListener,ListSelectionListener{
	private JSplitPane splitpane;
	private JTree tree;
	private Container con;
	private JLabel firstlabel;
	private JLabel l;   //图书搜索提示标签
	private CardLayout card; 
	private JTextField tf;
	private JButton search;
	private JButton borrow;
	private MyTable mytab1;//个人信息表
	private MyTable mytab2;//超期图书表
	private MyTable mytab3;//全部图书表
	private MyTable mytab4;//已借阅图书表
	private BookModel bm1;//超期图书
	private BookModel bm2;//全部图书
	private BorrowListModel bm3;//已借阅图书
	private UserModel um;//个人信息
	
	private int userid;
	private int[] rows;
	//面板
	private JPanel pfirstshow;//初始显示欢迎面板
	private JPanel plift;//左面板
	private JPanel pright;//右面板
	private JPanel ppermes;//个人信息
	private JPanel poverduebook;//超期图书
	private JPanel pchildbooksearch;  //查询图书
	private JPanel psearchnorth;
	private JPanel pborrowbook;//已借阅图书
	//节点
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode npermes;
	private DefaultMutableTreeNode nbooksearch;
	private DefaultMutableTreeNode nperlibrary;
	private DefaultMutableTreeNode npermesshow;
	private DefaultMutableTreeNode noverduebook;
	private DefaultMutableTreeNode nchildbooksearch;
	private DefaultMutableTreeNode nborrowbook;
	//构造方法
	public ClientMain(int userid){
		super("欢迎使用重庆大学数字图书馆：");
		this.setBounds(300, 150, 600, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userid=userid;
		
		con=this.getContentPane();
		firstlabel=new JLabel("欢迎使用重庆大学数字图书馆！",JLabel.CENTER);
		l=new JLabel("请输入查询关键字：");
		pfirstshow=new JPanel();
		plift=new JPanel();
		pright=new JPanel();
		ppermes=new JPanel();
		poverduebook=new JPanel();
		pborrowbook=new JPanel();
		//构造图书查询面板
		pchildbooksearch=new JPanel();
		search=new JButton(new ImageIcon("./images/search.jpg"));
		search.addActionListener(this);
		tf=new JTextField(15);
		psearchnorth=new JPanel();
		psearchnorth.setLayout(new FlowLayout());
		psearchnorth.add(l);
		psearchnorth.add(tf);
		psearchnorth.add(search);
		bm1=new BookModel();
		bm2=new BookModel();
		bm3=new BorrowListModel();
		um=new UserModel();
		mytab1=new MyTable(um);
		mytab2=new MyTable(bm1);
		mytab3=new MyTable(bm2);
		mytab4=new MyTable(bm3);
		mytab3.getSelectionModel().addListSelectionListener(this);
		
		ppermes.setLayout(new BorderLayout());
		ppermes.add(new JScrollPane(mytab1),BorderLayout.CENTER);
		poverduebook.setLayout(new BorderLayout());
		poverduebook.add(new JScrollPane(mytab2),BorderLayout.CENTER);
		pchildbooksearch.setLayout(new BorderLayout());
		pchildbooksearch.add(psearchnorth,BorderLayout.NORTH);
		pchildbooksearch.add(new JScrollPane(mytab3),BorderLayout.CENTER);
		pborrowbook.setLayout(new BorderLayout());
		pborrowbook.add(new JScrollPane(mytab4),BorderLayout.CENTER);
		
		borrow=new JButton("借阅此书");
		borrow.addActionListener(this);
		pchildbooksearch.add(borrow,BorderLayout.SOUTH);
		
		pfirstshow.add(firstlabel);
		root=new DefaultMutableTreeNode("系统");
		npermes=new DefaultMutableTreeNode("个人信息查询");
		nbooksearch=new DefaultMutableTreeNode("图书信息查询");
		nperlibrary=new DefaultMutableTreeNode("个人书库信息");
		root.add(npermes);
		root.add(nbooksearch);
		root.add(nperlibrary);
		npermesshow=new DefaultMutableTreeNode("个人信息");
		noverduebook=new DefaultMutableTreeNode("超期图书");
		npermes.add(npermesshow);
		npermes.add(noverduebook);
		nchildbooksearch=new DefaultMutableTreeNode("全部图书查询");
		nbooksearch.add(nchildbooksearch);
		nborrowbook=new DefaultMutableTreeNode("已借阅图书");
		nperlibrary.add(nborrowbook);
		tree=new JTree(root);
		tree.putClientProperty("JTree.LineStyle", "None");
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(this);
		plift.add(new JScrollPane(tree));
		card = new CardLayout();
		pright.setLayout(card);
		pright.add(pfirstshow,"1");
		pright.add(ppermes,"2");
		pright.add(poverduebook,"3");
		pright.add(pchildbooksearch,"4");
		pright.add(pborrowbook,"5");
		card.show(pright,"1");
		splitpane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,plift,pright);
		add(splitpane);
	}
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode selectnode;
		TreePath path=tree.getSelectionPath();
		if(path!=null){
			selectnode=(DefaultMutableTreeNode)path.getLastPathComponent();
			if(selectnode.equals(npermesshow)){     //如果选择“个人信息”
				card.show(pright,"2");
				Message mes=new Message();
				mes.setUserid(userid);
				mes.setFlag(5);           //5表示查询个人信息
				ConnectServer cs=new ConnectServer(mes);
				cs.sendMessage();
				Message rmes=cs.getMessage();
				um.setRowCount(0);
				um.addRow(new Vector());
				um.setValueAt(rmes.getUserid(), 0, 0);
				um.setValueAt(rmes.getUsername(), 0, 1);
				um.setValueAt(rmes.getUsersex(), 0, 2);
				um.setValueAt(rmes.getUserstudentid(), 0, 3);
				um.setValueAt(rmes.getUsercollege(), 0, 4);
				um.setValueAt(rmes.getUserpassword(), 0, 5);
				um.setValueAt(rmes.getUsermail(), 0, 6);
				um.setValueAt(rmes.getUserowe(), 0, 7);
			}else if(selectnode.equals(noverduebook)){     //如果“选择超期图书”
				card.show(pright,"3");
				Message mes=new Message();
				mes.setUserid(userid);
				mes.setFlag(7);                     //7表示要查询已借图书,此处是在已借图书的基础上进行筛选
				ConnectServer cs=new ConnectServer(mes);
				cs.sendMessage();
				int i=0;
				Message rmes=cs.getMessage();
				bm1.setRowCount(0);
				String p="yyyy-MM-dd";//数组指定时间格式
				SimpleDateFormat s=new SimpleDateFormat(p);
				String booknowtime=s.format(new Date()).toString();
				int nyear=Integer.parseInt(booknowtime.substring(2,4));
				int nmonth=Integer.parseInt(booknowtime.substring(5,7));
				int nday=Integer.parseInt(booknowtime.substring(8,booknowtime.length()));
				int ncount=12*(12*nyear+nmonth)+nday;
				while(rmes.getFlag()!=-3){
					if(rmes.getBookstarttime()!=null){
						String bookstarttime=rmes.getBookstarttime();
						int syear=Integer.parseInt(bookstarttime.substring(2,4));
						int smonth=Integer.parseInt(bookstarttime.substring(5,7));
						int sday=Integer.parseInt(bookstarttime.substring(8,booknowtime.length()));
						int scount=12*(12*syear+smonth)+sday;
						if((ncount-scount)>3){
							bm1.addRow(new Vector());
							bm1.setValueAt(rmes.getBookid(), i, 0);
							bm1.setValueAt(rmes.getBookname(), i, 1);
							bm1.setValueAt(rmes.getBookpress(), i, 2);
							bm1.setValueAt(rmes.getPressdate(), i, 3);
							bm1.setValueAt(rmes.getBookauthor(), i, 4);
							bm1.setValueAt(rmes.getBookstarttime(), i, 5);
						}
						i++;
					}
					rmes=cs.getMessage();
				}
			}else if(selectnode.equals(nchildbooksearch)){   //如果选择“全部图书查询”
				card.show(pright,"4");
				Message mes=new Message();
				mes.setFlag(3);
				ConnectServer cs=new ConnectServer(mes);
				cs.sendMessage();
				int i=0;
				Message rmes=cs.getMessage();
				bm2.setRowCount(0);
				while(rmes.getFlag()!=-3){
					bm2.addRow(new Vector());
					bm2.setValueAt(rmes.getBookid(), i, 0);
					bm2.setValueAt(rmes.getBookname(), i, 1);
					bm2.setValueAt(rmes.getBookpress(), i, 2);
					bm2.setValueAt(rmes.getPressdate(), i, 3);
					bm2.setValueAt(rmes.getBookauthor(), i, 4);
					bm2.setValueAt(rmes.getBookcount(), i, 5);
					bm2.setValueAt(rmes.getBookincount(), i, 6);
					i++;
					rmes=cs.getMessage();
				}
				this.rows=null;
			}else if(selectnode.equals(nborrowbook)){   //如果选择“已借图书”
				card.show(pright,"5");
				Message mes=new Message();
				mes.setUserid(userid);
				mes.setFlag(7);          //7表示要查询已借图书
				ConnectServer cs=new ConnectServer(mes);
				cs.sendMessage();
				int i=0;
				Message rmes=cs.getMessage();
				bm3.setRowCount(0);
				while(rmes.getFlag()!=-3){
					bm3.addRow(new Vector());
					bm3.setValueAt(rmes.getBookid(), i, 0);
					bm3.setValueAt(rmes.getBookname(), i, 1);
					bm3.setValueAt(rmes.getBookpress(), i, 2);
					bm3.setValueAt(rmes.getPressdate(), i, 3);
					bm3.setValueAt(rmes.getBookauthor(), i, 4);
					bm3.setValueAt(rmes.getBookstarttime(), i, 5);
					i++;
					rmes=cs.getMessage();
				}
			}else{
				card.show(pright,"1");
			}
		}	
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==search){
			String str=tf.getText().trim();
			if(str.equals("")){
				JOptionPane.showMessageDialog(this,
				         "请输入搜索关键字！");
			}
			else if(str.contains(" ")){
				JOptionPane.showMessageDialog(this,
		         "搜索关键字中请不要包含空格！");
			}
			else if(!str.contains("AND")){
				Message mes=new Message();
				mes.setFlag(4);          //4表示查询包含关键字的书籍
				mes.setShortmes(str);
				ConnectServer cs=new ConnectServer(mes);
				cs.sendMessage();
				int i=0;
				Message rmes=cs.getMessage();
				bm2.setRowCount(0);
				while(rmes.getFlag()!=-3){
					bm2.addRow(new Vector());
					bm2.setValueAt(rmes.getBookid(), i, 0);
					bm2.setValueAt(rmes.getBookname(), i, 1);
					bm2.setValueAt(rmes.getBookpress(), i, 2);
					bm2.setValueAt(rmes.getPressdate(), i, 3);
					bm2.setValueAt(rmes.getBookauthor(), i, 4);
					bm2.setValueAt(rmes.getBookcount(), i, 5);
					bm2.setValueAt(rmes.getBookincount(), i, 6);
					i++;
					rmes=cs.getMessage();
				}
			}
		}
		if(arg0.getSource()==borrow){
			if(rows==null){
				JOptionPane.showMessageDialog(this,
		         "请选择要借阅的图书！");
			}else{
				for(int i=0;i<rows.length;i++){
					Message mes=new Message();
					mes.setFlag(6);          //6表示要进行借阅
					String p="yyyy-MM-dd";//数组指定时间格式
					SimpleDateFormat s=new SimpleDateFormat(p);
					String bookstarttime=s.format(new Date()).toString();
					mes.setBookstarttime(bookstarttime);
					mes.setUserid(this.userid);
					int bookid=Integer.parseInt(String.valueOf(bm2.getValueAt(rows[i], 0)));
					mes.setBookid(bookid);
					ConnectServer cs=new ConnectServer(mes);
					cs.sendMessage();
					Message rmes=cs.getMessage();
					if(!rmes.isOk()){
						JOptionPane.showMessageDialog(this,
				         "书号："+bookid+"已被借空，借阅失败！");
					}else{
						int pbookincount=Integer.parseInt(String.valueOf(bm2.getValueAt(rows[i], 6)));
						int bookincount=pbookincount-1;
						bm2.setValueAt(bookincount,rows[i],6);
						JOptionPane.showMessageDialog(this,
						         "书号："+bookid+"借阅成功！");
					}
				}
			}
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		this.rows=mytab3.getSelectedRows();
	}
}
