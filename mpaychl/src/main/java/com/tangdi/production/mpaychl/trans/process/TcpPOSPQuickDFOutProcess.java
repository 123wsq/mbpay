package com.tangdi.production.mpaychl.trans.process;

import com.tangdi.production.mpaychl.utils.PackXmlImpl;
import com.tangdi.production.tdcomm.engine.TcpOutProcess;
import com.tangdi.production.tdcomm.util.Msg;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>秒付通道</b> 接出流程短连接
 * 
 * @author chenlibo
 * 
 */
public class TcpPOSPQuickDFOutProcess extends TcpOutProcess {

	private static Logger log = LoggerFactory
			.getLogger(TcpPOSPQuickDFOutProcess.class);
	private PackXmlImpl pack;
	private String encoding = "ISO-8859-1";

	public PackXmlImpl getPack() {
		return this.pack;
	}

	public void setPack(PackXmlImpl pack) {
		this.pack = pack;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void beforeprocess() throws TimeoutException {
		if (this.pack != null)
			this.pack.loadAfter();
	}

	public void run() {
		try {
			try {
				ByteArrayOutputStream bo = new ByteArrayOutputStream(1024);
				getPack().setDest(bo);
				getPack().call();
				byte[] buff = bo.toByteArray();
				String xmlDeclare = "<?xml version=\"1.0\" encoding=\""+this.encoding+"\"?>";
				byte[] declDate = xmlDeclare.getBytes();
				int sdatalen = declDate.length;
				if(buff != null)
					sdatalen += buff.length;
				
				byte[] sendData = new byte[sdatalen];
				System.arraycopy(declDate, 0, sendData, 0, declDate.length);
				if(buff != null){
					System.arraycopy(buff, 0, sendData, declDate.length, buff.length);
				}
				Msg.setData("SENDMSG", sendData);
				getInterceptor().call();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
