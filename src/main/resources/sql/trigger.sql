-- insert trigger
CREATE OR REPLACE FUNCTION trg_rating_insert()
RETURNS TRIGGER AS $$
BEGIN
INSERT INTO notifications (user_id, message, notify_at, is_sent)
VALUES (
           NEW.student_id,
           'You rated the course with ' || NEW.rating || ' stars!',
           NOW(),
           FALSE
       );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER rating_insert_trigger
    AFTER INSERT ON ratings
    FOR EACH ROW
    EXECUTE FUNCTION trg_rating_insert();

-- update trigger
CREATE TABLE rating_audit (
                              id BIGSERIAL PRIMARY KEY,
                              rating_id BIGINT,
                              old_rating INT,
                              new_rating INT,
                              updated_at TIMESTAMP DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION trg_rating_update()
RETURNS TRIGGER AS $$
BEGIN
INSERT INTO rating_audit (rating_id, old_rating, new_rating)
VALUES (OLD.id, OLD.rating, NEW.rating);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER rating_update_trigger
    AFTER UPDATE ON ratings
    FOR EACH ROW
    EXECUTE FUNCTION trg_rating_update();

-- delete trigger
CREATE OR REPLACE FUNCTION trg_rating_delete()
RETURNS TRIGGER AS $$
BEGIN
INSERT INTO rating_audit (rating_id, old_rating, new_rating)
VALUES (OLD.id, OLD.rating, NULL);

INSERT INTO notifications (user_id, message, notify_at, is_sent)
VALUES (
           OLD.student_id,
           'Your rating for course ' || OLD.course_id || ' was removed.',
           NOW(),
           FALSE
       );

RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER rating_delete_trigger
    AFTER DELETE ON ratings
    FOR EACH ROW
    EXECUTE FUNCTION trg_rating_delete();
