package com.bookshop01.cs.vo;

import java.util.Date;

public class csVO {
	private int acrticle_id;
	private String user_id;
	private String article_title;
	private String article_detail;
	private String goods_fileName;
	
	public int getAcrticle_id() {
		return acrticle_id;
	}
	public void setAcrticle_id(int acrticle_id) {
		this.acrticle_id = acrticle_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getArticle_detail() {
		return article_detail;
	}
	public void setArticle_detail(String article_detail) {
		this.article_detail = article_detail;
	}
	public String getGoods_fileName() {
		return goods_fileName;
	}
	public void setGoods_fileName(String goods_fileName) {
		this.goods_fileName = goods_fileName;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	private Date regdate;

}
