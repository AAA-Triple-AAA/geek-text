--Add a 'role' column to 'user' table with a default value of 'USER'
ALTER TABLE "user" ADD COLUMN role VARCHAR(50) DEFAULT 'USER' NOT NULL;

--Add a 'session_api_key' column to store API keys
ALTER TABLE "user" ADD COLUMN session_api_key VARCHAR(255) DEFAULT md5(random()::text) NOT NULL;

--Generate random API keys for existing users
UPDATE "user" SET session_api_key = md5(random()::text) WHERE session_api_key IS NULL;

--Update two specific users to 'ADMIN' role
UPDATE "user"
SET role = 'ADMIN'
WHERE id IN (4, 6);

--Ensure new users get an API key automatically
CREATE OR REPLACE FUNCTION generate_api_key()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.session_api_key = md5(random()::text);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--Attach trigger to user table to generate API key on INSERT
CREATE TRIGGER set_api_key
    BEFORE INSERT ON "user"
    FOR EACH ROW
EXECUTE FUNCTION generate_api_key();

-- Update each publisher discount value to a percentage
UPDATE publisher
SET discount = CASE
                   WHEN discount > 1.0 THEN discount / 100.0
                   WHEN discount < 0.0 THEN 0.0
                   ELSE discount
    END;

-- Add a constraint to ensure a percentage value
ALTER TABLE publisher
    ADD CONSTRAINT check_discount_range
        CHECK (discount >= 0.0 AND discount <= 1.0);
