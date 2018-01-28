package com.tangdi.production.mpbase.util;

/**
 * 签名文字
 * @author zhengqiang
 *
 */
public class ImgFont {
	
	private String text ;
	private int x;
	private int y;
	public ImgFont() {
	}
	public ImgFont(Object text, int x, int y) {
		if(text == null){
			this.text = "";
		}else{
			this.text = text.toString();
		}
		this.x = x;
		this.y = y;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	

}
