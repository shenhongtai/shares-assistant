package cn.imtiger.shares;

import javax.swing.JFrame;
import javax.swing.UIManager;

import cn.imtiger.shares.gui.SharesQueryFrame;

/**
 * Shares Assistant
 * @author shen_hongtai
 * @date 2020-8-20
 */
public class SharesAssistantApplication {
	
	public static void main(String[] args) {
		// 设置主题
		try {
	        JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 启动窗体
		new SharesQueryFrame();
	}
	
}
