package com.tangdi.production.mpaychl.trans.process;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpaychl.base.service.EncryptorService;
import com.tangdi.production.mpaychl.constants.MsgSubST;
import com.tangdi.production.mpbase.constants.MessageConstants;
import com.tangdi.production.tdbase.context.SpringContext;
import com.tangdi.production.tdbase.util.DesUtils;
import com.tangdi.production.tdcomm.atc.Util;
import com.tangdi.production.tdcomm.engine.TcpOutProcess;
import com.tangdi.production.tdcomm.itf.PackImpl;
import com.tangdi.production.tdcomm.tcp.HiByteBuffer;
import com.tangdi.production.tdcomm.util.Msg;


/**
 * 手刷接收单
 * 
 * @author youdd 2016/03/30
 * 
 */
public class TcpSDPayOutProcess extends TcpOutProcess {

	private static Logger log = LoggerFactory
			.getLogger(TcpSDPayOutProcess.class);
	private PackImpl pack;

	public PackImpl getPack() {
		return pack;
	}

	public void setPack(PackImpl pack) {
		this.pack = pack;
	}

	public void beforeprocess() throws TimeoutException {
		if (null != this.pack) {
			this.pack.loadAfter();
		}

	}

	public void run() {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream(1024);
			getPack().setDest(bo);
			getPack().call();
			byte[] buff = bo.toByteArray();
			HiByteBuffer sendBuf = new HiByteBuffer(1024);
			HiByteBuffer sendBuf1 = new HiByteBuffer(1024);
			Map<String, Object> map = Msg.getdatas();
			
			String macflag = Msg.getStrData(MessageConstants.GETMACFLAG);
			if (macflag != null && macflag.equals("0")) {
				sendBuf1.append(buff, 13, buff.length - 21);
				String s = Util.getHexStr(sendBuf1.getBytes());
				log.info("MesDat={}", s);
				String lenth = Util.getHexStr(Util.toBytes((short) sendBuf1
						.length()));
				map.put("MesLen", lenth);
				map.put("MesDat", s);
				doMac(map);
				String macstr = String.valueOf(map.get("MAC"));
				byte[] macbyte = Util.decodeHex(macstr);
				if (null == macbyte) {
					throw new Exception("");
				}
				sendBuf.append(buff, 2, buff.length - 10);
				sendBuf.append(macbyte);
			} else {
				sendBuf.append(buff, 2, buff.length - 2);
			}
			log.info("截掉8583 默认的两字节的前置长度={} ",
					Util.getHexStr(sendBuf.getBytes()));
			Msg.setData("SENDMSG", sendBuf.getBytes());
			this.getInterceptor().call();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成MAC
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> doMac(Map<String, Object> map) throws Exception {
		try {
			//调用加密机
			String encryServiceName = map.get("encryServiceName").toString();
			log.info("计算MAC中. 加密机实配置:[{}]",encryServiceName);
		    EncryptorService encryptorService = SpringContext.getBean(encryServiceName,
		    		EncryptorService.class);
		    map.put("MACMODE", MsgSubST.MAC_MODE_ECB);
			String mac = encryptorService.getMac(map);
			
			mac = DesUtils.toHexString(mac.substring(0, 8));
			map.put("MAC", mac);
			log.info("截取前8位转16进制,取得MAC=[{}]。",mac);
		} catch (Exception e) {
			throw new Exception("产生MAC 错误", e);
		}
		return map;
	}
}
