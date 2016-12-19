# Rates table
# --- !Ups

CREATE TABLE Rate(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    symbol varchar(16) NOT NULL,
    value double,
    ts datetime,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Rate;
