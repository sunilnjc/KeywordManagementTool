/*
 * GlossaryDataController
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

package com.glossary.GlossaryMangementTool.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.glossary.GlossaryMangementTool.contants.URL;
import com.glossary.GlossaryMangementTool.entity.UserInfo;
import com.glossary.GlossaryMangementTool.form.User;
import com.glossary.GlossaryMangementTool.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @return login form
	 */
	@RequestMapping("/login")
	private String login(Model model) {
		// return login form
		return URL.LOGIN;
	}

	/**
	 * @param loginForm
	 * @param model
	 * @param request
	 * @return Validate Username and password
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	private String menu(@ModelAttribute(name = "loginForm") User loginForm, Model model, HttpServletRequest request) {

		HttpSession session = request.getSession(true);

		String username = loginForm.getUsername();
		String password = loginForm.getPassword();

		List<UserInfo> userList = userRepository.findByUserNameIgnoreCaseAndPassword(username, password);

		if (userList != null && userList.size()>0) {
			UserInfo user = userList.get(0);			
			session.setAttribute("userName", username);
			
			String usrLocale = user.getDefaultLocale();
			if(usrLocale == null || usrLocale.equals("")){
				usrLocale = "ja";
			}
			session.setAttribute("usrLocale", usrLocale);
				
			return URL.MENU;

		} else {
			model.addAttribute("invalidCredentials", true);
			return URL.LOGIN;
		}
	}
	
	/**
	 * @param request 
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	private String menuBack(HttpServletRequest request) {
		return URL.MENU;
	}

}
