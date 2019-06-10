package cns.sdut.bank.view;
import java.awt.*;
import java.net.URL;

import javax.accessibility.Accessible;
import javax.swing.*;

import cn.sdut.bank.dao.caozuo;
import cn.sdut.bank.po.User;
import cn.sdut.bank.service.file_io;
import cn.sdut.bank.tools.Finddate;


import java.awt.Dimension;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;


public class CreatAccount extends JFrame implements ActionListener
{
	static JButton login, exit;
	static JLabel name, mon;
	static JTextField nametext, montext;
	static String Name, Mon;
	  
	public CreatAccount()
	{
		
		this.setTitle("开户");		
		JPanel jpanel = new JPanel();
		this.setContentPane(jpanel);
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 670) / 2;
		int y = (int)(height - 540) / 2;
		this.setBounds(x, y, 670, 540);
		
		//添加标签组件
		GridLayout gird = new GridLayout(5,0);
		JPanel jpanel1 = new JPanel();
		JPanel jpanel2 = new JPanel();
		JPanel jpanel3 = new JPanel();
		JPanel jpanel4 = new JPanel();
		JPanel jpanel5 = new JPanel();
		
		jpanel.setLayout(gird);
		jpanel.add(jpanel1);
		jpanel.add(jpanel2);
		jpanel.add(jpanel3);
		jpanel.add(jpanel4);
		jpanel.add(jpanel5);
		
		ImageIcon img = new ImageIcon("image/3.jpg");
		 JLabel imgLabel = new JLabel(img);//将背景图放在标签里。
		
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		
		name = new JLabel("开户姓名:      ");
		nametext = new JTextField(10);
		
		mon = new JLabel("开户金额:      ");
		montext = new JTextField(10);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//String user_name = nametext.getText();
		//String user_mon = montext.getText();
		
		login = new JButton("确认开户");
		JLabel Black_JLabel = new JLabel("      ");
		exit = new JButton("   退出   ");
		
		login.addActionListener(new actionTest() {
			public void actionPerformed(ActionEvent e)
			   {
			   		  //试验
			   		  
			   		  String user_name = null;
			   		  user_name = nametext.getText();
			   		  String user_mon = null;
			   		  user_mon = montext.getText();
	   				  caozuo cz = new caozuo();
	   				  Finddate f = new Finddate();
			   		  if(user_name.equals("") | user_name.length() > 8)
			   		  {
			   			  new Warning(0);
			   		  }
			   		  else if(user_mon.equals("")) 
		   			  {
		   				  new Warning(1);
		   			  } 
			   		  else 
			   		  {
					   		  int flag = 0;
		   					  for(int i = 0;i <= user_mon.length() - 1;i++)//判断是否输入的取款金额都是数字
		   					  {
		   						  if((user_mon.charAt(i) >= '0' && user_mon.charAt(i) <= '9') || user_mon.charAt(i) == '.') 
		   						  {
		   							  continue;
		   						  }
		   						  else
		   						  {
		   							  flag = 1;
		   							  break;
		   						  }
		   					  }
		   					  if(flag == 1)//有非数字的输入
		   					  {
		   						new Warning(2);
		   					  }
		   					  else//输入的金额合法
		   					  {   
		   						  file_io file = new file_io();
				   				  String path = "userinfo\\";
				   				  String next_id = file.nextId(path);
						   		  cz.insert(new User(user_name, user_mon, next_id));
						   		  String date = f.getdate(next_id);
				   				  new sun(user_name, user_mon, next_id, date);
		   					  }  
			   		  }
			   	}
		});
		
		exit.addActionListener(this);
		
		
		jpanel2.add(name);
		jpanel2.add(nametext);
		jpanel3.add(mon);
		jpanel3.add(montext);
		jpanel4.add(login);
		jpanel4.add(Black_JLabel);
		jpanel4.add(exit);
		
		this.getLayeredPane().setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		jpanel.setOpaque(false);
		jpanel1.setOpaque(false);
		jpanel2.setOpaque(false);
		jpanel3.setOpaque(false);
		jpanel4.setOpaque(false);
		jpanel5.setOpaque(false);
		this.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e)
	{	   	 
		this.setVisible(false);
		new Home();
	}
}

class sun extends JFrame implements ActionListener
{
	JLabel aa, bb, cc, dd, ee, ff;
	JButton ok;
	
	private ImageJPanel ip;
	
	
	sun(String Name, String Mon, String next_id, String date)
	{
		
		ip = new ImageJPanel();
		
		
		this.setTitle("开户");
		
		//JPanel jpanel = new JPanel();
		//this.setContentPane(jpanel);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(this);
		Container c = this.getContentPane();
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 670) / 2;
		int y = (int)(height - 540) / 2;
		this.setBounds(x, y, 670, 540);
		
		//添加标签组件
		c.setLayout(null);
		
		ok = new JButton("确认");
		
		ok.addActionListener(this);
			
		
		aa = new JLabel("恭喜您，开户成功！");
		bb = new JLabel("您的姓名为： " + Name);
		cc = new JLabel("您的账户余额： " + Mon + "元");
		dd = new JLabel("您的账号为：" + next_id);
		ee = new JLabel("您的初始密码为： 123456");
		ff = new JLabel("开户日期：" + date);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		aa.setBounds(250, 50, 200, 100);
		bb.setBounds(250, 100, 200, 100);
		cc.setBounds(250, 150, 200, 100);
		dd.setBounds(250, 200, 200, 100);
		ee.setBounds(250, 250, 200, 100);
		ff.setBounds(250, 300, 300, 100);
		ok.setBounds(250, 370, 100, 50);
		c.add(aa);
		c.add(bb);
		c.add(cc);
		c.add(dd);
		c.add(ee);
		c.add(ff);
		c.add(ok);
		
		ip.setBounds(0, 0, this.getWidth(), this.getHeight());
		c.add(ip);
		
		
		
		this.getLayeredPane().setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//jpanel.setOpaque(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.setVisible(false);
	}
	
}


class Warning
{
	JLabel T;
	JButton ok;
	Warning(int flag)
	{	
		JFrame warning = new JFrame("提示");
		JPanel jpanel = new JPanel();
		warning.setContentPane(jpanel);
		
		warning.setLayout(null);
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 300) / 2;
		int y = (int)(height - 200) / 2;
		warning.setBounds(x, y, 300, 200);
		
		//点击确定关闭弹窗
		ok = new JButton("确定");
		ok.addActionListener(new actionTest()
		{
			public void actionPerformed(ActionEvent e)
			{
				warning.dispose();
			}
		});
		
		if(flag == 0)
		T = new JLabel("请输入合法姓名！");
		
		else if(flag == 1)
			T = new JLabel("请输入合法金额！");

		else if(flag == 2)
			T = new JLabel("请输入合法金额！");
		//设置标签、按钮在屏幕上的位置及大小
		T.setBounds(100, 10, 200, 100);
		ok.setBounds(110, 100, 60, 30);
		
		warning.add(T);
		warning.add(ok);
		warning.setVisible(true);
	}
}

class actionTest implements ActionListener
{
    public void actionPerformed(ActionEvent e)
   {
   	  
   }  	 
}