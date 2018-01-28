package com.tangdi.production.mpaychl.trans.encry.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangdi.production.mpaychl.trans.encry.service.CodeEncryptionService;
import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ParamValidate;
import com.tangdi.production.mpbase.util.TdDesUtil;
import com.tangdi.production.mpbase.util.TdXOR;

/**
 * 软加密实现类
 * @author zhengqiang
 *
 */
public class CodeEncryptionServiceImpl implements CodeEncryptionService {
	private static Logger log = LoggerFactory.getLogger(CodeEncryptionServiceImpl.class);

	@Override
	public String getMac(Map<String, Object> map) throws TranException {
		log.debug("MAC计算中...");
		String mac = null;
		// 效验参数
		ParamValidate.doing(map, "MACKEY", "MesDat");
		String msgdata = String.valueOf(map.get("MesDat"));
		log.debug("msgdata={}", msgdata);
		try {
			mac = TdXOR.MacEcb16Mw(String.valueOf(map.get("MACKEY")), msgdata);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100201);
		}
		log.debug("MAC计算完成.MAC={}", mac);
		return mac;
	}

	@Override
	public String valMac(Map<String, Object> map) throws TranException {
		log.debug("MAC计算中...");
		String mac = null;
		// 效验参数
		ParamValidate.doing(map, "MACKEY", "MesDat");
		String msgdata = String.valueOf(map.get("MesDat"));
		log.debug("msgdata={}", msgdata);
		try {
			mac = TdXOR.MacEcb16Mw(String.valueOf(map.get("MACKEY")), msgdata);
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100201);
		}
		log.debug("MAC计算完成.MAC={}", mac);
		return mac;
	}

	@Override
	public String pinTurnEncryption(Map<String, Object> map) throws TranException {
		log.debug("PIN密钥转加密中...");
		String pinblock = null;
		/**
		 * terminalPIK 源工作密钥（终端设备） merPIK 目的工作密钥(合作机构大商户的工作密钥) pinblk PIN块格式
		 * ACTNO 账号
		 */
		ParamValidate.doing(map, "terminalPIK", "merPIK", "pinblk", "ACTNO");

		pinblock = "";
		String Tmk = "11111111111111110123456789ABCDEF";
		String zmk = "11111111111111110123456789ABCDEF";
		String Tpink = map.get("terminalPIK") == null ? "" : map.get("terminalPIK").toString();
		String pinkey = map.get("merPIK") == null ? "" : map.get("merPIK").toString();

		try {
			pinblock = TdXOR.pinReEncry(Tmk, zmk, Tpink, pinkey, map.get("ACTNO").toString(), map.get("pinblk").toString());
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100202);
		}
		log.debug("PIN密钥转加密完成.KEY={}", pinblock);
		return pinblock;
	}
	@Override
	public String pinDecryption(Map<String, Object> map) throws TranException {
		log.debug("PIN密钥转加密中...");
		String pinblock = null;
		
		return pinblock;
	}

	@Override
	public Map<String, Object> getTermKey(Map<String, Object> map) throws TranException {
		log.debug("传输密钥生成中...");

		Map<String, Object> zekkey = new HashMap<String, Object>();
		try {
			// 生成ZMK明文随机数
			String rdmkey = TdDesUtil.GetRdmKey(32);
			// zmk 明文
			String bdk14 = "11111111111111110123456789ABCDEF";
			String initchkvalue = "0000000000000000";
			zekkey.put("LMK", rdmkey);
			zekkey.put("ZMK", TdDesUtil.UnionEncryptData(bdk14, rdmkey));
			zekkey.put("KEYVALUE", TdDesUtil.UnionEncryptData(rdmkey, initchkvalue).substring(0, 8));
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100203, "获取工作密钥MACKEY失败！");
		}
		log.info("传输密钥生成完成.KEY={}", zekkey);
		return zekkey;
	}

	@Override
	public Map<String, Object> getTermMacKey(Map<String, Object> map) throws TranException {
		return null;
	}

	@Override
	public Map<String, Object> getTermPinKey(Map<String, Object> map) throws TranException {
		log.debug("PIN密钥生成中...");
		Map<String, Object> pinKey = new HashMap<String, Object>();
		try {
			String ZMK = String.valueOf(map.get("ZMK"));
			String RanNum = TdDesUtil.GetRdmKey(32);
			String LPINKEY = TdDesUtil.UnionEncryptData(ZMK, RanNum);
			String initchkvalue = "0000000000000000";
			pinKey.put("LPINKEY", LPINKEY);
			pinKey.put("ZPINKEY", LPINKEY);
			pinKey.put("ZPINVALUE", TdDesUtil.UnionEncryptData(LPINKEY, initchkvalue).substring(0, 8));
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100203, "获取工作密钥PINKEY失败!");
		}
		log.info("PIN密钥生成完成.KEY={}", pinKey);
		return pinKey;
	}

	@Override
	public Map<String, Object> getLocalMacKey(Map<String, Object> map) throws TranException {
		return null;
	}

	@Override
	public Map<String, Object> getLocalPinKey(Map<String, Object> map) throws TranException {
		return null;
	}

	@Override
	public Map<String, Object> keyDisperse(Map<String, Object> param) throws TranException {
		log.debug("密钥分散转换中...");
		ParamValidate.doing(param, "terminalLMK", "random", "track");
		try {
			param.put("SUBKEY", TdDesUtil.GenRandomKey(param.get("terminalLMK").toString(), param.get("random").toString()));
			log.info("密钥分散成功.");
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100208, "密钥分散失败");
		}
		log.debug("密钥分散完成.KEY={}", param);
		return param;
	}

	@Override
	public Map<String, Object> dataDecryption(Map<String, Object> param) throws TranException {
		log.debug("数据解密中...");
		ParamValidate.doing(param, "SUBKEY", "data");
		try {
			String MwTrackInf = TdDesUtil.UnionDecryptData(param.get("SUBKEY").toString(), param.get("data").toString());
			param.put("Data", MwTrackInf);
			log.info("数据解密成功.data={}", param.get("Data"));
		} catch (Exception e) {
			throw new TranException(ExcepCode.EX100209, "数据解密失败.");
		}

		log.debug("数据解密完成.");
		return param;
	}

	@Override
	public Map<String, Object> getTDKey(Map<String, Object> param)
			throws TranException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> encryZMK(Map<String, Object> map)
			throws TranException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getLocalTermKey(Map<String, Object> map) throws TranException {
		// TODO Auto-generated method stub
		return null;
	}

}
