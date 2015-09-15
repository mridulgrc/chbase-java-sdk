<jsp:useBean id="weights" type="java.util.List" scope="request" />
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
	com.chbase.thing.oxm.jaxb.weight.Weight" %>
<%@ page import="java.util.*" %>
<html>
<body>
<%@include file='header.jsp'%>
<form action="<%= request.getContextPath() %>/things/weight" 
	method="post">
  Weight: <input type="text" name="weight" /><br />
  <input type="submit" value="Submit" />
</form>
<table>
    		<tr>
    			<td>Id:</td>
    			<td>Value:</td>
    			<td>When:</td>
    			<td>Display:</td>
			</tr>
<%
Iterator iterator = weights.iterator();
while ( iterator.hasNext() ){
	Thing2 thing = (Thing2)iterator.next();
	Weight weight = (Weight)thing.getData();
	
	out.println(
			"<tr><td>" + thing.getThingId().getValue() + "</td><td>" 
			+ weight.getValue().getKg() + "</td><td>" 
			+ weight.getWhen().toString() + "</td><td>"
			+ (weight.getValue().getDisplay() == null
			? "n/a"
			: weight.getValue().getDisplay().getValue()) + "</td></tr>");
}
%>
</table>
</body>
</html>