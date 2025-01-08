<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="maquiage.css">
<html>
<head><title>BookAgenda</title></head>
<body>
<h1>BookAgenda</h1>

<div class="search-bar">
<form action="livro" method="get">
    <input type="text" name="pesquisaTitulo" placeholder="Pesquisar por título">
    <input type="hidden" name="action" value="list" />
    <button type="submit">Pesquisar</button>
    
    
</form>

<a href="livro?action=new" class="BotaoNovoLivro">Novo Livro</a>
</div>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Titulo</th>
        <th>Genero</th>
        <th>Autor</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="livro" items="${listLivro}">
        <tr>
            <td>${livro.id}</td>
            <td>${livro.titulo}</td>
            <td>${livro.genero}</td>
            <td>${livro.autor}</td>
            <td>
                <a href="livro?action=edit&id=${livro.id}">Editar</a> | 
                <a href="livro?action=delete&id=${livro.id}">Deletar</a>
            </td>
        </tr>
    </c:forEach>
</table>


<c:if test="${not empty errorMessage}">
    <p style="color:red;">${errorMessage}</p>
</c:if>


</body>
</html>