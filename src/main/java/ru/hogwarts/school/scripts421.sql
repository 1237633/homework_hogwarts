ALTER TABLE student ADD CONSTRAINT age_higher_than_twelve CHECK (age >= 12);
ALTER TABLE student ADD PRIMARY KEY (name);
ALTER TABLE faculty ADD CONSTRAINT color_name_unique UNIQUE (color, name);
ALTER TABLE student ALTER COLUMN age SET DEFAULT (20);
