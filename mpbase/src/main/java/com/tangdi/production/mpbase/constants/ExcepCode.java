package com.tangdi.production.mpbase.constants;

/**
 * 错误代码定义
 * 
 * @author zhengqiang </br> 90 系统错误码</br> 88 超时错误吗</br>
 *
 *         00 APP 模块</br> 01 登录业务</br> 02 商户业务</br> 03 终端业务（绑定、设备列表、设备状态）</br>
 *         04 验证码业务</br> 05 代理商业务 </br> 06 提现业务 T+0 T+1</br> 07 APP版本</br> 08
 *         交易异常业务（交易失败、风控不通过...）</br> 09 路由业务</br> 10 其他(电子签名...) </br> 11
 *         短信业务</br>
 * 
 *         10 通讯渠道模块</br> 01卡业务</br> 02加密机业务 </br> 03收单渠道业务</br> 04快捷渠道业务</br>
 *         05便民业务</br>
 * 
 *         </br>
 * 
 * 
 */
public class ExcepCode {
	// 系统错误码
	/**
	 * 拦截器不通过
	 */
	public static final String EX999999 = "999999"; //
	/**
	 * 参数校验失败(为空、错值提示)
	 */
	public static final String EX900000 = "900000"; // 字段不能为空

	/**
	 * 系统异常
	 */
	public static final String EX900001 = "900001"; // 系统异常
	/**
	 * 获取配置信息错误
	 */
	public static final String EX900002 = "900002"; //
	/**
	 * 参数校验错误
	 */
	public static final String EX900003 = "900003"; //
	/**
	 * 未定义错误消息
	 */
	public static final String EX900004 = "900004"; //
	/**
	 * 数字签名校验错误
	 */
	public static final String EX900005 = "900005";
	/**
	 * APP客户端连接超时
	 */
	public static final String EX888888 = "888888";
	/**
	 * APP客户端异地登陆
	 */
	public static final String EX888889 = "888889";

	// mpapp业务错误码
	/**
	 * 登陆错误次数超限，商户被冻结
	 */
	public static final String EX000101 = "000101";
	/**
	 * 密码错误!
	 */
	public static final String EX000102 = "000102";
	/**
	 * 登录失败：更新登陆信息异常！
	 */
	public static final String EX000103 = "000103";
	/**
	 * 支付密码错误!
	 */
	public static final String EX000105 = "000105";

	/**
	 * 商户注册失败。
	 */
	public static final String EX000201 = "000201";
	/**
	 * 手机号已被注册！
	 */
	public static final String EX000202 = "000202";

	/**
	 * 商户信息修改失败。
	 */
	public static final String EX000205 = "000205";

	/**
	 * 商户不存在！
	 */
	public static final String EX000206 = "000206"; //
	/**
	 * 无效手机号！
	 */
	public static final String EX000250 = "000250"; //

	/**
	 * 商户被禁用,请联系客服！
	 */
	public static final String EX000251 = "000251"; //
	/**
	 * 登陆密码被冻结,请联系客服！
	 */
	public static final String EX000252 = "000252"; //

	/**
	 * 商户信息查询失败
	 */
	public static final String EX000207 = "000207"; //
	/**
	 * 商户原密码错误
	 */
	public static final String EX000216 = "000216"; //
	/**
	 * 商户账户查询失败。
	 */
	public static final String EX000208 = "000208"; //
	/**
	 * 商户账户查询异常。
	 */
	public static final String EX000212 = "000212"; //
	/**
	 * 1.商户账户历史查询失败 2、商户账户历史更新失败 3、商户账户历史新增失败
	 */
	public static final String EX000213 = "000213"; //
	/**
	 * 1、商户账户历史查询异常 2、商户账户历史更新异常 3、商户账户历史新增异常
	 */
	public static final String EX000214 = "000214"; //
	/**
	 * 商户已被禁用
	 */
	public static final String EX000215 = "000215"; //
	/**
	 * 绑定银行卡信息失败 : 1.失败和 银行卡信息查询异常 2.银行卡信息修改异常 3. 银行卡信息解绑异常
	 */
	public static final String EX000209 = "000209"; //

