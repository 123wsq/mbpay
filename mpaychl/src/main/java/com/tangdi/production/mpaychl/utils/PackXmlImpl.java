package com.tangdi.production.mpaychl.utils;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.tangdi.production.tdcomm.engine.IChannel;
import com.tangdi.production.tdcomm.engine.RunnableFilter;
import com.tangdi.production.tdcomm.engine.parser.TpelParser;
import com.tangdi.production.tdcomm.engine.tags.IFileParser;
import com.tangdi.production.tdcomm.engine.tags.container.ITFChannel;
import com.tangdi.production.tdcomm.engine.tags.container.ITFTransaction;
import com.tangdi.production.tdcomm.util.BS;
import com.tangdi.production.tdcomm.util.Context;
import com.tangdi.production.tdcomm.util.Msg;
import com.tangdi.production.tdcomm.util.TdParseException;
import com.tangdi.production.tdcomm.util.TdTextException;
import com.tangdi.production.tdcomm.util.Xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Callable;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackXmlImpl implements Callable<Integer>, IFileParser {
	private static Logger log = LoggerFactory.getLogger(IChannel.class);
	private Supplier<ITFChannel>[] channels;
	private List<String> itfs = Lists.newArrayList();
	private Object dest;
	private Charset charset;

	void runPack(Runnable r) {
		if (this.charset != null)
			Context.pushInstance(Charset.class, this.charset);
		try {
			r.run();
		} finally {
			if (this.charset != null)
				Context.popInstance(Charset.class);
		}
	}

	public Object getDest() {
		return this.dest;
	}

	public void setITF(String itf) {
		this.itfs.add(itf);
	}

	public void loadAfter() {
		TpelParser parser = (TpelParser) Context.getInstance(TpelParser.class);
		this.channels = new Supplier[this.itfs.size()];
		int i = 0;
		for (String itf : this.itfs)
			try {
				this.channels[(i++)] = parser
						.getSupplier(ITFChannel.class, itf);
			} catch (TdParseException e) {
				log.error(e.getMessage(), e);
			}
	}

	public void setDest(Object dest) {
		this.dest = dest;
	}

	public void setCharset(String c) {
		this.charset = Charset.forName(c);
	}

	public Integer call() throws Exception {
		String cod = Msg.getItfCode();
		OutputStream out = null;
		RunnableFilter rf = null;
		if (this.dest != null) {
			Object d = this.dest;
			if ((d instanceof OutputStream)) {
				out = (OutputStream) d;
			} else if ((d instanceof File)) {
				out = new FileOutputStream((File) d);
			} else if ((d instanceof RunnableFilter)) {
				rf = (RunnableFilter) d;
			} else {
				log.error("不支持的dest类型{}", d.getClass());
				throw new RuntimeException("pack 不支持的dest类型" + d.getClass());
			}
		}

		Runnable r = null;
		for (Supplier c : this.channels) {
			ITFTransaction tran = ((ITFChannel) c.get()).getTransaction(cod);
			if (tran != null) {
				tran.setMsgLogLevel();
				tran.setMsgTimeOut();
				r = tran.getPack();
				if (r != null) {
					if (out != null) {
						Xml.begin();
						BS.pushOutput(out);
						try {
							runPack(r);
						} finally {
							BS.popOutput();
							Element root =  Xml.end();
							if(root != null){
								out.write(root.asXML().getBytes());
							}
							out.close();
						}
					} else if (rf != null) {
						rf.run(r);
					} else {
						runPack(r);
					}
				}
				return Integer.valueOf(0);
			}
		}

		log.error("没有找到交易码[" + cod + "]");
		throw new TdTextException("[%s]Pack未找到交易码%s", new Object[] {
				Msg.getMessageId(), cod });
	}
}
