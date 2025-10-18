package com.racetalk.service;

import com.racetalk.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    public void postMessage(ChatMessage chatMessage);

    public List<ChatMessage> getAllMessages();
}
