/*
 * UserInfo
 *
 * 1.0
 *
 * Copyright (c) 2018 FUJITSU
 * All rights reserved
 * 
 * Author : FCIPL
 * Date : 2018/08/13
 * 
 */

package com.glossary.GlossaryMangementTool.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MT_Users")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;

	@Id
	@Column(name = "UserId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(name = "UserName", length = 32)
	private String userName;

	@Column(name = "Password", length = 64)
	private String password;

	@Column(name = "GivenName", length = 32)
	private String givenName;

	@Column(name = "MiddleName", length = 32)
	private String middleName;

	@Column(name = "SurName", length = 32)
	private String surName;

	@Column(name = "MailAddress", length = 128)
	private String mailAddress;

	@Column(name = "DefaultLocale", length = 20)
	private String defaultLocale;

	@Column(name = "EntryDate")
	private Date entryDate;

	@Column(name = "LastUpdate")
	private Date lastUpdateDate;

	@Column(name = "UpdateUser", length = 32)
	private String updateUser;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}