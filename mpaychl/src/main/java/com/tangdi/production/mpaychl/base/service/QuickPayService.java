package com.tangdi.production.mpaychl.base.service;

/**
 * <b>快捷支付基础类接口</b> 每个厂家都继承自接口,实现自己的处理业务逻辑。</br>
 * 接口规范支持：根据客户端选择的不同厂商,来完成调用各自厂商的具体实现类，实现支付功能。
 * 
 * @author zhengqiang 2015/3/18
 * 
 *
 */
public interface QuickPayService extends PayService{

}
