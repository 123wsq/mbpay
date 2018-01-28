package com.tangdi.production.mpbase.constants;

/**
 * 终端 操作记录 常量配置区<br>
 * <br>
 * 
 * 0 = "操作员:{} 入库了终端"; <br>
 * 1 = "操作员:{} 终端下拨给代理商:{}"; <br>
 * 2 = "商户:{}绑定了终端"; <br>
 * 3 = "操作员:{} 解绑了商户:{}"; <br>
 * 4 = "操作员:{} 解绑了代理商:{},终端回到了代理商:{}"; <br>
 * 5 = "操作员:{} 解绑了代理商:{},终端回到了运营公司";<br>
 * 
 * @author limiao
 *
 */
public class TermStepDesc {
	// 操作 步骤 (0 入库，1 出库，2 绑定，3 解绑商户，4 解绑代理商)

	/**
	 * 0 入库---- 操作员:{} 入库了终端
	 */
	public static final int STEP_0 = 0;
	/**
	 * 1 出库(下拨)---- 操作员:{} 终端下拨给代理商:{}
	 */
	public static final int STEP_1 = 1;
	/**
	 * 2 绑定(商户绑定)----商户:{} 绑定了终端
	 */
	public static final int STEP_2 = 2;
	/**
	 * 3 解绑商户----操作员:{} 解绑了商户:{}
	 */
	public static final int STEP_3 = 3;
	/**
	 * 4 解绑代理商<br>
	 * 操作员:{} 解绑了代理商:{},终端回到了代理商:{}<br>
	 * 操作员:{} 解绑了代理商:{},终端回到了运营平台
	 */
	public static final int STEP_4 = 4;

	static final String STEP_DESC_0 = "操作员:{} 入库了终端";
	static final String STEP_DESC_1 = "操作员:{} 终端下拨给代理商:{}";
	static final String STEP_DESC_2 = "商户:{} 绑定了终端";
	static final String STEP_DESC_3 = "操作员:{} 解绑了商户:{}";
	static final String STEP_DESC_4 = "操作员:{} 解绑了代理商:{},终端回到了代理商:{}";
	static final String STEP_DESC_41 = "操作员:{} 解绑了代理商:{},终端回到了运营平台";

	private static String getLogMsg(String msg, String... args) {
		if (args != null && args.length > 0) {
			for (String value : args) {
				msg = msg.replaceFirst("\\{}", value);
			}
		}
		return msg;
	}

	public static String getTermStepDesc(int step, String... agrs) {
		String msg = "";
		switch (step) {
		case STEP_0:
			msg = getLogMsg(STEP_DESC_0, agrs);
			break;
		case STEP_1:
			msg = getLogMsg(STEP_DESC_1, agrs);
			break;
		case STEP_2:
			msg = getLogMsg(STEP_DESC_2, agrs);
			break;
		case STEP_3:
			msg = getLogMsg(STEP_DESC_3, agrs);
			break;
		case STEP_4:
			msg = getLogMsg(agrs.length == 2 ? STEP_DESC_41 : STEP_DESC_4, agrs);
			break;
		default:
			msg = "终端不知道怎么了 :-)";
			break;
		}
		return msg;
	}
}
