<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<h2>Danh sách nhân viên</h2>

<form action="${pageContext.request.contextPath}/employees" method="get">
    <input type="text" name="keyword" placeholder="Tìm theo tên" value="${keyword}"/>
    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/employees/add">Thêm mới</a>
</form>

<c:if test="${not empty message}">
    <p style="color:red">${message}</p>
</c:if>

<c:if test="${empty employees}">
    <p>Danh sách trống!</p>
</c:if>

<c:if test="${not empty employees}">
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Họ tên</th>
            <th>Email</th>
            <th>SĐT</th>
            <th>Ảnh</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.employee_name}</td>
                <td>${emp.email}</td>
                <td>${emp.phone_number}</td>
                <td><img src="${emp.avatar_url}" width="50"/></td>
                <td>
                    <c:choose>
                        <c:when test="${emp.status}">Đang làm việc</c:when>
                        <c:otherwise>Đã nghỉ</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/employees/edit/${emp.employee_id}">Sửa</a>
                    <a href="${pageContext.request.contextPath}/employees/delete/${emp.employee_id}" onclick="return confirm('Xác nhận xóa?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Pagination -->
    <ul>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <li style="display:inline; margin-right: 5px;">
                <a href="?page=${i}${keyword != null ? '&keyword=' + keyword : ''}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</c:if>
