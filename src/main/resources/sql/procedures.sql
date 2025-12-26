CREATE OR REPLACE PROCEDURE sp_checkout(
    IN p_student_id BIGINT,
    IN p_course_id  BIGINT,
    IN p_amount     NUMERIC,
    IN p_payment_method VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
v_enrollment_id BIGINT;
    v_cart_id BIGINT;
BEGIN
    -- Öğrenci kontrolü
    IF NOT EXISTS (SELECT 1 FROM students WHERE id = p_student_id) THEN
        RAISE EXCEPTION 'Student not found: %', p_student_id;
END IF;

    -- Kurs kontrolü
    IF NOT EXISTS (SELECT 1 FROM courses WHERE id = p_course_id) THEN
        RAISE EXCEPTION 'Course not found: %', p_course_id;
END IF;

    -- Daha önce enroll olmuş mu?
    IF EXISTS (
        SELECT 1 FROM enrollments
        WHERE student_id = p_student_id
          AND course_id = p_course_id
    ) THEN
        RAISE EXCEPTION 'Student already enrolled in this course';
END IF;

    -- Enrollment oluştur
INSERT INTO enrollments(student_id, course_id, enrollment_date, active)
VALUES (p_student_id, p_course_id, NOW(), TRUE)
    RETURNING id INTO v_enrollment_id;

-- Payment oluştur
INSERT INTO payments(
    enrollment_id,
    amount,
    payment_method,
    payment_status,
    payment_date
)
VALUES (
           v_enrollment_id,
           p_amount,
           p_payment_method,
           'SUCCESS',
           NOW()
       );

-- Shopping cart temizle
SELECT id INTO v_cart_id
FROM shopping_cart
WHERE student_id = p_student_id;

IF v_cart_id IS NOT NULL THEN
DELETE FROM shopping_cart_items
WHERE cart_id = v_cart_id
  AND course_id = p_course_id;
END IF;

END;
$$;


-- -------------------------------------------