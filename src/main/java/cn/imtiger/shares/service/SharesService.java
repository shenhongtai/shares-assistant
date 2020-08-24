package cn.imtiger.shares.service;

import cn.imtiger.shares.util.RestUtil;

/**
 * 新浪股票实时接口
 * @author shen_hongtai
 * @date 2020-8-20
 */
public class SharesService {
	
	/**
	 * 根据股票代码查询实时信息（不加前缀默认为沪股）
	 * @param code
	 * @return String[33]
		0 股票名称
		1 今日开盘价
		2 昨日收盘价
		3 当前价格
		4 今日最高价
		5 今日最低价
		6 竞买价
		7 竞卖价
		8 成交股数
		9 成交金额
		10 买1手
		11 买1报价
		12 买2手
		13 买2报价
		14 买3手
		15 买3报价
		16 买4手
		17 买4报价
		18 买5手
		19 买5报价
		20 卖1手
		21 卖1报价
		22 卖2手
		23 卖2报价
		24 卖3手
		25 卖3报价
		26 卖4手
		27 卖4报价
		28 卖5手
		29 卖5报价
		30 日期
		31 时间
	 */
	public static String[] sharesQuery(String code) {
		if (!code.startsWith("sh") && !code.startsWith("sz")) {
			code = "sh" + code;
		}
		String resp = RestUtil.get("http://hq.sinajs.cn/list=" + code);
		if (resp != null && !"".equals(resp)) {
			String subResp = resp.substring(resp.indexOf("\"") + 1, resp.lastIndexOf("\""));
			String[] arr = subResp.split(",");
			if (!"".equals(subResp) && arr.length == 33) {
				return arr;
			}
		}
		return null;
	}
	
}
