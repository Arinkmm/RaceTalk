package com.racetalk.service.impl;

import com.racetalk.dao.ChatMessageDao;
import com.racetalk.entity.ChatMessage;
import com.racetalk.service.ChatMessageService;

import java.util.List;

public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageDao chatMessageDao;

    public ChatMessageServiceImpl(ChatMessageDao chatMessageDao) {
        this.chatMessageDao = chatMessageDao;
    }

    @Override
    public void postMessage(ChatMessage chatMessage) {
        chatMessageDao.create(chatMessage);
    }

    @Override
    public List<ChatMessage> getAllMessages() {
        return chatMessageDao.findAll();
    }
}
