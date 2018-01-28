package com.tangdi.production.mpbase.lib;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Test {
	/**
	   * 图二的位置　从左上角开始
	   * @param x
	   * @param y
	   */
	  public void createPicTwo(int x,int y)
	  {
	      try
	      {
	        //读取第一张图片
	        File fileOne = new File("c:\\1.gif");
	        BufferedImage ImageOne = ImageIO.read(fileOne);
	        int width = ImageOne.getWidth();//图片宽度
	        int height = ImageOne.getHeight();//图片高度
	        //从图片中读取RGB
	        int[] ImageArrayOne = new int[width*height];
	        ImageArrayOne = ImageOne.getRGB(0,0,width,height,ImageArrayOne,0,width);
	        //对第二张图片做相同的处理
	        File fileTwo = new File("c:\\2.gif");
	        BufferedImage ImageTwo = ImageIO.read(fileTwo);
	        int widthTwo = ImageTwo.getWidth();//图片宽度
	        int heightTwo = ImageTwo.getHeight();//图片高度
	        int[] ImageArrayTwo = new int[widthTwo*heightTwo];
	        ImageArrayTwo = ImageTwo.getRGB(0,0,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);
	         
	        //生成新图片
	        BufferedImage ImageNew = new BufferedImage(width*2,height,BufferedImage.TYPE_INT_RGB);
	        ImageNew.setRGB(0,0,width,height,ImageArrayOne,0,width);//设置左半部分的RGB
	        ImageNew.setRGB(x,y,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);//设置右半部分的RGB
	        File outFile = new File("c:\\out.png");
	        ImageIO.write(ImageNew, "png", outFile);//写图片
	 
	      }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      }
	  }
	  
	  /**
	     * 
	     * @param filesrc
	     * @param logosrc
	     * @param outsrc
	     * @param x 位置
	     * @param y 位置
	     */
	  public void composePic(String filesrc,String logosrc,String outsrc,int x,int y) {
	    try {
	        File bgfile = new File(filesrc);
	        Image bg_src = javax.imageio.ImageIO.read(bgfile);
	         
	        File logofile = new File(logosrc);
	        Image logo_src = javax.imageio.ImageIO.read(logofile);
	         
	        int bg_width = bg_src.getWidth(null);
	        int bg_height = bg_src.getHeight(null);
	        int logo_width = logo_src.getWidth(null);;
	        int logo_height = logo_src.getHeight(null);
	 
	        BufferedImage tag = new BufferedImage(bg_width, bg_height, BufferedImage.TYPE_INT_RGB);
	         
	        Graphics2D g2d = tag.createGraphics();
	        g2d.drawImage(bg_src, 0, 0, bg_width, bg_height, null);
	         
	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1.0f)); //透明度设置开始  
	        g2d.drawImage(logo_src,x,y,logo_width,logo_height, null);            
	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //透明度设置 结束
	         
	        FileOutputStream out = new FileOutputStream(outsrc);
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	        encoder.encode(tag);
	        out.close();
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	  } 
	 
	  public static void main(String args[]) {
	      Long star = System.currentTimeMillis();
	      Test pic = new Test();
	      pic.composePic("c:\\bb.gif","c:\\bc.gif","c:\\out_pic.gif",490,360);
	      Long end =System.currentTimeMillis();
	      System.out.print("time====:"+(end-star));
	  }

}
