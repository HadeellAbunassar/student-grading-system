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
        <h1>Welcome To Our Grading System  - Instructor Portal </h1>


        <form id="myForm" action="${pageContext.request.contextPath}/instructor.do" method="post">
            <input type="hidden" id="username" name="username" value="<%= session.getAttribute("userName") %>">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="showCourses" value="showCourses">
                <label class="form-check-label" for="showCourses">
                    Show Your courses
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="showstucrs" value="showstucrs">
                <label class="form-check-label" for="showstucrs">
                    Show students in a course
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="options" id="assigngrades" value="assigngrades">
                <label class="form-check-label" for="assigngrades">
                    Assign Grades to Students
                </label>
            </div>
            <button type="submit" class="btn btn-primary" style="margin-top: 5%;">Submit</button>
        </form>


    </div>

</div>




</body>

</html>
