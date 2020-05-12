package com.dragon.pojo;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class User {
	private Integer id; //id 
	@NotEmpty(message = "用户编码必给")
	private String userCode; //用户编码
	@NotEmpty(message = "用户名必须给")
	private String userName; //用户名
	@Length(min=6,max=12,message = "密码长度必须在6到12位")
	private String userPassword; //密码
	private Integer gender;  //性别
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message = "出生日期必须是一个过去的时间")
	@JSONField(format="yyyy-MM-dd")
	private Date birthday;  //生日
	private String phone;   //电话
	private String address; //地址
	private Integer userRole;    //角色编号
	private Integer createdBy;   //创建者
	private Date creationDate; //创建日期
	private Integer modifyBy;     //修改者
	private Date modifyDate;   //修改日期
	private String idpicpath;//证件照
	private String workpicpath;//工作证照
	
	public String getIdpicpath() {
		return idpicpath;
	}
	public void setIdpicpath(String idpicpath) {
		this.idpicpath = idpicpath;
	}
	public String getWorkpicpath() {
		return workpicpath;
	}
	public void setWorkpicpath(String workpicpath) {
		this.workpicpath = workpicpath;
	}
	@SuppressWarnings("unused")
	private Integer age;//骞撮緞
	
	private String userRoleName;    //鐢ㄦ埛瑙掕壊鍚嶇О
	
	
	public String getUserRoleName() {
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public Integer getAge() {
		/*long time = System.currentTimeMillis()-birthday.getTime();
		Integer age = Long.valueOf(time/365/24/60/60/1000).IntegerValue();*/
		Date date = new Date();
		@SuppressWarnings("deprecation")
		Integer age = date.getYear()-birthday.getYear();
		return age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getUserRole() {
		return userRole;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
