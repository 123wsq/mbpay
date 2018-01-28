package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

/**
 * 终端密钥表
 * 
 * @author limiao
 *
 */
public class TerminalKeyInf extends BaseBean {

	private static final long serialVersionUID = 1L;

	private String terminalId;
	private String terminalLMK; // 终端LMK
	private String terminalLMKCV; // 终端LMK Check value
	private String terminalZMK; // 终端ZMK
	private String terminalZMKCV; // 终端ZMK Check value
	private String terminalTTK; // 终端密钥(解密磁道信息)
	private String terminalTTKCV; // 终端TTK Check value
	private String terminalPIK; // 终端PIN KEY
	private String terminalPIKCV; // 终端PIK Check value
	private String terminalTDK; // 终端磁道密钥
	private String terminalTDKCV; // 终端TDK Check value
	private String terminalMAK; // 终端 MAC KEY
	private String terminalMAKCV; // 终端MAK Check value

	/**
	 * 终端ID
	 * 
	 * @return
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * 终端ID
	 * 
	 * @param terminalId
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * 终端LMK
	 * 
	 * @return
	 */
	public String getTerminalLMK() {
		return terminalLMK;
	}

	/**
	 * 终端LMK
	 * 
	 * @param terminalLMK
	 */
	public void setTerminalLMK(String terminalLMK) {
		this.terminalLMK = terminalLMK;
	}

	/**
	 * 终端ZMK
	 * 
	 * @return
	 */
	public String getTerminalZMK() {
		return terminalZMK;
	}

	/**
	 * 终端ZMK
	 * 
	 * @param terminalZMK
	 */
	public void setTerminalZMK(String terminalZMK) {
		this.terminalZMK = terminalZMK;
	}

	/**
	 * 终端密钥(解密磁道信息)
	 * 
	 * @return
	 */
	public String getTerminalTTK() {
		return terminalTTK;
	}

	/**
	 * 终端密钥(解密磁道信息)
	 * 
	 * @param terminalTTK
	 */
	public void setTerminalTTK(String terminalTTK) {
		this.terminalTTK = terminalTTK;
	}

	/**
	 * 终端PIN KEY
	 * 
	 * @return
	 */
	public String getTerminalPIK() {
		return terminalPIK;
	}

	/**
	 * 终端PIN KEY
	 * 
	 * @param terminalPIK
	 */
	public void setTerminalPIK(String terminalPIK) {
		this.terminalPIK = terminalPIK;
	}

	/**
	 * 终端磁道密钥
	 * 
	 * @return
	 */
	public String getTerminalTDK() {
		return terminalTDK;
	}

	/**
	 * 终端磁道密钥
	 * 
	 * @param terminalTDK
	 */
	public void setTerminalTDK(String terminalTDK) {
		this.terminalTDK = terminalTDK;
	}

	/**
	 * 终端 MAC KEY
	 * 
	 * @return
	 */
	public String getTerminalMAK() {
		return terminalMAK;
	}

	/**
	 * 终端 MAC KEY
	 * 
	 * @param terminalMAK
	 */
	public void setTerminalMAK(String terminalMAK) {
		this.terminalMAK = terminalMAK;
	}

	public String getTerminalLMKCV() {
		return terminalLMKCV;
	}

	public void setTerminalLMKCV(String terminalLMKCV) {
		this.terminalLMKCV = terminalLMKCV;
	}

	public String getTerminalZMKCV() {
		return terminalZMKCV;
	}

	public void setTerminalZMKCV(String terminalZMKCV) {
		this.terminalZMKCV = terminalZMKCV;
	}

	public String getTerminalTTKCV() {
		return terminalTTKCV;
	}

	public void setTerminalTTKCV(String terminalTTKCV) {
		this.terminalTTKCV = terminalTTKCV;
	}

	public String getTerminalPIKCV() {
		return terminalPIKCV;
	}

	public void setTerminalPIKCV(String terminalPIKCV) {
		this.terminalPIKCV = terminalPIKCV;
	}

	public String getTerminalTDKCV() {
		return terminalTDKCV;
	}

	public void setTerminalTDKCV(String terminalTDKCV) {
		this.terminalTDKCV = terminalTDKCV;
	}

	public String getTerminalMAKCV() {
		return terminalMAKCV;
	}

	public void setTerminalMAKCV(String terminalMAKCV) {
		this.terminalMAKCV = terminalMAKCV;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
