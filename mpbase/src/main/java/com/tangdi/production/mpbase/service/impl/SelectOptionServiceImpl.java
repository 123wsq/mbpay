package com.tangdi.production.mpbase.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpbase.exception.SelectOptionException;
import com.tangdi.production.mpbase.service.SelectOptionService;
import com.tangdi.production.mpbase.util.JUtil;
import com.tangdi.production.tdbase.domain.DictObj;
import com.tangdi.production.tdbase.util.DictUtils;

@Service
public class SelectOptionServiceImpl implements SelectOptionService{
	private static Logger  log = LoggerFactory.getLogger(SelectOptionServiceImpl.class);
	
	@Override
	public String querySelectOption(String did,String value,String type) throws SelectOptionException {
		log.debug("querySelectOption[id:{},value:{},type;{}]",did,value,type);
		
		/*
		try{
			if(type == null || type.equals("0")){
				if(value != null && !value.equals("")){
					map= DictUtils.getDictOptionMap(did+":"+value);
				}else{
					map= DictUtils.getDictOptionMap(did);
				}
			}else {
				map= DictUtils.getDictOptionMapSpace(did);
			}
		
		}catch(Exception e){
			throw new SelectOptionException("查询下拉框信息出错了!",e);
		}*/
		
	//	HashMap<String,Object> map =  DictUtils.getDictCodeMap(did);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		HashMap<String,Object> nmap = new HashMap<String,Object>();
		Map<String,String> dmap = DictUtils.getDictCodeMap(did);
		Iterator<String> it = dmap.keySet().iterator();
	
		for(;it.hasNext();){
			Map<String,Object> map = new HashMap<String,Object>();
			String key = (String) it.next();
			map.put("text", dmap.get(key));
			map.put("value",key);
			list.add(map);
		}

		nmap.put("options", list);
		return JUtil.toJsonString(nmap) ;

	
	}

	@Override
	public Map<String, Object> queryDCIT()
			throws SelectOptionException {
		Map<String,Object> rmap = new LinkedHashMap<String,Object>();
		Map<String,DictObj> dictMap = DictUtils.getDictMap();
		
		Iterator<String> it = dictMap.keySet().iterator();
		
		for(;it.hasNext();){
			String key = (String) it.next();
			List<DictObj> list = dictMap.get(key).getChildList();
			for(DictObj dict:list){
				Map<String,String> vmap = new HashMap<String,String>();
				
				log.debug("父节点={}",dict.toString());
				List<DictObj> childlist = dict.getChildList();
					for(DictObj child:childlist){
						log.debug("子节点={}",child.toString());
						vmap.put(child.getAttrMap("DICT_VALUE"), child.getAttrMap("DICT_NAME"));
					}	
					rmap.put(dict.getAttrMap("DICT_CODE"), vmap);
			}
		}
		return rmap;
	}

}
