package cn.imtiger.shares.console;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.imtiger.shares.service.SharesService;

/**
 * 股票查询（控制台方式）
 * @author shen_hongtai
 * @date 2020-8-20
 */
public class SharesQueryConsole {
	
	public static void run() {
		System.out.println("请输入股票代码（沪股以sh开头，深股以sz开头，如sh000001）：");
		Scanner scan = new Scanner(System.in);
		String code = scan.nextLine();
		while (true) {
			if (code == null || "".equals(code)) {
				System.out.println("股票代码不能为空，请重新输入：");
				code = scan.nextLine();
			} else {
				if (SharesService.sharesQuery(code) != null) {
					break;
				}
				System.out.println("查询失败，请重新输入：");
				code = scan.nextLine();
			}
		}
		scan.close();

		final String checkedCode = code;
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		Thread thread = new Thread("SharesRefresh") {
			@Override
			public void run() {
				String[] arr = SharesService.sharesQuery(checkedCode);
				if (arr != null) {
					Double ups = Double.parseDouble(arr[3]) / Double.parseDouble(arr[2]) * 100 - 100;
					DecimalFormat df = new DecimalFormat("#0.00");
					String upstr = df.format(ups);
					Double ws = Double.parseDouble(arr[8]) / 1000000;
					String wsstr = df.format(ws);
					System.out.println();
					System.out.println(arr[0] + " " + arr[3] + " " + (ups > 0 ? "▲" : (ups == 0 ? "" : "▼")) + " " + upstr + "% " );
					System.out.println("今开 " + arr[1] + " 成交 " + wsstr + "万手");
					System.out.println("最高 " + arr[4] + " 最低 " + arr[5]);
					System.out.println(arr[30] + " " + arr[31]);
				}
			}
		};
		service.scheduleWithFixedDelay(thread, 1, 2, TimeUnit.SECONDS);
	}
	
}
