package com.racetalk.web.servlet;

import com.racetalk.dao.impl.NoteDaoImpl;
import com.racetalk.entity.Note;
import com.racetalk.entity.User;
import com.racetalk.service.NoteService;
import com.racetalk.service.impl.NoteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/notes")
public class NoteServlet extends HttpServlet {
    private NoteService noteService = new NoteServiceImpl(new NoteDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
        }

        List<Note> notes = noteService.getUserNotes(currentUser);
        req.setAttribute("notes", notes);

        req.getRequestDispatcher("/templates/notes.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (title == null) {
            req.setAttribute("noteErrorMessage", "Заголовок не может быть пустым");
            doGet(req, resp);
        }

        Note note = new Note();
        note.setUser(currentUser);
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());

        noteService.addNote(note);

        resp.sendRedirect(req.getContextPath() + "/notes");
    }
}