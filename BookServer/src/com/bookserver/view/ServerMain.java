package com.bookserver.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.bookserver.control.SocketServer;
import com.bookserver.model.BorrowListModel;
import com.bookserver.model.OweUserModel;
import com.bookserver.model.UserModel;
import com.common.BookModel;
import com.common.Message;
import com.common.MyTable;

import java.awt.CardLayout ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class ServerMain extends JFrame implements TableModelListener,TreeSelectionListener,ActionListener,ListSelectionListener{
	public  MyTable mytab1;  //全部图书查询
	private MyTable mytab2;  //用户信息查询
	private MyTable mytab3;  //欠费用户查询
	private MyTable mytab4;  //用户借书列表查询
	private BookModel bm1;  //全部图书查询的BookModel
	private BorrowListModel bm2;  //用户借书列表的BookModel
	private UserModel um1;      //用户信息查询的UserModel
	private OweUserModel um2;      //欠费读者查询的OweUserModel
	private static boolean flag=true;
	private JLabel l1;       //全部图书搜索框提示标签
	private JLabel l2;       //用户信息搜索框提示标签 
	private JLabel l3;       //欠费用户搜索框提示标签
	private JLabel l4;       //用户借书列表搜索框提示标签
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JButton search1;
	private JButton search2;
	private JButton search3;
	private JButton search4;
	private JPanel p1n;  //全书搜索的北面板
	private JPanel p1s;  //全书搜索的男面板
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;  //用户借书列表
	private Container con;
	private JSplitPane splitpane1;
	private JSplitPane splitpane2;
	private JTree tree;
	private CardLayout card;
	private JPanel puplift;
	private JPanel puplift1;
	private JPanel pdownlift;
	private JPanel pright;
	private JButton startserver;
	private JButton pauseserver;
	private JLabel showmes;
	private int[] rows1=null;  //全部图书的rows
	private int[] rows2=null;  //全部用户的rows
	private int[] rows3=null;  
	private int[] rows4=null;  //在用户借书列表中用来保存选中的列表
	private JButton deletebook;
	private JButton insertbook;
	private JButton deleteuser;
	private JButton deleteowebook;
	
	//面板
	private JPanel pfirstshow;//初始显示欢迎面板
	private JPanel pallbooksearch;
	private JPanel pusermessearch;
	private JPanel powningfeeusersearch;
	private JPanel pborrowusersearch; //用户借书列表查询
	//节点
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode nallbook;
	private DefaultMutableTreeNode nallbooksearch;
	private DefaultMutableTreeNode nusermes;
	private DefaultMutableTreeNode nusermessearch;
	private DefaultMutableTreeNode nowningfeeuser;
	private DefaultMutableTreeNode nowningfeeusersearch;
	private DefaultMutableTreeNode nborrowuser;
	private DefaultMutableTreeNode nborrowusersearch;
	
	public ServerMain(){
		super("欢迎使用重庆大学数字图书馆：");
		this.setBounds(300, 150, 600, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		con=this.getContentPane();
		pfirstshow=new JPanel();
		puplift=new JPanel();
		puplift1=new JPanel();
		puplift.setLayout(new BorderLayout());
		puplift1.setLayout(new GridLayout(1,2));
		pdownlift=new JPanel();
		pright=new JPanel();
		showmes=new JLabel("欢迎...",JLabel.CENTER);
		showmes.setForeground(Color.blue);
		startserver=new JButton("启动");
		pauseserver=new JButton("暂停");
		startserver.addActionListener(this);
		pauseserver.addActionListener(this);
		puplift1.add(startserver);
		puplift1.add(pauseserver);
		puplift.add(showmes,BorderLayout.CENTER);
		puplift.add(puplift1,BorderLayout.SOUTH);
		pallbooksearch=new JPanel();
		pusermessearch=new JPanel();
		powningfeeusersearch=new JPanel();
		pborrowusersearch=new JPanel();
		pallbooksearch.setLayout(new BorderLayout());
		pusermessearch.setLayout(new BorderLayout());
		powningfeeusersearch.setLayout(new BorderLayout());
		pborrowusersearch.setLayout(new BorderLayout());
		l1=new JLabel("请输入部分或全部书名：");
		l2=new JLabel("请输入待查询关键字：");
		l3=new JLabel("请输入待查询关键字：");
		l4=new JLabel("请输入待查询用户ID：");
		l4.setForeground(Color.RED);
		tf1=new JTextField(15);
		tf2=new JTextField(15);
		tf3=new JTextField(15);
		tf4=new JTextField(15);
		search1=new JButton(new ImageIcon("./images/search.jpg"));
		search2=new JButton(new ImageIcon("./images/search.jpg"));
		search3=new JButton(new ImageIcon("./images/search.jpg"));
		search4=new JButton(new ImageIcon("./images/search.jpg"));
		p1n=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p4=new JPanel();
		p1n.add(l1);
		p1n.add(tf1);
		p1n.add(search1);
		p2.add(l2);
		p2.add(tf2);
		p2.add(search2);
		p3.add(l3);
		p3.add(tf3);
		p3.add(search3);
		p4.add(l4);
		p4.add(tf4);
		p4.add(search4);
		search1.addActionListener(this);
		search2.addActionListener(this);
		search3.addActionListener(this);
		search4.addActionListener(this);
		pallbooksearch.add(p1n,BorderLayout.NORTH);
		pusermessearch.add(p2,BorderLayout.NORTH);
		powningfeeusersearch.add(p3,BorderLayout.NORTH);
		pborrowusersearch.add(p4,BorderLayout.NORTH);
		root=new DefaultMutableTreeNode("系统");
		nallbook=new DefaultMutableTreeNode("在库图书查询");
		nusermes=new DefaultMutableTreeNode("用户信息查询");
		nowningfeeuser=new DefaultMutableTreeNode("欠费读者查询");
		nborrowuser=new DefaultMutableTreeNode("用户借书列表");
		root.add(nallbook);
		root.add(nusermes);
		root.add(nowningfeeuser);
		root.add(nborrowuser);
		nallbooksearch=new DefaultMutableTreeNode("全部图书查询");
		nallbook.add(nallbooksearch);
		nusermessearch=new DefaultMutableTreeNode("用户信息查询");
		nusermes.add(nusermessearch);
		nowningfeeusersearch=new DefaultMutableTreeNode("欠费读者查询");
		nowningfeeuser.add(nowningfeeusersearch);
		nborrowusersearch=new DefaultMutableTreeNode("用户借书列表查询");
		nborrowuser.add(nborrowusersearch);
		tree=new JTree(root);
		tree.putClientProperty("JTree.LineStyle", "None");
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(this);
		pdownlift.add(new JScrollPane(tree));
		splitpane1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,puplift,pdownlift);
		card = new CardLayout();
		pright.setLayout(card);
		pright.add(pfirstshow,"1");
		pright.add(pallbooksearch,"2");
		pright.add(pusermessearch,"3");
		pright.add(powningfeeusersearch,"4");
		pright.add(pborrowusersearch,"5");
		card.show(pright,"1");
		splitpane2=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitpane1,pright);
		con.add(splitpane2);
		
		bm1=new BookModel();
		bm2=new BorrowListModel();
		um1=new UserModel();
		um2=new OweUserModel();
		mytab1=new MyTable(bm1);
		mytab2=new MyTable(um1);
		mytab3=new MyTable(um2);
		mytab4=new MyTable(bm2);
		bm1.addTableModelListener(this);  //添加表格事件监听
		mytab1.getSelectionModel().addListSelectionListener(this);
		um1.addTableModelListener(this);  //添加表格事件监听
		mytab2.getSelectionModel().addListSelectionListener(this);
		um2.addTableModelListener(this);  //添加表格事件监听
		mytab3.getSelectionModel().addListSelectionListener(this);
		bm2.addTableModelListener(this);  //添加表格事件监听
		mytab4.getSelectionModel().addListSelectionListener(this);
		
		deletebook=new JButton("删除书籍");
		insertbook=new JButton("插入书籍");
		deleteuser=new JButton("删除用户");
		deleteowebook=new JButton("此书已归还，删除此书");
		deletebook.addActionListener(this);
		insertbook.addActionListener(this);
		deleteuser.addActionListener(this);
		deleteowebook.addActionListener(this);
		p1s=new JPanel();
		p1s.setLayout(new FlowLayout());
		p1s.add(deletebook);
		p1s.add(insertbook);
		pallbooksearch.add(new JScrollPane(mytab1),BorderLayout.CENTER);
		pusermessearch.add(new JScrollPane(mytab2),BorderLayout.CENTER);
		powningfeeusersearch.add(new JScrollPane(mytab3),BorderLayout.CENTER);
		pborrowusersearch.add(new JScrollPane(mytab4),BorderLayout.CENTER);
		pborrowusersearch.add(deleteowebook,BorderLayout.SOUTH);
		pallbooksearch.add(p1s,BorderLayout.SOUTH);      
		pusermessearch.add(deleteuser,BorderLayout.SOUTH);  //添加删除用户按钮
		
	}
	
	public static void main(String[] args){
		new ServerMain().setVisible(true);
		ServerSocket server = null ;      // 定义ServerSocket类
		Socket client = null ;          // 表示客 户端
		try {
			server =new ServerSocket(8888);
		} catch (IOException e1) {
			e1.printStackTrace();
		} // 服务器在8888端口上监听
	  	while(true){
	  		while(flag){
		  		try{
		  			Thread.sleep(200);
		  		}catch(Exception e){
		  			e.printStackTrace();
		  			}
		  		}
		      try {
				client = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}               
		      new Thread(new SocketServer(client)).start();   
	  	} 
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode selectnode;
		TreePath path=tree.getSelectionPath();
		String DBURL = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf-8&useSSL=false" ;
		// MySQL数据库的连接用户名
		String DBUSER = "root";
		// MySQL数据库的连接密码
		String DBPASS = "root";
		Connection conn = null ;		// 数据库连接
		Connection conn1 = null ;		// 数据库连接
		Statement stmt = null ;		// 数据库的操作对象
		Statement stmt1 = null ;		// 数据库的操作对象
		ResultSet rs = null ;		// 保存查询结果
		ResultSet rs1 = null ;		// 保存查询结果
		if(path!=null){
			selectnode=(DefaultMutableTreeNode)path.getLastPathComponent();
			if(selectnode.equals(nallbooksearch)){    //如果选择了全部图书查询
				card.show(pright,"2");
				this.rows1=null;
				bm1.setRowCount(0);
				try {
					String sql ="select * from books;";
					conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
					stmt = conn.createStatement() ;
					rs = stmt.executeQuery(sql) ;
					int i=0;
					while(rs.next()){
						int bookid=rs.getInt(1);
						String bookname=rs.getString(2);
						String bookpress=rs.getString(3);
						String pressdate=rs.getString(4);
						String bookauthor=rs.getString(5);
						int bookcount=rs.getInt(6);
						int bookincount=rs.getInt(7);
						bm1.addRow(new Vector());
						bm1.setValueAt(bookid, i, 0);
						bm1.setValueAt(bookname, i, 1);
						bm1.setValueAt(bookpress, i, 2);
						bm1.setValueAt(pressdate, i, 3);
						bm1.setValueAt(bookauthor, i, 4);
						bm1.setValueAt(bookcount, i, 5);
						bm1.setValueAt(bookincount, i, 6);
						i++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(selectnode.equals(nusermessearch)){  //如果选择了读者信息查询
				card.show(pright,"3");
				this.rows2=null;
				um1.setRowCount(0);
				try {
					String sql ="select * from users;";
					conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
					stmt = conn.createStatement() ;
					rs = stmt.executeQuery(sql) ;
					int i=0;
					while(rs.next()){
						int userid=rs.getInt(1);
						String username=rs.getString(2);
						String usersex=rs.getString(3);
						int userstudentid=rs.getInt(4);
						String usercollege=rs.getString(5);
						String userpassword=rs.getString(7);
						String usermail=rs.getString(8);
						String userowe=rs.getString(9);
						um1.addRow(new Vector());
						um1.setValueAt(userid, i, 0);
						um1.setValueAt(username, i, 1);
						um1.setValueAt(usersex, i, 2);
						um1.setValueAt(userstudentid, i, 3);
						um1.setValueAt(usercollege, i, 4);
						um1.setValueAt(userpassword, i, 5);
						um1.setValueAt(usermail, i, 6);
						um1.setValueAt(userowe, i, 7);
						i++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(selectnode.equals(nowningfeeusersearch)){  //欠费读者列表
				card.show(pright,"4");
				String p="yyyy-MM-dd";//数组指定时间格式
				SimpleDateFormat s=new SimpleDateFormat(p);
				String booknowtime=s.format(new Date()).toString();
				int nyear=Integer.parseInt(booknowtime.substring(2,4));
				int nmonth=Integer.parseInt(booknowtime.substring(5,7));
				int nday=Integer.parseInt(booknowtime.substring(8,booknowtime.length()));
				int ncount=12*(12*nyear+nmonth)+nday;
				um2.setRowCount(0);
				try{
					int i=0;
					conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
					stmt = conn.createStatement() ;
					String sql ="select * from borrowbooks;";
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						String bookstarttime=rs.getString(7);
						int userid=rs.getInt(1);
						int bookid=rs.getInt(2);
						String bookname=rs.getString(3);
						if(bookstarttime!=null){
							int syear=Integer.parseInt(bookstarttime.substring(2,4));
							int smonth=Integer.parseInt(bookstarttime.substring(5,7));
							int sday=Integer.parseInt(bookstarttime.substring(8,booknowtime.length()));
							int scount=12*(12*syear+smonth)+sday;
							if((ncount-scount)>3){
								String sql1 ="select * from users where userid="+userid+";";
								conn1 = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
								stmt1 = conn.createStatement() ;
								rs1 = stmt1.executeQuery(sql1);
								while(rs1.next()){
									String username=rs1.getString(2);
									String usersex=rs1.getString(3);
									int userstudentid=rs1.getInt(4);
									String usercollege=rs1.getString(5);
									um2.addRow(new Vector());
									um2.setValueAt(userid, i, 0);
									um2.setValueAt(username, i, 1);
									um2.setValueAt(usersex, i, 2);
									um2.setValueAt(userstudentid, i, 3);
									um2.setValueAt(usercollege, i, 4);
									um2.setValueAt(bookname, i, 5);
									um2.setValueAt(bookid, i, 6);
								}
								i++;
							}
						}
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}else if(selectnode.equals(nborrowusersearch)){   //用户借书列表
				card.show(pright,"5");
			}
			else{
				card.show(pright,"1");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final String DBURL = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf-8&useSSL=false" ;
		// MySQL数据库的连接用户名
		final String DBUSER = "root";
		// MySQL数据库的连接密码
		final String DBPASS = "root";
		Connection conn = null;
		Statement stmt=null;
		ResultSet rs = null ;		// 保存查询结果
		try {
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			stmt = conn.createStatement() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(arg0.getSource()==startserver){
			flag=false;
			showmes.setText("服务器启动");
		}
		if(arg0.getSource()==pauseserver){
			flag=true;
			showmes.setText("服务器暂停");
		}
		if(arg0.getSource()==search1){  //查询图书
			String str=tf1.getText().trim();
			if(str.equals("")){
				JOptionPane.showMessageDialog(this,
				         "请输入搜索关键字！");
			}
			else if(str.contains(" ")){
				JOptionPane.showMessageDialog(this,
		         "搜索关键字中请不要包含空格！");
			}
			else if(!str.contains("AND")){
				try{
					String sql ="select * from books where bookname like '%"+str+"%' or bookname like '"+str+"%' or bookname like '%"+str+"';";
					rs = stmt.executeQuery(sql) ;
					int i=0;
					bm1.setRowCount(0);
					while(rs.next()){
						int bookid=rs.getInt(1);
						String bookname=rs.getString(2);
						String bookpress=rs.getString(3);
						String pressdate=rs.getString(4);
						String bookauthor=rs.getString(5);
						int bookcount=rs.getInt(6);
						int bookincount=rs.getInt(7);
						bm1.addRow(new Vector());
						bm1.setValueAt(bookid, i, 0);
						bm1.setValueAt(bookname, i, 1);
						bm1.setValueAt(bookpress, i, 2);
						bm1.setValueAt(pressdate, i, 3);
						bm1.setValueAt(bookauthor, i, 4);
						bm1.setValueAt(bookcount, i, 5);
						bm1.setValueAt(bookincount, i, 6);
						i++;
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(arg0.getSource()==search4){   //表明要查询用户的借书列表
			if(tf4.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this,
		         "请输入关键字！");
			}else{
				int userid=0;
				try{
					userid=Integer.parseInt(tf4.getText().trim());
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(this,
			         "输入的不是数字！");
				}
				if(userid!=0){
					String sql ="select * from borrowbooks where userid="+userid+";";
					try {
						rs = stmt.executeQuery(sql) ;
						int i=0;
						bm2.setRowCount(0);
						while(rs.next()){
							int bookid=rs.getInt(2);
							String bookname=rs.getString(3);
							String bookpress=rs.getString(4);
							String pressdate=rs.getString(5);
							String bookauthor=rs.getString(6);
							String bookstarttime=rs.getString(7);
							bm2.addRow(new Vector());
							bm2.setValueAt(bookid, i, 0);
							bm2.setValueAt(bookname, i, 1);
							bm2.setValueAt(bookpress, i, 2);
							bm2.setValueAt(pressdate, i, 3);
							bm2.setValueAt(bookauthor, i, 4);
							bm2.setValueAt(bookstarttime, i, 5);
							i++;
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(arg0.getSource()==deleteowebook){   //用户图书列表中删除已归还的图书
			if(rows4==null){
				JOptionPane.showMessageDialog(this,
		         "请选择要删除的图书！");
			}else{
				try {
					for(int i=0;i<rows4.length;i++){
						int bookid=Integer.parseInt(String.valueOf(bm2.getValueAt(rows4[i], 0)));
						String sql1="delete from borrowbooks where bookid="+bookid+";";
						stmt.executeUpdate(sql1);
						bm2.removeRow(rows4[i]);
						JOptionPane.showMessageDialog(this,
				         "书籍删除成功！");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(arg0.getSource()==deletebook){   //删除图书
			if(rows1==null){
				JOptionPane.showMessageDialog(this,
		         "请选择要删除的图书！");
			}else{
				try {
					for(int i=0;i<rows1.length;i++){
						int bookcount=0;
						int bookincount=0;
						int bookid=Integer.parseInt(String.valueOf(bm1.getValueAt(rows1[i], 0)));
						String sql="select bookcount,bookincount from books where bookid="+bookid+";";
						rs = stmt.executeQuery(sql) ;
						if(rs.next()){
							bookcount=rs.getInt("bookcount");
							bookincount=rs.getInt("bookincount");
						}
						if(bookcount==bookincount){
							String sql1="delete from books where bookid="+bookid+";";
							stmt.executeUpdate(sql1);
							bm1.removeRow(rows1[i]);
							JOptionPane.showMessageDialog(this,
					         "书籍删除成功！");
						}else{
							JOptionPane.showMessageDialog(this,
					         "图书未全部归还，不能删除");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(arg0.getSource()==insertbook){   //插入图书
			bm1.addRow(new Vector());
			int previousbookid=Integer.parseInt(String.valueOf(bm1.getValueAt(bm1.getRowCount()-2, 0)));
			int bookid=previousbookid+1;
			bm1.setValueAt(bookid, bm1.getRowCount()-1, 0);
			String sql="insert into books(bookid) values("+bookid+");";
			try {
				stmt.executeUpdate(sql) ;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(arg0.getSource()==deleteuser){   //删除用户
			if(rows2==null){
				JOptionPane.showMessageDialog(this,
		         "请选择要删除的用户！");
			}else{
				try {
					for(int i=0;i<rows2.length;i++){
						int userid=Integer.parseInt(String.valueOf(um1.getValueAt(rows2[i], 0)));
						String sql="select * from borrowbooks where userid="+userid+";";
						rs = stmt.executeQuery(sql) ;
						if(rs.next()){
							JOptionPane.showMessageDialog(this,
					         "该用户有书未还，不能删除！");
						}else{
							String sql1="delete from users where userid="+userid+";";
							stmt.executeUpdate(sql1);
							um1.removeRow(rows2[i]);
							JOptionPane.showMessageDialog(this,
					         "用户删除成功！");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		int row = arg0.getFirstRow();//从0开始累加
        int column = arg0.getColumn(); //它是从-1开始累加的，我也不知为啥
        final String DBURL = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf-8&useSSL=false" ;//MySQL数据库的连接用户名
		final String DBUSER = "root";// MySQL数据库的连接密码
		final String DBPASS = "root";
		Connection conn = null;
		Statement stmt=null;
		ResultSet rs = null ;		// 保存查询结果
		try {
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			stmt = conn.createStatement() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(column>=0&&rows1!=null&&arg0.getSource()==bm1){    //全部图书表格的修改事件处理,rows!=null用来排除初始化
			if(column==1){   //表明修改的是书名
				int bookid=Integer.parseInt(String.valueOf(bm1.getValueAt(rows1[0], 0)));
				String bookname=String.valueOf(bm1.getValueAt(row, column));
				String sql1="update books set bookname='"+bookname+"' where bookid="+bookid+";";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "书名信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==2){  //表明修改的是出版社的信息
				int bookid=Integer.parseInt(String.valueOf(bm1.getValueAt(rows1[0], 0)));
				String bookpress=String.valueOf(bm1.getValueAt(row, column));
				String sql1="update books set bookpress='"+bookpress+"' where bookid="+bookid+";";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "出版社信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==3){  //表明修改的是出版日期信息
				int bookid=Integer.parseInt(String.valueOf(bm1.getValueAt(rows1[0], 0)));
				String pressdate=String.valueOf(bm1.getValueAt(row, column));
				String sql1="update books set pressdate='"+pressdate+"' where bookid="+bookid+";";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "出版日期信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==4){  //表明修改的是作者信息
				int bookid=Integer.parseInt(String.valueOf(bm1.getValueAt(rows1[0], 0)));
				String bookauthor=String.valueOf(bm1.getValueAt(row, column));
				String sql1="update books set bookauthor='"+bookauthor+"' where bookid="+bookid+";";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "图书作者信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==5){  //表明修改的是书的总数
				int bookid=Integer.parseInt(String.valueOf(bm1.getValueAt(rows1[0], 0)));
				String bookcount=String.valueOf(bm1.getValueAt(row, column));
				String sql1="update books set bookcount="+bookcount+" where bookid="+bookid+";";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "图书总数信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==6){  //表明修改的是书的在馆总数
				int bookid=Integer.parseInt(String.valueOf(bm1.getValueAt(rows1[0], 0)));
				String bookincount=String.valueOf(bm1.getValueAt(row, column));
				String sql1="update books set bookincount="+bookincount+" where bookid="+bookid+";";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "图书总数信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(column>=0&&rows2!=null&&arg0.getSource()==um1){
			if(column==1){   //表明修改的是读者姓名
				int userid=Integer.parseInt(String.valueOf(um1.getValueAt(rows2[0], 0)));
				String username=String.valueOf(um1.getValueAt(row,column));
				String sql1="update users set username='"+username+"' where userid="+userid+"";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "读者姓名信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==2){   //表明修改的是读者性别
				int userid=Integer.parseInt(String.valueOf(um1.getValueAt(rows2[0], 0)));
				String usersex=String.valueOf(um1.getValueAt(row,column));
				String sql1="update users set usersex='"+usersex+"' where userid="+userid+"";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "读者性别信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==3){   //表明修改的是读者学号
				int userid=Integer.parseInt(String.valueOf(um1.getValueAt(rows2[0], 0)));
				int userstudentid=Integer.valueOf(String.valueOf(um1.getValueAt(row,column)));
				String sql1="update users set userstudentid="+userstudentid+" where userid="+userid+"";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "读者学号信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==4){   //表明修改的是读者学院
				int userid=Integer.parseInt(String.valueOf(um1.getValueAt(rows2[0], 0)));
				String usercollege=String.valueOf(um1.getValueAt(row,column));
				String sql1="update users set usercollege='"+usercollege+"' where userid="+userid+"";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "读者学院信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==5){   //表明修改的是读者密码
				int userid=Integer.parseInt(String.valueOf(um1.getValueAt(rows2[0], 0)));
				String userpassword=String.valueOf(um1.getValueAt(row,column));
				String sql1="update users set userpassword='"+userpassword+"' where userid="+userid+"";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "读者密码信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==6){   //表明修改的是读者邮箱
				int userid=Integer.parseInt(String.valueOf(um1.getValueAt(rows2[0], 0)));
				String usermail=String.valueOf(um1.getValueAt(row,column));
				String sql1="update users set usermail='"+usermail+"' where userid="+userid+"";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "读者邮箱信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(column==7){   //表明修改的是读者是否借书
				int userid=Integer.parseInt(String.valueOf(um1.getValueAt(rows2[0], 0)));
				String owe=String.valueOf(um1.getValueAt(row,column));
				String sql1="update users set owe='"+owe+"' where userid="+userid+"";
				try {
					stmt.executeUpdate(sql1);
					JOptionPane.showMessageDialog(this,
			         "读者借书信息修改成功！");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		this.rows1=mytab1.getSelectedRows();
		this.rows2=mytab2.getSelectedRows();
		this.rows3=mytab3.getSelectedRows();
		this.rows4=mytab4.getSelectedRows();
	}
}
