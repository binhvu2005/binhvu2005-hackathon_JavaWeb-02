<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Department List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
</head>
<body>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Department Management</h2>
        <a href="${pageContext.request.contextPath}/departments/add" class="btn btn-primary">
            <i class="fas fa-plus"></i> Add Department
        </a>
    </div>

    <div class="card mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/departments" method="get" class="row g-3">
                <div class="col-auto">
                    <input type="text" class="form-control" name="search" placeholder="Search department name..."
                           value="${param.search}">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Department Name</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${departments}" var="dept">
                    <tr>
                        <td>${dept.department_id}</td>
                        <td>${dept.department_name}</td>
                        <td>${dept.description}</td>
                        <td>
                            <span class="badge ${dept.status ? 'bg-success' : 'bg-danger'}">
                                    ${dept.status ? 'Active' : 'Inactive'}
                            </span>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/departments/update/${dept.department_id}" class="btn btn-sm btn-warning me-2">
                                <i class="fas fa-edit"></i>
                            </a>
                            <button class="btn btn-sm btn-danger" onclick="confirmDelete(${dept.department_id})">
                                <i class="fas fa-trash"></i>
                            </button>
                            <a href="${pageContext.request.contextPath}/employees/${dept.department_id}" class="btn btn-sm btn-info">
                                <i class="fas fa-eye"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Phân trang -->
    <c:if test="${!isSearch}">
        <nav aria-label="Page navigation" class="mt-4">
            <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/departments?page=${i}">
                                ${i}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </c:if>

    <!-- Thông báo -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger mt-3">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success mt-3">${successMessage}</div>
    </c:if>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirm Delete</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this department?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <a href="#" id="confirmDeleteBtn" class="btn btn-danger">Delete</a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function confirmDelete(id) {
        const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
        document.getElementById('confirmDeleteBtn').href = '${pageContext.request.contextPath}/departments/delete/' + id;
        modal.show();
    }
</script>
</body>
</html>
