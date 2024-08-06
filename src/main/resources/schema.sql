CREATE TABLE IF NOT EXISTS translations (
    id SERIAL PRIMARY KEY,
    ip_address varchar(250),
    local_date_time timestamp NOT NULL,
    source_lang varchar(250) NOT NULL,
    target_lang varchar(250) NOT NULL,
    query TEXT NOT NULL,
    response TEXT NOT NULL
    );