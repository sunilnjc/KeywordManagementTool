function sendAjaxReq(reqType, url, data, funcToCall){
		$.ajax({
			type : reqType,
			contentType : "application/json",
			url : url,
			data : data,
			dataType : 'json',
			success : function(response) {
				eval(funcToCall + '(' + response + ')');
			},
			error : function(e) {

				console.log("ERROR: ", e);
			}
		});
	}