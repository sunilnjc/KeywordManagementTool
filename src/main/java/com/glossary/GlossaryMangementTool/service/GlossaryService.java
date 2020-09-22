package com.glossary.GlossaryMangementTool.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.glossary.GlossaryMangementTool.contants.MessageConstants;
import com.glossary.GlossaryMangementTool.controller.GlossaryController;
import com.glossary.GlossaryMangementTool.entity.GlossaryTerms;
import com.glossary.GlossaryMangementTool.repository.GlossaryRepository;
import com.glossary.GlossaryMangementTool.util.AjaxResponseMessage;
import com.glossary.GlossaryMangementTool.util.ErrorMessages;

@Service
public class GlossaryService {

	private static final Logger logger = LoggerFactory.getLogger(GlossaryController.class);

	@Autowired
	private GlossaryRepository glossaryRepository;

	GlossaryTerms glossaryRegister = null;
	
	List<ErrorMessages> errorList;
	ErrorMessages error;

	/**
	 * 
	 * @return glossaryList from the database based on term_ja and kana_ja
	 */
	public List<GlossaryTerms> getGlossaryList() {

		// calling JPARepository findAllByOrderByTermJaKana() to fetch the list
		// data based on term_ja and kana_ja
		return glossaryRepository.findAllByOrderByTermJaKana();

	}
	
	/**
	 * 
	 * @return glossaryList from the database based on term_ja and kana_ja
	 */
	public Page<GlossaryTerms> getGlossaryList(Pageable pageable, String locale) {

		if (locale.equals("en"))
			// calling JPARepository findAllByOrderByTermJaKana() to fetch the list data based on term_ja and kana_ja
			return glossaryRepository.findAllByOrderByTermEn(pageable);
		else
			return glossaryRepository.findAllByOrderByTermJaKana(pageable);

	}

	/**
	 * 
	 * @param glossary
	 * @param username
	 * @param glossaryListSearch
	 * @return list object of GlossaryTerms entity
	 */
	public AjaxResponseMessage registerGlossaryData(GlossaryTerms glossary) {

		logger.info("Registering  the glossary data to the mt_terms table ");
		return registerOrUpdate(glossary);
	}

	/**
	 * 
	 * @param glossary
	 * @return list object of GlossaryTerms entity
	 */
	public AjaxResponseMessage updateGlossaryData(GlossaryTerms glossary) {

		logger.info("updating  the glossary data to the mt_terms table based on Id");
		return registerOrUpdate(glossary);
	}

	/**
	 * 
	 * @param glossary
	 * @return
	 */
	public AjaxResponseMessage registerOrUpdate(GlossaryTerms glossary) {
		logger.info("creating an object for AjaxResponseMessage class");
		AjaxResponseMessage response = glossaryDataRegisterAndUpdate(glossary);
		return response;
	}

	/**
	 * @param glossary
	 * @return
	 */
	public AjaxResponseMessage glossaryDataRegisterAndUpdate(GlossaryTerms glossary) {
		AjaxResponseMessage response = new AjaxResponseMessage();
		List<GlossaryTerms> listOfJapaneseTerms = new ArrayList<GlossaryTerms>();
		List<GlossaryTerms> listOfKanaTerms = new ArrayList<GlossaryTerms>();
		List<GlossaryTerms> listOfEnglishTerms = new ArrayList<GlossaryTerms>();
		errorList = new ArrayList<ErrorMessages>();
		int id = glossary.getId();
		String japaneseTerm = glossary.getTermJa();
		String kanaTerm = glossary.getTermJaKana();
		String englishTerm = glossary.getTermEn();

		listOfJapaneseTerms = glossaryRepository.findByTermJaIgnoreCaseAndIdNot(japaneseTerm, id);

		listOfKanaTerms = glossaryRepository.findByTermJaKanaIgnoreCaseAndIdNot(kanaTerm, id);

		listOfEnglishTerms = glossaryRepository.findByTermEnIgnoreCaseAndIdNot(englishTerm, id);

		if (!englishTerm.isEmpty() && !listOfEnglishTerms.isEmpty()) {
			logger.error(glossary.getTermEn() + ' ' + MessageConstants.TERM__ALREADY_REGISTERED);
			error = new ErrorMessages();
			error.setErrorId(MessageConstants.TERM_EN);
			error.setErrorMessage(glossary.getTermEn() + ' ' + MessageConstants.TERM__ALREADY_REGISTERED);
			errorList.add(error);
		}

		if (!kanaTerm.isEmpty() && !listOfKanaTerms.isEmpty()) {
			logger.error(glossary.getTermJaKana() + ' ' + MessageConstants.TERM__ALREADY_REGISTERED);
			ErrorMessages error1 = new ErrorMessages();
			error1.setErrorId(MessageConstants.TERM_KA);
			error1.setErrorMessage(glossary.getTermJaKana() + ' ' + MessageConstants.TERM__ALREADY_REGISTERED);
			errorList.add(error1);
		}
		if (!japaneseTerm.isEmpty() && !listOfJapaneseTerms.isEmpty()) {
			logger.error(glossary.getTermJa() + ' ' + MessageConstants.TERM__ALREADY_REGISTERED);
			ErrorMessages error2 = new ErrorMessages();
			error2.setErrorId(MessageConstants.TERM_JA);
			error2.setErrorMessage(glossary.getTermJa() + ' ' + MessageConstants.TERM__ALREADY_REGISTERED );
			errorList.add(error2);
		}
		if (errorList.isEmpty()) {
			glossaryRegister = glossaryRepository.save(glossary);
			logger.info("glossarydata saved sucessfully in DB");
			
			response.setValidated(true);
			response.setGlossaryTerms(glossaryRegister);
		} else {
			response.setErrorMessages(errorList);
			response.setValidated(false);
		}
		return response;
	}

