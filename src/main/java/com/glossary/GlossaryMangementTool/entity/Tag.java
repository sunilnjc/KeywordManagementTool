/*
 * Tag
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MT_Tags")
public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3414479583365228969L;

	@Id
	@Column(name = "TagId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tagId;

	@Column(name = "Tag_Ja", length = 16)
	@NotBlank(message = "JapaneseTag is mandatory*")
	@Size(max = 16, message="Input must be less than or equal to {max} characters")
	private String japaneseTag;

	@Column(name = "Tag_En", length = 32)
	@NotBlank(message = " EnglishTag  is mandatory*")
	@Size(max = 32, message="Input must be less than or equal to {max} characters")
	private String englishTag;

	@Column(name = "Tag_Order")
	private int tagOrder;

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	
	public int getTagOrder() {
		return tagOrder;
	}

	public void setTagOrder(int tagOrder) {
		this.tagOrder = tagOrder;
	}

	public String getJapaneseTag() {
		return japaneseTag;
	}

	public void setJapaneseTag(String japaneseTag) {
		this.japaneseTag = japaneseTag;
	}

	public String getEnglishTag() {
		return englishTag;
	}

	public void setEnglishTag(String englishTag) {
		this.englishTag = englishTag;
	}

}