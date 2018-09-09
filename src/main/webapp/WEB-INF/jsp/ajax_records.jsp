<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<c:forEach var="listrecord" items="${listrecords}">
    <tr>
        <td>${listrecord.id}</td>
        <td>${listrecord.name}</td>
        <td>${listrecord.address}</td>
    </tr>
 </c:forEach>