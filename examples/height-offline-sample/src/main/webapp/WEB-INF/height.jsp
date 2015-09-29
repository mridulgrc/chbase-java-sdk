<jsp:useBean id="heights" type="java.util.List" scope="request" />
<%@ page import="com.chbase.*,
	com.chbase.thing.oxm.jaxb.base.DisplayValue,
	com.chbase.thing.oxm.jaxb.base.WeightValue,
	com.chbase.thing.oxm.jaxb.dates.Date,
	com.chbase.thing.oxm.jaxb.dates.DateTime,
	com.chbase.thing.oxm.jaxb.thing.Thing2,
	com.chbase.thing.oxm.jaxb.thing.ThingType,
	com.chbase.thing.oxm.jaxb.thing.DataXml,
	javax.xml.bind.JAXBContext,
	javax.xml.bind.Marshaller,
	javax.xml.bind.Unmarshaller,
	com.chbase.oxm.jaxb.JaxbContextFactory,
	com.chbase.thing.oxm.jaxb.height.Height" %>
<%@ page import="java.util.*" %>
<html>
<body>
<h1>Offline access to heights</h1>

<p>
	This is a sample application that uses offline access.
	<br/> <i>This page does not need a logged in user to work</i>.
	
	<h5>Fill in the person id and record id of an already authed user</h5>
</p>
<form action="<%= request.getContextPath() %>/things/height" 
	method="post">
	PersonID : <input type="text" name="personid" /><br />
	<br />
	RecordID : <input type="text" name="recordid" /><br />
  <input type="submit" value="Fetch heights" />
</form>
<table>
    		<tr>
    			<th>Id</th>
    			<th>Value</th>
    			<th>When</th>
			</tr>
<%
Iterator iterator = heights.iterator();
while ( iterator.hasNext() ){
	Thing2 thing = (Thing2)iterator.next();
	Height height = (Height)thing.getData();
	
	out.println(
			"<tr><td>" + thing.getThingId().getValue() + "</td><td>" 
			+ height.getValue().getM() + "</td><td>" 
			+ height.getWhen().toString() + "</td><td>");
}
%>
</table>
</body>
</html>