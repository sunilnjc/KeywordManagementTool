package com.glossary.GlossaryMangementTool.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glossary.GlossaryMangementTool.entity.Tag;
import com.glossary.GlossaryMangementTool.service.TagService;
import com.glossary.GlossaryMangementTool.util.AjaxResponseMessage;
import com.glossary.GlossaryMangementTool.util.ErrorMessages;

@Controller
public class TagController {
	@Autowired
	private TagService tagService;

	private static final Logger logger = LoggerFactory.getLogger(TagController.class);
	
	/**
	 * 
	 * @return 
	 * 
	 *  Fetch the allTagList from Mt_tags table and retun to Taglist.html
	 */
	@GetMapping("/getAllTagList")
	public @ResponseBody AjaxResponseMessage fetchTaglist(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AjaxResponseMessage ajaxResponseMessage=new AjaxResponseMessage();
		String username = (String) session.getAttribute("userName");
		if (username != null) {
			logger.info("fetchTaglist() method from TagController");
			return tagService.getTagList();
		}else{
			ajaxResponseMessage.setSessionTimeout(true);
			logger.info("fetchTaglist():Session get expired");
			return ajaxResponseMessage;
		}
	}

	/**
	 * 
	 * @param tagregister
	 * @return
	 * When user click on register the tagdata this method will be call.
	 * Input as a All Tag Form data.
	 * 
	 */
	@PostMapping(value = "/tagregister")
	public @ResponseBody AjaxResponseMessage saveTagData(@RequestBody @Valid Tag tagregister,BindingResult bindingResult, HttpServletRequest request) {
		logger.info("saveTagData() method from TagController");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("userName");
		AjaxResponseMessage response = new AjaxResponseMessage();
		
		if (username != null) {
			if(!bindingResult.hasErrors()) {
				return tagService.saveTagData(tagregister);
			}
			else{
				List<FieldError> errors = bindingResult.getFieldErrors();
				List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
				
				for (FieldError fieldError : errors) {
					ErrorMessages error1 = new ErrorMessages();
					error1.setErrorId(fieldError.getField());
					error1.setErrorMessage(fieldError.getDefaultMessage());
					errorList.add(error1);
				}
				response.setErrorMessages(errorList);
				return response;
			}
			
		} else {
			AjaxResponseMessage ajaxResponseMessage=new AjaxResponseMessage();
			ajaxResponseMessage.setSessionTimeout(true);
			logger.info("saveTagData():Session get expired");
			return ajaxResponseMessage;
		}
	}
	
	@RequestMapping(value = "/getMaxTagId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	private @ResponseBody Long getMaxTagId(HttpServletRequest request) throws IOException {
		try {
			request.setCharacterEncoding("utf-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tagService.getMaxId();
	}
}
