CREATE OR REPLACE FUNCTION set_job_expires_at()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.expires_at := NEW.posted_at + (NEW.display_days * INTERVAL '1 day');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql IMMUTABLE;

CREATE TRIGGER trg_set_job_expires_at
    BEFORE INSERT ON jobs
    FOR EACH ROW
EXECUTE FUNCTION set_job_expires_at();

