ALTER TABLE grading_detail
DROP
COLUMN status;

ALTER TABLE grading_composition
DROP
COLUMN percentage;

ALTER TABLE grading_composition
    ADD percentage DOUBLE NULL;