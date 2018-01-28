package com.tangdi.production.mpcoop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.util.MSGCODE;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpcoop.bean.CooporgMerInf;
import com.tangdi.production.mpcoop.bean.CooporgRouteInf;
import com.tangdi.production.mpcoop.bean.CooporgTermkeyInf;
import com.tangdi.production.mpcoop.dao.CooporgMerDao;
import com.tangdi.production.mpcoop.service.CooporgMerService;
import com.tangdi.production.mpcoop.service.CooporgRouteService;
import com.tangdi.production.mpcoop.service.CooporgTermkeyService;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.rpc.hessian.HessinClientBean;

/**
 * 
 * @author shanbeiyi
 * @version 1.0
 *
 */
@Service
public class CooporgMerServiceImpl implements CooporgMerService {
	private static final Logger log = LoggerFactory.getLogger(CooporgMerServiceImpl.class);
	@Autowired
	private CooporgMerDao dao;
	
	@Autowired
	private GetSeqNoService seqNoService;

	@Autowired
	private HessinClientBean remoteService;
	@Autowired
	private CooporgRouteService cooporgRouteService;
	
	@Autowired
	private CooporgTermkeyService cooporgTermkeyService;


	@Override
	public List<CooporgMerInf> getListPage(CooporgMerInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CooporgMerInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CooporgMerInf getEntity(CooporgMerInf entity) throws Exception {
		log.debug("方法参数：" + entity.debug());
		CooporgMerInf cooporgMerInf = null;
		try {
			cooporgMerInf = dao.selectEntity(entity);
		} catch (Exception e) {
			throw new Exception("查询信息异常!" + e.getMessage(), e);
		}
		log.debug("处理结果:", cooporgMerInf.debug());
		return cooporgMerInf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addEntity(CooporgMerInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			//将数据中的金额转换为分存入数据库			
			
			entity.setDtLimit(MoneyUtils.toStrCent(entity.getDtLimit()));
			entity.setMtLimit(MoneyUtils.toStrCent(entity.getMtLimit()));
			entity.setLowLimit(MoneyUtils.toStrCent(entity.getLowLimit()));			
			entity.setHighLimit(MoneyUtils.toStrCent(entity.getHighLimit()));
			entity.setRateTypeTop(MoneyUtils.toStrCent(entity.getRateTypeTop()));
			entity.setMerStatus("0"); //商户状态 0 正常
			rt = dao.insertEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("插入数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int modifyEntity(CooporgMerInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			//将数据中的金额转换为分存入数据库			
			
			entity.setDtLimit(MoneyUtils.toStrCent(entity.getDtLimit()));
			entity.setMtLimit(MoneyUtils.toStrCent(entity.getMtLimit()));
			entity.setLowLimit(MoneyUtils.toStrCent(entity.getLowLimit()));			
			entity.setHighLimit(MoneyUtils.toStrCent(entity.getHighLimit()));
			entity.setRateTypeTop(MoneyUtils.toStrCent(entity.getRateTypeTop()));
			
			rt = dao.updateEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("更新数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int removeEntity(CooporgMerInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}", entity.debug());
		try {
			rt = dao.deleteEntity(entity);
			log.debug("处理结果:[{}]", rt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("删除数据异常!" + e.getMessage(), e);
		}
		return rt;
	}

	/**
	 * 查询合作机构商户终端是否存在
	 */
	@Override
	public int identifyId(CooporgMerInf entity) {
		return dao.identifyEntity(entity);
	}

	public int countCooporgList(CooporgMerInf entity) throws Exception {
		return dao.countCooporgList(entity);
	}

	public List<CooporgMerInf> getCooporgList(CooporgMerInf entity) throws Exception {
		return dao.selectCooporgList(entity);
	}

	
	
	/**
	 * 合作机构签到
	 * @param rtrId
	 * @param channelname
	 * @param merNo
	 * @param terNo
	 * @throws Exception 
	 */
	@Override @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object>  bankRtrSign(Map<String,Object> map) throws Exception {

		log.info("合作机构签到交易开始");
		String txncd = "0800";
		String coopOrgNo = map.get("coopOrgNo").toString();
		String merNo = map.get("merNo").toString();
		String terNo = map.get("terNo").toString();
	
		CooporgMerInf entity = getEntity(new CooporgMerInf(coopOrgNo,merNo,terNo));
		
		CooporgTermkeyInf termkey = new CooporgTermkeyInf();
		termkey.setCooporgNo(coopOrgNo);
		termkey.setMerNo(merNo);
		termkey.setTerNo(terNo);
		
		CooporgRouteInf cori=new CooporgRouteInf();
		cori.setCooporgNo(entity.getCooporgNo());
		cori.setTxncd(txncd);
		cori = cooporgRouteService.getEntity(cori);
		if(cori==null){
			throw new Exception("合作机构签到失败，未查到路由信息！");
		}
		//CooporgMerServiceImpl
		Map<String,Object> pmap = new HashMap<String,Object>();
		pmap.put("RtrId", cori.getRtrid());
		pmap.put("chanelname", coopOrgNo);
		pmap.put("merno", merNo);
		pmap.put("terno", terNo);
		pmap.put("rtrsvr", cori.getRtrsvr());
		pmap.put("rtrcod", cori.getRtrcod());
		pmap.put("lmk", entity.getMerKey());
		
		Map<String, Object> rmap = remoteService.dotran(TranCode.TRAN_SIGN, pmap);
		log.info("远程调用返回结果:{}",rmap);
		String retcode = String.valueOf(rmap.get(MsgCT.RETCOE));
		log.info("返回码[{}]",retcode);
		
		if(String.valueOf(rmap.get(MsgCT.RETCOE)).equals(MsgCT.ERT_SUCCESS)){
			try{
				entity.setBatchNo((String) rmap.get("BATCHNO"));
				//更新合作机构终端信息表（签到时间、批次号）
				modifysigEntity(entity);
			    //更新密钥表
				termkey.setPinkey((String) rmap.get("PINKEY"));
				termkey.setPkCheckValue((String) rmap.get("PINCHECKVALUE"));
				termkey.setMackey((String) rmap.get("MACKEY"));
				termkey.setMkCheckValue((String) rmap.get("MACCHECKVALUE"));
				cooporgTermkeyService.modifyEntity(termkey);
			}catch(Exception e){
				log.error(e.getMessage(),e);
				throw new Exception("合作机构签到失败！");
			}
		}else{
			String error = MSGCODE.GET(String.valueOf(rmap.get(MsgCT.RETCOE)));
			log.info("错误原因:{}",error);
			throw new Exception(error);
		}
		log.info("合作机构签到交易结束");
		return rmap;
	}

	@Override
	public int modifysigEntity(CooporgMerInf entity) throws Exception{
		return dao.updatesigEntity(entity);
	}
	@Override
	public int validateMerNo(CooporgMerInf cooporgMerInf) throws Exception{
		return dao.validateMerNo(cooporgMerInf);
	}
	
	public CooporgMerInf selectProvinceId(CooporgMerInf cooporgMerInf){
		CooporgMerInf merInf = new CooporgMerInf();
		merInf.setProvinceID(dao.selectProvinceID(cooporgMerInf.getProvinceName().substring(0, 2)).getProvinceID());
		merInf.setRateType(dao.selectRateType(cooporgMerInf.getRateType()).getRateType());
		merInf.setCooporgNo(dao.selectCooporgNo(cooporgMerInf.getCoopName()).getCooporgNo());
		return merInf;
	}

	@Override
	public String getFjpath(String uploadFileId) {
		return dao.getFjpath(uploadFileId);
	}
}
