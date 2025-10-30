package com.racetalk.dao.impl;

import com.racetalk.dao.NoteDao;
import com.racetalk.entity.Note;
import com.racetalk.entity.User;
import com.racetalk.exception.DataAccessException;
import com.racetalk.util.DatabaseConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteDaoImpl implements NoteDao {
    private static final Logger logger = LoggerFactory.getLogger(NoteDaoImpl.class);
    private final DatabaseConnectionUtil databaseConnection;

    public NoteDaoImpl(DatabaseConnectionUtil databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void create(Note note) {
        String sql = "INSERT INTO notes (user_id, title, content, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, note.getUser().getId());
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(note.getCreatedAt()));
            ps.executeUpdate();
            } catch (SQLException e) {
            logger.error("Error creating note for user id {}", note.getUser().getId(), e);
            throw new DataAccessException("Failed to create note", e);
        }
    }

    @Override
    public void update(Note note) {
        String sql = "UPDATE notes SET title = ?, content = ? WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating note for user id {}", note.getUser().getId(), e);
            throw new DataAccessException("Failed to update note", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting note id {}", id, e);
            throw new DataAccessException("Failed to delete note by id", e);
        }
    }

    @Override
    public List<Note> findByUser(User user) {
        String sql = "SELECT * FROM notes WHERE user_id = ? ORDER BY created_at DESC";
        List<Note> notes = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    Note note = new Note();
                    note.setId(rs.getInt("id"));
                    note.setUser(user);
                    note.setTitle(rs.getString("title"));
                    note.setContent(rs.getString("content"));
                    note.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    notes.add(note);
                }
            return notes;
            } catch (SQLException e) {
            logger.error("Error finding notes for user id {}", user.getId(), e);
            throw new DataAccessException("Failed to find notes by user", e);
        }
    }

    @Override
    public Optional<Note> findById(int id) {
        String sql = "SELECT user_id, title, content, created_at FROM notes WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Note note = new Note();
                note.setId(id);
                note.setTitle(rs.getString("title"));
                note.setContent(rs.getString("content"));
                note.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                User user = new User();
                user.setId(rs.getInt("user_id"));
                note.setUser(user);

                return Optional.of(note);
                }
            return Optional.empty();
            } catch (SQLException e) {
                logger.error("Error finding note for its id {}", id, e);
                throw new DataAccessException("Failed to find note by id", e);
        }
    }
}
