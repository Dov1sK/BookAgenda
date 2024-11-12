package Controle;

import DAO.LivroDAO;
import model.Livro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/livro")
public class LivroServlet extends HttpServlet {
    private LivroDAO livroDAO;

    public void init() {
        livroDAO = new LivroDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            
        	switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertLivro(request, response);
                    break;
                case "delete":
                    deleteLivro(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateLivro(request, response);
                    break;
                default:
                    listLivro(request, response);
                    break;
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Erro ao acessar o banco de dados.");
            request.getRequestDispatcher("livro-list.jsp").forward(request, response);
        }
    }

    private void listLivro(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String pesquisaTitulo = request.getParameter("pesquisaTitulo");
        List<Livro> listLivro;
       
        
        
        if (pesquisaTitulo != null && !pesquisaTitulo.trim().isEmpty()) {
            listLivro = livroDAO.Pesquisa(pesquisaTitulo);
        } else {
            listLivro = livroDAO.selectAllLivros();
        
        
        
        }
        request.setAttribute("listLivro", listLivro);
        request.getRequestDispatcher("livro-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("FormularioCadastro.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Livro existingLivro = livroDAO.selectLivro(id);
        request.setAttribute("livro", existingLivro);
        request.getRequestDispatcher("FormularioCadastro.jsp").forward(request, response);
    }

    private void insertLivro(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String titulo = request.getParameter("titulo");
        String genero = request.getParameter("genero");
        String autor = request.getParameter("autor");
        
       
        if (titulo == null || titulo.trim().isEmpty() ||
                genero == null || genero.trim().isEmpty() ||
                autor == null || autor.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Todos os campos são obrigatórios.");
                request.getRequestDispatcher("FormularioCadastro.jsp").forward(request, response);
                return;
        }    
        
       
        Livro novoLivro = new Livro(titulo, genero, autor);
        livroDAO.insertLivro(novoLivro);
        response.sendRedirect("livro?action=list");
    }

    private void updateLivro(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String genero = request.getParameter("genero");
        String autor = request.getParameter("autor");

       
        
        if (titulo == null || titulo.trim().isEmpty() ||
                genero == null || genero.trim().isEmpty() ||
                autor == null || autor.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Todos os campos são obrigatórios.");
                Livro existingLivro = livroDAO.selectLivro(id);
                request.setAttribute("livro", existingLivro);
                request.getRequestDispatcher("FormularioCadastro.jsp").forward(request, response);
                return;
           
        }
        
        Livro livro = new Livro(titulo, genero, autor);
        livro.setId(id);
        livroDAO.updateLivro(livro);
        response.sendRedirect("livro?action=list");
    }

    private void deleteLivro(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        livroDAO.deleteLivro(id);
        response.sendRedirect("livro?action=list");
    }
}