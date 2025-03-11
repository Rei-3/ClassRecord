CREATE TABLE grading_detail
(
    id          INT AUTO_INCREMENT NOT NULL,
    score       INT NOT NULL,
    enrollments INT NULL,
    grading     INT NULL,
    CONSTRAINT pk_grading_detail PRIMARY KEY (id)
);

ALTER TABLE grading_detail
    ADD CONSTRAINT FK_GRADING_DETAIL_ON_ENROLLMENTS FOREIGN KEY (enrollments) REFERENCES enrollments (id);

ALTER TABLE grading_detail
    ADD CONSTRAINT FK_GRADING_DETAIL_ON_GRADING FOREIGN KEY (grading) REFERENCES grading (id);