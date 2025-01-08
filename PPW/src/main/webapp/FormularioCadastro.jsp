<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html><link rel="stylesheet" href="maquiage2.css">
<head><title>BookAgenda</title></head>
<body>


<div class="form-container">

<h1>${livro != null ? 'Editar Livro' : 'Novo Livro'}</h1>

<form action="livro?action=${livro != null ? 'update' : 'insert'}" method="post">
    <input type="hidden" name="id" value="${livro != null ? livro.id : ''}" />
    
    
    <div class="form-group">
            <label for="titulo">Título:</label>
            <input type="text" id="titulo" name="titulo" value="${livro != null ? livro.titulo : ''}" required />
        </div>

       
        <div class="form-group">
            <label for="genero">Gênero:</label>
            <input type="text" id="genero" name="genero" value="${livro != null ? livro.genero : ''}" required />
        </div>


        <div class="form-group">
            <label for="autor">Autor:</label>
            <input type="text" id="autor" name="autor" value="${livro != null ? livro.autor : ''}" required />
        </div>
        
        
    <input type="submit" value="Cadastrar"/>
</form>


<a href="livro?action=list">Tabela</a>
</div>

</body>
</html>
