package com.tangdi.production.mpamng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tangdi.production.mpamng.bean.AgentInf;
import com.tangdi.production.mpamng.bean.TerminalInf;
import com.tangdi.production.mpamng.bean.TerminalKeyInf;
import com.tangdi.production.mpamng.constants.CT;
import com.tangdi.production.mpamng.dao.AgeCustDao;
import com.tangdi.production.mpamng.dao.AgentDao;
import com.tangdi.production.mpamng.dao.TerminalDao;
import com.tangdi.production.mpamng.report.TermKeyFileTemplate;
import com.tangdi.production.mpamng.service.TerminalService;
import com.tangdi.production.mpamng.util.ZDAnalysisExcelUtil;
import com.tangdi.production.mpbase.constants.TermStepDesc;
import com.tangdi.production.mpbase.constants.TranCode;
import com.tangdi.production.mpbase.service.FileReportService;
import com.tangdi.production.mpbase.service.TermStepService;
import com.tangdi.production.mpomng.bean.MobileMerInf;
import com.tangdi.production.tdauth.constants.Constant;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.TdExpBasicFunctions;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;
import com.tangdi.production.tdcomm.rpc.hessian.HessinClientBean;

@Service
public class TerminalServiceImpl implements TerminalService {
	private static final Logger log = LoggerFactory.getLogger(TerminalServiceImpl.class);
	@Autowired
	private TerminalDao terminalDao;

	@Autowired
	private AgentDao agentDao;

	@Autowired
	private AgeCustDao ageCustDao;

	@Autowired
	private GetSeqNoService seqNoService;

	@Autowired
	private TermStepService termStepService;

	@Autowired
	private FileReportService<TermKeyFileTemplate> reportService;

	@Autowired
	private HessinClientBean remoteService;

	@Value("#{mpamngConfig}")
	private Properties prop;
	
	@Override
	public int report(HashMap<String, Object> con, String uid) throws Exception {
		List<TermKeyFileTemplate> data = null;
		try {
			TerminalInf terminal = (TerminalInf) con.get("entityParam");
			data = generateTerminalInf(terminal);
		} catch (Exception e) {
			log.error("生成密钥文件出错！{}", e.getMessage());
			throw new Exception(e);
		}
		return reportService.report(data, uid, "终端密钥文件", null, CT.REPORT_TYPE_3, CT.FILE_TYPE_TXT, null);
	}