	/**
	 * 信息已在审核中： 银行卡信息已被编辑更新，请等待审核后修改
	 */
	public static final String EX000210 = "000210"; //
	/**
	 * 卡号不存在
	 */
	public static final String EX000211 = "000211"; //

	/**
	 * 银行卡未绑定或不存在
	 */
	public static final String EX000217 = "000217"; //
	/**
	 * 银行卡已被商户绑定
	 */
	public static final String EX000222 = "000222"; //
	/**
	 * 银行卡类型限定为借记卡
	 */
	public static final String EX000223 = "000223"; //
	/**
	 * 银行卡图片传输失败,请确认网络是否良好。
	 */
	public static final String EX000226 = "000226"; //

	/**
	 * 身份证图片传输失败,请确认网络是否良好。
	 */
	public static final String EX000227 = "000227"; //

	/**
	 * 电子签名传输失败,请确认网络是否良好。
	 */
	public static final String EX000228 = "000228"; //
	
	/**
	 * 银行卡扫描照片传输失败，请确认网络是否良好。
	 */
	public static final String EX000229 = "000229";

	/**
	 * 联行号异常：1联行号列表获取异常，2银行查询异常
	 */
	public static final String EX000224 = "000224"; //
	/**
	 * 银行名查询失败
	 */
	public static final String EX000225 = "000225"; //
	/**
	 * 余额不足
	 */
	public static final String EX000218 = "000218"; //
	/**
	 * 商户账户不存在
	 */
	public static final String EX000219 = "000219"; //

	/**
	 * 不支持该卡片
	 */
	public static final String EX000220 = "000220"; //

	/**
	 * 获取信息失败: 获取省信息失败 获取市信息失败 ...
	 */
	public static final String EX001008 = "001008"; //

	/**
	 * 获取验证码失败
	 */
	public static final String EX000411 = "000411"; //
	/**
	 * 验证码验证失败
	 */
	public static final String EX000412 = "000412"; //

	/******************************** 代理商业务 start ************************************/
	/**
	 * 代理商查询异常
	 */
	public static final String EX000500 = "000500";
	/**
	 * 代理商已被禁用
	 */
	public static final String EX000501 = "000501";
	/**
	 * 代理商已被冻结
	 */
	public static final String EX000502 = "000502";
	/**
	 * 代理商不存在
	 */
	public static final String EX000503 = "000503";
	/******************************** 代理商业务 end ************************************/
	/**
	 * 业务不可用 ： 路由已停用 ，查询路由信息异常（终端或快捷或便民）
	 */
	public static final String EX000913 = "000913"; //

	/**
	 * 终端不可用(查无数据，未绑定终端，终端信息状态校验失败)
	 */
	public static final String EX000301 = "000301";
	/**
	 * 终端绑定失败(更新)
	 */
	public static final String EX000303 = "000303";// 添加详细描述

	/**
	 * 终端工作密钥更新失败
	 */
	public static final String EX000304 = "000304";
	
	/**
	 * 查询刷卡头蓝牙地址失败
	 */
	public static final String EX000305 = "000305";

	/**
	 * 查询app版本信息失败
	 */
	public static final String EX000701 = "000701";//

	/**
	 * 交易失败
	 */
	public static final String EX000801 = "000801";

	/**
	 * 单笔消费金额必须在{}~{}之间
	 */
	public static final String EX000811 = "000811";
	/**
	 * 单卡单日消费不得超过{}次
	 */
	public static final String EX000812 = "000812";

	/**
	 * {}累计收款金额不得超过{}元
	 */
	public static final String EX000813 = "000813";

