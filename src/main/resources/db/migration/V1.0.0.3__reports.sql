create table report
(
    id          UUID NOT NULL,
    reporter_id UUID NOT NULL,
    date_time   timestamp with time zone,
    description TEXT,
    imagedata   BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_user
        FOREIGN KEY (reporter_id)
            REFERENCES copsboot_user (id)
);