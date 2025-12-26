CREATE OR REPLACE FUNCTION fn_course_report()
RETURNS TABLE (
    course_id BIGINT,
    course_title VARCHAR,
    instructor_name VARCHAR,
    total_students INT,
    avg_rating NUMERIC,
    avg_quiz_score NUMERIC,
    total_enrollments INT
) AS $$
BEGIN
RETURN QUERY
SELECT
    c.id AS course_id,
    c.title AS course_title,
    u.full_name AS instructor_name,
    COUNT(DISTINCT e.student_id) AS total_students,
    ROUND(AVG(r.rating), 2) AS avg_rating,
    ROUND(AVG(qa.score), 2) AS avg_quiz_score,
    COUNT(e.id) AS total_enrollments
FROM courses c
         LEFT JOIN instructors i ON c.instructor_id = i.id
         LEFT JOIN users u ON i.user_id = u.id
         LEFT JOIN enrollments e ON e.course_id = c.id
         LEFT JOIN ratings r ON r.course_id = c.id
         LEFT JOIN quizzes q ON q.course_id = c.id
         LEFT JOIN quiz_attempt qa ON qa.quiz_id = q.id
GROUP BY c.id, c.title, u.full_name
ORDER BY total_students DESC;
END;
$$ LANGUAGE plpgsql;
