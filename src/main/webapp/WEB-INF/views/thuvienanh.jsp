<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<html>
<head>
<title>Upload Result</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
</head>
<body>
	<jsp:include page="_menu.jsp" />

<div class="container">
	<a href="<%=request.getContextPath() + "/thuvienanh?path="%>${pre}">Truoc</a>
	<div class="row">
	<c:forEach items="${Files}" var="file">
		<c:choose>
			<c:when test="${file.isFolder}">

		<div class="card col-md-2">
		    <i class="fas fa-folder-open text-warning" style="font-size: 80px;"></i>
		    <div class="card-body">
		      <p class="card-text">${file.name}</p>
		      <a href="${pageContext.request.contextPath}/thuvienanh?path=${now}1408dt2410${file.name}" class="btn btn-primary w-100">open</a><br>
		      <br>
		      <a href="${pageContext.request.contextPath}/xoaanh?path=${now}&name=${file.name}" class="btn btn-danger  w-100">xoa</a>
		    </div>
		</div>
			  </c:when>
				<c:otherwise>
		<div class="card col-md-2">
		    <img class="card-img-top" src="<%=request.getContextPath()%>${file.url}" alt="Card image" style="width:100%">
		    <div class="card-body">
		      <p class="card-text">${file.name}</p>
		      	<a href="${pageContext.request.contextPath}/xoaanh?path=${now}&name=${file.name}" class="btn btn-danger w-100 stretched-link">xoa</a>
		    </div>
		</div>
			  </c:otherwise>
		</c:choose>
		
	</c:forEach>	
	</div>
	<div class="row">
		<div class="card col-md-2">
		    <i class="fas fa-folder-plus text-success" style="font-size: 60px;"></i>
		    <div class="card-body">
		      <p class="card-text">${file.name}</p>
		      <a href="#" class="btn btn-primary stretched-link" data-toggle="modal" data-target="#myModal">Thêm folder</a>
		    </div>
		</div>
		<div class="card col-md-2">
		    <i class="fas fa-file-medical text-success" style="font-size: 60px;"></i>
		    <div class="card-body">
		      <p class="card-text">${file.name}</p>
		      <a href="#" class="btn btn-primary stretched-link" data-toggle="modal" data-target="#myModal1">Thêm file</a>
		    </div>
		</div>
	</div>
	
	<!-- The Modal -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Them folder</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
     <form method="POST" action="${pageContext.request.contextPath}/themfolder">
                
     
        <input name="nameFolder" type="text"/>
        <input name="nowPath" type="hidden" value="${now}"/>
         
        <input type="submit" value="Upload">
          
    </form>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>
  
  <!-- The Modal -->
  <div class="modal fade" id="myModal1">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Modal Heading</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
            <!-- MyUploadForm -->
    <form:form modelAttribute="myUploadForm" method="POST" action="${pageContext.request.contextPath}/uploadMultiFile" enctype="multipart/form-data">
                
     
        <form:input path="fileDatas" type="file"  multiple="multiple"/>
        <form:input path="urlDestFile" type="hidden" value="${now}"/>
         
        <input type="submit" value="Upload">
          
    </form:form>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>
</div>
</body>
</html>