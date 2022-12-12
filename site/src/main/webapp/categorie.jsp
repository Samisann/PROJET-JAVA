<%@ include file="/header-admin.jsp" %>
<%@ page 
    
    import="java.util.ArrayList,java.util.List,com.site.entities.Category"
 %>
 <%
  List<Category> categories = new ArrayList<Category>();
  if(request.getAttribute("categories")!=null){
     categories = (ArrayList<Category>)request.getAttribute("categories");
  }
%>
<div class="container-fluid">
    <div class="row">

      <main class="col-md-10 ml-sm-auto col-lg-10 pt-3 px-4">
         <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
      
        </div>
        <div class="row">
          <div class="col-lg-12 col-md-12 col-sm-12 pr-0 mb-3">
            <div class="card-collapsible card">
              <div class="card-header">
                Table <i class="fa fa-caret-down caret"></i>
              </div>
              <div class="card-body">
                <table class="table">
                  <thead class="thead bg-primary text-white">
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Code</th>
                      <th scope="col">Libelle</th>
                      <th scope="col">Produit</th>
                      <th scope="col">Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                 	<%
                     for(Category dsp :categories){
                  %>
                    <tr>
                      <td><%= dsp.getLibelle()%></td>
                      <td><%= dsp.getProduit().getLibelle()%></td>
                      <td>
                          <a class="btn btn-sm btn-success" href="modifierCategorie?codeCategorie=<%= dsp.getCode()%>">Modifier</a>
                            <!-- Button trigger modal -->
                          <a href="deleteProduit?codeProduit=<%= dsp.getCode()%>" class="btn btn-sm btn-danger">
                            supprimer
                          </a>
                      </td>
                    </tr>
                  <%
                    }
                  %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</body>
</html>