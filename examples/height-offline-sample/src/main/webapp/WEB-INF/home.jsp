<html>
<head>
<title>Home</title>
</head>
<body>
<%@include file='header.jsp'%>
<h2>Offline Sample application</h2>
<a href="<%= request.getContextPath() %>/things/height">Heights Page</a> <br />

<p>
	This is a simple offline application that allows you to view
	the weights of the authed users, without being logging in.
	<br />
	Please make a note of the   person id and record id displayed above
	to view the heights of the person.
</p>
</body>
</html>
