CREATE TABLE attendance
(
    id INT AUTO_INCREMENT NOT NULL,
    CONSTRAINT pk_attendance PRIMARY KEY (id)
);

CREATE TABLE colleges
(
    id           INT AUTO_INCREMENT NOT NULL,
    college_name VARCHAR(255) NOT NULL,
    college_code VARCHAR(255) NULL,
    CONSTRAINT pk_colleges PRIMARY KEY (id)
);

CREATE TABLE courses
(
    id          INT AUTO_INCREMENT NOT NULL,
    course_name VARCHAR(255) NULL,
    course_code VARCHAR(255) NULL,
    college_id  INT NOT NULL,
    CONSTRAINT pk_courses PRIMARY KEY (id)
);

CREATE TABLE enrollments
(
    id                      INT AUTO_INCREMENT NOT NULL,
    added_on                datetime NULL,
    teaching_load_detail_id INT NOT NULL,
    student_id              INT NOT NULL,
    CONSTRAINT pk_enrollments PRIMARY KEY (id)
);

CREATE TABLE grade_category
(
    id            INT AUTO_INCREMENT NOT NULL,
    category_name VARCHAR(255) NULL,
    CONSTRAINT pk_grade_category PRIMARY KEY (id)
);

CREATE TABLE grading
(
    id                      INT AUTO_INCREMENT NOT NULL,
    `description`           VARCHAR(255) NULL,
    number_of_items         INT NULL,
    date_conducted          datetime NULL,
    category_id             INT NOT NULL,
    teaching_load_detail_id INT NOT NULL,
    term_id                 INT NOT NULL,
    CONSTRAINT pk_grading PRIMARY KEY (id)
);

CREATE TABLE grading_composition
(
    id                      INT AUTO_INCREMENT NOT NULL,
    percentage              FLOAT NULL,
    category_id             INT NOT NULL,
    teaching_load_detail_id INT NOT NULL,
    CONSTRAINT pk_grading_composition PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id        INT AUTO_INCREMENT NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE sem
(
    id       INT AUTO_INCREMENT NOT NULL,
    sem_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_sem PRIMARY KEY (id)
);

CREATE TABLE students
(
    id         INT AUTO_INCREMENT NOT NULL,
    student_id INT NULL,
    user_id    INT NOT NULL,
    course_id  INT NOT NULL,
    CONSTRAINT pk_students PRIMARY KEY (id)
);

CREATE TABLE subjects
(
    id           INT AUTO_INCREMENT NOT NULL,
    subject_name VARCHAR(255) NULL,
    subject_desc VARCHAR(255) NULL,
    units        INT NULL,
    type         INT NULL,
    course_id    INT NOT NULL,
    CONSTRAINT pk_subjects PRIMARY KEY (id)
);

CREATE TABLE teachers
(
    id         INT AUTO_INCREMENT NOT NULL,
    teacher_id VARCHAR(255) NOT NULL,
    user_id    INT          NOT NULL,
    CONSTRAINT pk_teachers PRIMARY KEY (id)
);

CREATE TABLE teaching_load
(
    id            INT AUTO_INCREMENT NOT NULL,
    added_on      datetime NULL,
    academic_year VARCHAR(255) NULL,
    status        BIT(1) NULL,
    sem_id        INT NOT NULL,
    teacher_id    INT NOT NULL,
    CONSTRAINT pk_teaching_load PRIMARY KEY (id)
);

CREATE TABLE teaching_load_details
(
    id               INT AUTO_INCREMENT NOT NULL,
    section          VARCHAR(255) NULL,
    schedule         VARCHAR(255) NULL,
    hash_key         VARCHAR(255) NULL,
    teaching_load_id INT NOT NULL,
    subject_id       INT NOT NULL,
    CONSTRAINT pk_teaching_load_details PRIMARY KEY (id)
);

CREATE TABLE term
(
    id        INT AUTO_INCREMENT NOT NULL,
    term_type VARCHAR(255) NULL,
    CONSTRAINT pk_term PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       INT AUTO_INCREMENT NOT NULL,
    fname    VARCHAR(255) NOT NULL,
    mname    VARCHAR(255) NULL,
    lname    VARCHAR(255) NOT NULL,
    username VARCHAR(255) NULL,
    email    VARCHAR(255) NOT NULL,
    otp      VARCHAR(255) NULL,
    gender   BIT(1)       NOT NULL,
    password VARCHAR(255) NULL,
    dob      date         NOT NULL,
    role_id  INT          NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE grading_composition
    ADD CONSTRAINT uc_de76c064e935ba49b400b1604 UNIQUE (teaching_load_detail_id, category_id);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_role_name UNIQUE (role_name);

ALTER TABLE teachers
    ADD CONSTRAINT uc_teachers_teacher UNIQUE (teacher_id);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_otp UNIQUE (otp);

ALTER TABLE user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE courses
    ADD CONSTRAINT FK_COURSES_ON_COLLEGE FOREIGN KEY (college_id) REFERENCES colleges (id);

ALTER TABLE enrollments
    ADD CONSTRAINT FK_ENROLLMENTS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE enrollments
    ADD CONSTRAINT FK_ENROLLMENTS_ON_TEACHING_LOAD_DETAIL FOREIGN KEY (teaching_load_detail_id) REFERENCES teaching_load_details (id);

ALTER TABLE grading_composition
    ADD CONSTRAINT FK_GRADING_COMPOSITION_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES grade_category (id);

ALTER TABLE grading_composition
    ADD CONSTRAINT FK_GRADING_COMPOSITION_ON_TEACHING_LOAD_DETAIL FOREIGN KEY (teaching_load_detail_id) REFERENCES teaching_load_details (id);

ALTER TABLE grading
    ADD CONSTRAINT FK_GRADING_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES grade_category (id);

ALTER TABLE grading
    ADD CONSTRAINT FK_GRADING_ON_TEACHING_LOAD_DETAIL FOREIGN KEY (teaching_load_detail_id) REFERENCES teaching_load_details (id);

ALTER TABLE grading
    ADD CONSTRAINT FK_GRADING_ON_TERM FOREIGN KEY (term_id) REFERENCES term (id);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE subjects
    ADD CONSTRAINT FK_SUBJECTS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE teachers
    ADD CONSTRAINT FK_TEACHERS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE teaching_load_details
    ADD CONSTRAINT FK_TEACHING_LOAD_DETAILS_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subjects (id);

ALTER TABLE teaching_load_details
    ADD CONSTRAINT FK_TEACHING_LOAD_DETAILS_ON_TEACHING_LOAD FOREIGN KEY (teaching_load_id) REFERENCES teaching_load (id);

ALTER TABLE teaching_load
    ADD CONSTRAINT FK_TEACHING_LOAD_ON_SEM FOREIGN KEY (sem_id) REFERENCES sem (id);

ALTER TABLE teaching_load
    ADD CONSTRAINT FK_TEACHING_LOAD_ON_TEACHER FOREIGN KEY (teacher_id) REFERENCES teachers (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);