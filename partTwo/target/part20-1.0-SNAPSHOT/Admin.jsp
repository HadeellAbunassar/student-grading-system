<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>Instructor Portal - Grading System</title>

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
        <h1>Welcome To Our Grading System - Admin Portal</h1>


        <form action="${pageContext.request.contextPath}/admin.do" method="post">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="addstudent" value="addstudent">
                <label class="form-check-label" for="addstudent">
                    Add Student
                </label>
            </div>
            <div class="mb-3">
                <label for="studentname" class="form-label">Student Name </label>
                <input type="text" name="studentname" class="form-control" id="studentname">
            </div>


            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="addinstructor" value="addinstructor">
                <label class="form-check-label" for="addinstructor">
                    Add Instructor
                </label>
            </div>
            <div class="mb-3">
                <label for="Instructorname" class="form-label">Instructor Name </label>
                <input type="text" name="Instructorname" class="form-control" id="Instructorname">
            </div>


            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="assigncoursetoinstructor" value="assigncoursetoinstructor">
                <label class="form-check-label" for="assigncoursetoinstructor">
                    Assign course to Instructor
                </label>
            </div>
            <div class="mb-3">
                <label for="Instructorname1" class="form-label">Instructor Name </label>
                <input type="text" name="Instructorname1" class="form-control" id="Instructorname1">
            </div>
            <div class="mb-3">
                <label for="coursename" class="form-label">Course Name </label>
                <input type="text" name="coursename" class="form-control" id="coursename">
            </div>
            <button type="submit" class="btn btn-primary" style="margin-top: 7%;">Submit</button>

            <c:if test="${not empty msg}">
                <p>${msg}</p>
            </c:if>

        </form>
    </div>
</div>



</body>

</html>