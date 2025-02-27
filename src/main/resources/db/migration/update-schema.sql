ALTER TABLE teaching_load_details
    ADD `key` VARCHAR(255) NULL;

ALTER TABLE grading_composition
DROP
COLUMN percentage;

ALTER TABLE grading_composition
    ADD percentage FLOAT NULL;