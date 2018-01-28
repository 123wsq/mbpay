package com.tangdi.production.mpomng.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangdi.production.mpbase.util.DateUtil;
import com.tangdi.production.mpomng.bean.RecInf;
import com.tangdi.production.mpomng.service.RecInfService;
import com.tangdi.production.tdbase.domain.ReturnMsg;


/**
 * 对账信息
 * @author 
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/mpomng/")
public class RecInfController {
	private static final Logger log = LoggerFactory.getLogger(RecInfController.class);

	@Autowired
	private RecInfService recInfService;

	/**
	 * 跳转到对账信息查询
	 * @return
	 */
	@RequestMapping({ "recInf/recInfoListView" })
	public String recInfoListView() {
		return "mpomng/prdInf/queryRecInfo";
	}
	
	/**
	 * 对账信息查询
	 * @param prdInf
	 * @return
	 */
	@RequestMapping(value = "recInf/queryRecInfList")
	@ResponseBody
	public ReturnMsg queryRecInfList(@ModelAttribute("recInf") RecInf recInf) {
		int total = 0;
		List<RecInf> list = null;
		try {
			
			log.debug("查询参数:{}", recInf.debug());
			try{	
				if(StringUtils.isNotEmpty(recInf.getCkdt())){
					recInf.setCkdt((DateUtil.convertDateToString((Date)DateUtil.convertStringToDate(recInf.getCkdt(),"yyyy-MM-dd"), DateUtil.FORMAT_YYYYMMDD)));
				}
			}catch(Exception e){
				recInf.setCkdt(null);
			}
			
			try{	
				if(StringUtils.isNotEmpty(recInf.getRecstatus())){
					recInf.setRecstatus(recInf.getRecstatus());
				}
			}catch(Exception e){
				recInf.setRecstatus(null);
			}
			
			total = recInfService.getCount(recInf);
			list = recInfService.getListPage(recInf);
			log.debug("总记录数:{},数据列表：{}", total, list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ReturnMsg(total, list);
	}
	
	@RequestMapping(value = "recInf/queryRecInfCountList")
	@ResponseBody
	public ReturnMsg queryPrdInfCountList(@ModelAttribute("recInf") RecInf recInf) {
		return null;
	}
}