	/**
	 * 交易流水业务异常
	 */
	public static final String EX000814 = "000814";
	/**
	 * 交易信息新增失败
	 */
	public static final String EX000815 = "000815";
	/**
	 * 获取账户余额异常
	 */
	public static final String EX000816 = "000816";
	/**
	 * 获取账户余额失败
	 */
	public static final String EX000817 = "000817";

	// 其它业务-----------------------------------------------------------
	/**
	 * 文件存储失败!
	 */
	public static final String EX001001 = "001001";

	/**
	 * 卡BIN信息获取失败！
	 */
	public static final String EX001002 = "001002";

	/**
	 * 电子签名上传失败！
	 */
	public static final String EX001003 = "001003";

	/**
	 * 未找到电子签名记录！
	 */
	public static final String EX001004 = "001004";

	/**
	 * 轮播图获取失败！
	 */
	public static final String EX001005 = "001005";
	// 短信业务----------------------------------------------------------
	/**
	 * 短信验证码获取失败！
	 */
	public static final String EX001101 = "001101";

	/**
	 * 短信校验码错误！
	 */
	public static final String EX001102 = "001102";

	/**
	 * 短信校验码已过期！
	 */
	public static final String EX001103 = "001103";

	/**
	 * 短信发送模版获取失败！
	 */
	public static final String EX001104 = "001104";

	/**
	 * 短信发送失败！
	 */
	public static final String EX001105 = "001105";

	/**
	 * 短信发送记录保存失败！
	 */
	public static final String EX001106 = "001106";

	/**
	 * 更新短信发送状态失败！
	 */
	public static final String EX001107 = "001107";

	/**
	 * 电子签名更新失败.
	 */
	public static final String EX080011 = "080011";

	// mpaychl业务错误代码--------------------------------------------------
	/**
	 * 磁道数据错误。
	 */
	public static final String EX100101 = "100101"; //

	/**
	 * 计算MAC失败
	 */
	public static final String EX100201 = "100201"; //
	/**
	 * 转加密PIN失败
	 */
	public static final String EX100202 = "100202"; //
	/**
	 * 生成密钥失败（终端主密钥、工作密钥、MACKEY转本地主密钥失败、PINKEY转本地主密钥失败）
	 */
	public static final String EX100203 = "100203"; //

	/**
	 * 密钥分算失败
	 */
	public static final String EX100208 = "100208"; //
	/**
	 * 数据解密失败
	 */
	public static final String EX100209 = "100209"; //

	/**
	 * 通道方签到失败
	 */
	public static final String EX100205 = "100205"; //

	/**
	 * 交易失败
	 */
	public static final String EX100301 = "100301";
	/**
	 * 交易超时 接收第三方系统数据超时
	 */
	public static final String EX100302 = "100302";

	/**
	 * 交易失败(快捷发送第三方失败)
	 */
	public static final String EX100401 = "100401";
	/**
	 * 交易超时(快捷发送第三方连接失败)
	 */
	public static final String EX100402 = "100402";

	/**
	 * 交易失败(便民业务发送失败)
	 */
	public static final String EX100501 = "100501"; //
	/**
	 * 交易失败(便民业务服务器连接失败)
	 */
	public static final String EX100502 = "100502"; //

	/**
	 * 终端业务 终端绑定失败！
	 */
	public static final String EX030000 = "030000";
	/**
	 * 终端业务 终端不可用
	 */
	public static final String EX030001 = "030001";

	/**
	 * 终端业务 终端未下拨
	 */
	public static final String EX030004 = "030004";

	/**
	 * 终端业务 不能跨代理商绑定终端
	 */
	public static final String EX030002 = "030002";

	/**
	 * 终端业务 终端已绑定 (不能重复绑定)
	 */
	public static final String EX030003 = "030003";

	/**
	 * 商品订单不存在
	 */
	public static final String EX080001 = "080001";

