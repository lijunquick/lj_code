package com.asj.pls.info;

import java.io.Serializable;

/**
 * 商品实体类
 * */
public class AddressInfo implements Serializable {
	
	private static final long serialVersionUID = -7452348332582739004L;
	
	private Long adId;//地址编号
	private String name;//姓名
	private String mobile;//电话
	private String detail;//详细地址
	private Integer isUse;//1可用 2不可用
	public Long getAdId() {
		return adId;
	}
	public void setAdId(Long adId) {
		this.adId = adId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
}
