<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add Department</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">Add New Department</h2>
  <form:form action="${pageContext.request.contextPath}/departments/add" method="post" modelAttribute="departmentDTO"  accept-charset="UTF-8">
    <style>
      .error {
        color: red;
        font-size: 0.8em;
      }
    </style>
    <div class="mb-3">
      <label for="departmentName" class="form-label">Department Name</label>
      <form:input path="department_name" class="form-control" id="departmentName"/>
      <form:errors path="department_name" cssClass="error"/>
    </div>
    <div class="mb-3">
      <label for="description" class="form-label">Description</label>
      <form:textarea path="description" class="form-control" id="description" rows="3"/>
      <form:errors path="description" cssClass="error"/>
    </div>
    <button type="submit" class="btn btn-primary">Add Department</button>
    <a href="<c:url value="/departments"/>" class="btn btn-secondary">Cancel</a>
  </form:form>
</div>
</body>
</html>
