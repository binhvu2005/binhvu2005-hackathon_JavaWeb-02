<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update Department</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Update Department</h2>
    <form action="<c:url value='/departments/update/${departmentId}'/>" method="post"  accept-charset="UTF-8">

        <input type="hidden" name="department_id" value="${departmentId}">

        <div class="mb-3">
            <label for="department_name" class="form-label">Department Name</label>
            <input type="text" class="form-control" id="department_name" name="department_name"
                   value="${departmentDTO.department_name}" required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description"
                      rows="3">${departmentDTO.description}</textarea>
        </div>



        <button type="submit" class="btn btn-primary">Update</button>
        <a href="<c:url value='/departments'/>" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
