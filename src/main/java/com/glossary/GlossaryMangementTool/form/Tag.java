package com.glossary.GlossaryMangementTool.form;

public class Tag {

	private int tagId;
	private String tagOrder;
	private String japaneseTag;
	private String englishTag;

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagOrder() {
		return tagOrder;
	}

	public void setTagOrder(String tagOrder) {
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

	public Tag(int tagId, String tagOrder, String japaneseTag, String englishTag) {
		this.tagId = tagId;
		this.tagOrder = tagOrder;
		this.japaneseTag = japaneseTag;
		this.englishTag = englishTag;
	}

	@Override
	public String toString() {
		return "TagRegister [tagId=" + tagId + ", tagOrder=" + tagOrder + ", japaneseTag=" + japaneseTag
				+ ", englishTag=" + englishTag + "]";
	}

}
