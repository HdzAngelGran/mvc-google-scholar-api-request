package org.arkn37.repository;

import org.arkn37.model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {

    private final Connection connection;

    public ArticleRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Article article) throws SQLException {
        String sql = "INSERT INTO challenge3.articles (title, authors, publication_date, summary, link, keywords, cited_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, article.getTitle());
            stmt.setString(2, article.getAuthors());
            stmt.setString(3, article.getPublicationDate());
            stmt.setString(4, article.getDescription());
            stmt.setString(5, article.getLink());
            stmt.setString(6, "");
            stmt.setInt(7, 1);

            stmt.executeUpdate();
        }
    }

    public List<Article> getAll() throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT id, title, authors, publication_date, summary, link, keywords, cited_by FROM challenge3.articles";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Article article = new Article();

                article.setId(rs.getInt("id"));
                article.setTitle(rs.getString("title"));
                article.setAuthors(rs.getString("authors"));
                article.setPublicationDate(rs.getString("publication_date"));
                article.setDescription(rs.getString("summary"));
                article.setLink(rs.getString("link"));
                article.setKeywords(rs.getString("keywords"));
                article.setCitedBy(rs.getInt("cited_by"));

                articles.add(article);
            }
        }
        return articles;
    }
}
