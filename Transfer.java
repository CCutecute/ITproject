package cns.sdut.bank.view;


import javax.swing.*;

import cn.sdut.bank.dao.caozuo;
import cn.sdut.bank.tools.Finddate;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transfer extends JFrame implements ActionListener
{
	static JTextField transfer_id = null;
	static JTextField receive_id = null;
	static JTextField transfer_money = null;
	static JPasswordField transfer_pwd = null;
	static JButton button_sure = null;
	static JButton button_cancel = null;
	static String trans_yuan;
	
	public Transfer()
	{
		
		this.setTitle("转账");	

		Container c = getContentPane();

		// 设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int) (width - 670) / 2;
		int y = (int) (height - 540) / 2;
		setBounds(x, y, 670, 540);

		JPanel jpanel = new JPanel();
		setContentPane(jpanel);
		GridLayout grid = new GridLayout(7, 0);
		JPanel jpanel1 = new JPanel();
		JPanel jpanel2 = new JPanel();
		JPanel jpanel3 = new JPanel();
		JPanel jpanel4 = new JPanel();
		JPanel jpanel5 = new JPanel();
		JPanel jpanel6 = new JPanel();
		JPanel jpanel7 = new JPanel();
		// JPanel jpanel8 = new JPanel();
		/// JPanel jpanel9 = new JPanel();
		// JPanel jpanel10 = new JPanel();

		jpanel.setLayout(grid);
		jpanel.add(jpanel1);
		jpanel.add(jpanel2);
		jpanel.add(jpanel3);
		jpanel.add(jpanel4);
		jpanel.add(jpanel5);
		jpanel.add(jpanel6);
		jpanel.add(jpanel7);
		// jpanel.add(jpanel9);
		// jpanel.add(jpanel10);

		ImageIcon img = new ImageIcon("image/3.jpg");
		JLabel imgLabel = new JLabel(img);
		getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		JLabel trid = new JLabel("转出账号:          ");
		transfer_id = new JTextField(10);
		JLabel reid = new JLabel("转入账号:          ");
		receive_id = new JTextField(10);
		JLabel trmo = new JLabel("转出金额:          ");
		transfer_money = new JTextField(10);
		JLabel trpwd = new JLabel("账号密码:         ");
		transfer_pwd = new JPasswordField(10);

		JButton button_sure = new JButton("确认转账");
		button_sure.addActionListener(this);
		JLabel Black_JLabel = new JLabel("       ");
		JButton button_cancel = new JButton("退出");
		button_cancel.addActionListener(this);

		jpanel2.add(trid);
		jpanel2.add(transfer_id);
		jpanel3.add(reid);
		jpanel3.add(receive_id);
		jpanel4.add(trmo);
		jpanel4.add(transfer_money);
		jpanel5.add(trpwd);
		jpanel5.add(transfer_pwd);
		jpanel6.add(button_sure);
		jpanel6.add(Black_JLabel);
		jpanel6.add(button_cancel);

		getLayeredPane().setLayout(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		jpanel.setOpaque(false);
		jpanel1.setOpaque(false);
		jpanel2.setOpaque(false);
		jpanel3.setOpaque(false);
		jpanel4.setOpaque(false);
		jpanel5.setOpaque(false);
		jpanel6.setOpaque(false);
		jpanel7.setOpaque(false);

		setSize(660, 500);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("确认转账")) 
		{
			String strtransfer_id =  transfer_id.getText();
			String strreceive_id = null;
			strreceive_id = receive_id.getText();
			String stryuan = transfer_money.getText();
			String strpwd = new String(transfer_pwd.getPassword());
			caozuo cz = new caozuo();
			Finddate f = new Finddate();
			
			if (strtransfer_id.equals(""))// 判断账号是否为空
			{
				new Warning4(0); 	
			} 
			else if (strreceive_id.equals(""))// 判断账号是否为空
			{
				new Warning4(1); 
			} 
			else if (stryuan.equals("")) 
			{
				new Warning4(2); 
			}
			else if (strpwd.equals("")) 
			{
				new Warning4(3); 
			} 
			else if (f.getCount(strtransfer_id) != 1)
			{
				new Warning4(4);
			} 
			else if (f.getCount(strreceive_id) != 1)
			{
				new Warning4(5);
			} 
			else if (strtransfer_id.compareTo(strreceive_id) == 0) // 判断转入账号和转出账号是否是一致
			{
				new Warning4(6);
			} 	
			else if (f.getPsw(strpwd, strtransfer_id) != 1) // 判断密码是否错误
			{
				new Warning4(7);
			}
			else 
			{
				String mon = f.getMoney(strtransfer_id);
			    BigDecimal a = new BigDecimal(mon);
			    BigDecimal b = new BigDecimal(stryuan);
			    a = a.subtract(b);
			    String panduan = String.valueOf(a);
			    	  
				if(panduan.charAt(0) == '-')  // 判断余额是否充足
				{	
					new Warning4(8); 		
				} 
				else 
				{
					int flag = 0;
						  for(int i = 0;i <= stryuan.length() - 1;i++)//判断是否输入的取款金额都是数字
						  {
							  if((stryuan.charAt(i) >= '0' && stryuan.charAt(i) <= '9') || stryuan.charAt(i) == '.') 
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
							  new Warning4(2);
						  }
						  else//输入的金额合法
						  {   
							  cz.changeMoney(strtransfer_id, strpwd, strreceive_id, stryuan);
							  new son(stryuan, f.getMoney(strtransfer_id));
						  }
				}
			}
		}
		else if (e.getActionCommand().equals("退出")) 
		{
			this.setVisible(false);
			new Home();
		}
	}

}