	/**
	 * 支付金额与商品订单金额不一致
	 */
	public static final String EX080002 = "080002";

	/**
	 * 创建支付订单失败
	 */
	public static final String EX080003 = "080003";
	/**
	 * 商品订单已支付,请重新下单
	 */
	public static final String EX080004 = "080004";
	/**
	 * 支付密码错误
	 */
	public static final String EX080005 = "080005";
	/**
	 * 商品订单处理中
	 */
	public static final String EX080006 = "080006";
	
	/**
	 * 商品订单未成功
	 */
	public static final String EX080007 = "080007";
	
	/**
	 * 支付订单不存在
	 */
	public static final String EX080008 = "080008";
	
	/**
	 * 支付订单未成功
	 */
	public static final String EX080009 = "080009";
	
	/**
	 * 支付流水不存在
	 */
	public static final String EX080010 = "080010";
	
	/**
	 * 隔日撤销
	 */
	public static final String EX080012 = "080012";
	
	/**
	 * 撤销金额不符
	 */
	public static final String EX080013 = "080013";

	/**
	 * 提现订单异常
	 */
	public static final String EX060000 = "060000";
	/**
	 * 提现订单创建失败
	 */
	public static final String EX060001 = "060001";
	/**
	 * 费率查询失败 异常
	 */
	public static final String EX060002 = "060002";

	// 2015/04/25 添加
	/**
	 * 通道单笔消费金额必须在{}~{}之间
	 */
	public static final String EX000821 = "000821";
	/**
	 * 通道单日消费不得超过{}元
	 */
	public static final String EX000822 = "000822";
	/**
	 * 通道单月消费不得超过{}元
	 */
	public static final String EX000823 = "000823";

	/**
	 * 通道限额检查异常
	 */
	public static final String EX000825 = "000825";
	/**
	 * 通道限额添加异常
	 */
	public static final String EX000826 = "000826";

	/**
	 * 无效推荐人
	 */
	public static final String EX000221 = "000221";

	// 通道方错误代码
	// =========================================================================

	/**
	 * 交易成功
	 */
	public static final String EX200300 = "200300";
	/**
	 * 请联系发卡行
	 */
	public static final String EX200301 = "200301";
	/**
	 * 无效商户
	 */
	public static final String EX200302 = "200302";
	/**
	 * 无效商户
	 */
	public static final String EX200303 = "200303";
	/**
	 * 没收卡，请与发卡行联系
	 */
	public static final String EX200304 = "200304";
	/**
	 * 持卡人认证失败
	 */
	public static final String EX200305 = "200305";
	/**
	 * 交易金额超限
	 */
	public static final String EX200306 = "200306";
	/**
	 * 暂时不能退货
	 */
	public static final String EX200307 = "200307";
	/**
	 * 没有原交易
	 */
	public static final String EX200308 = "200308";
	/**
	 * 撤销只允许当日操作
	 */
	public static final String EX200309 = "200309";
	/**
	 * 无参数需下载
	 */
	public static final String EX200310 = "200310";
	/**
	 * 无效卡号
	 */
	public static final String EX200311 = "200311";
	/**
	 * 无效交易
	 */
	public static final String EX200312 = "200312";
	/**
	 * 无效金额
	 */
	public static final String EX200313 = "200313";
	/**
	 * 无效卡号
	 */
	public static final String EX200314 = "200314";
	/**
	 * 无此发卡行
	 */
	public static final String EX200315 = "200315";
	/**
	 * 无此账户
	 */
	public static final String EX200316 = "200316";
	/**
	 * 挂失卡
	 */
	public static final String EX200317 = "200317";
	/**
	 * 被窃卡
	 */
	public static final String EX200318 = "200318";
	/**
	 * 余额不足
	 */
	public static final String EX200319 = "200319";
	/**
	 * 过期卡
	 */
	public static final String EX200320 = "200320";
	/**
	 * 该卡未初始化或睡眠卡
	 */
	public static final String EX200321 = "200321";
	/**
	 * 操作有误，或超出交易允许天数
	 */
	public static final String EX200322 = "200322";
	/**
	 * 非法交易
	 */
	public static final String EX200323 = "200323";
	/**
	 * 有作弊嫌疑
	 */
	public static final String EX200324 = "200324";

