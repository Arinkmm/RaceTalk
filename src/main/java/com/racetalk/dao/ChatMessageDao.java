package com.racetalk.dao;

import com.racetalk.entity.ChatMessage;

import java.util.List;

public interface ChatMessageDao {
    void create(ChatMessage chatMessage);

    void deleteById(int id);

    List<ChatMessage> findAll();
}
