/* COPYRIGHT 2018 FUJITSU
Author : FCIPL
Date : 2018/08/13 */

var modalDialogConfig = {
	messageTypes : {
		'info' : 'information',
		'error' : 'error',
		'warning' : 'warning',
		'confirm' : 'confirm'
	},
	buttonTypes : {
		'OK' : [ 'Ok' ],
		'OKCANCEL' : [ 'Ok', 'Cancel' ],
		'YESNO' : [ 'Yes', 'No' ]
	}
};

function showDialogAndInvokeFunc(title, infoType, info, buttons,
		functionToExecute, functionToExecute2) {
	var template = '<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"><div class="modal-dialog" role="document"><div class="modal-content" ><div class="modal-header" style="display: -webkit-inline-box; padding:4px; background-color: #c6d9f1;height:45px"><h5 class="modal-title" id="exampleModalLabel">'
			+ title
			+ '</h5></div><div class="modal-body" style="display: -webkit-inline-box"><img style="margin-left:36px;margin-top:20px;" src="/images/'
			+ infoType
			+ '.png" alt="Italian Trulli"><div>'
			+ info
			+ '</div></div><div class="modal-footer" style="background-color: #c6d9f1;height:45px">';
	var buttonsHtml = '';
	var clickableId = '';
	for (var i = 0; i < buttons.length; i++) {
		(i == 0 && buttons.length > 0) ? clickableId = "action"
				: clickableId = "no-action";
		buttonsHtml += '<button type="button" class="btn btn-primary"  style="background-color:#1f497d;color:white;" id='
				+ clickableId + '>' + buttons[i] + '</button>'
	}
	template += buttonsHtml;
	template += '</div></div></div></div>';
	var modalObj = $(template).modal({
		backdrop : false
	});
	$(modalObj).find('.modal-content').css({
		'height' : '200',
		'width' : '400',
		'top' : screen.height / 7
	});
	$(modalObj).find('.modal-content').find('.modal-body').find('div:eq(0)')
			.css({
				'display' : 'inline-flex',
				'margin-left' : '20px'
			});
	$(modalObj).find(':button').click(function() {
		if ($(this).is(':visible')) {

			if ($(this).attr('id') == 'action') {
				window[functionToExecute]();

				$('#exampleModal').modal('hide');
				$(this).closest('#exampleModal').hide();
			}
			if ($(this).attr('id') == 'no-action') {
				window[functionToExecute2]();
			}

		}
	});

}
