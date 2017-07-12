package com.ymu.framework.utils.json;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

public class User {
	
	@Expose
	@SerializedName("uname")
	@Since(1.2)
	private String username;
	
	@SerializedName("pwd")
	private String password;
	
	@Expose 
	private Date bornDate;
	
	@Expose
	@Since(1.1)
	private String gender;
	
	@Expose
	@Since(1.0)
	private String sex;

	public User() {
	}

	public User(String username, String password,Date bornDate, String gender, String sex) {
		super();
		this.password = password;
		this.username = username;
		this.bornDate = bornDate;
		this.gender = gender;
		this.sex = sex;
	}
	
	public User(String username, String password, String gender, String sex) {
		super();
		this.password = password;
		this.username = username;
		this.gender = gender;
		this.sex = sex;
	}
	
	

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", bornDate=" + bornDate + ", gender=" + gender + ", sex="
				+ sex + "]";
	}
	
	
}
