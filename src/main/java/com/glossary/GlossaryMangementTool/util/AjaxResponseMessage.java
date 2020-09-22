package com.glossary.GlossaryMangementTool.util;

import java.util.List;

import com.glossary.GlossaryMangementTool.entity.GlossaryTerms;
import com.glossary.GlossaryMangementTool.entity.Tag;

public class AjaxResponseMessage {

	//private String errorMessages;
	
	private boolean validated;

	private GlossaryTerms glossaryTerms;
	
	private List<ErrorMessages> errorMessages;
	
	private Tag tags;
	
	private List<Tag> listOfTags;
	
	private boolean sessionTimeout;

	
	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}


	public GlossaryTerms getGlossaryTerms() {
		return glossaryTerms;
	}

	public List<ErrorMessages> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<ErrorMessages> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public void setGlossaryTerms(GlossaryTerms glossaryTerms) {
		this.glossaryTerms = glossaryTerms;
	}

	
	public Tag getTags() {
		return tags;
	}

	public void setTags(Tag tags) {
		this.tags = tags;
	}
	
	public List<Tag> getListOfTags() {
		return listOfTags;
	}

	public void setListOfTags(List<Tag> listOfTags) {
		this.listOfTags = listOfTags;
	}
	
	public boolean isSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(boolean sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
}