	/**
	 * 
	 * @param glossary
	 * @return list object of GlossaryTerms entity
	 */
	public Long getMaxId() {

		return glossaryRepository.getMaxId();
	}
	
	/**
	 * 
	 * @return glossaryList from the database based on term_ja and kana_ja
	 */
	public Page<GlossaryTerms> getGlossaryList(Pageable pageable, String locale, String key) {

		if (key != "")
			key = getKeywithWildCard(key);

		if (locale.equals("en"))
			return glossaryRepository.findByTermEnContainingIgnoreCaseOrTermJaKanaContainingOrderByTermEn(pageable, key,
					key);
		else
			return glossaryRepository.findByTermEnContainingIgnoreCaseOrTermJaKanaContainingOrderByTermJaKana(pageable,
					key, key);

	}

	private String getKeywithWildCard(String key) {

		StringBuffer keyWithWildcard = new StringBuffer();
		keyWithWildcard.append("%");

		for (int i = 0; i < key.length(); i++) {
			keyWithWildcard.append(key.substring(i, i + 1)).append("%");
		}
		return keyWithWildcard.toString();
	}
	
	/**
	 * 
	 * @return glossaryList from the database based on term_ja and kana_ja
	 */
	public Page<GlossaryTerms> getGlossaryListForBtn(Pageable pageable, String locale, String key) {

		Page<GlossaryTerms> glossaryList = null;
		List<String> charList = getCharList(key);

		if (key.equals("あ") || key.equals("か") || key.equals("さ") || key.equals("た") || key.equals("な")
				|| key.equals("は") || key.equals("ま") || key.equals("や") || key.equals("ら") || key.equals("わ")) {
			if (locale.equals("en"))
				glossaryList = glossaryRepository.findByTermJaKanaContainingOrderByTermEn(pageable, charList);
			else
				glossaryList = glossaryRepository
						.findByTermJaKanaContainingOrderByTermJaKana(pageable, charList);
		} else if (key.equals("a-e") || key.equals("f-j") || key.equals("k-o") || key.equals("p-t")
				|| key.equals("u-z")) {
			if (locale.equals("en"))
				glossaryList = glossaryRepository.findByTermEnContainingIgnoreCaseOrderByTermEn(pageable, charList);
			else
				glossaryList = glossaryRepository
						.findByTermEnContainingIgnoreCaseOrderByTermJaKana(pageable, charList);
		}
		return glossaryList;

	}

	private List<String> getCharList(String key) {

		String charArray[] = null;

		switch (key) {
		case "あ":
			charArray = "あ,い,う,え,お".split(",");
			break;
		case "か":
			charArray = "か,き,く,け,こ,が,ぎ,ぐ,げ,ご".split(",");
			break;
		case "さ":
			charArray = "さ,し,す,せ,そ,ざ,じ,ず,ぜ,ぞ".split(",");
			break;
		case "た":
			charArray = "た,ち,つ,て,と,だ,づ,で,ど".split(",");
			break;
		case "な":
			charArray = "な,に,ぬ,ね,の".split(",");
			break;
		case "は":
			charArray = "は,ひ,ふ,へ,ほ,ば,び,ぶ,べ,ぼ,ぱ,ぴ,ぷ,ぺ,ぽ".split(",");
			break;
		case "ま":
			charArray = "ま,み,む,め,も".split(",");
			break;
		case "や":
			charArray = "や,ゆ,よ".split(",");
			break;
		case "ら":
			charArray = "ら,り,る,れ,ろ".split(",");
			break;
		case "わ":
			charArray = "わ,を,ん".split(",");
			break;
		case "a-e":
			charArray = "a,b,c,d,e".split(",");
			break;
		case "f-j":
			charArray = "f,g,h,i,j".split(",");
			break;
		case "k-o":
			charArray = "k,l,m,n,o".split(",");
			break;
		case "p-t":
			charArray = "p,q,r,s,t".split(",");
			break;
		case "u-z":
			charArray = "u,v,w,x,y,z".split(",");
			break;
		}

		return Arrays.asList(charArray);
	}
}
