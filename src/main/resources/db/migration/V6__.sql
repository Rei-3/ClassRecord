ALTER TABLE attendance
DROP
FOREIGN KEY FKgl9nfnrk7w0k865rq9dys75qy;

CREATE TABLE enrollment_white_listed_students
(
    id                        INT AUTO_INCREMENT NOT NULL,
    student_id                INT NULL,
    enrolled                  BIT(1) NOT NULL,
    teaching_load_deatails_id INT NULL,
    CONSTRAINT pk_enrollment_white_listed_students PRIMARY KEY (id)
);

CREATE TABLE grade_base
(
    id                 INT AUTO_INCREMENT NOT NULL,
    base_grade         INT NULL,
    base_grade_percent INT NULL,
    CONSTRAINT pk_grade_base PRIMARY KEY (id)
);

ALTER TABLE teaching_load_details
    ADD grade_base_id INT NULL;

ALTER TABLE grading_detail
    ADD recorded_on datetime NULL;

ALTER TABLE teaching_load_details
    ADD CONSTRAINT uc_teaching_load_details_grade_base UNIQUE (grade_base_id);

ALTER TABLE enrollment_white_listed_students
    ADD CONSTRAINT FK_ENROLLMENT_WHITE_LISTED_STUDENTS_ON_TEACHING_LOAD_DEATAILS FOREIGN KEY (teaching_load_deatails_id) REFERENCES teaching_load_details (id);

ALTER TABLE teaching_load_details
    ADD CONSTRAINT FK_TEACHING_LOAD_DETAILS_ON_GRADE_BASE FOREIGN KEY (grade_base_id) REFERENCES grade_base (id);

DROP TABLE attendance;

ALTER TABLE grading_detail
DROP
COLUMN status;

ALTER TABLE teaching_load_details
    MODIFY hash_key LONGTEXT;

ALTER TABLE grading_composition
DROP
COLUMN percentage;

ALTER TABLE grading_composition
    ADD percentage DOUBLE NULL;