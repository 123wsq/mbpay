package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

public class ProvinceInf extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	private String provinceId;
	
	private String provinceName;

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
