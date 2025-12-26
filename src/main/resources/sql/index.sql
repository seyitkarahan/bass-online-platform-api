-- Tekil (Unique) index
CREATE UNIQUE INDEX idx_users_email
    ON users (email);

-- Küme (Composite) index
CREATE INDEX idx_ratings_student_course
    ON ratings (student_id, course_id);

-- Başka bir örnek: enrollments tablosu
CREATE INDEX idx_enrollments_student_course
    ON enrollments (student_id, course_id);

-- courses tablosunda sık sorgulanan title ve category_id
CREATE INDEX idx_courses_title_category
    ON courses (title, category_id);
