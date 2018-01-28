package com.tangdi.production.mpcoop.service;

import java.util.List;
import java.util.Map;

import com.tangdi.production.mpcoop.bean.CooporgMerInf;
import com.tangdi.production.tdbase.service.BaseService;

/**
 * 
 * 合作机构商户终端管理
 * @author shanbeiyi
 * @version 1.0
 *
 */
public interface CooporgMerService extends BaseService<CooporgMerInf, Exception> {

	int identifyId(CooporgMerInf cooporgMerInf);

	public abstract int countCooporgList(CooporgMerInf entity) throws Exception;

	public abstract List<CooporgMerInf> getCooporgList(CooporgMerInf entity) throws Exception;
	/**
	 * 查询商户编号是否存在
	 * @param cooporgMerInf
	 * @return
	 * @throws Exception
	 */
	public int validateMerNo(CooporgMerInf cooporgMerInf) throws Exception;
	

	/**
	 * 合作机构签到
	 * @param rtrId   路由ID
	 * @param channelname 合作机构编号
	 * @param merNo  商户号
	 * @param terNo  终端号
	 * @param rtrsvr  渠道服务名
	 * @param rtrcod  渠道交易码
	 * @param lmk  合作机构终端主密钥
	 * @return 0 失败 1成功
	 */
	public  Map<String,Object> bankRtrSign(Map<String,Object> map)throws Exception  ;

	/**
	 * 签到更新信息
	 * @param entity
	 */
	public int modifysigEntity(CooporgMerInf entity)throws Exception;
	
	public CooporgMerInf selectProvinceId(CooporgMerInf cooporgMerInf);
	
	public String getFjpath(String uploadFileId);
}
