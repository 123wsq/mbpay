package com.tangdi.production.mpaychl.front.service;

import com.tangdi.production.mpaychl.base.service.PayService;



/**
 * <b>快捷支前置处理接口(无SDK方式)</b> </br>
 * 主要功能：通过厂商渠道代码,调用不同实现类来处理业务逻辑。所有的快捷支付实现都要继承自[QuickPaymentService]接口。
 * 
 * @author zhengqiang 2015/3/18
 * 
 *
 */
public interface QuickPayFrontService extends PayService{

}
