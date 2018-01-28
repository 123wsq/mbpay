package com.tangdi.production.mpbase.enumeration;


/**
 * 文件类型枚举： 用于定义文件类型
 * @author zhengqiang
 *
 */
public enum FileTypeEnum {
	
	/**
	 * JEPG.
	 */
	JPEG("FFD8FF"),
	
	/**
	 * PNG.
	 */
	PNG("89504E47"),
	
	/**
	 * GIF.
	 */
	GIF("47494638"),
	
	/**
	 * Windows Bitmap.
	 */
	BMP("424D");

	
	private String value = "";
	

	private FileTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}