package com.tangdi.production.mpaychl.trans.terminal.service.impl;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tangdi.production.mpaychl.base.service.EncryptorService;
import com.tangdi.production.mpaychl.constants.MsgSubST;
import com.tangdi.production.mpaychl.trans.terminal.service.BluetoothTerminalService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 *
 * <b>百富蓝牙设备支付实现类</b>
 * 
 * @author zhengqiang 2015/3/18
 *
 */
public class BFBluetoothTerminalServiceImpl extends BluetoothTerminalService{
	private static Logger log = LoggerFactory
			.getLogger(BFBluetoothTerminalServiceImpl.class);
	@Autowired
	private EncryptorService encryptorService;
	@Override
	public String convertPIN(Map<String,Object> map)
			throws TranException {//String termNo, String pinBlock, String track2
		Map<String,Object> param = new HashMap<String,Object>();
		param.putAll(map);
		String account = map.get("cardNo").toString();
		log.info("卡号:[{}]",account);
		int len = account.length();
		String accountTemp = account.substring(len-13,len-1);
		log.info("截取后卡号:[{}]",account);
		param.put("ACTNO", accountTemp);
		return encryptorService.pinTurnEncryption(param);
	}
	@Override
	public String decryptPIN(Map<String,Object> map)
			throws TranException {//String termNo, String pinBlock, String track2
		
		return null;
	}
	@Override
	public String[] decryptTrack(Map<String,Object> map)
			throws TranException {
		String[] trackInf = new String[5];
		if(String.valueOf(map.get("mediaType")).equals(MsgSubST.CARD_TYPE_1)){
			//磁条卡
			ParamValidate.doing(map,"terminalLMK","track");
			String[] tracks = getTrack(map.get("track").toString());
			log.info("上送的磁道信息密文：{}",tracks[0]);
			Map<String,Object> subkey = new HashMap<String, Object>();
			subkey.put("SUBKEY", map.get("terminalTTK"));
			String track2=getData(tracks[0]);
			log.info("待解析的磁道数据：{}",track2);
			subkey.put("data", track2);
			Map<String,Object> data   = encryptorService.dataDecryption(subkey);
			log.info("解密后磁道数据：{}",data.get("Data"));
			String result=data.get("Data").toString();
			trackInf[0] = replaceTrack(tracks[0],result);
			trackInf[0]=trackInf[0].replace("=", "D");
			log.info("重组后2磁道信息：{}",trackInf[0]);
		}else {
			//IC卡
			ParamValidate.doing(map,"terminalLMK","icdata","track");
			trackInf[0]=getTrack2(map.get("track").toString(),"F").replace("|", "");
			trackInf[2] =map.get("icdata").toString();
			log.info("去除填充字符:ICDATA=[{}]",trackInf[2]);
			log.info("去除填充字符:Track2=[{}]",trackInf[0]);
		}
		//获取卡号
		String cardNo = "";
		if(trackInf[0]!=null){
			cardNo=String.valueOf(trackInf[0]).replace("=", "D").replace("d", "D");
			cardNo=cardNo.substring(0,cardNo.indexOf("D"));
			trackInf[3] = cardNo;
		}
		
		return trackInf;
	}
	/**
	 * 磁道解密后替换密文部分
	 * @param oldTrack 加密后的2磁道信息
	 * @param track 解密后的密文部分
	 * @return
	 */
	public String replaceTrack(String oldTrack,String track){
		String newTrack="";
		if(oldTrack.length()<24){
			newTrack=oldTrack.replace(oldTrack.substring(oldTrack.length()-16,oldTrack.length()), track);
		}else if(oldTrack.length()==32){
			newTrack=oldTrack.replace(oldTrack.substring(8,16), track.substring(0, 8)).replace(oldTrack.substring(oldTrack.length()-8, oldTrack.length()), track.substring(8, 16)).replace("F", "");
		}else {
			newTrack=oldTrack.replace(oldTrack.substring(8,16), track.substring(0, 8)).replace(oldTrack.substring(oldTrack.length()-8, oldTrack.length()), track.substring(8, track.length()));
		}
		return newTrack.replace("=", "D");
	}
	/**
	 * 截取加密的磁道信息密文
	 * @param track
	 * @return
	 */
	public  String getData(String track){
		String data="";
		if(track.length()<24){
			data=track.substring(track.length()-16,track.length());
		}else if(track.length()==32){
			data=track.substring(8, 16)+track.substring(track.length()-8,track.length());
		}else {
			data=track.substring(8, 16)+track.substring(track.length()-8,track.length());
		}
		return data;
	}
/*	@Override
	public Map<String,Object> keyDispers(Map<String, Object> map)
			throws TranException {
		log.info("密钥分算计算执行中... 参数:[源密钥={},分散因子={}]",
				map.get("terminalLMK"),map.get("random"));
		Map<String,Object> rmap = encryptorService.keyDisperse(map);	
		log.info("密钥分算计算完成,子密钥={}",rmap.get("SUBKEY"));
		return 	rmap;
	}*/
	
	@Override
	public Map<String, Object> getLmkey(Map<String, Object> map)
			throws TranException {
		log.debug("进入获取主密钥方法,参数:{}",map.toString());
		return encryptorService.getTermKey(map);
	}
	@Override
	public Map<String, Object> getKey(Map<String, Object> map)
			throws TranException {
		Map<String,Object> encryMap = null;  //加密机返回结果
		Map<String,Object> rmap = new HashMap<String,Object>() ; //返回结果
		//获取工作密钥PINKEy
		encryMap = encryptorService.getTermPinKey(map);
		rmap.putAll(encryMap);
		//获取工作密钥PINKEy
		encryMap = null;
		encryMap = encryptorService.getTDKey(map);
		rmap.putAll(encryMap);
		return rmap;
	}
	
	/**
	 * 获取磁道信息
	 * @param track
	 * @return [0] 2磁道 [1] 3磁道
	 */
	private String[] getTrack(String track) throws TranException{
		log.info("磁道信息:{}",track);
		String[] tracks = new String[]{"",""};
		if(!track.contains("|")){
			throw new TranException(ExcepCode.EX100101,"磁道数据格式有误,缺少分隔符\"|\"");
		}
		tracks = track.split("\\|");
		log.info("磁道信息解析[2磁道={}]",tracks[0]);
		return tracks;
	}

	/**
	 * 去掉Track2后补F 。
	 * @param data  2磁道数据
	 * @param fill  填充字符
	 * @return track2
	 */
	public static String getTrack2(String data,String fill){
		return data.replace("F", "");
	}
	/**
	 * 去掉ICDATA后补F , 通过9F41计算器Tag 方式。
	 * @param data ICDATA
	 * @param tag  9F41 最后一个Tag
	 * @return icdata
	 */
	public static String getICData(String data,String tag){
		String temp_1 = data.substring(0, data.indexOf(tag));
		String temp_2 = data.substring(data.indexOf(tag));
		int tagLen = Integer.valueOf(temp_2.substring(tag.length(),tag.length()+2));
		String temp_3 = temp_2.substring(0, tag.length()+2+tagLen*2);
		return temp_1+temp_3;
	}
}
