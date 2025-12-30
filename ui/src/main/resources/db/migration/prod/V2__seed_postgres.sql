INSERT INTO gender (created_at, name, description)
VALUES (CURRENT_TIMESTAMP, 'Male', 'Männlich')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO gender (created_at, name, description)
VALUES (CURRENT_TIMESTAMP, 'Female', 'Weiblich')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO mandant_settings (course_weeks_per_schedule, max_participants_per_course, enforce_quantity_settings)
SELECT 8, 12, TRUE
    WHERE NOT EXISTS (SELECT 1 FROM mandant_settings);