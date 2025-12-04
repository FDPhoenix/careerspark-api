CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE EXTENSION IF NOT EXISTS "citext";

-- 1. Users
CREATE TABLE IF NOT EXISTS users (
    id    					UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email 					CITEXT UNIQUE NOT NULL,
    password_hash	 		TEXT NOT NULL,
    role 					TEXT NOT NULL CHECK (role in ('candidate', 'recruiter', 'admin')),
    full_name 				TEXT,
    phone 					TEXT,
    avatar_url 				TEXT,
    is_verified 			BOOLEAN NOT NULL DEFAULT FALSE,
    is_active 				BOOLEAN NOT NULL DEFAULT TRUE,
    created_at 				TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at 				TIMESTAMPTZ NOT NULL DEFAULT now()
    );

-- 2. Candidate Profiles
CREATE TABLE IF NOT EXISTS candidate_profiles (
    user_id         		UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    title           		TEXT,                        								-- VD: "Senior Fullstack Engineer"
    summary         		TEXT,
    location_id        		UUID,                        								-- Tỉnh/Thành phố
    salary_min      		INTEGER,                     								-- VND (triệu)
    salary_max      		INTEGER,
    years_of_exp    		INTEGER DEFAULT 0,
    is_open_to_work 		BOOLEAN DEFAULT TRUE,
    resume_url      		TEXT,
    created_at      		TIMESTAMPTZ DEFAULT NOW(),
    updated_at      		TIMESTAMPTZ DEFAULT NOW()
    );

-- 3. Companies
CREATE TABLE IF NOT EXISTS companies (
    id              		UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name            		TEXT NOT NULL,
    website 				TEXT,
    description 			TEXT,
    logo_url 				TEXT,
    founded_year    		INTEGER,
    size            		TEXT CHECK (size IN ('1-10', '11-50', '51-200', '201-500', '501-1000', '1000+')),
    location_id 			UUID,
    created_by      		UUID REFERENCES users(id),   								-- người tạo công ty (admin công ty)
    is_verified     		BOOLEAN DEFAULT FALSE,
    created_at      		TIMESTAMPTZ DEFAULT NOW(),
    updated_at      		TIMESTAMPTZ DEFAULT NOW()
    );

-- 4. Location
CREATE TABLE IF NOT EXISTS locations (
    id              		UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    country 				TEXT,
    region 					TEXT,
    city 					TEXT,
    address 				TEXT,
    lat 					NUMERIC(9,6),
    lng 					NUMERIC(9,6)
    );

-- 5. Sectors (Chỉ những ngành ví dụ như là IT, Marketing, )
CREATE TABLE IF NOT EXISTS sectors (
    id 						UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        			TEXT NOT NULL,
    slug        			TEXT UNIQUE NOT NULL,
    icon_url				TEXT,
    description 			TEXT,
    sort_order  			INTEGER DEFAULT 0,
    is_available		   	BOOLEAN DEFAULT TRUE
    );

-- 6. Position (Con của sector, chỉ những vị trí cụ thể trong ngành)
CREATE TABLE IF NOT EXISTS positions (
    id          			UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        			TEXT NOT NULL,
    slug        			TEXT UNIQUE NOT NULL,
    sector_id 				UUID REFERENCES sectors(id),
    sort_order  			INTEGER DEFAULT 0,
    is_available   			BOOLEAN DEFAULT TRUE
    );

-- 7. Jobs
CREATE TABLE IF NOT EXISTS jobs (
    id                  	UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_id          	UUID NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    sector_id 				UUID REFERENCES sectors(id),
    position_id 			UUID REFERENCES positions(id),
    title               	TEXT NOT NULL,
    slug                	TEXT NOT NULL,
    description         	TEXT NOT NULL,
    requirements        	TEXT,
    benefits            	TEXT,
    location_id				UUID REFERENCES locations(id),
    job_type            	TEXT NOT NULL CHECK (job_type IN ('full-time', 'part-time', 'contract', 'internship', 'remote', 'hybrid')),
    is_remote 				BOOLEAN DEFAULT FALSE,
    experience_level    	TEXT CHECK (experience_level IN ('intern', 'junior', 'middle', 'senior', 'leader', 'manager', 'executive')),
    years_of_exp_min      	INTEGER,
    years_of_exp_max      	INTEGER,
    salary_min          	INTEGER,                 									-- VND triệu
    salary_max          	INTEGER,
    salary_currency     	TEXT DEFAULT 'VND',
    is_salary_visible   	BOOLEAN DEFAULT TRUE,
    available_until			DATE,
    status              	TEXT DEFAULT 'active' CHECK (status IN ('draft', 'active', 'paused', 'closed', 'expired')),
    views_count         	BIGINT DEFAULT 0,
    posted_by           	UUID REFERENCES users(id),
    posted_at           	TIMESTAMPTZ DEFAULT NOW(),
    updated_at          	TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(company_id, slug)
    );

