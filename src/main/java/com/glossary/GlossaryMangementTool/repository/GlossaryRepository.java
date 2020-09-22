package com.glossary.GlossaryMangementTool.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glossary.GlossaryMangementTool.entity.GlossaryTerms;

@Transactional
public interface GlossaryRepository extends JpaRepository<GlossaryTerms, Integer> {
	public Page<GlossaryTerms> findAllByOrderByTermJaKana(Pageable pageable);
	public Page<GlossaryTerms> findAllByOrderByTermEn(Pageable pageable);
	public List<GlossaryTerms> findAllByOrderByTermJaKana();
	public List<GlossaryTerms> findByTermJaIgnoreCaseAndIdNot(String japaneseTerm, int id);
	public List<GlossaryTerms> findByTermEnIgnoreCaseAndIdNot(String englishTerm, int id);
	public List<GlossaryTerms> findByTermJaKanaIgnoreCaseAndIdNot(String kanaTerm, int id);
	public Page<GlossaryTerms> findByTermEnContainingIgnoreCaseOrTermJaKanaContainingOrderByTermEn(Pageable pageable, String key, String jkey);
	public Page<GlossaryTerms> findByTermEnContainingIgnoreCaseOrTermJaKanaContainingOrderByTermJaKana(Pageable pageable, String key,  String jkey);
	@Query("SELECT glossary FROM GlossaryTerms glossary where substring(glossary.termJaKana,1,1) IN (:chardata) ORDER BY glossary.termEn")
	public Page<GlossaryTerms> findByTermJaKanaContainingOrderByTermEn(Pageable pageable, @Param("chardata") List<String> charList);
	@Query("SELECT glossary FROM GlossaryTerms glossary where substring(glossary.termJaKana,1,1) IN (:chardata) ORDER BY glossary.termJa")
	public Page<GlossaryTerms> findByTermJaKanaContainingOrderByTermJaKana(Pageable pageable, @Param("chardata") List<String> charList);
	@Query("SELECT glossary FROM GlossaryTerms glossary where lower(substring(glossary.termEn,1,1)) IN (:chardata) ORDER BY glossary.termEn")
	public Page<GlossaryTerms> findByTermEnContainingIgnoreCaseOrderByTermEn(Pageable pageable, @Param("chardata") List<String> charList);
	@Query("SELECT glossary FROM GlossaryTerms glossary where lower(substring(glossary.termEn,1,1)) IN (:chardata) ORDER BY glossary.termJa")
	public Page<GlossaryTerms> findByTermEnContainingIgnoreCaseOrderByTermJaKana(Pageable pageable,
			@Param("chardata") List<String> charList);
	
	@Query("SELECT coalesce(max(glossary.id), 0) FROM GlossaryTerms glossary")
	public Long getMaxId();
}
