$(function() {
    const contextPath = '/RaceTalk_war';

    $('#username').on('input', function() {
        let username = $(this).val();
        if (username.length < 3) {
            $('#username-feedback').text('Логин должен быть не менее 3 символов').show();
            return;
        }
        $.getJSON(contextPath + '/validate/username', {username: username}, function(data) {
            if (!data.valid) {
                $('#username-feedback').text('Недопустимый формат логина').show();
            } else {
                $.getJSON(contextPath + '/validate/username-unique', {username: username}, function(resp) {
                    if (!resp.unique) {
                        $('#username-feedback').text('Этот логин уже занят').show();
                    } else {
                        $('#username-feedback').hide().text('');
                    }
                });
            }
        });
    });

    $('#password').on('input', function() {
        let password = $(this).val();
        $.getJSON(contextPath + '/validate/password', {password: password}, function(data) {
            if (!data.valid) {
                $('#password-feedback').text('Пароль не соответствует требованиям').show();
            } else {
                $('#password-feedback').hide().text('');
            }
        });
    });

    $('#signup-form').on('submit', function(e) {
        if ($('#username-feedback').is(':visible') || $('#password-feedback').is(':visible')) {
            e.preventDefault();
            alert('Пожалуйста, исправьте ошибки в форме');
        }
    });
});
