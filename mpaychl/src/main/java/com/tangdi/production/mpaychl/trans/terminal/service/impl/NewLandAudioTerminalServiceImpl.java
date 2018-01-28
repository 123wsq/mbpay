package com.tangdi.production.mpaychl.trans.terminal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpaychl.constants.Key;
import com.tangdi.production.mpaychl.constants.MsgSubST;
import com.tangdi.production.mpaychl.trans.terminal.service.AudioTerminalService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;

/**
 * <b>新大陆厂商实现</b>
 * 
 * @author sunhaining 2015/7/03
 *
 */
@Service
public class NewLandAudioTerminalServiceImpl 
		extends AudioTerminalService {

	private static Logger log = LoggerFactory.
			getLogger(NewLandAudioTerminalServiceImpl.class);

	@Override
	public String decryPIN(String pinkey, String pinBlock, String account)
			throws TranException {
		return null;
	}

	@Override
	public String convertPIN(Map<String, Object> map)
			throws TranException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.putAll(map);
		String account = map.get("cardNo").toString();
		log.info("卡号:[{}]",account);
		int len = account.length();
		String accountTemp = account.substring(len-13,len-1);
		log.info("截取后卡号:[{}]",accountTemp);
		param.put("ACTNO", accountTemp);
		return getEncryptorService().pinTurnEncryption(param);
	}
	
	@Override
	public String decryptPIN(Map<String, Object> map)
			throws TranException {
		Map<String,Object> param = new HashMap<String,Object>();
		param.putAll(map);
		String account = map.get("cardNo").toString();
		log.info("卡号:[{}]",account);
		int len = account.length();
		String accountTemp = account.substring(len-13,len-1);
		log.info("截取后卡号:[{}]",accountTemp);
		param.put("ACTNO", accountTemp);
		return getEncryptorService().pinDecryption(param);
	}

	/**
	 * <b>磁道解密</b> 使用加密做密钥分散和数据解密</br>
	 * 1.随机数分散磁道密钥</br>
	 * 2.数据解密</br>
	 */
	@Override
	public String[] decryptTrack(Map<String, Object> map)
			throws TranException {
		String[] trackInf = new String[5];
		if(String.valueOf(map.get("mediaType")).equals(MsgSubST.CARD_TYPE_1)){
			//磁条卡
			ParamValidate.doing(map, "random","terminalLMK","track");
			String[] tracks = getTrack(map.get("track").toString());
			Map<String,Object> subkey = new HashMap<String,Object>(); //keyDispers(map);
			String etrack2_  = tracks[0];
			String etrack2__ = getTrackData(etrack2_);
			//天谕
			if("TY".equals(map.get("terminalCom"))){
				etrack2__ = etrack2_;
			}
			
			subkey.put("SUBKEY", map.get("terminalLMK"));
			
			subkey.put("data", etrack2__);
			Map<String,Object> data   = getEncryptorService().dataDecryption(subkey);
			
			String rtrack2_ = data.get("Data").toString();
			if(rtrack2_.length() != 16){
				rtrack2_ = rtrack2_ + "0000000000000000";
				rtrack2_ = rtrack2_.substring(0, 16);
			}
			trackInf[0] = etrack2_.replace(etrack2__, rtrack2_);
			//天谕
			if("TY".equals(map.get("terminalCom"))){
				rtrack2_ = data.get("Data").toString();
				rtrack2_ = rtrack2_.substring(0, rtrack2_.indexOf("FF"));
				trackInf[0] = rtrack2_;
			}
			
			log.info("解密后磁道数据:[track2={},track3={}]",trackInf[0],"");
	
			log.info("去除填充字符:Track2=[{}]",trackInf[0]);
			
		}else{
			//IC卡
			ParamValidate.doing(map, "random","terminalLMK","icdata","track");
			//String track2 = null; //2磁道数据
			String icdata = map.get("icdata").toString(); //IC卡数据
			//Map<String,Object> subkey = keyDispers(map);
			String[] tracks = getTrack(map.get("track").toString());
			Map<String,Object> subkey = new HashMap<String,Object>();
			String etrack2_  = tracks[0];
			String etrack2__ = getTrackData(etrack2_);
			if("TY".equals(map.get("terminalCom"))){
				etrack2__ = etrack2_;
			}
			subkey.put("SUBKEY", map.get("terminalLMK"));
			
			subkey.put("data", etrack2__);
			Map<String,Object> data   = getEncryptorService().dataDecryption(subkey);
			
			String rtrack2_ = data.get("Data").toString();
			log.debug("rtrack2_=[{}]",rtrack2_);
			if(rtrack2_.length() != 16){
				rtrack2_ = rtrack2_ + "0000000000000000";
				rtrack2_ = rtrack2_.substring(0, 16);
			}
			trackInf[0] = etrack2_.replace(etrack2__, rtrack2_);
			//天谕
			if("TY".equals(map.get("terminalCom"))){
				rtrack2_ = data.get("Data").toString();
				rtrack2_ = rtrack2_.substring(0, rtrack2_.indexOf("FF"));
				trackInf[0] = rtrack2_;
			}
			
			log.info("解密后Track2磁道数据:[{}]",trackInf[0]);
			
			//subkey.put("data", map.get("icdata"));
			//ICDATA解密
			//Map<String,Object> data_2  = getEncryptorService().dataDecryption(subkey);
			
			//icdata = data_2.get("Data").toString();
			log.info("解密后ICDATA数据:[{}]",icdata);
			//trackInf[0] = getTrack2(track2,"F");
			trackInf[2] = icdata;
			
			log.info("去除填充字符:ICDATA=[{}]",trackInf[2]);
			log.info("去除填充字符:Track2=[{}]",trackInf[0]);
		}
		//获取卡号
		String cardNo = "";
		if(trackInf[0]!=null){
			cardNo=String.valueOf(trackInf[0]).replace("=", "D").replace("d", "D");
			trackInf[0] = cardNo;
			cardNo=cardNo.substring(0,cardNo.indexOf("D"));
			trackInf[3] = cardNo;
		}
		
		return trackInf;
	}
	/**
	 * 获取磁道信息
	 * @param track
	 * @return [0] 2磁道 [1] 3磁道
	 */
	private static String[] getTrack(String track) throws TranException{
		log.info("磁道信息:{}",track);

		String[] tracks = new String[]{"",""};
		if(!track.contains("|")){
			throw new TranException(ExcepCode.EX100101,"磁道数据格式有误,缺少分隔符\"|\"");
		}
		tracks = track.split("\\|");
		log.info("磁道信息解析[2磁道={},3磁道={}]",tracks[0],"");
		return tracks;
	}

	@Override
	public Map<String, Object> getLmkey(Map<String, Object> map)
			throws TranException {
		log.debug("进入获取主密钥方法,参数:{}",map.toString());
		String keyType = "000";
		log.info("设置主密钥生成类型[{}]",keyType);
		map.put("KeyTyp", keyType);
		Map<String,Object> tmkkey = getEncryptorService().getTermKey(map);
		Map<String,Object> tdkey  = new HashMap<String,Object>();
		tdkey.put("ZMK", tmkkey.get("LMK"));
		Map<String,Object> ttkkey = getEncryptorService().getTDKey(tdkey);
		//
		tmkkey.putAll(ttkkey);
		return tmkkey;

	}

	/**
	 * 设备获取工作密钥
	 */
	@Override
	public Map<String, Object> getKey(Map<String, Object> map)
			throws TranException {
		
		Map<String,Object> encryMap = null;  //加密机返回结果
		Map<String,Object> rmap = new HashMap<String,Object>() ; //返回结果
		/**
		 * 音频设备时固定为ZMK(LMK加密)与APP中的主密钥一致。
		 */
		//map.put("ZMK", Key.ZMK); 
		
		//获取工作密钥PINKEy
		encryMap = getEncryptorService().getTermPinKey(map);
		rmap.putAll(encryMap);
		//获取工作密钥PINKEy
		encryMap = null;
		encryMap = getEncryptorService().getTermMacKey(map);
		rmap.putAll(encryMap);
		return rmap;
	}

	@Override
	public Map<String,Object> keyDispers(Map<String, Object> map)
			throws TranException {
		log.info("密钥分算计算执行中... 参数:[源密钥={},分散因子={}]",
				map.get("terminalLMK"),map.get("random"));
		
		Map<String,Object> rmap = getEncryptorService().keyDisperse(map);	
		log.info("密钥分算计算完成,子密钥={}",rmap.get("SUBKEY"));
		return 	rmap;

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
	/**
	 * 去掉Track2后补F 。
	 * @param data  2磁道数据
	 * @param fill  填充字符
	 * @return track2
	 */
	public static String getTrack2(String data,String fill){
		return data.replace("", "D");
	}
	
	private static String getTrackData(String track2){
		String trdata = "";
		int trackLen = track2.length();
		if(trackLen % 2 == 0){
			trdata = track2.substring(trackLen-18, trackLen-2);
		}else{
			
			trdata = track2.substring(trackLen-17, trackLen-1);
		}
		return trdata;
	}
	
	
	

}
