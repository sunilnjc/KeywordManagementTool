<script th:inline="javascript">
	var selectedRow;
	$(function() {
		$("#glossary-table").hpaging({
			"limit" : 5,
			"parentID" : 'button-div',
			"activePage" : 1,
			"totalPages" : 10
		});
	});

	function callGlossaryDataModel(){

		document.getElementById('termId').value = 'new';
		document.getElementById('baseId').value = '0';

		document.getElementById('term_ja').value = '';
		document.getElementById('kana_ja').value = '';
		document.getElementById('short_ja').value = '';
		document.getElementById('desc_ja').value = '';

		document.getElementById('term_en').value = '';
		document.getElementById('short_en').value = '';
		document.getElementById('desc_en').value = '';
		document.getElementById('remark').value = '';


		document.getElementById('registerDate').value = '';
		document.getElementById('updatedDate').value = '';
		document.getElementById('updatedUser').value = '';


		for (i = 0; i < 8; i++) {
			var tagsinfo =  "tagsinfo"+(i+1);
			document.getElementById(tagsinfo).value = '';
		}

		$('[href="#glossary"]').tab('show');
		$('#glossaryDataModal').modal();
	}

	function callGlossaryDataUpdateModel(){
		var glossaryList = [[${glossaryList}]];
		var glossaryData = glossaryList[selectedRow];

		document.getElementById('termId').value = glossaryData.id;
		document.getElementById('baseId').value = glossaryData.baseItemId;

		document.getElementById('term_ja').value = glossaryData.termJa;
		document.getElementById('kana_ja').value = glossaryData.termJaKana;
		document.getElementById('short_ja').value = glossaryData.termJaShort;
		document.getElementById('desc_ja').value = glossaryData.descriptionJa;

		document.getElementById('term_en').value = glossaryData.termEn;
		document.getElementById('short_en').value = glossaryData.termEnShort;
		document.getElementById('desc_en').value = glossaryData.descriptionEn;
		document.getElementById('remark').value = glossaryData.notes;


		document.getElementById('registerDate').value = glossaryData.entryDate;
		document.getElementById('updatedDate').value = glossaryData.lastUpdateDate;
		document.getElementById('updatedUser').value = glossaryData.updateUser;

		if(glossaryData.termTags != null){
		var tagTerms = glossaryData.termTags.split(";");
		for (i = 0; i < tagTerms.length; i++) {
			var tagsinfo =  "tagsinfo"+(i+1);
			document.getElementById(tagsinfo).value = tagTerms[i];
		}
		}

		$('[href="#glossary"]').tab('show');
		$('#glossaryDataModal').modal();
	}

	$(document).ready(function() {
		selectedRow = 0;
		hightlight(selectedRow);
		adjustColumnWidth();
	});

	$(document).on("click", "#glossary-table tbody tr", function() {
		var row = $(this).index();
		removeHighlight(selectedRow);
		selectedRow = row;
		hightlight(row);
	});

	$(window).resize(function() {
		adjustColumnWidth();
	});

	function adjustColumnWidth() {
		var widthList = [];

		$("#glossary-table>tbody>tr:first>td").each(function() {
			var value = $(this).width();
			widthList.push(value);
		})
		var index = 0;
		$(".glossary-table-header>tbody>tr:first>td").each(function() {
			if (index == 3) {
				var element = document.querySelector('.container');
				if (element.offsetHeight < element.scrollHeight) {
					$(this).width(widthList[index] + 17);
				} else
					$(this).width(widthList[index]);
			} else {
				$(this).width(widthList[index]);
			}
			index = index + 1;
		})
	}

	function hightlight(row) {
		$("#radiobtn-td_" + row).addClass('selected');
		$("#radiobtn_" + row).prop('checked', true);
		$("#glossaryId-td_" + row).addClass('selected');
		$("#glossarydata-td_" + row).addClass('selected');
		$("#glossarydesc-td_" + row).addClass('selected');
		$("#japaneseReading_" + row).addClass('selected');
		$("#japaneseTerm_" + row).addClass('selected');
		$("#englishTerm_" + row).addClass('selected');
		$("#glossarydesc_" + row).addClass('selected');
	}

	function removeHighlight(row) {
		$("#radiobtn-td_" + row).removeClass('selected');
		$("#radiobtn_" + row).prop('checked', false);
		$("#glossaryId-td_" + row).removeClass('selected');
		$("#glossarydata-td_" + row).removeClass('selected');
		$("#glossarydesc-td_" + row).removeClass('selected');
		$("#japaneseReading_" + row).removeClass('selected');
		$("#japaneseTerm_" + row).removeClass('selected');
		$("#englishTerm_" + row).removeClass('selected');
		$("#glossarydesc_" + row).removeClass('selected');
	}


	function callTagListModel(){
		$("#tagListModal").modal()
	}

	function closeGlossaryDataModel(){
		$("#glossaryDataModal").modal('hide')
	}

	function dispGlossaryData() {
		$('#glossary-data').removeClass('active-tab');
		$('#tag-info').addClass('active-tab');
	}
	function dispTagData() {
		$('#glossary-data').addClass('active-tab');
		$('#tag-info').removeClass('active-tab');
	}

	function callTagDataModel() {
		$("#tagdataModal").modal()
	}

	function closeTagDataModel() {
		$("#tagdataModal").modal('hide');
	}

	function closeTagListModel() {
		$('#tagListModal').modal('hide');
	}

	$(function() {
		$("#tagtable").hpaging1({
			"limit" : '5',
			"parentID" : 'row-button',
			"activePage" : 1,
			"totalPages" : 10
		});
	});

	function validaForm() {
		var isError = true;
		console.log("errors" + isError);
		var japaneseTagid = document.getElementById('japanesetag_id').value;
		var englishTagid = document.getElementById('englishtag_id').value;

		if (japaneseTagid == "") {
			document.getElementById('japaneseTagErr').innerHTML = "japaneseTag is required *";
			isError = false;
		} else
			document.getElementById('japaneseTagErr').innerHTML = "";

		if (englishTagid == "") {
			document.getElementById('englishTagErr').innerHTML = "englishTag is required *";
			isError = false;
		} else
			document.getElementById('englishTagErr').innerHTML = "";
	}

	$(document).ready(function() {

		// SUBMIT FORM
		$("#tagRegisterForm").submit(function(event) {
			// Prevent the form from submitting via the browser.
			event.preventDefault();
			ajaxPost();
		});

		function ajaxPost() {

			// PREPARE FORM DATA
			var formData = {
				tagId : $("#tagId").val(),
				tagOrder : $("#tagOrder").val(),
				japaneseTag : $("#japanesetag_id").val(),
				englishTag : $("#englishtag_id").val()
			}

			// DO POST
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : window.location + "/tagregister",
				data : JSON.stringify(formData),
				dataType : 'json',
				success : function(result) {
					if (result.status == "Done") {
					} else {
						$("#postResultDiv").html("<strong>Error</strong>");
					}
					console.log(result);
				},
				error : function(e) {
					alert("Error!")
					console.log("ERROR: ", e);
				}
			});

			// Reset FormData after Posting
			resetData();

		}

		function resetData() {
			$("#tagId").val("");
			$("#tagOrder").val("");
		}
	})
</script>