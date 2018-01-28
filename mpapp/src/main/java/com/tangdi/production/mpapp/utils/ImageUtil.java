package com.tangdi.production.mpapp.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 图片处理工具
 * @author zhengqiang
 *
 */
public class ImageUtil {
	
	public static void main(String[] args) throws IOException{
		sign();
	}
	
	public static int sign() throws IOException{
		//创建一张原始图片
		//BufferedImage img = new BufferedImage(533, 800, BufferedImage.TYPE_INT_RGB);//创建图片
	   // BufferedImage bg = ImageIO.read(new File("C:\\Users\\Administrator.N1I11YAE7VJWQJS\\Desktop\\2.jpg"));//读取本地图片
		BufferedImage sign = ImageIO.read(new File("F:\\2015050720022794.jpg"));//读取互联网图片
		BufferedImage bg = ImageIO.read(new File("F:\\sign.png"));//读取本地图片
		Graphics g =bg.getGraphics();//开启画图
		//合成图片
		//g.drawImage(bg.getScaledInstance(533,800,Image.SCALE_DEFAULT), 0, 0, null);      // 绘制缩小后的图
		g.drawImage(sign.getScaledInstance(85,85, Image.SCALE_DEFAULT), 0,700 , null);   // 绘制缩小后的图
		//　　在图上写字
		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑",Font.PLAIN, 24));
		g.drawString("Java培训", 0,600);//绘制文字
		g.drawString("疯狂软件Java培训", 0, 650);
		g.setFont(new Font("微软雅黑",Font.PLAIN, 36));
		//　　画完记得关闭g
		g.dispose();
		//最后写到本地图片
		ImageIO.write(bg,"png", new File("F:\\3.png"));
		return 0;
	}

}