	/**
	 * 没有原交易
	 */
	public static final String EX200325 = "200325";
	/**
	 * 无效原金额
	 */
	public static final String EX200326 = "200326";

	/**
	 * 没收卡
	 */
	public static final String EX200327 = "200327";
	/**
	 * 卡已作废
	 */
	public static final String EX200332 = "200332";
	/**
	 * 重做交易或电话授权
	 */
	public static final String EX200333 = "200333";
	/**
	 * 有作弊嫌疑
	 */
	public static final String EX200334 = "200334";
	/**
	 * PIN格式错，请重新签到
	 */
	public static final String EX200335 = "200335";
	/**
	 * 不匹配的交易
	 */
	public static final String EX200336 = "200336";
	/**
	 * 有效期错
	 */
	public static final String EX200337 = "200337";
	/**
	 * PIN输入超过次数
	 */
	public static final String EX200338 = "200338";
	/**
	 * 手输卡号不允许做该交易
	 */
	public static final String EX200339 = "200339";
	/**
	 * 商户不支持此交易
	 */
	public static final String EX200340 = "200340";

	/**
	 * 挂失卡
	 */
	public static final String EX200341 = "200341";
	/**
	 * 不批准交易
	 */
	public static final String EX200342 = "200342";
	/**
	 * 被窃卡
	 */
	public static final String EX200343 = "200343";
	/**
	 * 不做任何处理
	 */
	public static final String EX200344 = "200344";
	/**
	 * 降级交易
	 */
	public static final String EX200345 = "200345";
	/**
	 * 拨号电话非法
	 */
	public static final String EX200346 = "200346";
	/**
	 * 退货金额超限
	 */
	public static final String EX200347 = "200347";
	/**
	 * 当日不许退货
	 */
	public static final String EX200348 = "200348";
	/**
	 * 30天内退货
	 */
	public static final String EX200349 = "200349";

	/**
	 * 余额不足
	 */
	public static final String EX200350 = "200350";

	/**
	 * 余额不足
	 */
	public static final String EX200351 = "200351";

	/**
	 * 格式错误
	 */
	public static final String EX200352 = "200352";

	/**
	 * 没收卡，请与发卡行联系
	 */
	public static final String EX200353 = "200353";

	/**
	 * 过期卡
	 */
	public static final String EX200354 = "200354";
	/**
	 * 密码错
	 */
	public static final String EX200355 = "200355";

	/**
	 * 超出发卡行取款次数限制
	 */
	public static final String EX200356 = "200356";

	/**
	 * 该卡不允许做此交易
	 */
	public static final String EX200357 = "200357";

	/**
	 * 商户不支持此交易
	 */
	public static final String EX200358 = "200358";

	/**
	 * PIN输入超过次数
	 */
	public static final String EX200328 = "200328";
	/**
	 * 止付卡
	 */
	public static final String EX200329 = "200329";
	/**
	 * 报文格式错误
	 */
	public static final String EX200330 = "200330";
	/**
	 * 交易拒绝
	 */
	public static final String EX200331 = "200331";

	/**
	 * 有作弊嫌疑
	 */
	public static final String EX200359 = "200359";
	/**
	 * 银行MAC校验错
	 */
	public static final String EX200360 = "200360";
	/**
	 * 超出金额限制
	 */
	public static final String EX200361 = "200361";
	/**
	 * 受限制卡
	 */
	public static final String EX200362 = "200362";
	/**
	 * 操作有误，或超出交易允许天数
	 */
	public static final String EX200363 = "200363";
	/**
	 * 无效原金额
	 */
	public static final String EX200364 = "200364";
	/**
	 * 超出发卡行取款次数限制
	 */
	public static final String EX200365 = "200365";
	/**
	 * 发卡方不允许该卡在本终端进行此交易
	 */
	public static final String EX200366 = "200366";
	
