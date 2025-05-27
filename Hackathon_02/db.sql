drop database quanlynhansu;
create database quanlynhansu;
use quanlynhansu;

create table Department(
	department_id int auto_increment primary key,
    department_name varchar(50) not null unique ,
    description text ,
    status bit default 1
);
CREATE TABLE Employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    avatar_url VARCHAR(255),
    status BIT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Department(department_id)
);

-- Thêm dữ liệu mẫu
INSERT INTO Department (department_name, description, status) VALUES
('Kế toán', 'Phòng kế toán công ty', 1),
('Nhân sự', 'Phòng nhân sự công ty', 1),
('Kỹ thuật', 'Phòng kỹ thuật công ty', 1);

INSERT INTO Employee (full_name, email, phone_number, avatar_url, status, department_id) VALUES
('Nguyễn Văn A', 'a@gmail.com', '0123456789', 'https://example.com/avatar/a.jpg', 1, 1),
('Trần Thị B', 'b@gmail.com', '0987654321', 'https://example.com/avatar/b.jpg', 1, 2),
('Lê Văn C', 'c@gmail.com', '0123456780', 'https://example.com/avatar/c.jpg', 1, 3);

-- Tạo thủ tục lưu trữ để lấy tất cả phòng ban
DELIMITER //
CREATE PROCEDURE get_all_departments()
BEGIN
    SELECT * FROM Department;
END //
DELIMITER ;
-- Tạo thủ tục lưu trữ để lấy số trang  phòng ban

DELIMITER //
CREATE PROCEDURE get_department_total_page( IN page_size INT)
BEGIN
    DECLARE total_count INT;
    SELECT COUNT(*) INTO total_count FROM Department;
    SET @total_page = CEIL(total_count / page_size);
    SELECT @total_page AS total_page;
END //
DELIMITER ;
-- Tạo thủ tục lưu trữ để lấy danh sách phòng ban theo trang
DELIMITER //
CREATE PROCEDURE get_all_departments_by_page(IN page_number INT, IN page_size INT)
BEGIN
    DECLARE offset INT;
    SET offset = (page_number - 1) * page_size;
    SELECT * FROM Department
    ORDER BY department_id
    LIMIT page_size OFFSET offset;
END //
DELIMITER ;

-- Tạo thủ tục lưu trữ để thêm phòng ban mới
DELIMITER //
CREATE PROCEDURE insert_department(IN dep_name VARCHAR(50), IN dep_description TEXT)
BEGIN
    INSERT INTO Department (department_name, description)
    VALUES (dep_name, dep_description);
END //
DELIMITER ;
-- chinh sửa phòng ban
DELIMITER //
CREATE PROCEDURE update_department(IN dep_id INT, IN dep_name VARCHAR(50), IN dep_description TEXT, IN dep_status BIT)
BEGIN
    UPDATE Department
    SET department_name = dep_name,
        description = dep_description,
        status = dep_status
    WHERE department_id = dep_id;
END //
DELIMITER ;
-- Xóa phòng ban nêu nó không có nhân viên có thi bao loi
DELIMITER //
CREATE PROCEDURE delete_department(IN dep_id INT)
BEGIN
    DECLARE emp_count INT;
    SELECT COUNT(*) INTO emp_count FROM Employee WHERE department_id = dep_id;

    IF emp_count = 0 THEN
        DELETE FROM Department WHERE department_id = dep_id;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot delete department with existing employees';
    END IF;
END //
DELIMITER ;
-- lấy phòng ban theo id
DELIMITER //
CREATE PROCEDURE get_department_by_id(IN dep_id INT)
BEGIN
    SELECT * FROM Department WHERE department_id = dep_id;
END //
DELIMITER ;
-- tìm kiếm phòng ban theo tên
DELIMITER //
CREATE PROCEDURE search_department_by_name(IN dep_name VARCHAR(50))
BEGIN
    SELECT * FROM Department WHERE department_name LIKE CONCAT('%', dep_name, '%');
END //
DELIMITER ;
-- Tạo thủ tục lưu trữ để lấy số trang nhân viên có trong phòng ban
DELIMITER //
    CREATE PROCEDURE get_employee_total_page(IN dep_id INT, IN page_size INT)
BEGIN
    DECLARE total_count INT;
    SELECT COUNT(*) INTO total_count FROM Employee WHERE department_id = dep_id;
    SET @total_page = CEIL(total_count / page_size);
    SELECT @total_page AS total_page;
END //
DELIMITER ;
-- Tạo thủ tục lưu trữ để lấy danh sách nhân viên theo trang của phòng ban
DELIMITER //
CREATE PROCEDURE get_all_employees_by_page(IN dep_id INT, IN page_number INT, IN page_size INT)
BEGIN
    DECLARE offset_val INT;
    SET offset_val = (page_number - 1) * page_size;

    SELECT * FROM Employee
    WHERE department_id = dep_id
    ORDER BY employee_id
    LIMIT page_size OFFSET offset_val;
END //
DELIMITER ;
-- Tạo thủ tục lưu trữ để thêm nhân viên mới
DELIMITER //
CREATE PROCEDURE insert_employee(
    IN emp_full_name VARCHAR(100),
    IN emp_email VARCHAR(100),
    IN emp_phone_number VARCHAR(20),
    IN emp_avatar_url VARCHAR(255),
    IN emp_department_id INT
)
BEGIN
    INSERT INTO Employee (full_name, email, phone_number, avatar_url, department_id)
    VALUES (emp_full_name, emp_email, emp_phone_number, emp_avatar_url, emp_department_id);
END //
DELIMITER ;
-- Chỉnh sửa thông tin nhân viên
DELIMITER //
CREATE PROCEDURE update_employee(
    IN emp_id INT,
    IN emp_full_name VARCHAR(100),
    IN emp_email VARCHAR(100),
    IN emp_phone_number VARCHAR(20),
    IN emp_avatar_url VARCHAR(255),
    IN emp_status BIT
)
BEGIN
    UPDATE Employee
    SET full_name = emp_full_name,
        email = emp_email,
        phone_number = emp_phone_number,
        avatar_url = emp_avatar_url,
        status = emp_status
    WHERE employee_id = emp_id;
END //
DELIMITER ;
-- Xóa nhân viên
DELIMITER //
CREATE PROCEDURE delete_employee(IN emp_id INT)
BEGIN
    DELETE FROM Employee WHERE employee_id = emp_id;
END //
DELIMITER ;
-- Lấy thông tin nhân viên theo ID
DELIMITER //
CREATE PROCEDURE get_employee_by_id(IN emp_id INT)
BEGIN
    SELECT * FROM Employee WHERE employee_id = emp_id;
END //
DELIMITER ;
-- Tìm kiếm nhân viên theo tên
DELIMITER //
CREATE PROCEDURE search_employee_by_name(IN emp_name VARCHAR(100))
BEGIN
    SELECT * FROM Employee WHERE full_name LIKE CONCAT('%', emp_name, '%');
END //
DELIMITER ;

-- tìm kiếm  email xem đã có trong hệ thống chưa
DELIMITER //
CREATE PROCEDURE check_email_exists(IN emp_email VARCHAR(100))
BEGIN
    DECLARE email_count INT;
    SELECT COUNT(*) INTO email_count FROM Employee WHERE email = emp_email;

    IF email_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Email already exists';
    END IF;
END //
DELIMITER ;
