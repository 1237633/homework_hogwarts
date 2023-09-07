-- liquibase formatted sql
-- changeset vasyan:1
CREATE INDEX student_name ON student(name);

-- changeset vasyan:2
CREATE INDEX faculty_color_and_name ON faculty(name, color);