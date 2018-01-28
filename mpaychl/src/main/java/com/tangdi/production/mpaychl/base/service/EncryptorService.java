package com.tangdi.production.mpaychl.base.service;

import java.util.Map;

import com.tangdi.production.mpbase.exception.TranException;


/**
 * <b>加密机标准结构定义</b>
 * 接口规范支持:</br> 1. 产生MAC </br>2.校验MAC </br>3.PIN 转加密 </br>4.获取终端主密钥</br>
 * 5.获取终端MAC密钥</br>
 * 6.获取终端PIN密钥</br>
 * 7.银联（或第三方机构）MAC 转为本地MAC</br>
 * 8.银联（或第三方机构）PIN 转为本地PIN</br>

 * @author zhengqiang 2015/3/18
 *
 */
public interface EncryptorService {
	
	final static String MANUFACTURER = "ENCRYPTOR_MANUFACTURER";
	
	/**
	 * 产生MAC
	 * 
	 * 
	 * @param map             数据{
	 *                             flag:算法 01：XOR， 02：ANSI9.9 ， 03：ANSI9.19               默认02
	 *                             MAKKEYLEN:MAC 密钥 长度  08: 8 位  ,10： 16位，18:24位      默认8
	 *                             MAKKEY:MAK 密钥                                                                               必须传
	 *                             MSGDATA:参与算mac 的数据                                                               必须传
                                   }
	 * @return                Map  
	 *                        返回mac   
	 *                        
	 
	 */
	public String getMac(Map<String, Object> map) throws TranException;
	
	/**
	 * 校验MAC
	 * @param map             数据{
	 *                             flag:算法 01：XOR， 02：ANSI9.9 ， 03：ANSI9.19               默认02
	 *                             MAKKEYLEN:MAC 密钥 长度  08: 8 位  ,10： 16位，18:24位      默认8
	 *                             MAKKEY:MAK 密钥                                                                               必须传
	 *                             MSGDATA:参与算mac 的数据                                                               必须传
	 *                             SRCMAC: 要校验的MAC                              必须传
	 * @return                Map  
	 *                        返回加密机算出来的mac
	 *                        
	 * 
	 */
	public String valMac(Map<String,Object> map)throws TranException;
	
	/**
	 * PIN 转加密
	 * @param map             数据{
	 *                            SOUPINKEY:源PINKEY                           必传
	 *                            OBJPINKEY:目的PINKEY                         必传
	 *                            SOUPINTYP:原PIN格式     01 账号   02 无账号                            必传
	 *                            OBJPINTYP:目的PIN格式  01账号   02 无账号                            必传
	 *                            PINBLOCK: 源pin块                                                                   必传                                                    
	 *                            ACTNO   :转换前主账号                                                               必传
	 *                            ACTNO2  :转换后主账号                                                               必传
	 *                           }
	 * @return                
	 *                            PINBLOCK:转加密过后的PINBLOCK
	 *                            
	 * 
	 *                        
	 * 
	 */
	public String  pinTurnEncryption(Map<String,Object> map)throws TranException;
	public String  pinDecryption(Map<String,Object> map)throws TranException;
	
	/**
	 * 获取终端主密钥
	 * 
	 * @param map             数据{KEYTYPE:主密钥类型       终端主密钥                                              默认21
	 *                            KEYLEN:MAC 密钥 长度  08: 8 位  ,10： 16位，18:24位      默认10
	 *                            }
	 * @return                Map{KEYMSG:终端主密钥明文,KEYMSG1:终端主密钥密文,MESDAT:校验码}
	 * 
	 */
	public Map<String,Object> getTermKey(Map<String,Object> map) throws TranException;
	
	
	/**
	 * 获取终端MAC密钥
	 * 
	 * @param map             数据{ ZMK: 终端主密钥                                         必传
	 *                           }
	 * @return                Map{ MAKKEY: 保存到数据库
	 *                             MACKEY1: 给终端
	 *                            
	 *                           }
	 *    
	 * 
	 */
	public Map<String,Object> getTermMacKey(Map<String,Object> map)throws TranException;
	
	
	/**
	 * 获取终端PIN密钥                        
	 *
	 * @param map              数据{ ZMK: 终端主密钥                                         必传
	 *                            }
	 * @return                Map { PINKEY: 保存到数据库
	 *                              PINKEY1: 给终端
	 *                            
	 *                           }  
	 * 
	 */
	public Map<String,Object> getTermPinKey(Map<String,Object> map)throws TranException;
	
	/**
	 * 银联（或第三方机构）MAC 转为本地MAC
	 *
	 * @param map             数据{    TMKINDEX:银联密钥索引        必传
	 *                                MACKEY : 银联MACKEY   必传
	 *                                MESDAT : 校验值                  必传
	 *                            }
	 * @return                Map {
	 *                                MAKKEY: 保存到数据 
	 *                             }
	 *
	 *                          
	 *                             
	 * 
	 */
	public Map<String,Object> getLocalMacKey(Map<String,Object> map)throws TranException;
	
	
	/**
	 * 银联（或第三方机构）pin  转为本地pin
	 *
	 * @param map             数据{ 
	 *                              TMKINDEX:银联密钥索引        必传
	 *                              PINKEY : 银联PINKEY   必传
	 *                              MACKEY : 银联MACKEY   必传
	 *                            }
	 * @return                Map { 
	 *                             PINKEY: 保存到数据 
	 *                            }
     * 
	 */
	public Map<String,Object> getLocalPinKey(Map<String,Object> map)throws TranException;
	
	/**
	 * 密钥分散 
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> keyDisperse(Map<String,Object> param) throws TranException; 
	
	
	/**
	 * 数据解密
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> dataDecryption(Map<String,Object> param) throws TranException; 


	/**
	 * 磁道密钥
	 * @param param
	 * @return
	 * @throws TranException
	 */
	public Map<String,Object> getTDKey(Map<String,Object> param) throws TranException;

	/**
	 * 本地加密机加密第三方明文密钥
	 * */
	public Map<String, Object> encryZMK(Map<String, Object> map) throws TranException;

	/**
	 * 本地密钥转换第三方终端主密钥
	 * */
	public Map<String, Object> getLocalTermKey(Map<String, Object> map) throws TranException; 
}
