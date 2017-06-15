package com.example.hb_we.keap;

public class Move_sqlit {
	String date;
	String isselct;

	public Move_sqlit() {
	}

	public Move_sqlit(String date, String isselct) {
		this.date = date;
		this.isselct = isselct;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIsselct() {
		return isselct;
	}

	public void setIsselct(String isselct) {
		this.isselct = isselct;
	}

}
