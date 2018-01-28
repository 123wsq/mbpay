package com.tangdi.production.mpaychl.trans.encry.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpaychl.trans.encry.service.SJL05EncryptorService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.tdbase.util.DesUtils;
import com.tangdi.production.tdcomm.channel.CallThrids;

/**
 * <b>江南SJL05加密机接口实现类</b></br>
 * 
 * @author 
 *
 */
@Service
public class SJL05EncryptorServiceImpl implements SJL05EncryptorService {

	private static Logger log = LoggerFactory
			.getLogger(SJL05EncryptorServiceImpl.class);
	
	private final static String RSPCODE_OK = "A";
	
	@Override
	public String getMac(Map<String, Object> map) throws TranException {
		log.debug("MAC计算中...");
		String mac = null;
		//效验参数
		ParamValidate.doing(map, "MACKEY","MesDat");

		String msgdata = String.valueOf(map.get("MesDat"));
		log.debug("msgdata={}",msgdata);
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("InsCod", "D132") ;       //指令 
		pdata.put("macflg", "04") ;        //mac算法ECB
		pdata.put("maklen", "08") ;        //密钥长度
		pdata.put("makkey", map.get("MACKEY"));        //密钥
		pdata.put("inivec", "0000000000000000");         //初始向量
		String dataLen = DesUtils.Int2Hex(msgdata.length()/2, 4);
		pdata.put("datlen", dataLen);      //消息长度 
		pdata.put("datval", msgdata);     //消息数据
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("mac_gen", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {
			log.info("计算MAC成功");
			mac = String.valueOf(rdata.get("MAC"));
			map.put("MAC", mac);
		} else {
			throw new TranException(ExcepCode.EX100201);
		}
		log.debug("MAC计算完成.MAC={}",mac);
		return mac;
	}

	@Override
	public String valMac(Map<String, Object> map) throws TranException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * PIN转加密
	 */
	@Override
	public String pinTurnEncryption(Map<String, Object> map)
			throws TranException {
		log.debug("PIN密钥转加密中...");
		String pinblock = null;
		/**
		 * terminalPIK  源工作密钥（终端设备）
		 * merPIK       目的工作密钥(合作机构大商户的工作密钥)
		 * pinblk     PIN块格式
		 * ACTNO         账号
		 */
		ParamValidate.doing(map, "terminalPIK","merPIK","pinblk","cardNo");
		String msgdata = String.valueOf(map.get("MesDat"));
		log.debug("msgdata={}",msgdata);
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("InsCod", "D124") ;             //指令
		pdata.put("pik1len", "10") ;              //原密钥长度
		pdata.put("pik1", map.get("terminalPIK")) ;   //原密钥    
		pdata.put("pik2len", "10") ;                  //目的密钥长度
		pdata.put("pik2",  map.get("merPIK")) ;       //目的密钥
		pdata.put("pin1format", "01") ;               //原密码格式
		pdata.put("pin2format", "01") ;                //目的密码格式
		pdata.put("ac_before", map.get("cardNo")) ;    //卡号
		pdata.put("split_before", ";") ;       
		pdata.put("ac_after", map.get("cardNo")) ;      
		pdata.put("split_after",";") ;    
		pdata.put("oriPinBlk",map.get("pinblk")) ; 
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("pin_convert", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {
			log.info("转加密PIN成功");
			pinblock = String.valueOf(rdata.get("ObjPIN"));
		} else {
			throw new TranException(ExcepCode.EX100202);
		}
		log.debug("PIN密钥转加密完成.KEY={}",pinblock);
		return pinblock;
	}
	
	public String pinDecryption(Map<String, Object> map)
			throws TranException {
		log.debug("PIN密钥转加密中...");
		String pinblock = null;
		/**
		 * terminalPIK  源工作密钥（终端设备）
		 * merPIK       目的工作密钥(合作机构大商户的工作密钥)
		 * pinblk     PIN块格式
		 * ACTNO         账号
		 */
		ParamValidate.doing(map, "terminalPIK","pinblk","cardNo");
		String msgdata = String.valueOf(map.get("MesDat"));
		log.debug("msgdata={}",msgdata);
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("InsCod", "D126") ;             //指令
		pdata.put("pik1len", "10") ;              //原密钥长度
		pdata.put("pik1", map.get("terminalPIK")) ;   //原密钥    
		pdata.put("pin1format", "01") ;               //原密码格式
		pdata.put("ac_before", map.get("cardNo")) ;    //卡号
		pdata.put("oriPinBlk",map.get("pinblk")) ; 
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("pin_dec", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {
			log.info("转加密PIN成功");
			pinblock = String.valueOf(rdata.get("ObjPIN"));
		} else {
			throw new TranException(ExcepCode.EX100202);
		}
		log.debug("PIN密钥转加密完成.KEY={}",pinblock);
		return pinblock;
	}

	/**
	 * 获取终端传输密钥
	 * 产生<b>LMK</b> 和<b>ZMK</b></br>
	 * <b>LMK</b> 存储DB</br>
	 * <b>ZMK</b> 存储到文件中,灌入终端设备中。
	 */
	@Override
	public Map<String, Object> getTermKey(Map<String, Object> map)
			throws TranException {
		log.debug("传输密钥生成中...");
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("InsCod", "D107") ;       //指令 
		pdata.put("KeyLen", "10") ;     //LMK密钥方案单倍长
		pdata.put("KeyTyp", "13") ;      //密钥类型0x01：通信主密钥;0x11：PIN加密密钥	0x12：MAC计算密钥0x13：数据加密密钥0x21:CVV计算密钥
		pdata.put("ZmkIdx", "000A") ;     //ZMK索引
		pdata.put("ZmkLen", "10") ;     //LMK密钥方案单倍长
		//pdata.put("LmkZmk", "DDB693EA07D09C3DDDB693EA07D09C3D") ;
		//pdata.put("LmkZmk", String.valueOf(map.get("ZMK"))) ;
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("key_gen", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
		Map<String,Object> zekkey = null;
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {
			zekkey = new HashMap<String,Object>();
			zekkey.put("LMK",   rdata.get("lmkkey"));
			zekkey.put("ZMK",   rdata.get("zmkkey"));
			zekkey.put("KEYVALUE", rdata.get("keycv"));
		} else {
			throw new TranException(ExcepCode.EX100203,"获取工作密钥MACKEY失败！");
		}
		log.info("传输密钥生成完成.KEY={}",zekkey);
		return zekkey;

	}

	@Override
	public Map<String, Object> getTermMacKey(Map<String, Object> map)
			throws TranException {
		log.debug("MAC密钥生成中...");
		Map<String, Object> macKey=null;
		
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("KeyLen", "08") ;     //LMK密钥方案单倍长
		pdata.put("InsCod", "D107") ;       //指令 
		pdata.put("KeyTyp", "12") ;      //密钥类型0x01：通信主密钥;0x11：PIN加密密钥	0x12：MAC计算密钥0x13：数据加密密钥0x21:CVV计算密钥
		pdata.put("ZmkIdx", "000A") ;     //ZMK索引
		pdata.put("ZmkLen", "10") ;     //LMK密钥方案单倍长
		
//		if("02".equals(map.get("termType"))){
//			pdata.put("ZmkIdx", "000A") ;     //ZMK索引
//		}else{
//			pdata.put("ZmkIdx", "FFFF") ;     //ZMK索引
//			pdata.put("LmkZmk", String.valueOf(map.get("ZMK")));
//		}

		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("key_gen", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {
			macKey = new HashMap<String,Object>();
			macKey.put("LMAKKEY",   rdata.get("lmkkey"));
			macKey.put("ZMAKKEY",   rdata.get("zmkkey"));
			macKey.put("ZMAKVALUE", rdata.get("keycv"));
		} else {
			throw new TranException(ExcepCode.EX100203,"获取工作密钥MACKEY失败！");
		}
		log.info("MAC密钥生成完成.KEY={}",macKey);
		return macKey;
	}
	

	@Override
	public Map<String, Object> getTermPinKey(Map<String, Object> map)
			throws TranException {
		log.debug("PIN密钥生成中...");
		
		Map<String, Object> pinKey=null;
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("KeyLen", "10") ;     //LMK密钥方案单倍长
		pdata.put("InsCod", "D107") ;       //指令 
		pdata.put("KeyTyp", "11") ;      //密钥类型0x01：通信主密钥;0x11：PIN加密密钥	0x12：MAC计算密钥0x13：数据加密密钥0x21:CVV计算密钥
		pdata.put("ZmkIdx", "000A") ;     //ZMK索引
		pdata.put("ZmkLen", "10") ;     //LMK密钥方案单倍长
		
//		if("02".equals(map.get("termType"))){
//			pdata.put("ZmkIdx", "000A") ;     //ZMK索引
	//	}else{
	//		pdata.put("ZmkIdx", "FFFF") ;     //ZMK索引
	//		pdata.put("LmkZmk", String.valueOf(map.get("ZMK")));
	//	}
		
		
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("key_gen", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {			
			pinKey = new HashMap<String,Object>();
			pinKey.put("LPINKEY",   rdata.get("lmkkey"));
			pinKey.put("ZPINKEY",   rdata.get("zmkkey"));
			pinKey.put("ZPINVALUE", rdata.get("keycv"));
			
		} else {
			throw new TranException(ExcepCode.EX100203,"获取工作密钥PINKEY失败!");
		}
		log.info("PIN密钥生成完成.KEY={}",pinKey);
		return pinKey;
	}

	/*
	 * 获取第三方的密钥为根密钥加密不用转换
	 * 
	 */
	@Override
	public Map<String, Object> getLocalMacKey(Map<String, Object> map)
			throws TranException {
		log.debug("MAC密钥从ZMK加密转换为LMK加密...");		
		Map<String, Object> macKey=null;
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		
		pdata.put("InsCod", "D102") ;     	//指令 
		pdata.put("KeyTyp", "12") ;      	//密钥类型0x01：通信主密钥;0x11：PIN加密密钥	0x12：MAC计算密钥0x13：数据加密密钥0x21:CVV计算密钥
		pdata.put("ZmkIdx", "FFFF") ;     	//ZMK索引 FFFF:提供ZMK
		pdata.put("LmkZmk", String.valueOf(map.get("ZMK"))) ;	//BMK
		pdata.put("ZmkKey", String.valueOf(map.get("MACKEY"))); //工作密钥
		pdata.put("KeyLen", "08") ;     //工作密钥长度	LMK密钥方案单倍长
		pdata.put("ZmkLen", "10") ;     //通信主密钥长度	LMK密钥方案单倍长
		
		
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("D102_CONVERT", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {			
			macKey = new HashMap<String,Object>();
			macKey.put("LMACKEY",   rdata.get("lmkkey"));
			macKey.put("LMACKEYCV", rdata.get("keycv"));
		} else {
			throw new TranException(ExcepCode.EX100203,"获取工作密钥MACKEY失败!");
		}
		log.info("PIN密钥生成完成.KEY={}",macKey);
		
		return macKey;
	}
	/*
	 * 获取第三方的密钥为根密钥加密不用转换
	 * 
	 */
	@Override
	public Map<String, Object> getLocalPinKey(Map<String, Object> map)
			throws TranException {
		log.debug("PIN密钥从ZMK加密转换为LMK加密...");
		
		Map<String, Object> pinKey=null;
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		
		pdata.put("InsCod", "D102") ;     	//指令 
		pdata.put("KeyTyp", "11") ;      	//密钥类型0x01：通信主密钥;0x11：PIN加密密钥	0x12：MAC计算密钥0x13：数据加密密钥0x21:CVV计算密钥
		pdata.put("ZmkIdx", "FFFF") ;     	//ZMK索引 FFFF:提供ZMK
		pdata.put("LmkZmk", String.valueOf(map.get("ZMK"))) ;	//BMK
		pdata.put("ZmkKey", String.valueOf(map.get("PINKEY"))); //工作密钥
		pdata.put("KeyLen", "10") ;     //工作密钥长度	LMK密钥方案单倍长
		pdata.put("ZmkLen", "10") ;     //通信主密钥长度	LMK密钥方案单倍长
		
		
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("D102_CONVERT", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {			
			pinKey = new HashMap<String,Object>();
			pinKey.put("LPINKEY",   rdata.get("lmkkey"));
			pinKey.put("LPINKEYCV", rdata.get("keycv"));
			
		} else {
			throw new TranException(ExcepCode.EX100203,"获取工作密钥PINKEY失败!");
		}
		log.info("PIN密钥生成完成.KEY={}",pinKey);
		
		return pinKey;
	}
	
	/**转换第三方终端主密钥*/
	@Override
	public Map<String, Object> getLocalTermKey(Map<String, Object> map) throws TranException{
		log.debug("TMK从zmk加密转换为lmk加密...");
		log.info("转换密钥参数：{}",map.toString());
		Map<String, Object> termKey=null;
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		
		pdata.put("InsCod", "D102") ;     	//指令 
		pdata.put("KeyTyp", "01") ;      	//密钥类型0x01：通信主密钥;0x11：PIN加密密钥	0x12：MAC计算密钥0x13：数据加密密钥0x21:CVV计算密钥
		pdata.put("ZmkIdx", "0004") ;     	//ZMK索引 FFFF:提供ZMK
		//pdata.put("LmkZmk", String.valueOf(map.get("ZMK"))) ;	//BMK
		pdata.put("ZmkKey", String.valueOf(map.get("lmk"))); //终端主密钥
		pdata.put("KeyLen", "10") ;     //工作密钥长度	LMK密钥方案单倍长
		pdata.put("ZmkLen", "10") ;     //通信主密钥长度	LMK密钥方案单倍长
		
		
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("D102_CONVERT", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {			
			termKey = new HashMap<String,Object>();
			termKey.put("LTERMKEY",   rdata.get("lmkkey"));
			termKey.put("LTERMKEYCV", rdata.get("keycv"));
			
		} else {
			throw new TranException(ExcepCode.EX100203,"获取工作密钥TERMKEY失败!");
		}
		log.info("TMK密钥生成完成.KEY={}",termKey);
		
		return termKey;
	}
	
	@Override
	public Map<String, Object> encryZMK(Map<String, Object> map) throws TranException{
		log.debug("本地加密机加密第三方明文密钥");
		Map<String, Object> lmkkey=null;
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("KeyLen", "10") ;     //LMK密钥方案单倍长
		pdata.put("InsCod", "D108") ;       //指令 
		pdata.put("KeyTyp", "01") ;      //密钥类型0x01：通信主密钥;0x11：PIN加密密钥	0x12：MAC计算密钥0x13：数据加密密钥0x21:CVV计算密钥
		//pdata.put("ZmkIdx", "000A") ;     //ZMK索引
		//pdata.put("ZmkLen", "10") ;     //LMK密钥方案单倍长
		pdata.put("LmkZmk", "31313131313131313131313131313131") ;
		//pdata.put("LmkZmk", "D3E03C3618128A9FD3E03C3618128A9F") ;
		//pdata.put("LmkZmk", String.valueOf(map.get("ZMK"))) ;
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("d108_convert", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {			
			lmkkey = new HashMap<String,Object>();
			lmkkey.put("LTERMKEY",   rdata.get("lmkkey"));
			lmkkey.put("LTERMKEYCV", rdata.get("keycv"));
			
		} else {
			throw new TranException(ExcepCode.EX100203,"加密第三方明文失败!");
		}
		log.info("PIN密钥生成完成.KEY={}",lmkkey);
		return lmkkey;
	}
	

	@Override
	public Map<String, Object> keyDisperse(Map<String, Object> map)
			throws TranException {
		
		log.debug("密钥分散转换中...");
		ParamValidate.doing(map, "terminalLMK","random","track");
		
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("InsCod", "D156") ;             //指令
		pdata.put("SourceKeyLen", "10") ;     //源密钥类型
		pdata.put("SourceKey", map.get("terminalLMK")) ; //源密钥  双倍长
		pdata.put("SourceKeyType", "01") ;     //源密钥类型
		pdata.put("SubKeyType", "00A") ;        //子密钥类型
		pdata.put("BmkLen", "10") ; //加密密钥长度
		pdata.put("Bmk",map.get("terminalLMK")) ;     //加密密
		pdata.put("WorkKeyType", "13") ;            //子密钥标识
		pdata.put("Mode", "01") ;                //分散标志
		pdata.put("Level", "01") ;              //分算级数
		pdata.put("Factor", map.get("random")) ;//分算因子
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("key_Disperse", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {
			log.info("密钥分散成功.");
		} else {
			throw new TranException(ExcepCode.EX100208,"密钥分散失败");
		}
		log.debug("密钥分散完成.KEY={}",map);
		return map;
	}
	@Override
	public Map<String, Object> dataDecryption(Map<String, Object> map)
			throws TranException {
		log.debug("数据解密中...");
		ParamValidate.doing(map, "SUBKEY","data");
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("InsCod", "D114") ;             //指令		
		pdata.put("WorkKey", map.get("SUBKEY")) ;            //子密钥
		pdata.put("ArithmeticMod", "12") ;        //算法模式
		String data = (String)map.get("data");
		String dataLen = DesUtils.Int2Hex(data.length()/2, 4);;
		pdata.put("datlen", dataLen) ;               //数据长度
		pdata.put("datval", data) ;            //填充字符
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("data_Decryption", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {
			log.info("数据解密成功.data={}",rdata.get("Data"));
		} else {
			throw new TranException(ExcepCode.EX100209,"数据解密失败.");
		}
		log.debug("数据解密完成.");
		return map;
	}

	@Override
	public Map<String, Object> getTDKey(Map<String, Object> map)
			throws TranException {
		log.debug("TDK密钥生成中...");
		
		Map<String, Object> tdkKey=null;
		//组加密机报文
		Map<String,Object> pdata = new HashMap<String,Object>();
		pdata.put("KeyLen", "10") ;     //LMK密钥方案单倍长
		pdata.put("InsCod", "D107") ;       //指令 
		pdata.put("KeyTyp", "13") ;      //密钥类型0x01：通信主密钥;0x11：PIN加密密钥	0x12：MAC计算密钥0x13：数据加密密钥0x21:CVV计算密钥
		pdata.put("ZmkIdx", "FFFF") ;     //ZMK索引
		pdata.put("ZmkLen", "10") ;     //LMK密钥方案单倍长
		//pdata.put("LmkZmk", "5EEB89A5B8264EC862F3125DB675ADF5") ;
		pdata.put("LmkZmk", String.valueOf(map.get("ZMK"))) ;
		log.debug("请求数据:{}",pdata.toString());
		Map<String, Object> rdata = CallThrids.CallThirdOther("key_gen", "STHDSJL05", pdata);
		map.putAll(rdata);
		log.debug("响应数据:{}",rdata);
		String retcode = String.valueOf(rdata.get("RETCODE"));
		String rspcode = String.valueOf(rdata.get("RSPCODE"));
	
		if (retcode.equals(MsgCT.RETCODE_OK) && rspcode.equals(RSPCODE_OK)) {			
			tdkKey = new HashMap<String,Object>();
			tdkKey.put("LTDKKEY",   rdata.get("lmkkey"));
			tdkKey.put("ZTDKKEY",   rdata.get("zmkkey"));
			tdkKey.put("ZTDKVALUE", rdata.get("keycv"));
			
		} else {
			throw new TranException(ExcepCode.EX100203,"获取工作密钥TDKKEY失败!");
		}
		log.info("PIN密钥生成完成.KEY={}",tdkKey);
		return tdkKey;
	}

	


}
