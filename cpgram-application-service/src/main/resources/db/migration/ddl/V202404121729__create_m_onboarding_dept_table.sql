CREATE TABLE m_onboarding_dept (
    uuid VARCHAR(36) PRIMARY KEY,
    dept_code VARCHAR(50),
    dept_name VARCHAR(100),
    field_name VARCHAR(100),
    level INTEGER,
    parent_level INTEGER,
    is_mandatory BOOLEAN,
    owner_name VARCHAR(100),
    field_details jsonb,
    categories jsonb
);