package com.tangdi.production.mprcs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mprcs.dao.DefaultLimitDao;
import com.tangdi.production.mprcs.service.DefaultLimitService;
import com.tangdi.production.mprcs.bean.DefaultLimitInf;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;


/**
 * 
 * @author zhengqiang
 * @version 1.0
 *
 */
@Service
public class DefaultLimitServiceImpl implements DefaultLimitService{
	private static final Logger log = LoggerFactory
			.getLogger(DefaultLimitServiceImpl.class);
	@Autowired
	private DefaultLimitDao dao;

	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public int modifyEntity(DefaultLimitInf entity) throws Exception {
		int rt = 0;
		log.debug("方法参数:{}",entity.debug());
		try{
			entity.setIsUse(entity.getIsUse());         //1 启用
			//终端收款
			entity.setLimitMinAmtTp(MoneyUtils.toStrCent(entity.getLimitMinAmtTp()));
			entity.setLimitMaxAmtTp(MoneyUtils.toStrCent(entity.getLimitMaxAmtTp()));
			entity.setLimitDayTimesTp(MoneyUtils.toStrCent(entity.getLimitDayTimesTp()));
			entity.setLimitDayAmtTp(MoneyUtils.toStrCent(entity.getLimitDayAmtTp()));
			entity.setLimitMonthTimesTp(MoneyUtils.toStrCent(entity.getLimitMonthTimesTp()));			
			entity.setLimitMonthAmtTp(MoneyUtils.toStrCent(entity.getLimitMonthAmtTp()));
			//终端提现
			entity.setLimitMinAmtTc(MoneyUtils.toStrCent(entity.getLimitMinAmtTc()));
			entity.setLimitMaxAmtTc(MoneyUtils.toStrCent(entity.getLimitMaxAmtTc()));
			entity.setLimitDayAmtTc(MoneyUtils.toStrCent(entity.getLimitDayAmtTc()));
			entity.setLimitMonthAmtTc(MoneyUtils.toStrCent(entity.getLimitMonthAmtTc()));
			
			//快捷收款
			entity.setLimitMinAmtOp(MoneyUtils.toStrCent(entity.getLimitMinAmtOp()));
			entity.setLimitMaxAmtOp(MoneyUtils.toStrCent(entity.getLimitMaxAmtOp()));
			entity.setLimitDayTimesOp(MoneyUtils.toStrCent(entity.getLimitDayTimesOp()));
			entity.setLimitDayAmtOp(MoneyUtils.toStrCent(entity.getLimitDayAmtOp()));
			entity.setLimitMonthTimesOp(MoneyUtils.toStrCent(entity.getLimitMonthTimesOp()));			
			entity.setLimitMonthAmtOp(MoneyUtils.toStrCent(entity.getLimitMonthAmtOp()));
			//快捷提现
			entity.setLimitMinAmtOc(MoneyUtils.toStrCent(entity.getLimitMinAmtOc()));
			entity.setLimitMaxAmtOc(MoneyUtils.toStrCent(entity.getLimitMaxAmtOc()));
			entity.setLimitDayAmtOc(MoneyUtils.toStrCent(entity.getLimitDayAmtOc()));
			entity.setLimitMonthAmtOc(MoneyUtils.toStrCent(entity.getLimitMonthAmtOc()));
			
			//扫码收款
			entity.setLimitMinAmtSp(MoneyUtils.toStrCent(entity.getLimitMinAmtSp()));
			entity.setLimitMaxAmtSp(MoneyUtils.toStrCent(entity.getLimitMaxAmtSp()));
			entity.setLimitDayTimesSp(MoneyUtils.toStrCent(entity.getLimitDayTimesSp()));
			entity.setLimitDayAmtSp(MoneyUtils.toStrCent(entity.getLimitDayAmtSp()));
			entity.setLimitMonthTimesSp(MoneyUtils.toStrCent(entity.getLimitMonthTimesSp()));			
			entity.setLimitMonthAmtSp(MoneyUtils.toStrCent(entity.getLimitMonthAmtSp()));
			//扫码提现
			entity.setLimitMinAmtSc(MoneyUtils.toStrCent(entity.getLimitMinAmtSc()));
			entity.setLimitMaxAmtSc(MoneyUtils.toStrCent(entity.getLimitMaxAmtSc()));
			entity.setLimitDayAmtSc(MoneyUtils.toStrCent(entity.getLimitDayAmtSc()));
			entity.setLimitMonthAmtSc(MoneyUtils.toStrCent(entity.getLimitMonthAmtSc()));			
			
			rt = dao.updateEntity(entity);
			log.debug("处理结果:[{}]",rt);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new Exception("更新数据异常!"+e.getMessage(),e);
		}
		return rt;
	}

	@Override
	public DefaultLimitInf queryLimit() throws Exception {
		return dao.selectDefaultLimit();
	}

	public static void main(String[] args){
		String date = TdExpBasicFunctions.GETDATE();
		String year = date.substring(0,4);
		String month= date.substring(4,6);
		String day  = date.substring(6);
		
		System.out.println(month);
	}


	
	
}
