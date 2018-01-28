package com.tangdi.production.mpbase.util;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.LinkedList;


public class ImgUtil {
	private static Logger  log = LoggerFactory.getLogger(ImgUtil.class);
	private Font font = new Font("新宋体",Font.BOLD, 16);// 添加字体的属性设置

	private Graphics2D g = null;

	private int fontsize = 0;

	private int x = 0;

	private int y = 0;

	/**
	 * 导入本地图片到缓冲区
	 */
	public BufferedImage loadImageLocal(String imgName) {
		try {
			return ImageIO.read(new File(imgName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 导入网络图片到缓冲区
	 */
	public BufferedImage loadImageUrl(String imgName) {
		try {
			URL url = new URL(imgName);
			return ImageIO.read(url);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 生成新图片到本地
	 */
	public void writeImageLocal(String newImage, BufferedImage img) {
		if (newImage != null && img != null) {
			try {
				File outputfile = new File(newImage);
				if (!outputfile.exists()&& !outputfile .isDirectory()) {
					outputfile.mkdirs();
				}
				ImageIO.write(img, "png", outputfile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 设定文字的字体等
	 */
	public void setFont(String fontStyle, int fontSize) {
		this.fontsize = fontSize;
		this.font = new Font(fontStyle, Font.PLAIN, fontSize);
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 */
	public BufferedImage modifyImage(BufferedImage img, Object content, int x,
			int y) {

		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.black);//设置字体颜色
			if (this.font != null)
				g.setFont(this.font);
			// 验证输出位置的纵坐标和横坐标
			if (x >= h || y >= w) {
				this.x = h - this.fontsize + 2;
				this.y = w;
			} else {
				this.x = x;
				this.y = y;
			}
			if (content != null) {
				/* --------对要显示的文字进行处理-------------- */                    
				AttributedString ats = new AttributedString(content.toString());                                 
				g.setFont(font);                   
				/* 消除java.awt.Font字体的锯齿 */                    
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,                                                
				RenderingHints.VALUE_ANTIALIAS_ON);                       
				/* 消除java.awt.Font字体的锯齿 */                                                     
				ats.addAttribute(TextAttribute.FONT, font, 0, content.toString().length());               
				AttributedCharacterIterator iter = ats.getIterator(); 
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,                                                    
						RenderingHints.VALUE_ANTIALIAS_ON); 
				g.drawString(iter, this.x, this.y);
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}
	public BufferedImage signImage(BufferedImage img, LinkedList<ImgFont> list) {
		try {
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.black);//设置字体颜色
			if (this.font != null)
				g.setFont(this.font);
			// 验证输出位置的纵坐标和横坐标
			for(ImgFont font:list){
				log.info("Font=[{},{},{}]",font.getText(),font.getX(),font.getY());
				g.drawString(font.getText(), font.getX(), font.getY());
			}
			g.dispose();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		return img;
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
	 */
	public BufferedImage modifyImage(BufferedImage img, Object[] contentArr,
			int x, int y, boolean xory) {
		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.RED);
			if (this.font != null)
				g.setFont(this.font);
			// 验证输出位置的纵坐标和横坐标
			if (x >= h || y >= w) {
				this.x = h - this.fontsize + 2;
				this.y = w;
			} else {
				this.x = x;
				this.y = y;
			}
			if (contentArr != null) {
				int arrlen = contentArr.length;
				if (xory) {
					for (int i = 0; i < arrlen; i++) {
						g.drawString(contentArr[i].toString(), this.x, this.y);
						this.x += contentArr[i].toString().length()
								* this.fontsize / 2 + 5;// 重新计算文本输出位置
					}
				} else {
					for (int i = 0; i < arrlen; i++) {
						g.drawString(contentArr[i].toString(), this.x, this.y);
						this.y += this.fontsize + 2;// 重新计算文本输出位置
					}
				}
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 * 
	 * 时间:2007-10-8
	 * 
	 * @param img
	 * @return
	 */
	public BufferedImage modifyImageYe(BufferedImage img) {

		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.blue);//设置字体颜色
			if (this.font != null)
				g.setFont(this.font);
			g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {

		try {
			int w = b.getWidth();
			int h = b.getHeight();
			g = d.createGraphics();
			g.drawImage(b, 60, 750, w, h, null);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return d;
	}
	/**
	 * 保存电子签名
	 * @param signPath
	 * @param list
	 * @return
	 */
	public int saveEsign(String templatePath,String signPath, String cpath,LinkedList<ImgFont> list){
		BufferedImage img = loadImageLocal(templatePath);
		int sw = 423;

		BufferedImage sourceImg = null;
		try {
			sourceImg = ImageIO.read(new FileInputStream(new File(signPath)));
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		log.debug("电子签名图片对象BufferedImage=[{}]",sourceImg);
		if(sourceImg == null){
			log.debug("手签签名源图未找到.");
			return 0;
		}
		int width = sourceImg.getWidth();
		int hight = sourceImg.getHeight();
		
		
		
		if(width < hight){
			int dr =  hight/sw + 1;
			
			BufferedImage sourceResizeImg = ResizeImage.resizeImage(
					rotate(loadImageLocal(signPath), 90), dr);
			
			//图片旋转
			writeImageLocal(cpath,modifyImagetogeter(sourceResizeImg,
					signImage(img, list)));
		}else{
			int dr =  width/sw + 1;
			
			BufferedImage sourceResizeImg = ResizeImage.resizeImage(
					loadImageLocal(signPath), dr);
			
			writeImageLocal(cpath,modifyImagetogeter(sourceResizeImg,
					signImage(img, list)));
		}
		

		return 0;
	}
	/**
	 * 图片旋转
	 * @param src 源图片
	 * @param angel 角度
	 * @return
	 */
	public BufferedImage rotate(Image src, int angel) {  
        int src_width = src.getWidth(null);  
        int src_height = src.getHeight(null);  
        // 计算新的图片大小
        Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(  
                src_width, src_height)), angel);  
  
        BufferedImage res = null;  
        res = new BufferedImage(rect_des.width, rect_des.height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2 = res.createGraphics();  
        // transform   
        g2.translate((rect_des.width - src_width) / 2,  
                (rect_des.height - src_height) / 2);  
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);  
  
        g2.drawImage(src, null, null);  
        return res;  
    }  
	 public  Rectangle calcRotatedSize(Rectangle src, int angel) {
	        if (angel >= 90) {  
	            if(angel / 90 % 2 == 1){  
	                int temp = src.height;  
	                src.height = src.width;  
	                src.width = temp;  
	            }  
	            angel = angel % 90;  
	        }  
	  
	        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;  
	        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;  
	        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;  
	        double angel_dalta_width = Math.atan((double) src.height / src.width);  
	        double angel_dalta_height = Math.atan((double) src.width / src.height);  
	  
	        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha  
	                - angel_dalta_width));  
	        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha  
	                - angel_dalta_height));  
	        int des_width = src.width + len_dalta_width * 2;  
	        int des_height = src.height + len_dalta_height * 2;  
	        return new java.awt.Rectangle(new Dimension(des_width, des_height));  
	    }  



	public static void main(String[] args) {

		ImgUtil tt = new ImgUtil();

		
//		BufferedImage b = tt
//				.loadImageLocal("E:\\文件(word,excel,pdf,ppt.txt)\\zte-logo.png");
		LinkedList<ImgFont> list = new LinkedList<ImgFont>();
		list.add(new ImgFont("郑强",120,180));    //商户号
		list.add(new ImgFont("000000",180,270));  //终端号
		list.add(new ImgFont("000001",180,310));  //商户编号
		list.add(new ImgFont("000002",100,380));  //卡号
		list.add(new ImgFont("消费",180,475));  //交易类型
		list.add(new ImgFont("",380,475));  //有效期
		list.add(new ImgFont("000005",180,520));  //批次号
		list.add(new ImgFont("000006",380,520));  //查询号
		list.add(new ImgFont("15/06/30 12:12:12",180,550));  //时间
		list.add(new ImgFont("000008",120,580));   //序号
		list.add(new ImgFont("000009",380,580));   //授权号
		list.add(new ImgFont("200.5",120,720));   //
		 
	  //   tt.saveEsign("F:\\sign.png", "", list);
		//tt.writeImageLocal("D:\\cc.jpg", tt.modifyImagetogeter(b, d));
		//将多张图片合在一起\\
		ImgUtil sign = new ImgUtil();//SIGN_PIC_PATH
		sign.saveEsign("F:\\sign.png"
				,"F:\\150701\\2015070109142662.png","F:\\150701\\esign\\sign.png",list);
		;
		//tt.writeImageLocal("F:\\0000000000.png", rotate(sign.loadImageLocal("F:\\150701\\2015070109123157.png"), 90));
		System.out.println("success");
	}

}