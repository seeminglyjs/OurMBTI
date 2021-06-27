package com.our.ourmbti.dto;

import java.util.Date;

public class Board {
	private int bNo;
	private int uNo;
	private String bTitle;
	private String bContent;           
	private Date bWriteDate;      
	private Date bUpdateDate;      
	private int bHit;
	private String bReport;
	private int bLikes;
	private String bType;
	
	@Override
	public String toString() {
		return "Board [bNo=" + bNo + ", uNo=" + uNo + ", bTitle=" + bTitle + ", bContent=" + bContent + ", bWriteDate="
				+ bWriteDate + ", bUpdateDate=" + bUpdateDate + ", bHit=" + bHit + ", bReport=" + bReport + ", bLikes="
				+ bLikes + ", bType=" + bType + "]";
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
	public String getbTitle() {
		return bTitle;
	}
	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}
	public String getbContent() {
		return bContent;
	}
	public void setbContent(String bContent) {
		this.bContent = bContent;
	}
	public Date getbWriteDate() {
		return bWriteDate;
	}
	public void setbWriteDate(Date bWriteDate) {
		this.bWriteDate = bWriteDate;
	}
	public Date getbUpdateDate() {
		return bUpdateDate;
	}
	public void setbUpdateDate(Date bUpdateDate) {
		this.bUpdateDate = bUpdateDate;
	}
	public int getbHit() {
		return bHit;
	}
	public void setbHit(int bHit) {
		this.bHit = bHit;
	}
	public String getbReport() {
		return bReport;
	}
	public void setbReport(String bReport) {
		this.bReport = bReport;
	}
	public int getbLikes() {
		return bLikes;
	}
	public void setbLikes(int bLikes) {
		this.bLikes = bLikes;
	}
	public String getbType() {
		return bType;
	}
	public void setbType(String bType) {
		this.bType = bType;
	}
	
}
