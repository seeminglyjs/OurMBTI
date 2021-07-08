package com.our.ourmbti.dto;

public class BoardLikes {

	private int blikeNo;
	private int bNo;
	private int uNo;
	
	@Override
	public String toString() {
		return "BoardLikes [blikeNo=" + blikeNo + ", bNo=" + bNo + ", uNo=" + uNo + "]";
	}
	
	public int getBlikeNo() {
		return blikeNo;
	}
	public void setBlikeNo(int blikeNo) {
		this.blikeNo = blikeNo;
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
	
	
}
