CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE sleep_logs (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    sleep_date DATE NOT NULL UNIQUE,
    bed_time TIME NOT NULL,
    wake_time TIME NOT NULL,
    total_minutes_in_bed INT NOT NULL,
    mood VARCHAR(10) NOT NULL CHECK (mood IN ('BAD', 'OK', 'GOOD')),
    created_at TIMESTAMP DEFAULT now()
);
