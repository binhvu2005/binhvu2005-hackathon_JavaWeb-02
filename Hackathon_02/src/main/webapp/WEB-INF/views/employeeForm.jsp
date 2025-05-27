<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<h2>
    <c:choose>
        <c:when test="${employeeDTO.employee_id == 0}">Thêm nhân viên</c:when>
        <c:otherwise>Chỉnh sửa nhân viên</c:otherwise>
    </c:choose>
</h2>

<form method="post" enctype="multipart/form-data">
    <!-- ID ẩn để chỉnh sửa -->
    <input type="hidden" name="employee_id" value="${employeeDTO.employee_id}" />

    <label>Họ tên:</label><br/>
    <input type="text" name="employee_name" value="${employeeDTO.employee_name}" />
    <span style="color: red">${errors.employee_name}</span><br/>

    <label>Email:</label><br/>
    <input type="email" name="email" value="${employeeDTO.email}" />
    <span style="color: red">${errors.email}</span><br/>

    <label>Số điện thoại:</label><br/>
    <input type="text" name="phone_number" value="${employeeDTO.phone_number}" />
    <span style="color: red">${errors.phone_number}</span><br/>

    <label>Phòng ban:</label><br/>
    <select name="department_id">
        <c:forEach var="dept" items="${departments}">
            <option value="${dept.department_id}"
                    <c:if test="${dept.department_id == employeeDTO.department_id}">selected</c:if>>
                    ${dept.department_name}
            </option>
        </c:forEach>
    </select><br/>

    <label>Ảnh đại diện:</label><br/>
    <input type="file" name="avatar" /><br/>

    <c:if test="${employeeDTO.avatar_url != null}">
        <img src="${pageContext.request.contextPath}/${employeeDTO.avatar_url}" width="80"/>
    </c:if><br/>

    <button type="submit">Lưu</button>
    <a href="${pageContext.request.contextPath}/employees">Hủy</a>
</form>
