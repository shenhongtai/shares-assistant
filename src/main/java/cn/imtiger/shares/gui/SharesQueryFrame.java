package cn.imtiger.shares.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        szzz.setBounds(6, 40, 90, 28);
        szzz.setForeground(Color.LIGHT_GRAY);
        szzz.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(szzz);
        
        // 深证成指 sz399001
        JLabel szcz = new JLabel("深证成指");
        szcz.setBounds(6, 70, 90, 28);
        szcz.setForeground(Color.LIGHT_GRAY);
        szcz.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(szcz);
        
        // 创业板指 sz399006
        JLabel cybz = new JLabel("创业板指");
        cybz.setBounds(6, 100, 90, 28);
        cybz.setForeground(Color.LIGHT_GRAY);
        cybz.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(cybz);
        
        // 沪深300 sz399300
        JLabel hs300 = new JLabel("沪深300");
        hs300.setBounds(6, 130, 90, 28);
        hs300.setForeground(Color.LIGHT_GRAY);
        hs300.setFont(new Font("黑体", Font.BOLD, 16));
        queryPanel.add(hs300);
        
        // 显示窗口
        this.pack();
        this.setVisible(true);
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
