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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glossary.GlossaryMangementTool.contants.URL;
import com.glossary.GlossaryMangementTool.entity.GlossaryTerms;
import com.glossary.GlossaryMangementTool.service.GlossaryService;
import com.glossary.GlossaryMangementTool.util.AjaxResponseMessage;
import com.glossary.GlossaryMangementTool.util.ErrorMessages;

@Controller
public class GlossaryController {

	// Create logger instance for the Glossary Controller
	private static final Logger logger = LoggerFactory.getLogger(GlossaryController.class);
			
	List<ErrorMessages> errorList;
	
	ErrorMessages error = new ErrorMessages();
	
	@Autowired
	private GlossaryService service;
	
	List<GlossaryTerms> glossaryList = null;

	/**
	 * one method call for both register and updating of glossary data
	 * @param glossary
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	private @ResponseBody AjaxResponseMessage registerGlossaryData(@RequestBody @Valid GlossaryTerms glossary, 
			BindingResult bindingResult, HttpServletRequest request) throws IOException {

		System.out.println(glossary);
		HttpSession session = request.getSession(true);		
	
		logger.info("creating an object for AjaxResponseMessage class");
		AjaxResponseMessage response = new AjaxResponseMessage();
		
		logger.info("fetching the glossary list data from the table mt_terms");
		
		// fetching the glossary list data from the table mt_terms
		glossaryList = service.getGlossaryList();
		
		if(!bindingResult.hasErrors()) {
			// this condition compares the Id fetched from the glossaryList to the id received from the form data
			//if Id's matches, updateGlossaryData() will be called
			//else registerGlossaryData() will be called
			if (checkGlossaryIdDuplicate(glossary, response)) {
			    
				// update the glossary form data to mt_terms table based on Id
				logger.info("update the glossary list entry based on the Id ");
				AjaxResponseMessage glossaryTerms = service.updateGlossaryData(glossary);
				return glossaryTerms;
				
			} else {
				
			    //Register the glossary form data to mt_terms table 
				logger.info("update the glossary list entry based on the Id ");
				AjaxResponseMessage glossaryTerms = service.registerGlossaryData(glossary);
				return glossaryTerms;
			}
		}else {
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
	}
	
	/**
	 * check whether duplicate entry exists for Id
	 * @param glossary
	 * @param response 
	 * @return
	 */
	private boolean checkGlossaryIdDuplicate(GlossaryTerms glossary, AjaxResponseMessage response) {
		return glossaryList.stream().anyMatch(searchGlossaryList -> searchGlossaryList.getId() == glossary.getId());
	}
	

	/**
	 * On load, display the glossary data
	 * 
	 * @return glossary list form
	 */
	@RequestMapping("/glossarylist")
	private String getGlossaryList(Model model, Pageable pageable, HttpServletRequest request) {

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		/*
		 * if(username == null) { model.addAttribute("sessionTimeOut",true);
		 * return URL.MENU; }
		 */

		String usrLocale = (String) session.getAttribute("usrLocale");
		Page<GlossaryTerms> glossaryList = service.getGlossaryList(pageable, usrLocale);

		model.addAttribute("glossaryList", glossaryList);

		// return login form
		return URL.GLOSSARY_LIST;
	}

	@RequestMapping(value = "/getMaxId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	private @ResponseBody Long getMaxId(HttpServletRequest request) throws IOException {
		try {
			request.setCharacterEncoding("utf-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return service.getMaxId();
	}
	
	@RequestMapping(value = { "/glossarylist/locale" }, method = RequestMethod.GET)
	public String changeLocale(@RequestParam String selected, Model model, Pageable pageable,
			HttpServletRequest request) {

		HttpSession session = request.getSession();

		session.setAttribute("usrLocale", selected);

		Page<GlossaryTerms> glossaryList = service.getGlossaryList(pageable, selected);

		model.addAttribute("glossaryList", glossaryList);

		return URL.GLOSSARY_LIST;
	}
	
	@RequestMapping(value = { "/glossarylist/filter" }, method = RequestMethod.GET)
	public String filterByInput(@RequestParam String key, @RequestParam Boolean btnevnt, Model model, Pageable pageable,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		
		String usrLocale = (String)session.getAttribute("usrLocale");

		Page<GlossaryTerms> glossaryList;
		
		if(btnevnt !=null && btnevnt) {
			glossaryList = service.getGlossaryListForBtn(pageable, usrLocale, key);
		}else {
			glossaryList = service.getGlossaryList(pageable, usrLocale, key);
			model.addAttribute("inputKey", key);
		}

		model.addAttribute("glossaryList", glossaryList);
		
		model.addAttribute("key", key);
		
		model.addAttribute("btnevnt", btnevnt);

		return URL.GLOSSARY_LIST;
	}
}
