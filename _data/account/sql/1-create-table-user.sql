CREATE TABLE t_user (
                        user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        username VARCHAR(30) NOT NULL,
                        first_name VARCHAR(50),
                        last_name VARCHAR(50),
                        date_birth DATE,
                        email VARCHAR(255),
                        phone_number VARCHAR(20),
                        is_active BOOLEAN
);
