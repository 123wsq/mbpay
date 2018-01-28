package com.tangdi.production.mpbase.service;

import java.util.LinkedList;

import com.tangdi.production.mpbase.exception.TranException;
import com.tangdi.production.mpbase.util.ImgFont;

public interface EsignService {
	
	public String save(LinkedList<ImgFont> list,String spath,String ctype) throws TranException;


}
