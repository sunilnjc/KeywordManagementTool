/*
 * TagRepository
 *
 * 1.0
 *
 * Copyright (c) 2018 FUJITSU
 * All rights reserved
 * 
 * Author : FCIPL
 * Date : 2018/08/22
 * 
 */

package com.glossary.GlossaryMangementTool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.glossary.GlossaryMangementTool.entity.Tag;



public interface TagRepository extends JpaRepository<Tag, Integer> {
	@Query("SELECT coalesce(max(tag.tagId), 0) FROM Tag tag")
	public Long getMaxId();

	List<Tag> findAllByOrderByTagId();
	
	List<Tag> findByEnglishTagIgnoreCaseAndTagIdNot(String englishTag, int tagId);

	List<Tag> findByJapaneseTagIgnoreCaseAndTagIdNot(String japaneseTag, int tagId);
}
