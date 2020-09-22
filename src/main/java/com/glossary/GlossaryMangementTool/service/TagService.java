/*
 * TagService
 *
 * 1.0
 *
 * Copyright (c) 2018 FUJITSU
 * All rights reserved
 * 
 * Author : FCIPL
 * Date : 2018/08/28
 */
package com.glossary.GlossaryMangementTool.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glossary.GlossaryMangementTool.controller.TagController;
import com.glossary.GlossaryMangementTool.entity.Tag;
import com.glossary.GlossaryMangementTool.repository.TagRepository;
import com.glossary.GlossaryMangementTool.util.AjaxResponseMessage;
import com.glossary.GlossaryMangementTool.util.ErrorMessages;

@Service
public class TagService {
	@Autowired
	private TagRepository tagRepository;
	List<ErrorMessages> errorList;
	ErrorMessages error = new ErrorMessages();
	
	private static final Logger logger = LoggerFactory.getLogger(TagController.class);
	
	/**
	 * 
	 * @return 
	 *   calling tagRepository return List<Tags>
	 */
	public AjaxResponseMessage getTagList() {
		logger.info("getTagList() method from TagService");
		List<Tag> tagList = tagRepository.findAllByOrderByTagId();
		AjaxResponseMessage response = new AjaxResponseMessage();
		response.setListOfTags(tagList);
		return response;
	}

	/**
	 * 
	 * @param tagregister
	 * 
	 * save the TagForm in database through tagRespository
	 */
	public AjaxResponseMessage saveTagData(Tag tagregister) {
		errorList = new ArrayList<ErrorMessages>();
		logger.info("saveTagData() method from TagService");

		int tagId = tagregister.getTagId();
		String englishTag = tagregister.getEnglishTag();
		String japaneseTag = tagregister.getJapaneseTag();
		List<Tag> findEnglishTags = tagRepository.findByEnglishTagIgnoreCaseAndTagIdNot(englishTag, tagId);
		List<Tag> findJapaneseTags = tagRepository.findByJapaneseTagIgnoreCaseAndTagIdNot(japaneseTag, tagId);

		AjaxResponseMessage response = new AjaxResponseMessage();
		return findDuplicateTags(tagregister, findEnglishTags, findJapaneseTags, response);
	}
	
	/**
	 * @param tagregister
	 * @param findEnglishTags
	 * @param findJapaneseTags
	 * @param response
	 * @return If JapaneseTag and English tagsize==0 then save the registerthe
	 *         form data.
	 */
	private AjaxResponseMessage findDuplicateTags(Tag tagregister, List<Tag> findEnglishTags,
			List<Tag> findJapaneseTags, AjaxResponseMessage response) {
		if (findJapaneseTags.size() == 0 && findEnglishTags.size() == 0) {
			Tag tag = tagRepository.save(tagregister);
			response.setValidated(true);
			response.setTags(tag);
			return response;

		} else {
			if (findJapaneseTags.size() != 0) {
				ErrorMessages error = new ErrorMessages();
				logger.error("japaneseTag is duplicated");
				error.setErrorId("japaneseTag");
				error.setErrorMessage("japaneseTag is already registered");
				errorList.add(error);
				response.setValidated(false);
				System.out.println("japaneseTag is duplicated");
			}
			if (findEnglishTags.size() != 0) {
				logger.error("EnglishTag is duplicated");
				System.out.println("EnglishTag is duplicated");
				ErrorMessages error1 = new ErrorMessages();
				error1.setErrorId("englishTag");
				error1.setErrorMessage("EnglishTag is already registered");
				errorList.add(error1);
				response.setValidated(false);
			}
			response.setErrorMessages(errorList);
			return response;
		}
	}
	
	public Long getMaxId() {

		return tagRepository.getMaxId();
	}	
}
