/* COPYRIGHT 2018 FUJITSU
Author : FCIPL
Date : 2018/08/13 */

function validateUsernamePassword() {

	var username = $('#username_id').val();
	var password = $('#password_id').val();

	if (isEmpty(username)) {

		showDialogAndInvokeFunc('未入力エラー',
				modalDialogConfig.messageTypes.warning, 'ユーザー名は未入力です。',
				modalDialogConfig.buttonTypes.OK, 'focusUser', '');

		return false;
	} else if (isEmpty(password)) {

		showDialogAndInvokeFunc('未入力エラー',
				modalDialogConfig.messageTypes.warning, 'パスワードは未入力です。',
				modalDialogConfig.buttonTypes.OK, 'focusPassword', '');
		return false;
	}
}

function isEmpty(value) {
	if (($.trim(value)).length === 0)
		return true;
	else
		return false;
}

function focusUser() {
	setTimeout(function() {
		$("#username_id").focus();
	});
}

function focusPassword() {
	setTimeout(function() {
		$("#password_id").focus();
	});
}
