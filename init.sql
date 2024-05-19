CREATE TABLE abilities (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           description TEXT,
                           pic_url VARCHAR(255)
);
CREATE TABLE dotacharacters (
                                id SERIAL PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                power INTEGER,
                                agility INTEGER,
                                intelligence INTEGER,
                                attack_type VARCHAR(255),
                                pic_url VARCHAR(255),
                                url_video VARCHAR(255),
                                character_id BIGINT REFERENCES abilities(id)
);
