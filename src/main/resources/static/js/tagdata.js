$(document).ready(function() {

	$('#japanesetag_Id_label').css("background-color", "red");
	$('#englishtag_Id_label').css("background-color", "red");
//	validateFormOnSubmit();
	// SUBMIT FORM
});
function ajaxPostForRegisterTag() {

	// PREPARE FORM DATA
	var formData = {
		tagId : $("#tag_Id").val(),
		tagOrder : $("#tagOrder_Id").val(),
		japaneseTag : $("#japaneseTag").val(),
		englishTag : $("#englishTag").val()
	}

	// DO POST
	$.ajax({
		type : "POST",
		contentType : "application/json",
		cache: false,
		url : "/tagregister",
		data : JSON.stringify(formData),
		dataType : 'json',
		success : function(response) {
			var sessionTimeOutTag=response.sessionTimeout;
			if(sessionTimeOutTag){
				alert('session expired--ClickTo Login')
				 window.location.replace("\login");		
			}
			
			if(response.validated){
				showDialogAndInvokeFunc(' ',
						 modalDialogConfig.messageTypes.info,
						 '保存しました 。',
						 modalDialogConfig.buttonTypes.OK,
						 'redirectGlossaryDataModel',
						 '');
				
				if($("#tagdataModal").modal('hide')){
					ajaxGetForTagList(0);
				}
				resetData();
			}else {
				if (!$.isArray(response)) {
					response = [response];
				}

				$.each(response[0].errorMessages, function(tagErrors, tagError){
					var spans = $('#errorvalid_' + tagError.errorId + '');
					spans.remove();
					$('input[id='+tagError.errorId+']').after('<span id="errorvalid_' + tagError.errorId + '">'+tagError.errorMessage+'</span>');
					if(tagError.errorId=='englishTag'){
						$('#englishtag_Id_label').css("background-color", "red");
					}else{
					$('#japanesetag_Id_label').css("background-color", "red");
					}
				}); 
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
}
//Reset FormData after Posting
function resetData() {
	$("#tag_Id").val("");
	$("#tagOrder_Id").val("");
	$("#japaneseTag").val("");
	$("#englishTag").val("");
}

//To get AllTagList from database
$(document).ready(function() {

	function fetchAllTags(){
		tagSelectedRow = 0;		
		ajaxGetForTagList(0);
	}
	
	// Calling from glossayData.html
	$(".textfield").click(function(event) {		
		fetchAllTags();
	});
});

// DO GET FETCHE THE ALL Taglist from database
function ajaxGetForTagList(tagSelectedRow) {
	$.ajax({
		type : "GET",
		cache: false,
		url : "/getAllTagList",
		success : function(result) {

			 $("#tagtable").find("tbody").empty();
			 
			 if(result.sessionTimeout){
				 alert('SessionExpired:Click To Login')
				 window.location.replace("\login");	
			 }
		
			var htmlData ='';
			$.each(result.listOfTags, function(i, tag) {

				htmlData = htmlData + "<tr class='updatedTableRows'><td  id=radio-td_" + i + " style='text-align: center; width: 5%'><input id=radiobtn-tag_" + i +  " type='radio' value=" + i + " /> " +
				" <td  id=tagid-td_"+i+" style='width: 10%'>"+tag.tagId+"</td>" +
				" <td  id=tagorder-td_"+i+" style='width: 10%'>"+tag.tagOrder+"</td>" +
				"<td  id=japanesetag-td_"+i+" style='width: 37.5%'>"+tag.japaneseTag+"</td>" +
				"<td  id=englishtag-td_"+i+" style='width: 37.5%'>"+tag.englishTag+"</td></tr>"
			});
			$(htmlData).appendTo($("#tableBody"));
			
			hightlightTagTable(tagSelectedRow);
			_createTagPagination();
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
}

$(document).ready(function() {

	function fetchTagId() {		
		ajaxGetForTagId();
	}

	// Calling from glossayData.html
	$("#registerbutton").click(function(event) {
		sendAjaxReq('GET','getMaxTagId','','setTagId')
	});
});

function setTagId(tagId){
	$('#tag_Id').val(tagId+1);
}

function japaneseLabelValidation(){
	$("span").remove();
	var _japaneseId = document.getElementById("japaneseTag");
	if (_japaneseId && _japaneseId.value) {
		$('#japanesetag_Id_label').css("background-color", "#1f497d");
	}else{
		$('#japanesetag_Id_label').css("background-color", "red");
	}
}
	
function englishLabelValidation(){
	$("span").remove();
	var _englishId = document.getElementById("englishTag");
	if (_englishId && _englishId.value) {
		$('#englishtag_Id_label').css("background-color", "#1f497d");
	}else{
		$('#englishtag_Id_label').css("background-color", "red");
	}
}