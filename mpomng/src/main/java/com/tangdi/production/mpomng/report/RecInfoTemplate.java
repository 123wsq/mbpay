package com.tangdi.production.mpomng.report;

import com.tangdi.production.tdbase.annotation.ReportField;
import com.tangdi.production.tdbase.annotation.ReportHead;

/**
 * 对账业务导出模板
 * @author youdd
 *
 */
@ReportHead(display=true)
public class RecInfoTemplate {
	@ReportField(title="商户号")
	private String merno ;
	
	@ReportField(title="第三方标识",dict="BKCODE")//,
	private String bkcode;
	
	@ReportField(title="终端号")
	private String terno;
	
	@ReportField(title="交易时间")
	private String trantime;
	
	@ReportField(title="支付订单号")
	private String locpayno;
	
	@ReportField(title="交易流水号 ")
	private String transeq; 
	
	@ReportField(title="转出卡卡号")
	private String cardno;
	
	@ReportField(title="缴费号")
	private String payno;

	@ReportField(title="交易金额")
	private String payamt;
	
	@ReportField(title="结算金额")
	private String incamt;
	
	@ReportField(title="结算日期")
	private String settledt;
	
	
	@ReportField(title="检索参考号")
	private String refno;
	
	
	@ReportField(title="交易类型",dict="TRANTYPE")
	private String trantype;
	
	@ReportField(title="疑账类型",dict="CHECKDOUBTTYPE")
	private String ckdoubttype;
	
	@ReportField(title="对账日期")
	private String ckdt;
	
	public String getCkdt() {
		return ckdt;
	}

	public void setCkdt(String ckdt) {
		this.ckdt = ckdt;
	}
	
	public String getMerno() {
		return merno;
	}

	public void setMerno(String merno) {
		this.merno = merno;
	}

	public String getBkcode() {
		return bkcode;
	}

	public void setBkcode(String bkcode) {
		this.bkcode = bkcode;
	}

	public String getTerno() {
		return terno;
	}

	public void setTerno(String terno) {
		this.terno = terno;
	}

	public String getTrantime() {
		return trantime;
	}

	public void setTrantime(String trantime) {
		this.trantime = trantime;
	}

	public String getLocpayno() {
		return locpayno;
	}

	public void setLocpayno(String locpayno) {
		this.locpayno = locpayno;
	}

	public String getTranseq() {
		return transeq;
	}

	public void setTranseq(String transeq) {
		this.transeq = transeq;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getPayno() {
		return payno;
	}

	public void setPayno(String payno) {
		this.payno = payno;
	}

	public String getPayamt() {
		return payamt;
	}

	public void setPayamt(String payamt) {
		this.payamt = payamt;
	}

	public String getIncamt() {
		return incamt;
	}

	public void setIncamt(String incamt) {
		this.incamt = incamt;
	}

	public String getSettledt() {
		return settledt;
	}

	public void setSettledt(String settledt) {
		this.settledt = settledt;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getTrantype() {
		return trantype;
	}

	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

	public String getCkdoubttype() {
		return ckdoubttype;
	}

	public void setCkdoubttype(String ckdoubttype) {
		this.ckdoubttype = ckdoubttype;
	}
}
