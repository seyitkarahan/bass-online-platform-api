-- CREATE TABLE users
-- (
--     id         BIGSERIAL PRIMARY KEY,
--     name       VARCHAR(200)        NOT NULL,
--     email      VARCHAR(200) UNIQUE NOT NULL,
--     password   VARCHAR(255)        NOT NULL,
--     role       VARCHAR(50)         NOT NULL
--         CHECK (role IN ('ADMIN', 'INSTRUCTOR', 'STUDENT')),
--     created_at TIMESTAMP DEFAULT NOW()
-- );
--
-- CREATE TABLE students
-- (
--     id          BIGSERIAL PRIMARY KEY,
--     user_id     BIGINT NOT NULL UNIQUE
--         REFERENCES users (id) ON DELETE CASCADE,
--     grade_level VARCHAR(50)
-- );
--
-- CREATE TABLE instructors
-- (
--     id        BIGSERIAL PRIMARY KEY,
--     user_id   BIGINT NOT NULL UNIQUE
--         REFERENCES users (id) ON DELETE CASCADE,
--     bio       TEXT,
--     expertise VARCHAR(255)
-- );
--
-- CREATE TABLE categories
-- (
--     id          BIGSERIAL PRIMARY KEY,
--     name        VARCHAR(150) NOT NULL,
--     description TEXT
-- );
--
-- CREATE TABLE courses
-- (
--     id            BIGSERIAL PRIMARY KEY,
--     instructor_id BIGINT         NOT NULL
--         REFERENCES instructors (id) ON DELETE CASCADE,
--     category_id   BIGINT         NOT NULL
--         REFERENCES categories (id),
--     title         VARCHAR(255)   NOT NULL,
--     description   TEXT,
--     price         DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
--     created_at    TIMESTAMP DEFAULT NOW()
-- );
--
-- CREATE TABLE lessons
-- (
--     id        BIGSERIAL PRIMARY KEY,
--     course_id BIGINT       NOT NULL
--         REFERENCES courses (id) ON DELETE CASCADE,
--     title     VARCHAR(255) NOT NULL,
--     content   TEXT
-- );
--
-- CREATE TABLE quizzes
-- (
--     id        BIGSERIAL PRIMARY KEY,
--     course_id BIGINT       NOT NULL
--         REFERENCES courses (id) ON DELETE CASCADE,
--     title     VARCHAR(200) NOT NULL
-- );
--
-- CREATE TABLE questions
-- (
--     id            BIGSERIAL PRIMARY KEY,
--     quiz_id       BIGINT NOT NULL
--         REFERENCES quizzes (id) ON DELETE CASCADE,
--     question_text TEXT   NOT NULL,
--     answer        TEXT   NOT NULL
-- );
--
-- CREATE TABLE enrollments
-- (
--     id              BIGSERIAL PRIMARY KEY,
--     student_id      BIGINT NOT NULL
--         REFERENCES students (id) ON DELETE CASCADE,
--     course_id       BIGINT NOT NULL
--         REFERENCES courses (id) ON DELETE CASCADE,
--     enrollment_date TIMESTAMP DEFAULT NOW(),
--     active          BOOLEAN   DEFAULT TRUE,
--     UNIQUE (student_id, course_id)
-- );
--
-- CREATE TABLE payments
-- (
--     id             BIGSERIAL PRIMARY KEY,
--     enrollment_id  BIGINT         NOT NULL UNIQUE
--         REFERENCES enrollments (id) ON DELETE CASCADE,
--     amount         DECIMAL(10, 2) NOT NULL,
--     payment_method VARCHAR(100),
--     payment_status VARCHAR(50),
--     payment_date   TIMESTAMP DEFAULT NOW()
-- );
--
-- CREATE TABLE ratings
-- (
--     id         BIGSERIAL PRIMARY KEY,
--     student_id BIGINT NOT NULL
--         REFERENCES students (id) ON DELETE CASCADE,
--     course_id  BIGINT NOT NULL
--         REFERENCES courses (id) ON DELETE CASCADE,
--     rating     INT CHECK (rating BETWEEN 1 AND 5),
--     comment    TEXT,
--     created_at TIMESTAMP DEFAULT NOW(),
--     UNIQUE (student_id, course_id)
-- );
--
-- CREATE TABLE comments
-- (
--     id         BIGSERIAL PRIMARY KEY,
--     user_id    BIGINT NOT NULL
--         REFERENCES users (id) ON DELETE CASCADE,
--     course_id  BIGINT NOT NULL
--         REFERENCES courses (id) ON DELETE CASCADE,
--     content    TEXT   NOT NULL,
--     created_at TIMESTAMP DEFAULT NOW()
-- );
--
-- CREATE TABLE course_likes
-- (
--     id         BIGSERIAL PRIMARY KEY,
--     user_id    BIGINT NOT NULL
--         REFERENCES users (id) ON DELETE CASCADE,
--     course_id  BIGINT NOT NULL
--         REFERENCES courses (id) ON DELETE CASCADE,
--     created_at TIMESTAMP DEFAULT NOW(),
--     UNIQUE (user_id, course_id)
-- );
--
-- CREATE TABLE notifications
-- (
--     id        BIGSERIAL PRIMARY KEY,
--     user_id   BIGINT NOT NULL
--         REFERENCES users (id) ON DELETE CASCADE,
--     message   TEXT   NOT NULL,
--     notify_at TIMESTAMP,
--     is_sent   BOOLEAN DEFAULT FALSE
-- );
--
-- CREATE TABLE shopping_cart
-- (
--     id         BIGSERIAL PRIMARY KEY,
--     student_id BIGINT NOT NULL UNIQUE
--         REFERENCES students (id) ON DELETE CASCADE
-- );
--
-- CREATE TABLE shopping_cart_items
-- (
--     id        BIGSERIAL PRIMARY KEY,
--     cart_id   BIGINT NOT NULL
--         REFERENCES shopping_cart (id) ON DELETE CASCADE,
--     course_id BIGINT NOT NULL
--         REFERENCES courses (id) ON DELETE CASCADE,
--     UNIQUE (cart_id, course_id)
-- );

-- courses
SELECT
    c.id,
    c.title,
    c.price,
    u.name AS instructor_name,
    cat.name AS category_name
FROM courses c
         JOIN instructors i ON c.instructor_id = i.id
         JOIN users u ON i.user_id = u.id
         JOIN categories cat ON c.category_id = cat.id
ORDER BY c.created_at DESC;

-- enrollments
SELECT
    e.id AS enrollment_id,
    c.title AS course_title,
    e.active,
    p.payment_status
FROM enrollments e
         JOIN courses c ON e.course_id = c.id
         LEFT JOIN payments p ON p.enrollment_id = e.id
WHERE e.student_id = ?;

-- ratings
SELECT
    r.rating,
    r.comment,
    u.name AS student_name
FROM ratings r
         JOIN students s ON r.student_id = s.id
         JOIN users u ON s.user_id = u.id
WHERE r.course_id = ?;

-- comments
SELECT
    u.name AS user_name,
    c.content,
    c.created_at
FROM comments c
         JOIN users u ON c.user_id = u.id
WHERE c.course_id = ?
ORDER BY c.created_at DESC;

-- notifications
SELECT
    id,
    message,
    is_sent,
    notify_at
FROM notifications
WHERE user_id = ?
ORDER BY notify_at DESC;

