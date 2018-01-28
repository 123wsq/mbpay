package com.tangdi.production.mpaychl.trans.process;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.tdcomm.atc.Util;
import com.tangdi.production.tdcomm.engine.TcpOutProcess;
import com.tangdi.production.tdcomm.iso8583.Unpack8583;
import com.tangdi.production.tdcomm.util.BS;

/**
 * 手刷接收单
 * 
 * @author youdd 2016/03/30
 * 
 */
public class TcpSDPayOutAfterProcess extends  TcpOutProcess  {

	private static Logger log = LoggerFactory
			.getLogger(TcpSDPayOutAfterProcess.class);

	private Unpack8583 unpack;

	public Unpack8583 getUnpack() {
		return unpack;
	}

	public void setUnpack(Unpack8583 unpack) {
		this.unpack = unpack;
	}

	public void beforeprocess() throws TimeoutException {

		if (null != this.unpack) {
			this.unpack.loadAfter();
		}

	}

	public void run() {

		try {
			log.debug("开始解析数据...");
			byte[] tpdu = BS.read(5);
			log.info("接收到的tpdu={}", Util.getHexStr(tpdu));
			
			byte[] head = BS.read(6);
			String heads=Util.getHexStr(head);
			log.info("接受到head={}",heads);
			getUnpack().call();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}
   public static void main(String args[]){
	   System.out.println(Integer.parseInt("61", 16));
   }
}
