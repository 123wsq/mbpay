package com.tangdi.production.mpapp.session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.tdbase.util.TdExpBasicFunctions;

/**
 * 客户信息管理
 * @author zhengqiang
 *
 */
public class Cust {
	
	private static final Logger log = LoggerFactory
			.getLogger(Cust.class);
	/**
	 * 客户ID（唯一标识）
	 */
	private String custId;
	/**
	 * 客户端登陆时间单位:秒
	 */
	private long logintime;
	/**
	 * 最近操作时间
	 */
	private long lasttime;
	
	private String ip;
	/**
	 * 客户端系统类型: 1 Andriod 2 IOS
	 */
	private String sysType;
	
	/**
	 * 超时时间
	 */
	private long timeout;
	/**
	 * 确定唯一APP设备
	 */
	private String sysTerNo ;
	
	public Cust(){
		
	}
	
	/**
	 * 
	 * 初始化客户登陆Session
	 * @param custId 客户编号
	 * @param timeout 超时时间
	 * @param ip 客户段IP
	 * @param sysType 客户端系统类型
	 * @param sysTerNo 设备MAC
	 */
	public Cust(String custId,long timeout,long logintime,String ip,String sysType,String sysTerNo){
		this.custId = custId;
		this.timeout = timeout;
		this.ip = ip;
		this.sysType = sysType;
		this.logintime = logintime;
		this.lasttime = this.logintime;
		this.sysTerNo = sysTerNo;
	}
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	/**
	 * 校验是否超时
	 * @return 0 未超时 1 超时
	 */
	
	public boolean getIstimeout(){
		long currentTime = Long.valueOf(TdExpBasicFunctions.GETDATETIME());
		log.info("校验是否超时===  当前时间：" + currentTime + "============  最后更新时间" + lasttime);
		log.info("校验是否超时===  " + (getTime(currentTime + "") - getTime(lasttime + "")) + "s");
		if((getTime(currentTime + "") - getTime(lasttime + "")) >= timeout){			
			return true;
		}
		this.lasttime = Long.valueOf(TdExpBasicFunctions.GETDATETIME());
		return false;
	}
	
	public long getTime(String time){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date date  = format.parse(time);
			
			return date.getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public String getSysType(){
		if(this.sysType.equals("1")){
			return "Andriod";
		}else{
			return "IOS";
		}
	}
	
	public String getIp(){
		return this.ip;
	}
	public String getSysTerNo(){
		return this.sysTerNo;
	}
	
	@Override
	public String toString() {
		return " [客户编号=" + custId 
				+ ", MAC=" + this.sysTerNo
				+ ", 登陆时间=" + logintime
				+ ", IP="+ip+", 系统类型="+getSysType()
				+",设置超时时长=" + timeout + "ms"
				+ ", 最近交易时间=" + lasttime+"]";
	}
	
	
	

}
