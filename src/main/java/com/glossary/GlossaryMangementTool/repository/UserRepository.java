/*
 * URL
 *
 * 1.1
 *
 * Copyright (c) 2018 FUJITSU
 * All rights reserved
 * 
 * Author : FCIPL
 * Date : 2018/08/13
 * 
 */

package com.glossary.GlossaryMangementTool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.glossary.GlossaryMangementTool.entity.UserInfo;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
	@Query(value = "select user_id, user_name from MT_Users where user_name=:username and password=:password", nativeQuery = true)
	Object fetchUserId(@Param("username") String username, @Param("password") String password);
	
	public List<UserInfo> findByUserNameIgnoreCaseAndPassword(String userName, String password);
}