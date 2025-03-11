ALTER TABLE attendance
    ADD date datetime NULL;

ALTER TABLE attendance
    ADD enrollments_id INT NULL;

ALTER TABLE attendance
    ADD is_present BIT(1) NULL;

ALTER TABLE attendance
    MODIFY enrollments_id INT NOT NULL;

ALTER TABLE attendance
    MODIFY is_present BIT (1) NOT NULL;

ALTER TABLE attendance
    ADD CONSTRAINT FK_ATTENDANCE_ON_ENROLLMENTS FOREIGN KEY (enrollments_id) REFERENCES enrollments (id);