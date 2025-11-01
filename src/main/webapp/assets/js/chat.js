$(document).ready(function() {
    function renderMessage(msg) {
        $('#messages').append(
            '<div class="msg d-flex align-items-center mb-2">' +
            '<a href="' + contextPath + '/user/profile/' + msg.userId + '" class="d-flex align-items-center text-decoration-none">' +
            '<img src="' + (msg.photo || (contextPath + '/assets/images/driver-profile/default.png')) + '" class="rounded-circle me-2 photo" />' +
            '<span class="msg-user">' + msg.username + '</span>' +
            '</a>' +
            '<span class="msg-content ms-2 flex-grow-1">' + msg.content + '</span>' +
            '<span class="msg-time ms-2">' + msg.createdAt.replace('T', ' ').substring(0, 16) + '</span>' +
            '</div>'
        );
        $('#messages').scrollTop($('#messages')[0].scrollHeight);
    }

    function loadMessages() {
        $('#messages').empty();
        $.getJSON(contextPath + '/chat/handle', function(data) {
            if (Array.isArray(data)) {
                data.forEach(renderMessage);
            } else {
                console.error("Unexpected data format", data);
            }
        });
    }

    $('#chatForm').submit(function(e) {
        e.preventDefault();
        let text = $('#messageInput').val().trim();
        if (!text) {
            alert("Введите сообщение");
            return;
        }
        $.post(contextPath + '/chat/handle', { message: text }, function(data) {
            if (data.success) {
                $('#messageInput').val('');
                loadMessages();
            } else {
                alert(data.message || "Ошибка отправки сообщения");
            }
        }, 'json');
    });

    loadMessages();
});