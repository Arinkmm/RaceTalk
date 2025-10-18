package com.racetalk.dao;

import com.racetalk.entity.ChatMessage;

import java.util.List;

public interface ChatMessageDao {
    public void create(ChatMessage chatMessage);

    public List<ChatMessage> findAll();
}
