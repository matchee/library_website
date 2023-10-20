package com.bookclient.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bookclient.control.ConnectServer;
import com.common.Message;

public class ClientRegister extends JFrame implements ActionListener{
	private JLabel background;
	private ImageIcon backpicture;
	private JPanel p;
	private JPanel pmain;
	private JPanel pcenter;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private JPanel p5;
	private JPanel p6;
	private JPanel p7;
	private JLabel name;
	private JLabel sex;
	private JLabel college;
	private JLabel classnum;
	private JLabel password;
	private JLabel mail;
	private JTextField nametf;
	private ButtonGroup sexchooser;
	private JRadioButton man;
	private JRadioButton woman;
	private JTextField collegetf;
	private JTextField studentidtf;
	private JPasswordField passwordtf;
	private JTextField mailtf;
	private JButton registerbutton;
	private JButton cancelbutton;
	public ClientRegister(){
		super("欢迎注册重庆大学数字图书馆：");
		backpicture=new ImageIcon("./images/ClientRegister.jpg");
		background=new JLabel(backpicture);
		background.setBounds(0, 0, backpicture.getIconWidth(), backpicture.getIconHeight());
		registerbutton=new JButton("注册");
		cancelbutton=new JButton("取消");
		registerbutton.addActionListener(this);
		cancelbutton.addActionListener(this);
		name=new JLabel("姓名：");
		sex=new JLabel("性别：");
		college=new JLabel("学院：");
		classnum=new JLabel("学号：");
		password=new JLabel("密码：");
		mail=new JLabel("邮箱：");
		nametf=new JTextField(10);
		sexchooser=new ButtonGroup();
		man=new JRadioButton("男",true);
		woman=new JRadioButton("女",false);
		sexchooser.add(man);
		sexchooser.add(woman);
		collegetf=new JTextField(10);
		studentidtf=new JTextField(10);
		passwordtf=new JPasswordField(10);
		mailtf=new JTextField(10);
		p=(JPanel)this.getContentPane();
		pmain=new JPanel();
		pcenter=new JPanel();
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p4=new JPanel();
		p5=new JPanel();
		p6=new JPanel();
		p7=new JPanel();
		p.setOpaque(false);
		pmain.setOpaque(false);
		pcenter.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p5.setOpaque(false);
		p6.setOpaque(false);
		p7.setOpaque(false);
		man.setOpaque(false);
		woman.setOpaque(false);
		p.setLayout(null);
		pmain.setLayout(new BorderLayout());
		pmain.setBounds(80,50,400,200);
		pcenter.setLayout(new GridLayout(3,2));
		p1.add(name);
		p1.add(nametf);
		p2.add(sex);
		p2.add(man);
		p2.add(woman);
		p3.add(college);
		p3.add(collegetf);
		p4.add(classnum);
		p4.add(studentidtf);
		p5.add(password);
		p5.add(passwordtf);
		p6.add(mail);
		p6.add(mailtf);
		p7.add(registerbutton);
		p7.add(cancelbutton);
		pcenter.add(p1);
		pcenter.add(p2);
		pcenter.add(p3);
		pcenter.add(p4);
		pcenter.add(p5);
		pcenter.add(p6);
		pmain.add(pcenter,BorderLayout.CENTER);
		pmain.add(p7,BorderLayout.SOUTH);
		p.add(pmain);
		pcenter.setBorder(BorderFactory.createTitledBorder("请填写注册信息："));
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
		this.setBounds(300,150,backpicture.getIconWidth(),backpicture.getIconHeight());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==registerbutton){
			if(nametf.getText().equals("")){
				JOptionPane.showMessageDialog(this, "请输入您的姓名！",
						"输入有误！",JOptionPane.WARNING_MESSAGE);  
				}
			else if(collegetf.getText().equals("")){
				JOptionPane.showMessageDialog(this, "请输入您的学院！",
						"输入有误！",JOptionPane.WARNING_MESSAGE);  
				}
			else if(studentidtf.getText().equals("")){
				JOptionPane.showMessageDialog(this, "请输入您的学号！",
						"输入有误！",JOptionPane.WARNING_MESSAGE);  
				}
			else if(String.valueOf(passwordtf.getPassword()).trim().equals("")){
				JOptionPane.showMessageDialog(this, "请输入您的注册密码！",
						"输入有误！",JOptionPane.WARNING_MESSAGE);  
				}
			else if(mailtf.getText().equals("")){
				JOptionPane.showMessageDialog(this, "请输入您的邮箱！",
						"输入有误！",JOptionPane.WARNING_MESSAGE);  
				}
			else{      //开始注册
				Message mes=new Message();
				mes.setFlag(1);            //标志1表明是注册
				mes.setUsername(nametf.getText().trim());
				if(man.isSelected()){
					mes.setUsersex("男");
				}else{
					mes.setUsersex("女");
				}
				mes.setUsercollege(collegetf.getText().trim());
				mes.setUserstudentid(Integer.parseInt(studentidtf.getText().trim()));
				mes.setUserpassword(String.valueOf(passwordtf.getPassword()));
				mes.setUsermail(mailtf.getText().trim());
				ConnectServer cs=new ConnectServer(mes);
				cs.sendMessage();
				Message mes1=cs.getMessage();
				int uid=mes1.getUserid();
				JOptionPane.showMessageDialog(this,
		         "注册成功，您的帐号是： "+uid+"请记住您的帐号！");
				this.dispose();//关闭窗体
			}
		}
		if(arg0.getSource()==cancelbutton){
			this.dispose();//关闭窗体
		}
	}
}