	/**
	 * 生成 终端
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<TermKeyFileTemplate> generateTerminalInf(TerminalInf terminalInf) throws Exception {
		List<TermKeyFileTemplate> termKeyFileTemplate = new ArrayList<TermKeyFileTemplate>();
		TerminalInf entity = null;
		TerminalKeyInf terminalKeyInf = null;
		String date = "0000";
		String sn = "";
		for (int i = 0; i < terminalInf.getTermAllocateNum(); i++) {
			entity = new TerminalInf();
			entity.setTerminalId(seqNoService.getSeqNoNew("SEQ_MPAMNG_TERMINAL", "9", "0"));
			// 终端号
			date = TdExpBasicFunctions.GETDATE().substring(2, 6);
			String terminalSeq = seqNoService.getSeqNoNew("SEQ_MPAMNG_TERMINAL_NO", "8", "1");
			entity.setTerminalSeq(terminalSeq);
			// "1150400000001"
			// 终端机身号 1504 + 1 + + 8 = 13位
			sn = terminalInf.getTerminalType() + date + "0" + terminalSeq;
			log.info("生成设备SN=[{}]", sn);
			entity.setTerminalNo(sn);
			entity.setTerminalType(terminalInf.getTerminalType());
			entity.setTerminalCom(terminalInf.getTerminalCom());
			entity.setCreateUserId(terminalInf.getCreateUserId());
			entity.setCreateDate(terminalInf.getCreateDate());
			log.info("终端数据保存中.数据:{}", entity);
			terminalDao.insertEntity(entity);
			log.info("终端保存完成.");

			log.info("记录终端 日志-------------");
			termStepService.saveTermStepDesc(entity.getTerminalNo(), TermStepDesc.STEP_0, terminalInf.getCreateUserId());

			terminalKeyInf = generationKey(entity);
			log.info("终端密钥数据保存中.KEY={}", terminalKeyInf);
			terminalDao.insertTerminalKey(terminalKeyInf);
			log.info("终端密钥数据保存完成.");
			termKeyFileTemplate.add(new TermKeyFileTemplate(entity.getTerminalNo(), terminalKeyInf.getTerminalZMK(), terminalKeyInf.getTerminalZMKCV()));
		}
		return termKeyFileTemplate;
	}

	/**
	 * 生成 终端密钥
	 *
	 * @param terminalInf
	 * @return
	 * @throws Exception
	 */
	private TerminalKeyInf generationKey(TerminalInf terminalInf) throws Exception {
		TerminalKeyInf terminalKeyInf = new TerminalKeyInf();
		terminalKeyInf.setTerminalId(terminalInf.getTerminalId());

		Map<String, Object> mmap = new HashMap<String, Object>();
		mmap.put("company", terminalInf.getTerminalCom());
		Map<String, Object> keyMap = remoteService.dotran(TranCode.TRAN_TERMKEY, mmap);
		log.debug("调用交易返回值：{}", keyMap);
		if (!keyMap.get("RETCODE").toString().equals("000000")) {
			throw new Exception("获取传输密钥出错了.");
		}
		terminalKeyInf.setTerminalLMK((String) keyMap.get("LMK"));
		terminalKeyInf.setTerminalZMK((String) keyMap.get("ZMK"));
		terminalKeyInf.setTerminalZMKCV((String) keyMap.get("KEYVALUE"));
		terminalKeyInf.setTerminalTTK((String) keyMap.get("LTDKKEY"));
		terminalKeyInf.setTerminalTTKCV((String) keyMap.get("ZTDKVALUE"));
		terminalKeyInf.setTerminalTDK((String) keyMap.get("ZTDKKEY"));
		terminalKeyInf.setTerminalTDKCV((String) keyMap.get("ZTDKVALUE"));
		log.info("密钥获取完成.");
		return terminalKeyInf;
	}

	public int addEntity(TerminalInf terminalInf) throws Exception {
		return 0;
	}

	/**
	 * 修改 终端
	 */
	public int modifyEntity(TerminalInf entity) throws Exception {
		return terminalDao.updateEntity(entity);
	}

	/**
	 * 删除 终端
	 */
	public int removeEntity(TerminalInf entity) throws Exception {
		return 0;
	}

	/**
	 * 根据ID查询
	 * 
	 */
	public TerminalInf getEntity(TerminalInf entity) throws Exception {
		return terminalDao.selectEntity(entity);
	}

	/**
	 * 获取总条数
	 * 
	 */
	public Integer getCount(TerminalInf entity) throws Exception {
		return terminalDao.countEntity(entity);
	}

