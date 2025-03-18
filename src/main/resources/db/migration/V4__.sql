ALTER TABLE grading_detail
    ADD status VARCHAR(255) NULL;

ALTER TABLE grading_detail
    MODIFY status VARCHAR (255) NOT NULL;

ALTER TABLE grading_detail
    MODIFY score INT NULL;