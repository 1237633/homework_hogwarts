CREATE TABLE driver(
    name varchar PRIMARY KEY,
    age smallint CHECK (age>0),
    license boolean DEFAULT false,
    carModel varchar REFERENCES car(model)
);

CREATE TABLE car(
    make varchar NOT NULL,
    model varchar PRIMARY KEY,
    cost int CHECK ( cost > 0 )
);

