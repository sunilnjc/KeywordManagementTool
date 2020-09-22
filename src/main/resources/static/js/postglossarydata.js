$(document).ready(function(event) {

	$("#baseId").attr("value", 0);

	validateTerms();

	$('#registerTermForm').on('submit', function(event) {
		if (true) {
			event.preventDefault();
			ajaxPost();
		} else {
			event.preventDefault();
		}
	});

	$('#termJa').keyup(function() {
		validateTerms();	
		var spans = $('#errorvalid_termJa');
		spans.remove();
		$("#termJa").removeClass("focused");
		/*$("#termJa_error_message").hide();*/

	});

	$('#termJaKana').keyup(function() {
		validateTerms();
		var spans = $('#errorvalid_termJaKana');
		spans.remove();
		$("#termJaKana").removeClass("focused");
		/*$("#kana_ja_error_message").hide();*/
	});

});

function validateTerms() {
	if ($("#termJa").val().length == 0) {
		if ($("#termJaKana").val().length == 0) {
			$('#JapaneseErrorLabelId').addClass("label_jaKaEn");
			$('#kanaErrorLabelId').addClass("label_jaKaEn");
			return false;
		} else {
			$('#JapaneseErrorLabelId').addClass("label_jaKaEn");
			$('#kanaErrorLabelId').removeClass("label_jaKaEn");
			return false;
		}

	} else {

		if ($("#termJaKana").val().length == 0) {
			$('#kanaErrorLabelId').addClass("label_jaKaEn");
			$('#JapaneseErrorLabelId').removeClass("label_jaKaEn");
			return false;
		} else {
			$('#JapaneseErrorLabelId').removeClass("label_jaKaEn");
			$('#kanaErrorLabelId').removeClass("label_jaKaEn");
			return true;
		}
	}
}
// calling ajaxPost function which in turn calls ajax function
// to save the form data to the database
function ajaxPost() {

	var termTags = '';
	$("#tagInfo input").each(function() {
		$(this).val(); // Gets the value
		if ($(this).val().length > 0) {
			var tempTags = $(this).val();
			termTags = termTags + tempTags + ';'
		}
	});

	var formData = {
		id : $("#termId").val(),
		termJa : $("#termJa").val(),
		termJaKana : $("#termJaKana").val(),
		termJaShort : $("#short_ja").val(),
		descriptionJa : $("#desc_ja").val(),
		termEn : $("#term_en").val(),
		termEnShort : $("#short_en").val(),
		descriptionEn : $("#desc_en").val(),
		notes : $("#remark").val(),
		entryDate : $("#registerDate").val(),
		lastUpdateDate : $("#updatedDate").val(),
		updateUser : $("#updatedUser").val(),
		termTags : termTags
	}

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/save",
		data : JSON.stringify(formData),
		dataType : 'json',
		success : function(response) {
			
			
			if (response.validated) {
				showDialogAndInvokeFunc(' ',
						modalDialogConfig.messageTypes.info, 'データは正常に保存されました！',
						modalDialogConfig.buttonTypes.OK,
						'closeRegisterModalWindow', '');
				
			} else {
				if (!$.isArray(response)) {
					response = [ response ];
				}
				$.each(response[0].errorMessages, function(key, value) {
					var spans = $('#errorvalid_' + value.errorId + '');
					spans.remove();
					
					$('input[name=' + value.errorId + ']').addClass('focused');
					$('input[name=' + value.errorId + ']').after(
							'<span id="errorvalid_' + value.errorId + '">'
									+ value.errorMessage + '</span>');
				});
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
	resetData();
}

// calling resetData function to reset the form data after
// form is submitted successfully
function resetData() {
	$("#termJa").val("");
	$("#termJaKana").val("");
	$("#short_ja").val("");
	$("#desc_ja").val("");
	$("#term_en").val("");
	$("#short_en").val("");
	$("#desc_en").val("");
	$("#remark").val("");
}



function closeRegisterModalWindow(){
	$("#glossaryDataModal").modal('hide');		
//	
//	@{/glossarylist/filter(key=*{key}, btnevnt=*{btnevnt}, page=${glossaryList.number-1},size=${glossaryList.size})}
	
//	 window.location.replace("/login");	
//	window.location.reload();
}