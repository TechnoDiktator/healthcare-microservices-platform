
-- Default password for all seeded doctors: password123

-- Ensure the 'users' table exists
CREATE TABLE IF NOT EXISTS "users" (
                                       id UUID PRIMARY KEY,
                                        email VARCHAR(255) UNIQUE NOT NULL,
                                        password VARCHAR(255) NOT NULL,
                                        role VARCHAR(50) NOT NULL
    );

-- Insert the user if no existing user with the same id or email exists
INSERT INTO "users" (id, email, password, role)
SELECT '223e4567-e89b-12d3-a456-426614174006', 'testuser@test.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'ADMIN'
    WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '223e4567-e89b-12d3-a456-426614174006'
       OR email = 'testuser@test.com'
);

INSERT INTO users (id, email, password, role)
VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
     'rahul.sharma@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
     'priya.verma@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('cccccccc-cccc-cccc-cccc-cccccccccccc',
     'amit.singh@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('dddddddd-dddd-dddd-dddd-dddddddddddd',
     'neha.gupta@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
     'vikram.mehta@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('ffffffff-ffff-ffff-ffff-ffffffffffff',
     'anjali.patel@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('11111111-aaaa-bbbb-cccc-111111111111',
     'rohan.kapoor@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('22222222-bbbb-cccc-dddd-222222222222',
     'sneha.iyer@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('33333333-cccc-dddd-eeee-333333333333',
     'arjun.nair@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR'),

    ('44444444-dddd-eeee-ffff-444444444444',
     'kavya.reddy@example.com',
     '$2a$10$xuN2kIzofZKerhRzCBYnTOZxXy4nH5lpV.Bn89Gt36TbUTb/RjcwO',
     'DOCTOR')

    ON CONFLICT (email) DO NOTHING;