	/**
	 * 发卡行响应超时
	 */
	public static final String EX200367 = "200367";
	
	/**
	 * 发卡行响应超时
	 */
	public static final String EX200368 = "200368";
	/**
	 * 发卡方状态不正常，请稍后重试
	 */
	public static final String EX200369 = "200369";
	/**
	 * 发卡方线路异常，请稍后重试
	 */
	public static final String EX200370 = "200370";
	/**
	 * 银联交换中心异常
	 */
	public static final String EX200371 = "200371";
	/**
	 * 终端号未登记
	 */
	public static final String EX200372 = "200372";
	/**
	 * 请先签到
	 */
	public static final String EX200373 = "200373";
	/**
	 * 请联系收单机构手工退货
	 */
	public static final String EX200374 = "200374";
	/**
	 * PIN输入超过次数
	 */
	public static final String EX200375 = "200375";
	/**
	 * 上批未结，请先结完上批
	 */
	public static final String EX200376 = "200376";
	/**
	 * 不支持该卡种
	 */
	public static final String EX200377 = "200377";
	/**
	 * 根据银联规定，完成通知交易不能撤销
	 */
	public static final String EX200378 = "200378";
	/**
	 * 请使用与预授权交易同一类型终端做完成交易
	 */
	public static final String EX200379 = "200379";
	/**
	 * 请使用刷卡方式进行交易
	 */
	public static final String EX200380 = "200380";
	/**
	 * 请先进行结账
	 */
	public static final String EX200381 = "200381";
	/**
	 * 无效卡号
	 */
	public static final String EX200382 = "200382";
	/**
	 * 降级交易
	 */
	public static final String EX200383 = "200383";
	/**
	 * 系统日切，请稍后后重试
	 */
	public static final String EX200390 = "200390";
	/**
	 * 交易失败
	 */
	public static final String EX200391 = "200391";
	/**
	 * 发卡方线路异常，请稍后重试
	 */
	public static final String EX200392 = "200392";
	/**
	 * 重复交易
	 */
	public static final String EX200394 = "200394";
	/**
	 * 处理中心系统异常，请稍后重试
	 */
	public static final String EX200396 = "200396";
	/**
	 * 终端号未登记
	 */
	public static final String EX200397 = "200397";
	/**
	 * 发卡方超时
	 */
	public static final String EX200398 = "200398";
	/**
	 * PIN格式错，请重新签到
	 */
	public static final String EX200399 = "200399";
	/**
	 * MAC校验错，请重新签到
	 */
	public static final String EX2003A0 = "2003A0";
	/**
	 * 其他错误
	 */
	public static final String EX2003A1 = "2003A1";
	/**
	 * 交易成功，请向发卡行确认
	 */
	public static final String EX2003A2 = "2003A2";
	/**
	 * 账户不正确
	 */
	public static final String EX2003A3 = "2003A3";
	/**
	 * 交易成功，请向发卡行确认
	 */
	public static final String EX2003A4 = "2003A4";
	/**
	 * 交易成功，请向发卡行确认
	 */
	public static final String EX2003A5 = "2003A5";
	/**
	 * 交易成功，请向发卡行确认
	 */
	public static final String EX2003A6 = "2003A6";
	/**
	 * 安全处理失败
	 */
	public static final String EX2003A7 = "2003A7";
	/**
	 * POS中心异常，请稍后重试
	 */
	public static final String EX2003M1 = "2003M1";
	/**
	 * 软件版本错误，请稍后重试
	 */
	public static final String EX2003M2 = "2003M2";
	/**
	 * M3 软件版本错误，请稍后重试
	 */
	public static final String EX2003M3 = "2003M3";
	/**
	 * POS中心异常，请稍后重试
	 */
	public static final String EX2003M4 = "2003M4";
	/**
	 * 批次号错误
	 */
	public static final String EX2003M5 = "2003M5";
	/**
	 * 商户信息错误
	 */
	public static final String EX2003M6 = "2003M6";
	/**
	 * 请重新签到
	 */
	public static final String EX2003M7 = "2003M7";
	/**
	 * 重传脱机数据
	 */
	public static final String EX2003M8 = "2003M8";
	/**
	 * 机构不存在
	 */
	public static final String EX2003M9 = "2003M9";
	/**
	 * 数据校验错误
	 */
	public static final String EX2003B0 = "2003B0";

