INSERT INTO role VALUES(1, 'ADMIN') ON CONFLICT (id) DO NOTHING;
INSERT INTO role VALUES(2, 'EMPLOYEE') ON CONFLICT (id) DO NOTHING;
INSERT INTO role VALUES(3, 'CUSTOMER') ON CONFLICT (id) DO NOTHING;

INSERT INTO services VALUES(1, 480, false, 'Day Off', 0.0, 'Q') ON CONFLICT (id) DO NOTHING;

INSERT INTO users VALUES(2, 0, 'Day Off', 'Day', 'Day Off', 'Off') ON CONFLICT (id) DO NOTHING;