-- 1. ADD n_job COLUMNS
ALTER TABLE sectors
    ADD COLUMN IF NOT EXISTS n_job BIGINT NOT NULL DEFAULT 0;

ALTER TABLE positions
    ADD COLUMN IF NOT EXISTS n_job BIGINT NOT NULL DEFAULT 0;


-- 2. BACKFILL DATA
UPDATE sectors s
SET n_job = sub.cnt
FROM (
         SELECT sector_id, COUNT(*) AS cnt
         FROM jobs
         WHERE status = 'ACTIVE'
         GROUP BY sector_id
     ) sub
WHERE s.id = sub.sector_id;

UPDATE positions p
SET n_job = sub.cnt
FROM (
         SELECT position_id, COUNT(*) AS cnt
         FROM jobs
         WHERE status = 'ACTIVE'
         GROUP BY position_id
     ) sub
WHERE p.id = sub.position_id;


-- 3. FUNCTION UPDATE n_job
CREATE OR REPLACE FUNCTION update_sector_position_job_count()
    RETURNS TRIGGER AS $$
BEGIN
    -- INSERT
    IF TG_OP = 'INSERT' THEN
        IF NEW.status = 'ACTIVE' THEN
            IF NEW.sector_id IS NOT NULL THEN
                UPDATE sectors SET n_job = n_job + 1 WHERE id = NEW.sector_id;
            END IF;

            IF NEW.position_id IS NOT NULL THEN
                UPDATE positions SET n_job = n_job + 1 WHERE id = NEW.position_id;
            END IF;
        END IF;
        RETURN NEW;
    END IF;

    -- DELETE
    IF TG_OP = 'DELETE' THEN
        IF OLD.status = 'ACTIVE' THEN
            IF OLD.sector_id IS NOT NULL THEN
                UPDATE sectors SET n_job = n_job - 1 WHERE id = OLD.sector_id;
            END IF;

            IF OLD.position_id IS NOT NULL THEN
                UPDATE positions SET n_job = n_job - 1 WHERE id = OLD.position_id;
            END IF;
        END IF;
        RETURN OLD;
    END IF;

    -- UPDATE
    IF TG_OP = 'UPDATE' THEN
        -- status ACTIVE -> NOT ACTIVE
        IF OLD.status = 'ACTIVE' AND NEW.status <> 'ACTIVE' THEN
            IF OLD.sector_id IS NOT NULL THEN
                UPDATE sectors SET n_job = n_job - 1 WHERE id = OLD.sector_id;
            END IF;
            IF OLD.position_id IS NOT NULL THEN
                UPDATE positions SET n_job = n_job - 1 WHERE id = OLD.position_id;
            END IF;
        END IF;

        -- status NOT ACTIVE -> ACTIVE
        IF OLD.status <> 'ACTIVE' AND NEW.status = 'ACTIVE' THEN
            IF NEW.sector_id IS NOT NULL THEN
                UPDATE sectors SET n_job = n_job + 1 WHERE id = NEW.sector_id;
            END IF;
            IF NEW.position_id IS NOT NULL THEN
                UPDATE positions SET n_job = n_job + 1 WHERE id = NEW.position_id;
            END IF;
        END IF;

        -- sector change
        IF OLD.sector_id IS DISTINCT FROM NEW.sector_id AND NEW.status = 'ACTIVE' THEN
            IF OLD.sector_id IS NOT NULL THEN
                UPDATE sectors SET n_job = n_job - 1 WHERE id = OLD.sector_id;
            END IF;
            IF NEW.sector_id IS NOT NULL THEN
                UPDATE sectors SET n_job = n_job + 1 WHERE id = NEW.sector_id;
            END IF;
        END IF;

        -- position change
        IF OLD.position_id IS DISTINCT FROM NEW.position_id AND NEW.status = 'ACTIVE' THEN
            IF OLD.position_id IS NOT NULL THEN
                UPDATE positions SET n_job = n_job - 1 WHERE id = OLD.position_id;
            END IF;
            IF NEW.position_id IS NOT NULL THEN
                UPDATE positions SET n_job = n_job + 1 WHERE id = NEW.position_id;
            END IF;
        END IF;

        RETURN NEW;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- 4. CREATE TRIGGER
DROP TRIGGER IF EXISTS trg_update_sector_position_job_count ON jobs;

CREATE TRIGGER trg_update_sector_position_job_count
    AFTER INSERT OR UPDATE OR DELETE
    ON jobs
    FOR EACH ROW
EXECUTE FUNCTION update_sector_position_job_count();
