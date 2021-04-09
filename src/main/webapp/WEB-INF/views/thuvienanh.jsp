<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     
<html>
<head>
<meta charset="UTF-8">
<title>Upload Result</title>
</head>
<body>
    <jsp:include page="_menu.jsp"/>
    
    <h3>Uploaded Files:</h3>
     
    Description: ${description}
     
    <br/>
     <% int stt = 1; %>
    <c:forEach items="${uploadedFiles}" var="file">
          <li style="list-style-type: none">
          <h6><%=stt %></h6>
          	<img src="<%=request.getContextPath()%>${file}" width="10%"/>
          </li>
          <% stt++; %>
    </c:forEach>
 
</body>
</html>