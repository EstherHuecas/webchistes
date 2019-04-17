<%-- 
    Document   : home
    Created on : 24-feb-2019, 21:02:26
    Author     : Esther
--%>

<%@page import="entities.Chiste"%>
<%@page import="java.util.List"%>
<%@page import="entities.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/fontawesome.css">
  <link rel="stylesheet" type="text/css" href="css/mycss.css" media="screen" />
  <title>Web Chistes</title>
</head>

<body>
    
    <%
        //Lista con las categorias
        List<Categoria> listaCategorias = (List<Categoria>)request.getSession().getAttribute("listaCategorias");
        //Lista con los chistes
        List<Chiste> listaChistes = (List<Chiste>)request.getAttribute("listaChistes");
        //Le paso el valor de la bandera del Controller
        Boolean mejores = (Boolean) request.getSession().getAttribute("bandera"); //Para que siempre se quede activado el "Mejores chistes"
        //Coger el id de la Categoria pulsada 
        String idCategoriaPulsada = (String)request.getSession().getAttribute("idCategoria");
    %>
    
  <div class="container">
    <div class="bg-azulclaro bordes-redondos">
      <header class="text-center">
          <a href="Controller?op=inicio"><img src="img/logo.png" class="img-fluid" alt="logo"></a>
      </header>
      <div class="bg-azulclarito cajagrandemedio">
        <div class="row">
          <div class="col-sm-1"></div>
          <h2 class="text-danger col-sm-2 tipo-letra">Categoría:</h2>
          <!--Select-->
          <select class="custom-select my-1 mr-sm-2 col-sm-3" id="selectCategoria" name="selectCategoria" 
                  onchange='window.location = "Controller?op=categoriaPulsada&selectCategoria="+this.value'>
            <option selected>Elija Categoría</option>
            <% for (Categoria categoria : listaCategorias) {%>
            <option value="<%=categoria.getId()%>"><%="Categoria " + categoria.getNombre()%></option>
            <%}%>
            <!--Fin select-->
          </select>
          <div class="col-sm-1"></div>
          <!-- Button Modal Categoria-->
          <button type="button" class="btn btn-transparent" data-toggle="modal" data-target="#modal-categoria">
            <img src="img/add.png" />
          </button>
          <!--Enlace--> 
          <h4 class="tipo-letra text-danger">
              <% if(mejores==false) {%>
              <!--Controller?op=nombredelcase&nombrebandera-->
            <a class="text-danger" href='Controller?op=mejoresChistes&mejores=<%=mejores%>'>Mejores chistes</a>
              <%} else {%>
              <!--Pinta por la categoría que he pulsado antes-->
            <a class="text-danger" href="Controller?op=mejoresChistes&mejores=<%=mejores%>&idCategoria=<%=idCategoriaPulsada%>">Por categoria</a>
              <%}%>
          </h4>
          <!--Fin enlace-->
          <!--Fin row-->
        </div>
        <!--Fin azul clarito-->
      </div>
      <!--Último botón modal-->
      <div class="cajagrande">
        <div class="row">
          <div class="col-sm-6"></div>
          <!-- Button Modal Chiste-->
          <button type="button" class="btn btn-transparent" data-toggle="modal" data-target="#modal-chiste">
            <img src="img/add.png" />
          </button>
        </div>
      </div>
      <!--Fin del botón modal-->
      <h1 class="tipo-letra text-center negrita-letra text-danger">CHISTES-AZARQUIEL</h1>
      
      <div class="row bg-chicha mx-0">
        <div class="col-sm-1"></div>
        <div class="col-md-12">
          <% if(listaChistes != null && listaChistes.size()>0){ %>
            <% for(Chiste chiste : listaChistes) { %>
            <div class="row mx-0">
                
            <h1> <%= chiste.getTitulo() %> </h1>
            <div class="col-sm-3">
            <h3> (<%= chiste.getIdcategoria().getNombre() %>) </h3>
            </div>
            </div>
            <h5> <%= chiste.getAdopo() %> </h5>
            <p> <%= chiste.getDescripcion() %> </p>
              <span class="rating">
                 <a href="controller.jsp?op=rating&rating=1&chisteid=<%=chiste.getId()%>">&#9733;</a>
                 <a href="controller.jsp?op=rating&rating=2&chisteid=<%=chiste.getId()%>">&#9733;</a>
                 <a href="controller.jsp?op=rating&rating=3&chisteid=<%=chiste.getId()%>">&#9733;</a>
                 <a href="controller.jsp?op=rating&rating=4&chisteid=<%=chiste.getId()%>">&#9733;</a>
                 <a href="controller.jsp?op=rating&rating=5&chisteid=<%=chiste.getId()%>">&#9733;</a>
              </span>
            <% } %>
          <% } %>
         
      </div>
      <!--Fin header-->
    </div>
    <!--Fin container-->
  </div>

  <!-- Modal Categoria-->
  <div class="modal fade" id="modal-categoria" tabindex="-1" role="dialog" aria-labelledby="modal-categoria" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Nueva categoria</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
          <form action="Controller?op=insertarCategorias" method="post">
          <div class="modal-body">
            <div class="form-grupo">
              <label for="tituloCategoria">Categoria:</label>
              <input type="text" class="form-control" id="tituloCategoria" placeholder="Titulo categoria" name="Categoria">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
            <button type="submit" class="btn btn-primary">Aceptar</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!--Fin modal categoria-->
  <!-- Modal Chiste-->
  <div class="modal fade" id="modal-chiste" tabindex="-1" role="dialog" aria-labelledby="modal-chiste" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="myModalLabel">Nuevo chiste</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
          <form action="Controller?op=insertarChistes" method="post">
          <div class="modal-body">
            <div class="form-group">
              <label for="apodo">Apodo:</label>
              <input type="text" class="form-control" name="Apodo" id="apodo" placeholder="Apodo">
            </div>
            <div class="form-group">
              <label for="descripcion">Descripcion:</label>
              <input type="text" name="Chistes" class="form-control" id="descripcion" placeholder="Descripcion">
            </div>
            <div class="form-group">
              <label for="selectModal">Categoria:</label>
              <select class="form-control" id="Categoria" name="Categoria">
               <option selected="">Elija Categoría</option>
            <% for (Categoria categoria : listaCategorias) {%>
            <option value="<%=categoria.getId()%>"><%="Categoria " + categoria.getNombre()%></option>
            <%}%>
              </select>
            </div>
            <div class="form-group">
              <label for="titulo">Titulo:</label>
              <input type="text" name="Titulo" class="form-control" id="titulo" placeholder="Titulo">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
            <button type="submit" class="btn btn-primary">Aceptar</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!--Fin modal chistes-->

  <!-- Optional JavaScript -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="js/jquery-3.3.1.slim.min.js"></script>
  <script src="js/popper.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <!-- MyJS -->
  <script type="text/javascript" src="js/myjs.js"></script>
</body>

</html>
