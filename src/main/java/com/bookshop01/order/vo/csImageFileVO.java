package com.bookshop01.order.vo;

public class csImageFileVO {
	private int article_id;
	private int image_id;
	private String fileName;
	private String fileSize;
	private String reg_id;
	

	public csImageFileVO() {
		super();
	}


	public int getArticle_id() {
		return article_id;
	}


	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}


	public int getImage_id() {
		return image_id;
	}


	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileSize() {
		return fileSize;
	}


	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}


	public String getReg_id() {
		return reg_id;
	}


	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}


}