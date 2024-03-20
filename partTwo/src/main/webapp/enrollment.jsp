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
        <h1>Welcome To Our Grading System  - Student Portal Enrollment Page</h1>
        <c:if test="${not empty availableCourses}">
            <h3 style="margin-left: 35%">Available Courses</h3>
            <ul style="list-style-type: none;">
                <c:forEach var="c" items="${availableCourses}">
                    <li>${c}</li>
                </c:forEach>
            </ul>

            <div class="form-container">
                <h4>Enroll in a Course -ID- :</h4>

                <form action="${pageContext.request.contextPath}/enrollment.do" method="post">
                    <div class="form-group">
                        <label for="courseId">Course ID:</label>
                        <input type="text" id="courseId" name="courseId" required>
                    </div>
                    <input type="hidden" id="username" name="username" value="${session.getAttribute("userName")}">
                    <button type="submit" class="btn btn-primary" style="margin-top: 5%;">Submit</button>
                </form>
            </div>
        </c:if>

        <c:if test="${not empty enrollInCourse}">
            <p>${enrollInCourse}</p>
        </c:if>

    </div>
</div>

</body>
</html>
