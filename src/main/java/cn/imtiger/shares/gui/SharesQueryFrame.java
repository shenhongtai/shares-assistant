package cn.imtiger.shares.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.imtiger.shares.service.SharesService;

public class SharesQueryFrame extends JFrame {

	private static final long serialVersionUID = -8665200650944069759L;
	JFrame frame;
    JTextField textField = new JTextField("sh000001", 8);
	JDialog dialog = new JDialog();
    JLabel szzzVal = new JLabel();
    JLabel szczVal = new JLabel();
    JLabel cybzVal = new JLabel();
    JLabel hs300Val = new JLabel();
    JLabel szzzPer = new JLabel();
    JLabel szczPer = new JLabel();
    JLabel cybzPer = new JLabel();
    JLabel hs300Per = new JLabel();
    
	public SharesQueryFrame() {
        // 创建及设置窗口
		frame = this;
		this.setTitle("盯盘助手");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(305, 200));
		this.setLocationRelativeTo(null);
		this.setResizable(false);

        // 添加面板
        JPanel queryPanel = new JPanel(null);
        queryPanel.setVisible(true);
        this.getContentPane().add(queryPanel);
        
        // 添加标签
        JLabel label = new JLabel("股票代码：");
        label.setBounds(6, 6, 90, 28);
        label.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(label);
        
        // 添加输入框
        textField.setFont(new Font("黑体", Font.BOLD, 16));
        textField.setBounds(96, 6, 140, 28);
        queryPanel.add(textField);
        
        // 添加按钮
    	JButton button = new JButton("查询");
    	button.setFont(new Font("黑体", Font.PLAIN, 14));
        button.setBounds(243, 8, 48, 24);
        this.getRootPane().setDefaultButton(button);
        button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = textField.getText();
				if (code == null || "".equals(code)) {
					showDialog("股票代码不能为空，请检查");
					return;
				}
				String[] arr = SharesService.sharesQuery(code);
				if (arr == null) {
					showDialog("股票代码不正确，请检查");
					return;
				}
				new SharesViewFrame(frame, code, arr[0]);
				frame.setVisible(false);
			}
		});
        queryPanel.add(button);
        
        // 上证综指 sh000001
        JLabel szzz = new JLabel("上证综指");
        szzz.setBounds(6, 45, 89, 28);
        szzz.setForeground(Color.LIGHT_GRAY);
        szzz.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(szzz);
        szzzVal.setBounds(95, 45, 115, 28);
        szzzVal.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(szzzVal);
        szzzPer.setBounds(210, 45, 90, 28);
        szzzPer.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(szzzPer);
        
        // 深证成指 sz399001
        JLabel szcz = new JLabel("深证成指");
        szcz.setBounds(6, 75, 89, 28);
        szcz.setForeground(Color.LIGHT_GRAY);
        szcz.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(szcz);
        szczVal.setBounds(95, 75, 115, 28);
        szczVal.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(szczVal);
        szczPer.setBounds(210, 75, 90, 28);
        szczPer.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(szczPer);
        
        // 创业板指 sz399006
        JLabel cybz = new JLabel("创业板指");
        cybz.setBounds(6, 105, 89, 28);
        cybz.setForeground(Color.LIGHT_GRAY);
        cybz.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(cybz);
        cybzVal.setBounds(95, 105, 115, 28);
        cybzVal.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(cybzVal);
        cybzPer.setBounds(210, 105, 90, 28);
        cybzPer.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(cybzPer);
        
        // 沪深300 sz399300
        JLabel hs300 = new JLabel("沪深300");
        hs300.setBounds(6, 135, 89, 28);
        hs300.setForeground(Color.LIGHT_GRAY);
        hs300.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(hs300);
        hs300Val.setBounds(95, 135, 115, 28);
        hs300Val.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(hs300Val);
        hs300Per.setBounds(210, 135, 90, 28);
        hs300Per.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(hs300Per);
        
        // 刷新股指信息
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Thread thread = new Thread("SharesRefresh") {
			@Override
			public void run() {
				fillIndex(szzzVal, szzzPer, SharesService.sharesQuery("sh000001"));
				fillIndex(szczVal, szczPer, SharesService.sharesQuery("sz399001"));
				fillIndex(cybzVal, cybzPer, SharesService.sharesQuery("sz399006"));
				fillIndex(hs300Val, hs300Per, SharesService.sharesQuery("sz399300"));
			}
        };
        service.scheduleWithFixedDelay(thread, 0, 2, TimeUnit.SECONDS);
        
        // 显示窗口
        this.pack();
        this.setVisible(true);
    }
	
	private void fillIndex(JLabel label, JLabel perLabel, String[] data) {
		if (data != null && data.length == 33) {
			// 涨跌幅
			DecimalFormat df = new DecimalFormat("#0.00");
			Double ups = Double.parseDouble(data[3]) / Double.parseDouble(data[2]) * 100 - 100;
			String upsstr = df.format(ups) + "%";
			
			label.setText(data[3]);
			label.setForeground(ups > 0 ? Color.RED : (ups == 0 ? Color.LIGHT_GRAY : new Color(0, 188, 0)));
			perLabel.setText((ups > 0 ? "▲" : (ups == 0 ? "—" : "▼")) + " " + (ups < 0 ? upsstr.substring(1) : upsstr));
			perLabel.setForeground(ups > 0 ? Color.RED : (ups == 0 ? Color.LIGHT_GRAY : new Color(0, 188, 0)));
		} else {
			label.setText("暂无数据");
			label.setForeground(Color.WHITE);
		}
	}
	
	private void showDialog(String text) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel msgLabel = new JLabel();
		msgLabel.setText(text);
		JButton button = new JButton("确定");
    	button.setFont(new Font("黑体", Font.PLAIN, 14));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		panel.add(msgLabel);
		panel.add(button);
		dialog.getContentPane().add(panel);
		dialog.setMinimumSize(new Dimension(200, 90));
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
	}
	
}
