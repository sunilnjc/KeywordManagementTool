/*
 * GlossaryTerms
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

/*
 * URL
 *
 * 1.1
 *
 * Copyright (c) 2018 FUJITSU
 * All rights reserved
 * 
 * Author : FCIPL
 * Date : 2018/08/14
 * 
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MT_Terms")
public class GlossaryTerms implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8647083230088484352L;

	@Id
	@Column(name = "TermId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "BaseItemId")
	private Integer baseItemId;

	@Column(name = "Term_Ja", length = 64)
	@NotBlank(message = "日本語名  field is required *")
	@Size(max = 64, message="Input must be less than or equal to {max} characters")
	private String termJa;

	@Column(name = "Term_Ja_Kana", length = 192)
	@NotBlank(message = "日本語読み  field is required *")
	@Size(max = 192, message="Input must be less than or equal to {max} characters")
	private String termJaKana;

	@Column(name = "Term_Ja_Short", length = 16)
	private String termJaShort;

	@Column(name = "Description_Ja", length = 300)
	private String descriptionJa;

	@Column(name = "Term_En", length = 100)
	@Size(max = 100, message="Input must be less than or equal to {max} characters")
	private String termEn;

	@Column(name = "Term_En_Short", length = 30)
	private String termEnShort;

	@Column(name = "Description_En", length = 300)
	private String descriptionEn;

	@Column(name = "Term_Tags", length = 300)
	private String termTags;

	@Column(name = "Notes", length = 128)
	private String notes;

	@Column(name = "EntryDate")
	private Date entryDate;

	@Column(name = "LastUpdate")
	private Date lastUpdateDate;

	@Column(name = "UpdateUser", length = 32)
	private String updateUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBaseItemId() {
		return baseItemId;
	}

	public void setBaseItemId(Integer baseItemId) {
		this.baseItemId = baseItemId;
	}

	public String getTermJa() {
		return termJa;
	}

	public void setTermJa(String termJa) {
		this.termJa = termJa;
	}

	public String getTermJaKana() {
		return termJaKana;
	}

	public void setTermJaKana(String termJaKana) {
		this.termJaKana = termJaKana;
	}

	public String getTermJaShort() {
		return termJaShort;
	}

	public void setTermJaShort(String termJaShort) {
		this.termJaShort = termJaShort;
	}

	public String getDescriptionJa() {
		return descriptionJa;
	}

	public void setDescriptionJa(String descriptionJa) {
		this.descriptionJa = descriptionJa;
	}

	public String getTermEn() {
		return termEn;
	}

	public void setTermEn(String termEn) {
		this.termEn = termEn;
	}

	public String getTermEnShort() {
		return termEnShort;
	}

	public void setTermEnShort(String termEnShort) {
		this.termEnShort = termEnShort;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public String getTermTags() {
		return termTags;
	}

	public void setTermTags(String termTags) {
		this.termTags = termTags;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	@Override
	public String toString() {
		return "GlossaryTerms [id=" + id + ", baseItemId=" + baseItemId + ", termJa=" + termJa + ", termJaKana="
				+ termJaKana + ", termJaShort=" + termJaShort + ", descriptionJa=" + descriptionJa + ", termEn="
				+ termEn + ", termEnShort=" + termEnShort + ", descriptionEn=" + descriptionEn + ", termTags="
				+ termTags + ", notes=" + notes + ", entryDate=" + entryDate + ", lastUpdateDate=" + lastUpdateDate
				+ ", updateUser=" + updateUser + "]";
	}
}
