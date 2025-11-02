package com.racetalk.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.entity.ChatMessage;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.ChatMessageService;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "ChatHandler", urlPatterns = "/chat/handle")
public class ChatHandler extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(ChatHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private ChatMessageService chatMessageService;
    private UserService userService;

    @Override
    public void init() {
        chatMessageService = (ChatMessageService) getServletContext().getAttribute("chatMessageService");
        userService = (UserService) getServletContext().getAttribute("userService");
        if (chatMessageService == null || userService == null) {
            throw new IllegalStateException("Services are not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<ChatMessage> messages = chatMessageService.getAllMessages();
            resp.setContentType("application/json;charset=UTF-8");

            List<Map<String, String>> jsonList = messages.stream()
                    .map(m -> Map.of(
                            "id", String.valueOf(m.getId()),
                            "userId", String.valueOf(m.getUser().getId()),
                            "username", m.getUser().getUsername(),
                            "photo", m.getUser().getPhoto() != null ? m.getUser().getPhoto() : "",
                            "content", m.getContent(),
                            "createdAt", m.getCreatedAt().toString()
                    ))
                    .collect(Collectors.toList());

            mapper.writeValue(resp.getWriter(), jsonList);
        } catch (ServiceException e) {
            logger.error("Failed to get chat messages", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getWriter(), Map.of("error", "Failed to retrieve chat messages"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json;charset=UTF-8");
            User currentUser = (User) req.getSession().getAttribute("user");
            String messageText = req.getParameter("message");
            String action = req.getParameter("action");

            if ("delete".equals(action)) {
                if (userService.isAdmin(currentUser)) {
                    int messageId = Integer.parseInt(req.getParameter("id"));
                    chatMessageService.deleteMessage(messageId);
                    mapper.writeValue(resp.getWriter(), Map.of("success", true));
                    return;
                }
            }

            ChatMessage message = new ChatMessage();
            message.setUser(currentUser);
            message.setContent(messageText);
            message.setCreatedAt(LocalDateTime.now());

            chatMessageService.postMessage(message);

            mapper.writeValue(resp.getWriter(), Map.of(
                    "success", true,
                    "id", String.valueOf(message.getId()),
                    "userId", String.valueOf(currentUser.getId()),
                    "username", currentUser.getUsername(),
                    "photo", currentUser.getPhoto() != null ? currentUser.getPhoto() : "",
                    "content", message.getContent(),
                    "createdAt", message.getCreatedAt().toString()
            ));
        } catch (ServiceException e) {
            logger.error("Failed to post chat message", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getWriter(), Map.of("error", "Failed to post chat message"));
            }
    }
}