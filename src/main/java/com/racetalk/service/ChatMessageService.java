package com.racetalk.service;

import com.racetalk.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    void postMessage(ChatMessage chatMessage);

    void deleteMessage(int id);

    List<ChatMessage> getAllMessages();
}
