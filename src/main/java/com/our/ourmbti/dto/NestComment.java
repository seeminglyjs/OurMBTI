package com.our.ourmbti.dto;

import java.util.Date;

public class NestComment {

	private int ncNo;
	private int cNo;
	private int uNo;
	private String ncContent;
	private int ncReport;
	private int ncLikes;
	private Date ncWriteDate;
	
	@Override
	public String toString() {
		return "NestComment [ncNo=" + ncNo + ", cNo=" + cNo + ", uNo=" + uNo + ", ncContent=" + ncContent
				+ ", ncReport=" + ncReport + ", ncLikes=" + ncLikes + ", ncWriteDate=" + ncWriteDate + "]";
	}
	
	public int getNcNo() {
		return ncNo;
	}
	public void setNcNo(int ncNo) {
		this.ncNo = ncNo;
	}
	public int getcNo() {
		return cNo;
	}
	public void setcNo(int cNo) {
		this.cNo = cNo;
	}
	public int getuNo() {
		return uNo;
	}
	public void setuNo(int uNo) {
		this.uNo = uNo;
	}
	public String getNcContent() {
		return ncContent;
	}
	public void setNcContent(String ncContent) {
		this.ncContent = ncContent;
	}
	public int getNcReport() {
		return ncReport;
	}
	public void setNcReport(int ncReport) {
		this.ncReport = ncReport;
	}
	public int getNcLikes() {
		return ncLikes;
	}
	public void setNcLikes(int ncLikes) {
		this.ncLikes = ncLikes;
	}
	public Date getNcWriteDate() {
		return ncWriteDate;
	}
	public void setNcWriteDate(Date ncWriteDate) {
		this.ncWriteDate = ncWriteDate;
	}	
}
