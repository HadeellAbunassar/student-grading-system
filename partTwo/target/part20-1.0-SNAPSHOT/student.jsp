<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

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
        #myForm{
            margin-left: 35%;
            margin-top: 5%;
        }
    </style>

</head>

<body>
<div class="center">
    <div>
        <h1>Welcome To Our Grading System  - Student Portal </h1>


        <form id="myForm" action="${pageContext.request.contextPath}/student.do" method="post">
            <input type="hidden" id="username" name="username" value="<%= session.getAttribute("userName") %>">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="showGrades" value="showGrades">
                <label class="form-check-label" for="showGrades">
                    Show Your Grades
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="showCourseInfo" value="showCourseInfo">
                <label class="form-check-label" for="showCourseInfo">
                    Show Course statistics
                </label>
            </div>
            <p>if you want to show course statistics , write the course name:</p>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">Course Name </label>
                <input type="text" name="course" class="form-control" id="exampleInputPassword1">
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="showYourCourses" value="showYourCourses">
                <label class="form-check-label" for="showYourCourses">
                    Show Your Courses
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="enrollInCourse" value="enrollInCourse">
                <label class="form-check-label" for="enrollInCourse">
                    Enroll in Course
                </label>
            </div>
            <button type="submit" class="btn btn-primary" style="margin-top: 5%;">Submit</button>
        </form>


    </div>

</div>




</body>

</html>