-- 8. Job Sector (Bảng phụ tạo ra do mối quan hệ nhiều nhiều giữa bảng jobs và sector)
CREATE TABLE IF NOT EXISTS job_sectors (
    job_id      			UUID REFERENCES jobs(id) ON DELETE CASCADE,
    sector_id				UUID REFERENCES sectors(id) ON DELETE CASCADE,
    PRIMARY KEY (job_id, sector_id)
    );

-- 9. Skills
CREATE TABLE IF NOT EXISTS skills (
    id          			UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        			TEXT UNIQUE NOT NULL
    );

-- 10. Candidate Skills
CREATE TABLE IF NOT EXISTS candidate_skills (
    candidate_id			UUID REFERENCES users(id) ON DELETE CASCADE,
    skill_id    			UUID REFERENCES skills(id) ON DELETE CASCADE,
    level 					TEXT CHECK (level IN ('beginner','intermediate','advanced','expert')),
    PRIMARY KEY (candidate_id, skill_id)
    );

-- 11. Job Required Skills
CREATE TABLE IF NOT EXISTS job_required_skills (
    job_id      			UUID REFERENCES jobs(id) ON DELETE CASCADE,
    skill_id    			UUID REFERENCES skills(id) ON DELETE CASCADE,
    PRIMARY KEY (job_id, skill_id)
    );

-- 12. Work Experiences
CREATE TABLE IF NOT EXISTS work_experiences (
    id              		UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    candidate_id			UUID REFERENCES users(id) ON DELETE CASCADE,
    company_name    		TEXT NOT NULL,
    title           		TEXT NOT NULL,
    is_current      		BOOLEAN DEFAULT FALSE,
    start_date      		DATE NOT NULL,
    end_date        		DATE,
    description     		TEXT,
    created_at      		TIMESTAMPTZ DEFAULT NOW(),
    updated_at      		TIMESTAMPTZ DEFAULT NOW()
    );

-- 13. Education
CREATE TABLE IF NOT EXISTS educations (
    id              		UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    candidate_id			UUID REFERENCES users(id) ON DELETE CASCADE,
    school_name          	TEXT NOT NULL,
    degree          		TEXT,
    field_of_study  		TEXT,
    start_date      		DATE,
    end_date        		DATE,
    grade          			TEXT,
    description     		TEXT,
    created_at      		TIMESTAMPTZ DEFAULT NOW()
    );

-- 14. Applications
CREATE TABLE IF NOT EXISTS applications (
    id              		UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    job_id      			UUID REFERENCES jobs(id) ON DELETE CASCADE,
    candidate_id			UUID REFERENCES users(id) ON DELETE CASCADE,
    resume_url				TEXT NOT NULL,
    cover_letter    		TEXT,
    status          		TEXT DEFAULT 'applied' CHECK (status IN ('applied','reviewed','interview','offer','rejected','withdrawn')),
    applied_at      		TIMESTAMPTZ DEFAULT NOW(),
    updated_at      		TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(job_id, candidate_id)
    );

-- 15. Save Jobs
CREATE TABLE IF NOT EXISTS saved_jobs (
    candidate_id   			UUID REFERENCES users(id) ON DELETE CASCADE,
    job_id      			UUID REFERENCES jobs(id) ON DELETE CASCADE,
    saved_at    			TIMESTAMPTZ DEFAULT NOW(),
    PRIMARY KEY (candidate_id, job_id)
    );

-- 16. Company Followers
CREATE TABLE IF NOT EXISTS company_followers (
    candidate_id   			UUID REFERENCES users(id) ON DELETE CASCADE,
    company_id  			UUID REFERENCES companies(id) ON DELETE CASCADE,
    followed_at 			TIMESTAMPTZ DEFAULT NOW(),
    PRIMARY KEY (candidate_id, company_id)
    );

-- 17. Job Views
CREATE TABLE IF NOT EXISTS job_views (
    id          			BIGSERIAL PRIMARY KEY,
    job_id      			UUID REFERENCES jobs(id) ON DELETE CASCADE,
    candidate_id   			UUID REFERENCES users(id),
    ip_address  			INET,
    viewed_at   			TIMESTAMPTZ DEFAULT NOW()
    );
