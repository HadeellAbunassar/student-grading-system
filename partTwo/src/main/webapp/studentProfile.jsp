<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>Student Portal - Grading System</title>

    <style>
        .center {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

    </style>

</head>
<body>
<div class="center">
    <div>
        <h1>Welcome To Our Grading System  - Student Portal</h1>
        <ul style="list-style-type: none;">
            <c:forEach var="c" items="${studentChoice}">
                <li>${c}</li>
            </c:forEach>
        </ul>
    </div>
</div>

</body>
</html>
