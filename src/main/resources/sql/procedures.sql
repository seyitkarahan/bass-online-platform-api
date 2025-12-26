-- Enrollment oluştur + Payment kaydet + Shopping Cart temizle.
CREATE OR REPLACE FUNCTION sp_checkout(
    p_student_id BIGINT,
    p_course_id BIGINT,
    p_amount NUMERIC,
    p_payment_method VARCHAR
)
RETURNS VOID AS $$
DECLARE
v_enrollment_id BIGINT;
    v_cart_id BIGINT;
BEGIN
    -- Enrollment oluştur
INSERT INTO enrollments(student_id, course_id, enrollment_date, active)
VALUES(p_student_id, p_course_id, NOW(), TRUE)
    RETURNING id INTO v_enrollment_id;

-- Payment oluştur
INSERT INTO payments(enrollment_id, amount, payment_method, payment_status, payment_date)
VALUES(v_enrollment_id, p_amount, p_payment_method, 'SUCCESS', NOW());

-- Öğrencinin sepetteki ilgili ürünü sil
SELECT id INTO v_cart_id FROM shopping_cart WHERE student_id = p_student_id;
IF v_cart_id IS NOT NULL THEN
DELETE FROM shopping_cart_items WHERE cart_id = v_cart_id AND course_id = p_course_id;
END IF;
END;
$$ LANGUAGE plpgsql;

-- Kullanım (JDBC üzerinden):
SELECT sp_checkout(1, 101, 199.99, 'CREDIT_CARD');

-- Öğrenci quiz submit ettiğinde attempt + student_answers + score update işlemini tek SP ile yapmak.
CREATE OR REPLACE FUNCTION sp_submit_quiz(
    p_student_id BIGINT,
    p_quiz_id BIGINT,
    p_answers JSON -- [{"questionId":1,"answer":"A"}, {"questionId":2,"answer":"B"}]
)
RETURNS INT AS $$
DECLARE
v_attempt_id BIGINT;
    v_correct_count INT := 0;
    v_total_questions INT;
    v_score INT;
    v_answer_record JSON;
BEGIN
    -- Toplam soru sayısı
SELECT COUNT(*) INTO v_total_questions FROM questions WHERE quiz_id = p_quiz_id;

-- Quiz Attempt oluştur
INSERT INTO quiz_attempt(student_id, quiz_id, attempt_date, completed)
VALUES(p_student_id, p_quiz_id, NOW(), FALSE)
    RETURNING id INTO v_attempt_id;

-- JSON üzerinden cevapları kaydet
FOR v_answer_record IN SELECT * FROM json_array_elements(p_answers)
                                         LOOP
DECLARE
v_question_id BIGINT := (v_answer_record->>'questionId')::BIGINT;
            v_answer TEXT := v_answer_record->>'answer';
            v_correct_answer TEXT;
            v_is_correct BOOLEAN;
BEGIN
SELECT answer INTO v_correct_answer FROM questions WHERE id = v_question_id;
v_is_correct := (v_answer = v_correct_answer);

            IF v_is_correct THEN
                v_correct_count := v_correct_count + 1;
END IF;

INSERT INTO student_answers(attempt_id, question_id, answer, is_correct)
VALUES(v_attempt_id, v_question_id, v_answer, v_is_correct);
END;
END LOOP;

    -- Attempt tamamla ve score güncelle
    v_score := (v_correct_count * 100) / v_total_questions;
UPDATE quiz_attempt
SET completed = TRUE, correct_answers = v_correct_count, score = v_score
WHERE id = v_attempt_id;

RETURN v_score;
END;
$$ LANGUAGE plpgsql;

-- Kullanım (JDBC üzerinden):
SELECT sp_submit_quiz(
               1,
               101,
               '[{"questionId":1,"answer":"A"}, {"questionId":2,"answer":"C"}]'
       );

