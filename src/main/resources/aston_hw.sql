CREATE TABLE IF NOT EXISTS students
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    age        INT NOT NULL,
    lecture_id INT,
        FOREIGN KEY (lecture_id) REFERENCES lectures (id)
);

CREATE TABLE IF NOT EXISTS teachers
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    age        INT NOT NULL,
    lecture_id INT,
    FOREIGN KEY (lecture_id) REFERENCES lectures (id)
);

CREATE TABLE IF NOT EXISTS lectures
(
    id         SERIAL PRIMARY KEY,
    topic       VARCHAR(255) NOT NULL
);