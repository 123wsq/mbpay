package com.tangdi.production.mpomng.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.ExcepCode;
import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpomng.bean.CustAccountInf;
import com.tangdi.production.mpomng.dao.CustAccountDao;
import com.tangdi.production.mpomng.service.CustAccountService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;



/**
 * 商户账户业务实现
 * @author zhengqiang 2015/09/17
 * @version 1.0
 *
 */
@Service
public class CustAccountServiceImpl implements CustAccountService{
	private static final Logger log = LoggerFactory
			.getLogger(CustAccountServiceImpl.class);
	@Autowired
	private CustAccountDao dao;
	@Autowired
	private GetSeqNoService seqNoService;

	@Override
	public List<CustAccountInf> getListPage(CustAccountInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CustAccountInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CustAccountInf getEntity(CustAccountInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		CustAccountInf custAccountInf = null;
		try{
			custAccountInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		return custAccountInf;
	}
	

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(CustAccountInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.insertEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("插入数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyEntity(CustAccountInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.updateEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int removeEntity(CustAccountInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.deleteEntity(entity);
			 log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("删除数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void blanceChange01(Map<String, Object> map) throws Exception {
		map.put("changeType",MsgCT.ACCOUT_CHANGE_TYPE_3001);
		log.debug("账户余额变动类型 3001-T0审核退回");
		accountTran(map,1);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void blanceChange02(Map<String, Object> map) throws Exception {
	//	map.put("changeType",MsgCT.ACCOUT_CHANGE_TYPE_3011);
	//	log.debug("账户余额变动类型 3011-T0审核通过减余额");
	//	accountTran(map,11);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void t0ToBlanceChange(Map<String, Object> map) throws Exception {
		map.put("changeType",MsgCT.ACCOUT_CHANGE_TYPE_3002);
		log.debug("账户余额变动类型 3002-提现订单当天未清算退回");
		accountTran(map,2);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void t1ToBlanceChange(Map<String, Object> map) throws Exception {
		map.put("changeType",MsgCT.ACCOUT_CHANGE_TYPE_3003);
		log.debug("账户余额变动类型 3003-T1余额转T1Y");
		accountTran(map,3);
	}
	@Override
	public void t1yToBlanceChange(Map<String, Object> map) throws Exception {
		map.put("changeType",MsgCT.ACCOUT_CHANGE_TYPE_3005);
		log.debug("账户余额变动类型 3005-T1Y自动提现");
		accountTran(map,5);
	}

	@Override  @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void merProfitToBlanceChange(Map<String, Object> map)
			throws Exception {
		//map.put("changeType",MsgCT.ACCOUT_CHANGE_TYPE_3004);
		//log.debug("账户余额变动类型 3004-商户分润金额转T1");
		//accountTran(map,4);
		
	}
	

	/**
	 * 提现订单转余额
	 * @param map
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	private void accountTran(Map<String, Object> param,int type) throws Exception {
		//查询余额
		log.debug("开始更新账户信息.");
		Double t1=Double.valueOf(param.get("t1").toString());//t1金额
		Double t0=Double.valueOf(param.get("t0").toString());//t0金额
		log.info("T1金额=[{}],t0金额=[{}]",t1.intValue(),t0.intValue());
		
		String custId = param.get("custId").toString();
		log.debug("商户编号:[{}]",custId);
		CustAccountInf cacct = new CustAccountInf(custId);
		cacct.setAcType(param.get("acType").toString());
		CustAccountInf account = getCustAccountLock(cacct);
		log.debug("查询账户余额：[{}]",account);
		
		Double acBal=Double.valueOf(account.getAcBal());//账户余额
		Double acT1Y=Double.valueOf(account.getAcT1Y());//t1未提余额
		Double acT1=Double.valueOf(account.getAcT1());//t1余额
		Double acT0=Double.valueOf(account.getAcT0());//t0余额
		Double acT1AP=Double.valueOf(account.getAcT1AP());//t0已审核余额
		
		CustAccountInf uaccout = new CustAccountInf();
		uaccout.setCustId(custId);
		uaccout.setAcType(param.get("acType").toString());
		
		//转账
		if(type == 1 || type == 2){//T0审核不通过时,余额退回
			//订单金额
			Double txamt=Double.valueOf(param.get("txamt").toString());
			log.info("T0审核不通过时,余额退回.");

			uaccout.setAcT0("0");
			uaccout.setAcT1("0");
			//uaccout.setAcT1(String.valueOf((acT1.intValue() + t0.intValue())));
			uaccout.setAcT1AP(String.valueOf((acT1AP.intValue() + t0.intValue())));
			uaccout.setAcT1Y(String.valueOf((acT1Y.intValue() + t1.intValue())));
			uaccout.setAcBal(String.valueOf((acBal.intValue() + txamt.intValue())));
		}else if(type ==3){//T1转T1Y余额
			uaccout.setAcT0("0");
			uaccout.setAcT1("0");
			uaccout.setAcT1AP("0");
			uaccout.setAcT1Y(String.valueOf((acT1Y.intValue()+ acT1AP.intValue())));
			uaccout.setAcBal(String.valueOf((acBal.intValue())));
		}else if(type ==5){//T1Y清空
			uaccout.setAcT0(String.valueOf(acT0.intValue()));
			uaccout.setAcT1(String.valueOf((acT1.intValue())));
			uaccout.setAcT1Y(String.valueOf(acT1Y.intValue() -t1.intValue()));
			uaccout.setAcBal(String.valueOf((acBal.intValue()-t1.intValue())));
		}else if(type ==6){//at1y 清空 账户余额
			uaccout.setAcT0(String.valueOf(acT0.intValue()));
			uaccout.setAcT1(String.valueOf((acT1.intValue())));
			uaccout.setAcT1Y(String.valueOf(acT1Y.intValue()));
			uaccout.setAcT1AP(String.valueOf(acT1AP.intValue()));
			uaccout.setAcBal(String.valueOf(acBal.intValue()));	
		}else if(type == 12){
			uaccout.setAcT0(String.valueOf(acT0.intValue()));
			uaccout.setAcBal(String.valueOf(acBal.intValue() + t1.intValue() + t0.intValue()));
			if(MsgCT.CAS_TYPE_T0.equals(param.get("casType"))){
				log.info("T0提现订单，清算失败");
				uaccout.setAcT1Y(String.valueOf((acT1Y.intValue()  + t1.intValue())));
				uaccout.setAcT1(String.valueOf(acT1.intValue() + t0.intValue()));
			}else{
				log.info("T1提现订单，清算失败");
				uaccout.setAcT1Y(String.valueOf((acT1Y.intValue() + t0.intValue()) + t1.intValue()));
				uaccout.setAcT1(String.valueOf(acT1.intValue()));
			}
		}
		log.debug("更新余额账户:[{}]",uaccout);
		dao.updateEntity(uaccout);
		
		//更新His
		log.info("余额变动记录：{}",param);
		Map<String , Object>  accountHisMap = new HashMap<String, Object>();
		String accountLogId = "";
		try {
			accountLogId = seqNoService.getSeqNoNew("AC_HIS_ID", "9", "1");
			log.info("账户历史序列号:{}",accountLogId);
			accountHisMap.put("accountLogId", accountLogId);
			accountHisMap.put("custId", account.getCustId());
			accountHisMap.put("account", account.getAccount());
			accountHisMap.put("acType", account.getAcType());
			accountHisMap.put("ccy", account.getCcy());
			accountHisMap.put("oldAcBal", account.getAcBal());
			accountHisMap.put("oldAcT0", account.getAcT0());
			accountHisMap.put("oldAcT1", account.getAcT1());
			accountHisMap.put("oldAcT1Y", account.getAcT1Y());
			accountHisMap.put("oldAcT1AP", account.getAcT1AP());
			accountHisMap.put("oldAcT1UNA", account.getAcT1UNA());
			accountHisMap.put("oldAcT1AUNP", account.getAcT1AUNP());
			accountHisMap.put("acBal", uaccout.getAcBal());
			accountHisMap.put("acT0",  uaccout.getAcT0());
			accountHisMap.put("acT1",  uaccout.getAcT0());
			accountHisMap.put("acT1Y", uaccout.getAcT1Y());
			accountHisMap.put("acT1AP", uaccout.getAcT1AP());
			accountHisMap.put("oldFrozBalance", account.getFrozBalance());
			accountHisMap.put("lstTxDatetime", TdExpBasicFunctions.GETDATETIME());			
			accountHisMap.put("changeType", param.get("changeType"));//"1001"	
			log.info("保存余额变动历史数据:{}",accountHisMap);
		    dao.insertHis(accountHisMap);
		} catch (Exception e) {
			throw new Exception("保存余额变动历史异常",e);
		}
		log.debug("账户更新完成.");
	}

	@Override
	public List<Map<String, Object>> queryT1() throws Exception {
		return dao.selectT1();
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public CustAccountInf getCustAccountLock(CustAccountInf entity)
			throws Exception {
		log.debug("加锁查询账户信息,方法参数："+entity.debug());
		CustAccountInf custAccountInf = null;
		try{
			custAccountInf = dao.selectCustAccountForUpdate(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		return custAccountInf;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<CustAccountInf> getCustAccountAllLock() throws Exception {
		return dao.selectCustAccountAllForUpdate();
	}

	@Override
	public void t1ToT1YBlanceChange(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getCustAccountCount() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
/*
	/*
	@Override
	public CustAccountInf getCustAccountForUpdate(CustAccountInf entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	/*
	@Override
	public List<CustAccountInf> getCustAccountAllForUpdate() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	@Override
	public void at1ToBlanceChange(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		map.put("changeType",MsgCT.ACCOUT_CHANGE_TYPE_2001);
		log.debug("账户余额变动类型 2001-AT1Y系统自动提现");
		accountTran(map,6);
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void settleFalse(Map<String, Object> map) throws Exception {
		map.put("changeType",MsgCT.ACCOUT_CHANGE_TYPE_4001);
		log.debug("账户余额变动类型 4001-清算失败");
		accountTran(map,12);
	}

	@Override
	public int addCustAccount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
		int rt=0;
		try {
			rt=dao.insertEntity(param);
		} catch (Exception e) {
			throw new Exception("商户账户添加异常",e);
		}
		if (rt==0) {
			throw new Exception("商户账户添加失败");
		}
		
		
		return rt;
	}
}
