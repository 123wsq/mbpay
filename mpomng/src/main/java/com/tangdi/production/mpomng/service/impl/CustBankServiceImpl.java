package com.tangdi.production.mpomng.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.constants.MsgCT;
import com.tangdi.production.mpomng.bean.CustBankInf;
import com.tangdi.production.mpomng.bean.MeridentifyInf;
import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.mpomng.constants.CT;
import com.tangdi.production.mpomng.dao.CustBankInfDao;
import com.tangdi.production.mpomng.dao.CustBankInfTempDao;
import com.tangdi.production.mpomng.service.CustBankInfService;
import com.tangdi.production.mpomng.service.CustBankInfTempService;
import com.tangdi.production.mpomng.service.MeridentifyService;
import com.tangdi.production.mpomng.service.MobileMerService;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;



/**
 * 
 * @author zhuji
 * @version 1.0
 *
 */
@Service
public class CustBankServiceImpl implements CustBankInfService{
	private static final Logger log = LoggerFactory
			.getLogger(CustBankServiceImpl.class);
	@Autowired
	private CustBankInfDao dao;
	
	@Autowired
	private CustBankInfTempDao tempDao;

	@Autowired
	private CustBankInfTempService custBankInfTempService;
	
	@Autowired
	private MeridentifyService meridentifyService;
	
	@Autowired
	private MobileMerService mobileMerService;
	
	@Override
	public List<CustBankInf> getListPage(CustBankInf entity) throws Exception {
		return dao.selectList(entity);
	}

	@Override
	public Integer getCount(CustBankInf entity) throws Exception {
		return dao.countEntity(entity);
	}

	@Override
	public CustBankInf getEntity(CustBankInf entity) throws Exception {
		log.debug("方法参数："+entity.debug());
		CustBankInf custBankInf = null;
		try{
			custBankInf = dao.selectEntity(entity);
		}catch(Exception e){
			throw new Exception("查询信息异常!"+e.getMessage(),e);
		}
		log.debug("处理结果:",custBankInf.debug());
		return custBankInf;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int addEntity(CustBankInf entity) throws Exception {
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
	public int modifyEntity(CustBankInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.updateEntity(entity);
			 
			 /*
			 log.debug("处理结果:[{}]",rt);
			 if(rt>0){
				removeEntity(entity); 
			 }
			 */
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int removeEntity(CustBankInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			 rt = dao.deleteEntity(entity);
			 log.debug("处理结果:[{}]",rt);
			 if(rt>0){
				tempDao.insertEntity(entity);
			 }
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("删除数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int audit(CustBankInf custBankInf, String status, String updateDesc)
			throws Exception {
		try {
			CustBankInf entity = custBankInfTempService.getEntityById(custBankInf);

			MeridentifyInf meridentifyInf = new MeridentifyInf();
			meridentifyInf.setCustId(custBankInf.getCustId());
			MeridentifyInf inf = meridentifyService.getEntity(meridentifyInf);
			
			log.info("查询结果：custbankInf={},meridentifyInf={}", entity.debug(),inf.debug());
			if ("1".equals(inf.getCustStatus())) {
				int rt = 0;
				if (status.equals(MsgCT.BANK_CARD_AUDIT_SUCCESS)) {
					log.info("修改银行卡状态....");
					custBankInf.setUpdateDesc(updateDesc);
					custBankInf.setCardState(status);
					custBankInfTempService.modifyEntity(custBankInf);
					rt = modifyEntity(entity);
					if (rt == 0) {
						rt = addEntity(entity);
					}
					log.info("修改商户状态...");
					//商户实名认证
					meridentifyInf.setCustStatus("2");
					meridentifyInf.setAuditIdea(updateDesc);
					meridentifyInf.setIdentifyTime(TdExpBasicFunctions.GETDATETIME());
					meridentifyService.modifyEntity(meridentifyInf);
					//商户登录信息
					MobileMerInf mobileMerInf=new MobileMerInf();
					mobileMerInf.setAuthStatus("2");
					mobileMerInf.setCustId(custBankInf.getCustId());
					mobileMerService.modifyEntity(mobileMerInf);
					
				} else if (status.equals(MsgCT.BANK_CARD_AUDIT_FAIL)) {
					custBankInf.setUpdateDesc(updateDesc);
					custBankInf.setCardState(CT.CUSTBANK_STATUS_4);
					custBankInfTempService.modifyEntity(custBankInf);
					log.info("修改商户状态...");
					//商户实名认证
					meridentifyInf.setCustStatus("3");
					meridentifyInf.setAuditIdea(updateDesc);
					meridentifyInf.setIdentifyTime(TdExpBasicFunctions.GETDATETIME());
					meridentifyService.modifyEntity(meridentifyInf);
					//商户登录信息
					MobileMerInf mobileMerInf=new MobileMerInf();
					mobileMerInf.setAuthStatus("3");
					mobileMerInf.setCustId(custBankInf.getCustId());
					mobileMerService.modifyEntity(mobileMerInf);
				}
			} else {
				int rt = 0;
				if (status.equals(MsgCT.BANK_CARD_AUDIT_SUCCESS)) {
					custBankInf.setUpdateDesc(updateDesc);
					custBankInf.setCardState(status);
					custBankInfTempService.modifyEntity(custBankInf);
					rt = modifyEntity(entity);
					if (rt == 0) {
						rt = addEntity(entity);
					}
				} else if (status.equals(MsgCT.BANK_CARD_AUDIT_FAIL)) {
					custBankInf.setUpdateDesc(updateDesc);
					custBankInf.setCardState(CT.CUSTBANK_STATUS_4);
					custBankInfTempService.modifyEntity(custBankInf);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("审核失败失败", e);
		}
		return 0;
	}

	@Override
	public int bankNum(String custId) throws Exception {
		return dao.selectBankNum(custId);
	}
}
