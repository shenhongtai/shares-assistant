package cn.imtiger.shares.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.imtiger.shares.service.SharesService;

public class SharesViewFrame extends JFrame {

	private static final long serialVersionUID = -9028824668424120944L;
	JFrame frame;
	JFrame queryFrame;
	String shareCode;
    JLabel now = new JLabel("0000.000");
    JLabel change = new JLabel("+000.000");
    JLabel percent = new JLabel("+00.00%");
    JLabel today = new JLabel("今开价 0000.000");
    JLabel yesterday = new JLabel("昨收价 0000.000");
    JLabel max = new JLabel("最高价 0000.000");
    JLabel min = new JLabel("最低价 0000.000");
    JLabel trade = new JLabel("交易量 000.00万手");
    JLabel tradePercent = new JLabel("成交额 000.00亿元");
    JLabel time = new JLabel("0000-00-00 00:00:00");

	public SharesViewFrame(JFrame sourceFrame, String code, String name) {
		frame = this;
		shareCode = code;
		queryFrame = sourceFrame;
		
        // 创建及设置窗口
	    this.setTitle(name + " (" + code + ")");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(305, 200));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setAlwaysOnTop(true);

        // 添加面板
        JPanel panel = new JPanel(null);
        panel.setVisible(true);
        this.getContentPane().add(panel);
        
        // 当前价
        now.setFont(new Font("Arial", Font.BOLD, 40));
        now.setForeground(Color.RED);
        now.setBounds(6, 6, 184, 48);
        panel.add(now);
        // 涨跌金额
        change.setFont(new Font("Arial", Font.BOLD, 14));
        change.setForeground(Color.RED);
        change.setBounds(200, 10, 100, 20);
        panel.add(change);
        // 涨跌幅
        percent.setFont(new Font("Arial", Font.BOLD, 14));
        percent.setForeground(Color.RED);
        percent.setBounds(200, 30, 100, 20);
        panel.add(percent);
        // 今日开盘价
        today.setFont(new Font("黑体", Font.BOLD, 14));
        today.setBounds(6, 58, 150, 20);
        panel.add(today);
        // 昨日收盘价
        yesterday.setFont(new Font("黑体", Font.BOLD, 14));
        yesterday.setBounds(156, 58, 150, 20);
        panel.add(yesterday);
        // 最高价
        max.setFont(new Font("黑体", Font.BOLD, 14));
        max.setBounds(6, 86, 150, 20);
        panel.add(max);
        // 最低价
        min.setFont(new Font("黑体", Font.BOLD, 14));
        min.setBounds(156, 86, 150, 20);
        panel.add(min);
        // 交易量
        trade.setFont(new Font("黑体", Font.BOLD, 14));
        trade.setBounds(6, 114, 150, 20);
        panel.add(trade);
        // 换手率
        tradePercent.setFont(new Font("黑体", Font.BOLD, 14));
        tradePercent.setBounds(156, 114, 150, 20);
        panel.add(tradePercent);
        // 更新时间
        time.setFont(new Font("黑体", Font.BOLD, 14));
        time.setBounds(6, 142, 300, 20);
        panel.add(time);
        
        // 返回按钮
        JButton button = new JButton("返回");
    	button.setFont(new Font("黑体", Font.PLAIN, 14));
        button.setBounds(240, 142, 48, 24);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				queryFrame.setVisible(true);
			}
		});
		panel.add(button);
        
        // 更新数据
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Thread thread = new Thread("SharesRefresh") {
			@Override
			public void run() {
				String[] data = SharesService.sharesQuery(shareCode);
				if (data != null) {
					refresh(data);
				}
			}
        };
        service.scheduleWithFixedDelay(thread, 0, 2, TimeUnit.SECONDS);
        
        // 显示窗口
        this.pack();
        this.setVisible(true);
    }
	
	private void refresh(String[] data) {
		DecimalFormat df = new DecimalFormat("#0.00");
		// 涨跌额
		Double up = Double.parseDouble(data[3]) - Double.parseDouble(data[2]);
		String upstr = df.format(up);
		// 涨跌幅
		Double ups = Double.parseDouble(data[3]) / Double.parseDouble(data[2]) * 100 - 100;
		String upsstr = df.format(ups);
		// 成交（万手）
		Double ws = Double.parseDouble(data[8]) / 1000000;
		String wsstr = df.format(ws);
		// 成交额（亿元）
		Double je = Double.parseDouble(data[9]) / 100000000;
		String jestr = df.format(je);
		
		today.setText("今开价 " + data[1]);
		yesterday.setText("昨收价 " + data[2]);
		now.setText(data[3]);
        now.setBounds(6, 6, data[3].length() * 23, 48);
        now.setForeground(up > 0 ? Color.RED : (up == 0 ? Color.LIGHT_GRAY : new Color(0, 188, 0)));
		change.setText((up > 0 ? "+" : "") + upstr);
		change.setBounds(data[3].length() * 23, 10, 100, 20);
		change.setForeground(up > 0 ? Color.RED : (up == 0 ? Color.LIGHT_GRAY : new Color(0, 188, 0)));
		percent.setText((up > 0 ? "+" : "") + upsstr + "%");
        percent.setBounds(data[3].length() * 23, 30, 100, 20);
        percent.setForeground(up > 0 ? Color.RED : (up == 0 ? Color.LIGHT_GRAY : new Color(0, 188, 0)));
        max.setText("最高价 " + data[4]);
        min.setText("最低价 " + data[5]);
        trade.setText("成交量 " + wsstr + "万手");
        tradePercent.setText("成交额 " + jestr + "亿元");
        time.setText(data[30] + " " + data[31]);
	}
	
}