class son extends JFrame implements ActionListener
{
	JLabel aa, bb, cc, dd;
	JButton ok;
	
	private ImageJPanel ip;
	
	son(String trans_yuan, String allmon)
	{
		ip = new ImageJPanel();
		
		this.setTitle("转账");
		
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
			
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");  
	    String time = sdf.format(date.getTime()); 
		aa = new JLabel("恭喜您，转账成功！");
		bb = new JLabel("此次转账金额为：  "+ trans_yuan + "元");
		cc = new JLabel("您的账户余额为： " +   allmon + "元");
		dd = new JLabel("操作时间： " + time);
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		aa.setBounds(200, 50, 200, 100);
		bb.setBounds(200, 100, 200, 100);
		cc.setBounds(200, 150, 400, 100);
		dd.setBounds(200, 200, 400, 100);
		ok.setBounds(200, 350, 100, 50);
		
		c.add(aa);
		c.add(bb);
		c.add(cc);
		c.add(dd);
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

class Warning4
{
	JLabel T;
	JButton ok;
	Warning4(int flag)
	{	
		JFrame Warning4 = new JFrame("提示");
		JPanel jpanel = new JPanel();
		Warning4.setContentPane(jpanel);
		
		Warning4.setLayout(null);
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 300) / 2;
		int y = (int)(height - 200) / 2;
		Warning4.setBounds(x, y, 300, 200);
		
		//点击确定关闭弹窗
		ok = new JButton("确定");
		ok.addActionListener(new actionTest()
		{
			public void actionPerformed(ActionEvent e)
			{
				Warning4.dispose();
			}
		});
		
		if(flag == 0)
			T = new JLabel("请输入合法转出账号！");	
		else if(flag == 1)
			T = new JLabel("请输入合法转入账号！");
		else if(flag == 2)
			T = new JLabel("请输入合法转账金额！");
		else if(flag == 3)
			T = new JLabel("请输入合法密码！");
		else if(flag == 4)
			T = new JLabel("您的转出账号不存在！");
		else if(flag == 5)
			T = new JLabel("您的转入账号不存在！");
		else if(flag == 6)
			T = new JLabel("转入账号和转出账号一致！");
		else if(flag == 7)
			T = new JLabel("您的密码错误！");
		else if(flag == 8)
			T = new JLabel("您的余额不足！");
		
		
		//设置标签、按钮在屏幕上的位置及大小
		T.setBounds(100, 10, 200, 100);
		ok.setBounds(110, 100, 60, 30);
		
		Warning4.add(T);
		Warning4.add(ok);
		Warning4.setVisible(true);
	}
}
