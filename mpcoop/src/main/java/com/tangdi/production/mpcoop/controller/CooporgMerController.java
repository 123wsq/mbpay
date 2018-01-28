package com.tangdi.production.mpcoop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpbase.util.MoneyUtils;
import com.tangdi.production.mpcoop.bean.CooporgInf;
import com.tangdi.production.mpcoop.bean.CooporgMerInf;
import com.tangdi.production.mpcoop.bean.CooporgTermkeyInf;
import com.tangdi.production.mpcoop.service.CooporgMerService;
import com.tangdi.production.mpcoop.service.CooporgService;
import com.tangdi.production.mpcoop.service.CooporgTermkeyService;
import com.tangdi.production.mpcoop.util.JGAnalysisExcelUtil;
import com.tangdi.production.tdbase.domain.ReturnMsg;
import com.tangdi.production.tdbase.util.ServletUtil;
import com.tangdi.production.tdcomm.idservice.GetSeqNoService;

/**
 * 
 * @author shanbeiyi
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpcoop/")
public class CooporgMerController {
	private static final Logger log = LoggerFactory.getLogger(CooporgMerController.class);

	@Autowired
	private CooporgService cooporgService;
	@Autowired
	private GetSeqNoService seqNoService;
	/**
	 * 合作机构商户终端维护接口
	 */
	@Autowired
	private CooporgMerService cooporgMerService;

	@Autowired
	private CooporgTermkeyService cooporgTermkeyService;
	
	
	
	
	/**
	 * 合作机构商户终端管理跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "coopOrgMerManage")
	public String cooporgMerManageView() {
		return "mpcoop/cooporgmer/coopOrgMerManage";
	}

	/**
	 *
	 * 合作机构商户终端信息列表
	 * 
	 * @param request
	 * @param session
	 * @return ReturnMsg
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerManage/query")
	@ResponseBody
	public ReturnMsg queryCoopOrgTer(@ModelAttribute("CooporgMerInf") CooporgMerInf cooporgMerInf,HttpServletRequest request) throws Exception {
		log.info("请求参数:"+cooporgMerInf);
		if(StringUtils.isNotEmpty(cooporgMerInf.getStartTime())){
			cooporgMerInf.setStartTime(cooporgMerInf.getStartTime().replace("-", "")+"000000");
		}else{
			cooporgMerInf.setStartTime(null);
		}
		if(StringUtils.isNotEmpty(cooporgMerInf.getEndTime())){
			cooporgMerInf.setEndTime(cooporgMerInf.getEndTime().replace("-", "")+"595959");
		}else{
			cooporgMerInf.setEndTime(null);
		}
		Integer total = cooporgMerService.getCount(cooporgMerInf);
		List<CooporgMerInf> cooporgmerList = cooporgMerService.getListPage(cooporgMerInf);
		return new ReturnMsg(total, cooporgmerList);
	}

	/**
	 * 进入添加合作机构商户终端界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerManage/addView")
	public String addView() {
		return "mpcoop/cooporgmer/coopOrgMerAdd";
	}
	
	/**
	 * 进入批量添加合作机构商户终端界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerManage/batchView")
	public String batchView() {
		return "mpcoop/cooporgmer/coopOrgMerBatch";
	}

	/**
	 * 添加合作机构商户终端信息
	 * 
	 * @param cooporgMerInf
	 * @return
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerAdd/add")
	@ResponseBody
	public ReturnMsg addCoopMerTer(@ModelAttribute("CooporgMerInf") CooporgMerInf cooporgMerInf) {
		ReturnMsg rm = new ReturnMsg();
		addCoopMerT(cooporgMerInf, rm);
		return rm;
	}
	
	/**
	 * 批量添加合作机构商户终端信息
	 * 
	 * @param cooporgMerInf
	 * @return
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerAdd/batchAdd")
	@ResponseBody
	public ReturnMsg batchAddCoopMerTer(@RequestParam("uploadFileId") String uploadFileId,@ModelAttribute("CooporgMerInf") CooporgMerInf cooporgMerInf) {
		ReturnMsg rm = new ReturnMsg();
		log.info("获取选择的excel文件ID:"+uploadFileId);
		String fileName = cooporgMerService.getFjpath(uploadFileId);
		log.info("获取excel文件的路径:"+fileName);
		List<CooporgMerInf> data = JGAnalysisExcelUtil.excel(fileName);
		for (CooporgMerInf inf : data) {
			CooporgMerInf coo=cooporgMerService.selectProvinceId(inf);
			if(coo != null){
				inf.setProvinceID(coo.getProvinceID());
				inf.setCooporgNo(coo.getCooporgNo());
				inf.setRateType(coo.getRateType());
				inf.setEditUserId(cooporgMerInf.getEditUserId());
			}else{
				rm.setMsg("203","合作机构商户终端添加失败，该省份[" + inf.getProvinceName() + "]不存在！");
				return rm;
			}
			cooporgMerInf = inf;
			addCoopMerT(cooporgMerInf, rm);
		}
		return rm;
	}

	/**
	 * 添加合作机构商户终端信息的公用部分
	 * 
	 * @param cooporgMerInf 
	 * @return
	 */
	private ReturnMsg addCoopMerT(CooporgMerInf cooporgMerInf, ReturnMsg rm) {
		int num;
		int key;
		try {
			log.info("返回参数："+cooporgMerInf);
			CooporgInf coop = new CooporgInf();
			coop.setCooporgNo(cooporgMerInf.getCooporgNo());
			//判断合作机构的类型：0——收单；1——快捷
			CooporgInf coopType=cooporgService.selectTypeEntity(coop);
			if(coopType.getCoopType().equals("1")){
				//快捷：没有终端号，给一个自增的数
				String terNo=seqNoService.getSeqNoNew("TER_NO", "8", "0");
				cooporgMerInf.setTerNo(terNo);
			}
			//判断合作机构是否存在
			int count1 = cooporgService.identifyId(coop);
			if (count1 <= 0) {
				rm.setMsg("203", "合作机构不存在，合作机构[" + cooporgMerInf.getCooporgNo() + "]不存在！");
				return rm;
			}
			//判断商户编号是否已经存在
			int count3=cooporgMerService.validateMerNo(cooporgMerInf);
			if(count3>0){
				rm.setMsg("203", "合作机构商户终端添加失败，商户编号[" + cooporgMerInf.getMerNo() + "]已存在！");
				return rm;
			}
			//判断合作机构商户终端是否已存在
			int count2 = cooporgMerService.identifyId(cooporgMerInf);
			if (count2 > 0) {
				rm.setMsg("203", "合作机构商户终端添加失败，合作机构[" + cooporgMerInf.getCooporgNo() + "]商户号[" + cooporgMerInf.getMerNo() + "]终端号[" + cooporgMerInf.getTerNo() + "]已存在！");
				return rm;
			}
			//添加合作机构商户终端
			num = cooporgMerService.addEntity(cooporgMerInf);
			//密钥表里添加终端信息
			CooporgTermkeyInf TermkeyInf = new CooporgTermkeyInf();
			TermkeyInf.setCooporgNo(cooporgMerInf.getCooporgNo());
			TermkeyInf.setMerNo(cooporgMerInf.getMerNo());
			TermkeyInf.setTerNo(cooporgMerInf.getTerNo());
			key = cooporgTermkeyService.addEntity(TermkeyInf);
			
			if (num == 1 && key == 1)
				rm.setMsg("200", "合作机构商户终端添加成功！");
			else
				rm.setMsg("201", "合作机构商户终端添加失败！");			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			rm.setMsg("202", "用户添加异常！");
		}
		return rm;
	}

	/**
	 * 删除/批量删除合作机构商户终端信息
	 * 
	 * @param cooporgMerInf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerManage/delete")
	@ResponseBody
	public ReturnMsg removeCoopOrgMers(@RequestParam("cooporgNo") String cooporgno, @RequestParam("merNo") String merno, @RequestParam("terNo") String terno) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		try {
			String[] coopOrgNos = cooporgno.split(",");
			String[] merNos = merno.split(",");
			String[] terNos = terno.split(",");
			for (int i = 0; i < coopOrgNos.length; i++) {
				CooporgMerInf entity = new CooporgMerInf();				
				entity.setCooporgNo(coopOrgNos[i]);
				entity.setMerNo(merNos[i]);
				entity.setTerNo(terNos[i]);
				CooporgTermkeyInf TermkeyInf = new CooporgTermkeyInf();
				TermkeyInf.setCooporgNo(coopOrgNos[i]);
				TermkeyInf.setMerNo(merNos[i]);
				TermkeyInf.setTerNo(terNos[i]);
				//删除合作机构商户终端信息
				cooporgMerService.removeEntity(entity);
				//删除合作机构商户终端密钥信息
				cooporgTermkeyService.removeEntity(TermkeyInf);
			}
			msg.setMsg("200", "合作机构删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}

		return msg;
	}
	
	
	/**
	 * 进入合作机构商户终端修改界面 ReturnMsg
	 * 
	 * @return
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerManage/editView")
	public String editView() {
		return "mpcoop/cooporgmer/coopOrgMerEdit";
	}

	/**
	 * 查询单条合作机构商户终端信息
	 * 
	 * @param fileDownloadInf
	 * @return ReturnMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerManage/queryCoopOrgMerById")
	@ResponseBody
	public ReturnMsg queryCoopOrgMerById(@RequestParam("cooporgNo") String cooporgno, @RequestParam("merNo") String merno, @RequestParam("terNo") String terno) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		CooporgMerInf comi = new CooporgMerInf();
		comi.setCooporgNo(cooporgno.replace(",", ""));
		comi.setMerNo(merno.replace(",", ""));
		comi.setTerNo(terno.replace(",", ""));
		comi = cooporgMerService.getEntity(comi);
		msg.setObj(comi);
		return msg;
	}

	
	/**
	 * 根据合作机构编号查询对应状态
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cooporgmer/queryCooporgInfById")
	@ResponseBody
	public ReturnMsg queryCoopType(HttpServletRequest request, HttpSession session) throws Exception {
		String cooporgNo = ServletRequestUtils.getStringParameter(request, "cooporgNo", "");
		log.debug("CooporgController queryCoopTypeById:   CooporgNo:" + cooporgNo);
		ReturnMsg rm = new ReturnMsg();
		CooporgInf coop = new CooporgInf();
		coop.setCooporgNo(cooporgNo.replace(",", ""));
		rm.setObj(cooporgService.selectTypeEntity(coop));
		return rm;
	}

	/**
	 * 修改合作机构商户终端信息
	 * 
	 * @param cooporgMerInf
	 * @return
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerEdit/edit")
	@ResponseBody
	public ReturnMsg editCoopMerTer(@ModelAttribute("CooporgMerInf") CooporgMerInf cooporgMerInf) {
		ReturnMsg msg = new ReturnMsg();
		try {
			cooporgMerService.modifyEntity(cooporgMerInf);
			msg.setMsg("200", "合作机构商户终端更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", "更新失败");
		}
		return msg;
	}

	/**
	 * 进入合作机构商户终端详细信息页面
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerManage/selectinfView")
	public String selectinfCoopOrgMerView() {
		return "mpcoop/cooporgmer/coopOrgMerSelectInf";
	}

	/**
	 *
	 * 合作机构商户终端信息列表
	 * 
	 * @param request
	 * @param session
	 * @return ReturnMsg
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "cooporgmer/queryCoopOrgList")
	@ResponseBody
	public ReturnMsg queryCoopOrgList(@ModelAttribute("CooporgMerInf") CooporgMerInf cooporgMerInf) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.ALWAYS);
		mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//非封顶类费率是没有费率封顶金额的，为空
		if(cooporgMerInf.getRateTypeTop()!=null){
			cooporgMerInf.setRateTypeTop(MoneyUtils.toStrCent(cooporgMerInf.getRateTypeTop()));
		}
			
		int total = cooporgMerService.countCooporgList(cooporgMerInf);
		List<CooporgMerInf> cooporgmerList = cooporgMerService.getCooporgList(cooporgMerInf);
		log.info("返回数据："+cooporgmerList);
		try {
			rm.setObj(mapper.writeValueAsString(cooporgmerList));
			rm.setRecords(total);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rm;
	}


	
	/**
	 * 合作机构商户终端签到
	 * @param cooporgno
	 * @param merno
	 * @param terno
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cooporgmer/coopOrgMerManage/orgmersgin")
	@ResponseBody
	public ReturnMsg signCoopOrgMers(@RequestParam("cooporgNo") String cooporgno, 
			@RequestParam("merNo") String merno, 
			@RequestParam("terNo") String terno) throws Exception {
		ReturnMsg msg = new ReturnMsg();
		try {
			String[] coopOrgNos = cooporgno.split(",");
			String[] merNos = merno.split(",");
			String[] terNos = terno.split(",");
			for (int i = 0; i < coopOrgNos.length; i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("coopOrgNo", coopOrgNos[i]);
				map.put("merNo", merNos[i]);
				map.put("terNo", terNos[i]);
				cooporgMerService.bankRtrSign(map);
			}
			msg.setMsg("200", "合作机构签到成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg.setMsg("202", e.getMessage());
		}
		return msg;
	}

}
