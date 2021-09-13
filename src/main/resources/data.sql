CREATE TYPE gender_enum as ENUM('MALE', 'FEMALE');

CREATE TABLE IF NOT EXISTS employee (
    employee_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    department_id BIGINT NOT NULL,
    job_title VARCHAR(256) NOT NULL,
    gender gender_enum NOT NULL,
    date_of_birth DATE NOT NULL
);

INSERT INTO employee (first_name, last_name, department_id, job_title, gender, date_of_birth)
VALUES
    ('Olga', 'Reshet', 1, 'HR manager', 'FEMALE', '1990-04-20'),
    ('Sergey', 'Petrov', 2, 'JavaScript developer', 'MALE', '1988-11-29'),
    ('Ivan', 'Uglov', 2, 'Python developer', 'MALE', '1992-03-14');