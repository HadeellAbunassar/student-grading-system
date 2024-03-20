<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Instructor Portal</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Welcome To Our Grading System - Instructor Portal Assigning Grades</h1>

    <form action="${pageContext.request.contextPath}/assignGrades.do" method="post">
        <div class="form-group">
            <label for="courseName">Course Name:</label>
            <input type="text" class="form-control" id="courseName" name="courseName" required>
        </div>
        <div class="form-group">
            <label for="studentName">Student Name:</label>
            <input type="text" class="form-control" id="studentName" name="studentName" required>
        </div>
        <div class="form-group">
            <label for="grades">Grades:</label>
            <input type="text" class="form-control" id="grades" name="grades" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

    <c:if test="${not empty msg}">
        <p>${msg}</p>
    </c:if>
</div>
</body>
</html>
