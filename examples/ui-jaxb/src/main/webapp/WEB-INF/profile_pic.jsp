<%@ page import="com.microsoft.*" %>
<%@ page import="java.util.*" %>
<html>
<body>
<%@include file='header.jsp'%>
<h2>Offline access for height </h2>
<h4>Enter an authed user credential</h4>
<form action="<%= request.getContextPath() %>/things/height" 
	method="post">
	Person ID: <input type="text" name="personid" /><br />
	Record ID: <input type="text" name="recordid" /><br />
  Height: <input type="text" name="height" /><br />
  <input type="submit" value="Submit" />
</form>
</body>
</html>