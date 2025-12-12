-- 1. Users
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- 2. Companies
CREATE INDEX IF NOT EXISTS idx_companies_created_by ON companies(created_by);
CREATE INDEX IF NOT EXISTS idx_companies_verified ON companies(is_verified);

-- 3. Jobs
CREATE INDEX IF NOT EXISTS idx_jobs_status_active ON jobs(status) WHERE status = 'active';
CREATE INDEX IF NOT EXISTS idx_jobs_company ON jobs(company_id);
CREATE INDEX IF NOT EXISTS idx_jobs_posted_at ON jobs(posted_at DESC);
CREATE INDEX IF NOT EXISTS idx_jobs_location ON jobs(location_id);
CREATE INDEX IF NOT EXISTS idx_jobs_job_type ON jobs(job_type);
CREATE INDEX IF NOT EXISTS idx_jobs_experience ON jobs(experience_level);
CREATE INDEX IF NOT EXISTS idx_jobs_salary_range ON jobs(salary_min, salary_max);
CREATE INDEX IF NOT EXISTS idx_jobs_views ON jobs(views_count DESC);
CREATE INDEX IF NOT EXISTS idx_jobs_sector ON jobs(sector_id);
CREATE INDEX IF NOT EXISTS idx_jobs_position ON jobs(position_id);
CREATE INDEX IF NOT EXISTS idx_jobs_remote ON jobs(is_remote) WHERE is_remote = TRUE;

-- Index for filter job (IT + Remote + Salary + Position)
CREATE INDEX IF NOT EXISTS idx_jobs_hot_filter ON jobs(sector_id, position_id, job_type, is_remote, salary_min) WHERE status = 'active';

-- 4. Job Sectors (many-to-many)
CREATE INDEX IF NOT EXISTS idx_job_sectors_sector ON job_sectors(sector_id);

-- 5. Applications
CREATE INDEX IF NOT EXISTS idx_applications_job ON applications(job_id);
CREATE INDEX IF NOT EXISTS idx_applications_candidate ON applications(candidate_id);
CREATE INDEX IF NOT EXISTS idx_applications_status ON applications(status);
CREATE INDEX IF NOT EXISTS idx_applications_applied_at ON applications(applied_at DESC);

-- 6. Saved Jobs & Followers
CREATE INDEX IF NOT EXISTS idx_saved_jobs_candidate ON saved_jobs(candidate_id);
CREATE INDEX IF NOT EXISTS idx_company_followers_candidate ON company_followers(candidate_id);

-- 7. Job Views
CREATE INDEX IF NOT EXISTS idx_job_views_job ON job_views(job_id);
CREATE INDEX IF NOT EXISTS idx_job_views_viewed_at ON job_views(viewed_at DESC);

-- 8. Skills & Candidate Skills
CREATE INDEX IF NOT EXISTS idx_candidate_skills_skill ON candidate_skills(skill_id);
CREATE INDEX IF NOT EXISTS idx_job_required_skills_skill ON job_required_skills(skill_id);

-- 9. Full-text search
ALTER TABLE jobs ADD COLUMN IF NOT EXISTS search_vector TSVECTOR;
UPDATE jobs SET search_vector = setweight(to_tsvector('english', coalesce(title, '')), 'A') || setweight(to_tsvector('english', coalesce(description, '')), 'B');
CREATE INDEX IF NOT EXISTS idx_jobs_fts ON jobs USING GIN(search_vector);

-- 10. Verification
CREATE INDEX IF NOT EXISTS idx_verification_otp ON verification_otps(otp);
CREATE INDEX IF NOT EXISTS idx_verification_user ON verification_otps(user_id);

-- 11. Refresh Token
CREATE INDEX IF NOT EXISTS idx_refresh_token ON refresh_tokens(token);
CREATE INDEX IF NOT EXISTS idx_refresh_user ON refresh_tokens(user_id);
CREATE INDEX IF NOT EXISTS idx_refresh_revoked ON refresh_tokens(revoked);