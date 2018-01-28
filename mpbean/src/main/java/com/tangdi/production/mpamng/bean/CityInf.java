package com.tangdi.production.mpamng.bean;

import com.tangdi.production.tdbase.domain.BaseBean;

public class CityInf extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	private String cityId;
	
	private String cityName;
	
	private String provinceId;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
