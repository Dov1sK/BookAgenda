package DAO;

import model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/livro";
    private String jdbcUsername = "root";  // Ajuste o usuário
    private String jdbcPassword = "12345";  // Ajuste a senha

    private static final String INSERT_LIVRO_SQL = "INSERT INTO livro (titulo, genero, autor) VALUES (?, ?, ?)";
    private static final String SELECT_LIVRO_BY_ID = "SELECT id, titulo, genero, autor FROM livro WHERE id = ?";
    private static final String SELECT_ALL_LIVROS = "SELECT * FROM livro";
    private static final String DELETE_LIVRO_SQL = "DELETE FROM livro WHERE id = ?";
    private static final String UPDATE_LIVRO_SQL = "UPDATE livro SET titulo = ?, genero= ?, autor =? WHERE id = ?";

    protected Connection getConnection()  {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Métodos CRUD

    public void insertLivro(Livro livro) throws SQLException {
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LIVRO_SQL)) {
            preparedStatement.setString(1, livro.getTitulo());
            preparedStatement.setString(2, livro.getGenero());
            preparedStatement.setString(3, livro.getAutor());
            preparedStatement.executeUpdate();
        }
    }

    public Livro selectLivro(int id) {
        Livro livro = null;
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LIVRO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String genero = rs.getString("genero");
                String autor = rs.getString("autor");
                livro = new Livro(titulo, genero, autor);
                livro.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livro;
    }
    
    
    

    public List<Livro> selectAllLivros() {
        List<Livro> livros = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LIVROS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String genero = rs.getString("genero");
                String autor = rs.getString("autor");
                Livro livro = new Livro(titulo, genero, autor);
                livro.setId(id);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }
    
    
    

    public boolean updateLivro(Livro livro) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_LIVRO_SQL)) {
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getGenero());
            statement.setString(3, livro.getAutor());
            statement.setInt(4, livro.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteLivro(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_LIVRO_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
 }
    
    
    public List<Livro> Pesquisa(String titulo) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro WHERE titulo LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + titulo + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String genero = rs.getString("genero");
                String autor = rs.getString("autor");
                Livro livro = new Livro(rs.getString("titulo"), genero, autor);
                livro.setId(id);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }
    
    
}