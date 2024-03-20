<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <title>Grading System</title>

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
    <h1>Welcome To Our Grading System</h1>

    <form action="${pageContext.request.contextPath}/index.do" method="post">
      <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">Name </label>
        <input type="text" name="name" class="form-control" id="exampleInputEmail1"  required>

      </div>
      <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">Password </label>
        <input type="password" name="password" class="form-control" id="exampleInputPassword1" required>
      </div>

      <select name="role" class="form-select form-control" aria-label="Default select example">
        <option selected>Role</option>
        <option value="Admin">Admin</option>
        <option value="Instructor">Instructor</option>
        <option value="Student">Student</option>
      </select>

      <button type="submit" class="btn btn-primary" style="margin-top: 7%;">Submit</button>

      <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger text-center" role="alert">
            ${errorMessage}
        </div>
      </c:if>

    </form>
  </div>
</div>



</body>

</html>