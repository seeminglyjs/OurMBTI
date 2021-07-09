package com.our.ourmbti.dto;

import java.util.Date;

public class Comment {

	private int cNo;
	private int bNo;
	private int uNo;
	private String cContent;
	private String cReport;
	private int cLikes;
	private Date cWriteDate;
	
	@Override
	public String toString() {
		return "Comment [cNo=" + cNo + ", bNo=" + bNo + ", uNo=" + uNo + ", cContent=" + cContent + ", cReport="
				+ cReport + ", cLikes=" + cLikes + ", cWriteDate=" + cWriteDate + "]";
	}
	
	public int getcNo() {
		return cNo;
	}
	public void setcNo(int cNo) {
		this.cNo = cNo;
	}
	public int getbNo() {
		return bNo;
	}
	public void setbNo(int bNo) {
		this.bNo = bNo;
	}
	public int getuNo() {
		return uNo;
	}
	public void setuNo(int uNo) {
		this.uNo = uNo;
	}
	public String getcContent() {
		return cContent;
	}
	public void setcContent(String cContent) {
		this.cContent = cContent;
	}
	public String getcReport() {
		return cReport;
	}
	public void setcReport(String cReport) {
		this.cReport = cReport;
	}
	public int getcLikes() {
		return cLikes;
	}
	public void setcLikes(int cLikes) {
		this.cLikes = cLikes;
	}
	public Date getcWriteDate() {
		return cWriteDate;
	}
	public void setcWriteDate(Date cWriteDate) {
		this.cWriteDate = cWriteDate;
	}
	
	
}
