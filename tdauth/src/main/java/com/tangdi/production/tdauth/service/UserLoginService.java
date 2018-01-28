package com.tangdi.production.tdauth.service;

import java.awt.image.BufferedImage;

import com.tangdi.production.tdauth.bean.UAI;
import com.tangdi.production.tdauth.bean.UserInf;
import com.tangdi.production.tdauth.service.exception.CaptchaException;
import com.tangdi.production.tdbase.domain.ReturnMsg;
/**
 * 用户登陆接口
 * @author ..
 *
 */
public interface UserLoginService {

	/**
	 * 生成数字验证码
	 * 
	 * @return
	 */
	public String generateCaptcha();
	/**
	 * 生成验证码图片
	 * @param captcha 验证码
	 * @return
	 */
	public BufferedImage generateCaptchaImg(String captcha);
	/**
	 * 用户登录
	 * @param user 用户信息
	 * @param clientCaptcha 客户端提交的验证码
	 * @param serverCaptcha 服务器端保存的验证码
	 * @return
	 */
	public UAI logIn(UserInf user, String clientCaptcha, String serverCaptcha)throws CaptchaException,Exception;
	/**
	 * 用户登录（不校验验证码）
	 * @param user 用户信息
	 * @return
	 */
	public UserInf logIn(UserInf user)throws Exception;
	/**
	 * 用户登出
	 * @param user
	 * @return
	 */
	public ReturnMsg logOut(UserInf user);
	public UAI PosplogIn(UserInf user)throws CaptchaException,Exception;
}