	/**
	 * 终端 分页
	 * 
	 * @throws Exception
	 */
	@Override
	public List<TerminalInf> getListPage(TerminalInf entity) throws Exception {
		return terminalDao.selectList(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int unbindAgent(TerminalInf entity) throws Exception {
		// 查询当前终端的 代理商的上级代理商
		Map<String, Object> ageCustEntity = ageCustDao.selectAgeCustEntity(entity.toMap());

		if (ageCustEntity == null || ageCustEntity.get("agentId") == null) {
			return 0;
		}

		String agentId = ageCustEntity.get("agentId").toString();
		AgentInf agentInf = new AgentInf();
		agentInf.setAgentId(agentId);

		agentInf = agentDao.selectEntity(agentInf);

		if (agentInf.getAgentId().equals(agentInf.getFirstAgentId())) {
			Map<String, Object> acMap = new HashMap<String, Object>();
			acMap.put("terminalId", entity.getTerminalId());
			// 解绑一级代理商，直接删除关系
			ageCustDao.deleteAgeCustInfo(acMap);
			// 查询 终端信息
			entity = terminalDao.selectEntity(entity);

			entity.setRateLivelihood("");
			entity.setRateGeneral("");
			entity.setRateEntertain("");
			entity.setRateGeneralTop("");
			entity.setRateGeneralMaximun("");
			entity.setRateEntertainTop("");
			entity.setRateEntertainMaximun("");
			entity.setTerminalStatus("0");

			log.info("记录终端 日志-------------   解绑代理商");
			termStepService.saveTermStepDesc(entity.getTerminalNo(), TermStepDesc.STEP_4, entity.getEditUserId(), agentInf.getAgentId());
		} else {
			ageCustEntity.put("agentId", agentInf.getFathAgentId());
			log.info("解绑代理商: {},----回到代理商:{} 库存", agentId, agentInf.getFathAgentId());
			ageCustEntity.put("ageId", agentInf.getFathAgentId());
			ageCustDao.updateAgent(ageCustEntity);

			// 查询 终端信息
			entity = terminalDao.selectEntity(entity);

			// 查询 上级代理商费率
			agentInf.setAgentId(agentInf.getFathAgentId());
			log.info("解绑代理商: {}  的费率:", agentInf.getAgentId());
			agentInf = agentDao.selectEntity(agentInf);

			entity.setRateLivelihood(agentInf.getRateLivelihood());
			entity.setRateGeneral(agentInf.getRateGeneral());
			entity.setRateEntertain(agentInf.getRateEntertain());
			entity.setRateGeneralTop(agentInf.getRateGeneralTop());
			entity.setRateGeneralMaximun(agentInf.getRateGeneralMaximun());
			entity.setRateEntertainTop(agentInf.getRateEntertainTop());
			entity.setRateEntertainMaximun(agentInf.getRateEntertainMaximun());
			entity.setTerminalStatus("1");

			log.info("记录终端 日志-------------   解绑代理商");
			termStepService.saveTermStepDesc(entity.getTerminalNo(), TermStepDesc.STEP_4, entity.getEditUserId(), agentId, agentInf.getAgentId());
		}

		// 2.更新终端表
		terminalDao.unbindAgent(entity);

		return 1;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int unbindMer(TerminalInf entity) throws Exception {
		Map<String, Object> acMap = new HashMap<String, Object>();
		acMap.put("terminalId", entity.getTerminalId());

		Map<String, Object> custIdMap = ageCustDao.selectAgeCustEntity(acMap);
		if (custIdMap.get("custId") != null && custIdMap.get("custId").toString().length() > 0) {
			// 查询 终端信息
			entity = terminalDao.selectEntity(entity);

			// 1.更新商户代理商关系表信息
			acMap.put("custId", "");
			//更新关系表
			ageCustDao.updateAgeCustInfo(acMap);
			//在数据库中新增默认绑定代理商
			acMap.put("custId", custIdMap.get("custId").toString());
			acMap.put("terminalId", "999999999");
			acMap.put("ageId", prop.get("SM_DEFAGENT")+"");
			ageCustDao.insertAgeCustInfo(acMap);
			
			// 2.更新终端表
			terminalDao.unbindMer(entity);

			log.info("记录终端 日志-------------   解绑商户");
			termStepService.saveTermStepDesc(entity.getTerminalNo(), TermStepDesc.STEP_3, entity.getEditUserId(), custIdMap.get("custId").toString());
		}
		return 1;
	}

	@Override
	public Integer queryTermAllocateNum(TerminalInf entity) throws Exception {
		return terminalDao.queryTermAllocateNum(entity);
	}

	@Override
	public TerminalInf findTermAllocate(TerminalInf entity) throws Exception {
		return terminalDao.findTermAllocate(entity);
	}

	/**
	 * 1.检测终端是否可用(终端是否 下拨)<br>
	 * 2.如果终端下拨给代理商后，再次下拨的话只能是当前代理商的下级代理商 <br>
	 * 3.修改终端状态为【出库】，修改代理商终端商户关系表
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int termAllocate(TerminalInf entity) throws Exception {
		// 需要下拨的代理商 编号
				String souAgentId = entity.getSouAgentId();
				String allocateAgentId = entity.getAgentId();
				if (Constant.SYS_AGENT_ID.equals(souAgentId)) {

				}
				int updateTerm = 0;
				if (entity.getAllocateType() == 0) {
					String[] terminalNoArray = entity.getTerminalNo().split(",");
					for (int i = 0; i < terminalNoArray.length; i++) {
						entity.setAgentId(allocateAgentId);
						entity.setPageSize(1);
						entity.setTerminalNo(terminalNoArray[i]);
						updateTerm = updateTerm(entity, allocateAgentId);
					}
				} else {
					entity.setTerminalNo(entity.getTerminalNo().split(",")[0]);
					entity.setPageSize(entity.getTermAllocateNum());
					updateTerm = updateTerm(entity, allocateAgentId);
				}
				if (updateTerm == 2) {
					return CT.TERM_ALLOCATE_2;
				} else if (updateTerm == -1) {
					return CT.TERM_ALLOCATE_FAIL;
				} else {
					return CT.TERM_ALLOCATE_SUCCESS;
				}
	}

	private int updateTerm(TerminalInf entity, String allocateAgentId) throws Exception {
		TerminalInf terminalInf = terminalDao.findTermAllocate(entity);
		if (terminalInf == null || terminalInf.getTerminalId() == null || terminalInf.getTerminalId().length() <= 0) {
			log.info(" termAllocate  终端下拨  终端不存在");
			return CT.TERM_ALLOCATE_2;
		}

		terminalInf.setAgentId(entity.getAgentId());
		terminalInf.setPageSize(entity.getPageSize());
		List<TerminalInf> terminalInfs = terminalDao.findTermAllocateList(terminalInf);

		if (terminalInfs == null || terminalInfs.size() <= 0) {
			log.info(" termAllocate  所选终端库存不足");
			return CT.TERM_ALLOCATE_FAIL;
		}

		// 查询 可以下拨的终端
		if (entity.getTermAllocateNum() != null && entity.getTermAllocateNum() > terminalInfs.size()) {
			log.info(" termAllocate  所选终端库存不足");
			return CT.TERM_ALLOCATE_FAIL;
		}

		for (TerminalInf newTerminalInf : terminalInfs) {

			newTerminalInf.setAgentId(allocateAgentId);
			newTerminalInf.setEditUserId(entity.getEditUserId());
			newTerminalInf.setEditDate(entity.getEditDate());
			newTerminalInf.setRateLivelihood(entity.getRateLivelihood());
			newTerminalInf.setRateGeneral(entity.getRateGeneral());
			newTerminalInf.setRateGeneralTop(entity.getRateGeneralTop());
			newTerminalInf.setRateGeneralMaximun(entity.getRateGeneralMaximun());
			newTerminalInf.setRateEntertain(entity.getRateEntertain());
			newTerminalInf.setRateEntertainTop(entity.getRateEntertainTop());
			newTerminalInf.setRateEntertainMaximun(entity.getRateEntertainMaximun());
			newTerminalInf.setRateTCas(entity.getRateTCas());
			newTerminalInf.setMaxTCas(entity.getMaxTCas());

			Map<String, Object> ageCustMap = new HashMap<String, Object>();
			ageCustMap.put("terminalId", newTerminalInf.getTerminalId());
			// 1.查询关系表中终端是否已绑定 绑定：更新 未绑定：插入

			Map<String, Object> ageCustEntity = ageCustDao.selectAgeCustEntity(ageCustMap);
			if (ageCustEntity == null || ageCustEntity.size() <= 0) {
				ageCustMap.put("custId", "");
				ageCustMap.put("ageId", entity.getAgentId());
				ageCustDao.insertAgeCustInfo(ageCustMap);
			} else {
				ageCustEntity.put("ageId", entity.getAgentId());
				ageCustDao.updateAgent(ageCustEntity);
			}

			// 2.更新终端表
			terminalDao.termAllocate(newTerminalInf);

			log.info("记录终端 日志-------------");
			termStepService.saveTermStepDesc(entity.getTerminalNo(), TermStepDesc.STEP_1, entity.getEditUserId(), allocateAgentId);

		}
		return CT.TERM_ALLOCATE_SUCCESS;

	}

	@Override
	public Integer getTermFeeCount(TerminalInf entity) throws Exception {
		return terminalDao.getTermFeeCount(entity);
	}

	@Override
	public List<TerminalInf> getTermFeeListPage(TerminalInf entity) throws Exception {
		return terminalDao.getTermFeeListPage(entity);
	}

	@Override
	public boolean isExistEffectivePay(String terminalNo) throws Exception {
		int effectivePayCount = terminalDao.getEffectivePayCount(terminalNo);
		if (effectivePayCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TerminalInf getEidtManual(String fileName, TerminalInf entity) {
		log.info("解析excel...");
		List<Map<String, Object>> data = ZDAnalysisExcelUtil.excel(fileName);
		for (Map<String, Object> map : data) {
			TerminalInf inf = new TerminalInf();
			inf.setTerminalNo(map.get("c").toString());
			if (!"".equals(map.get("c").toString())) {
				TerminalInf ter = terminalDao.selectTerminalNo(inf);
				if (ter == null) {
					try {
						entity.setTerminalId(seqNoService.getSeqNoNew("SEQ_MPAMNG_TERMINAL", "9", "0"));
						entity.setTerminalSeq(seqNoService.getSeqNoNew("SEQ_MPAMNG_TERMINAL_NO", "8", "1"));
						entity.setTerminalCom(map.get("a").toString());
						entity.setTerminalType(map.get("b").toString());
						entity.setTerminalNo(map.get("c").toString());
						log.info("终端数据保存中.数据:{}", entity);
						terminalDao.insertEntity(entity);
						log.info("终端保存完成.记录终端 日志-------------");
						termStepService.saveTermStepDesc(entity.getTerminalNo(), TermStepDesc.STEP_0, entity.getCreateUserId());
						entity.setMsg("终端入库提交完成！");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					entity.setMsg("该" + map.get("c").toString() + "物理号已存在！");
					log.info("该" + map.get("c").toString() + "物理号已存在！！！");
				}
			} 
		}
		return entity;
	}
	
	@Override
	public String getFjpath(String uploadFileId){
		return terminalDao.getFjpath(uploadFileId);
	}

	@Override
	public boolean checkAgtFee(TerminalInf terminal) {
		
		Double termFee = 0.0;
		Double agentFee = 0.0;
		
		String agentid = terminal.getAgentId();
		if(null == agentid ||"".equals(agentid)){
			return true;
		}
		AgentInf agentInf = new AgentInf();
		agentInf.setAgentId(agentid);
		try {
			agentInf = agentDao.selectEntity(agentInf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String rateTCas = terminal.getRateTCas();//T0提现费率
		String maxTCas = terminal.getMaxTCas();//T0提现单笔收费
		String rateLivelihood = terminal.getRateLivelihood();//民生类
		String rateGeneral = terminal.getRateGeneral();//一般类
		String rateEntertain = terminal.getRateEntertain();//餐娱类
		String rateGeneralTop = terminal.getRateGeneralTop();//批发类
		String rateEntertainTop = terminal.getRateEntertainTop();//房产类
		
		String rateGeneralMaximun = terminal.getRateGeneralMaximun();//批发封顶
		String rateEntertainMaximun = terminal.getRateEntertainMaximun();//房产封顶
		
		if(rateTCas!= null && !rateTCas.equals("")){
			termFee = Double.parseDouble(rateTCas)+termFee;
		}
		if(rateLivelihood!= null && !rateLivelihood.equals("")){
			termFee = Double.parseDouble(rateLivelihood)+termFee;
		}
		if(rateGeneral!= null && !rateGeneral.equals("")){
			termFee = Double.parseDouble(rateGeneral)+termFee;
		}
		if(rateEntertain!= null && !rateEntertain.equals("")){
			termFee = Double.parseDouble(rateEntertain)+termFee;
		}
		if(rateGeneralTop!= null && !rateGeneralTop.equals("")){
			termFee = Double.parseDouble(rateGeneralTop)+termFee;
		}
		if(rateEntertainTop!= null && !rateEntertainTop.equals("")){
			termFee = Double.parseDouble(rateEntertainTop)+termFee;
		}
		
		String rateTCas_p = agentInf.getRateTCas();//T0提现费率
		String maxTCas_p = agentInf.getMaxTCas();//T0提现单笔收费
		String rateLivelihood_p = agentInf.getRateLivelihood();//民生类
		String rateGeneral_p = agentInf.getRateGeneral();//一般类
		String rateEntertain_p = agentInf.getRateEntertain();//餐娱类
		String rateGeneralTop_p = agentInf.getRateGeneralTop();//批发类
		String rateEntertainTop_p = agentInf.getRateEntertainTop();//房产类
		
		String rateGeneralMaximun_p = agentInf.getRateGeneralMaximun();//批发封顶
		String rateEntertainMaximun_p = agentInf.getRateEntertainMaximun();//房产封顶
 		
		
		if(rateTCas_p!= null && !rateTCas_p.equals("")){
			agentFee = Double.parseDouble(rateTCas_p)+agentFee;
		}
		if(rateLivelihood_p!= null && !rateLivelihood_p.equals("")){
			agentFee = Double.parseDouble(rateLivelihood_p)+agentFee;
		}
		if(rateGeneral_p!= null && !rateGeneral_p.equals("")){
			agentFee = Double.parseDouble(rateGeneral_p)+agentFee;
		}
		if(rateEntertain_p!= null && !rateEntertain_p.equals("")){
			agentFee = Double.parseDouble(rateEntertain_p)+agentFee;
		}
		if(rateGeneralTop_p!= null && !rateGeneralTop_p.equals("")){
			agentFee = Double.parseDouble(rateGeneralTop_p)+agentFee;
		}
		if(rateEntertainTop_p!= null && !rateEntertainTop_p.equals("")){
			agentFee = Double.parseDouble(rateEntertainTop_p)+agentFee;
		}
		
		if(Double.parseDouble(maxTCas_p) > Double.parseDouble(maxTCas)){
			return false;
		}

		if(agentFee > termFee){
			return false;
		}
		
		if(rateGeneralMaximun_p ==null || rateGeneralMaximun_p.equals("")){
			rateGeneralMaximun_p="0";
		}
		if(rateEntertainMaximun_p ==null || rateEntertainMaximun_p.equals("")){
			rateEntertainMaximun_p="0";
		}
		if(rateGeneralMaximun ==null || rateGeneralMaximun.equals("")){
			rateGeneralMaximun="0";
		}
		if(rateEntertainMaximun ==null || rateEntertainMaximun.equals("")){
			rateEntertainMaximun="0";
		}
		
		if(Double.parseDouble(rateGeneralMaximun_p) > Double.parseDouble(rateGeneralMaximun)){
			return false;
		}
		
		if(Double.parseDouble(rateEntertainMaximun_p) > Double.parseDouble(rateEntertainMaximun)){
			return false;
		}
		
		return true;
	}

	@Override
	public void modifyCustFee(TerminalInf terminal) {
		terminalDao.modifyCustFee(terminal);
	}
}