	/**
	 * MAC 校验失败（本地计算MAC和接收到的MAC比较）
	 */
	public static final String EX200400 = "200400";
	
	/**
	 * 不支持的支付方式
	 */
	public static final String EX200401 = "200401";

	// 新增
	// ------------------------------------------------------------------------

	/**
	 * 终端未设定刷卡费率.
	 */
	public static final String EX030007 = "030007";

	// 风控
	// -----------------------------------------------------------------------

	/**
	 * 该银行卡已被限制交易
	 */
	public static final String EX000850 = "000850";
	/**
	 * 最低提现额不能小于{}元。
	 */
	public static final String EX000230 = "000230"; //
	/**
	 * 提现时间段为早上{}点至下午{}点。
	 */
	public static final String EX000231 = "000231"; //
	
	/**
	 * 消费时间段为早上{}点至下午{}点。
	 */
	public static final String EX000232 = "000232"; //

	/**
	 * 提现订单查询失败
	 */
	public static final String EX060005 = "060005";

	/**
	 * 获取提现（消费）时间失败！
	 */
	public static final String EX060003 = "060003";

	/**
	 * 获取通道参数失败！
	 */
	public static final String EX060004 = "060004";
	
	/**
	 * 单笔交易金额不能小于{}元。
	 */
	public static final String EX000851 = "000851";

	/**
	 * 单笔交易金额不能大于{}元。
	 */
	public static final String EX000852 = "000852"; //

	/**
	 * 日交易金额不能大于{}元。
	 */
	public static final String EX000853 = "000853"; //
	/**
	 * 日交易次数不能超过{}笔。
	 */
	public static final String EX000854 = "000854"; //
	/**
	 * 月交易金额不能大于{}元。
	 */
	public static final String EX000855 = "000855"; //

	/**
	 * 月交易次数不能超过{}笔。
	 */
	public static final String EX000856 = "000856"; //

	/**
	 * 单笔提现金额不能小于{}元。
	 */
	public static final String EX000857 = "000857";

	/**
	 * 单笔提现金额不能大于{}元。
	 */
	public static final String EX000858 = "000858"; //

	/**
	 * 日提现金额不能大于{}元。
	 */
	public static final String EX000859 = "000859"; //
	/**
	 * 日提现次数不能超过{}笔。
	 */
	public static final String EX000860 = "000860"; //

	/**
	 * 黑名单卡
	 */
	public static final String EX000898 = "000898"; //

	/**
	 * 交易限额不通过。
	 */
	public static final String EX000899 = "000899"; //

	/**
	 * 无效金额
	 */
	public static final String EX000800 = "000800";

	/**
	 * 金额超限（超过系统最大处理金额）
	 */
	public static final String EX000802 = "000802";

	/**
	 * 余额账户可存储资金超限
	 */
	public static final String EX000803 = "000803";
	
	/**
	 * 磁道银行卡与上送银行卡不符
	 * */
	public static final String EX010001 = "010001";
	
	/**
	 * 修改银行卡、身份证验证不通过
	 * */
	public static final String EX010002 = "010002";
}
