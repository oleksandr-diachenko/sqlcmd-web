-- Database: sqlcmd_log

-- DROP DATABASE sqlcmd_log;

CREATE DATABASE sqlcmd_log
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'C'
       LC_CTYPE = 'C'
       CONNECTION LIMIT = -1;


-- Sequence: user_action_seq

-- DROP SEQUENCE user_action_seq;

CREATE SEQUENCE user_action_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 679
  CACHE 1;
ALTER TABLE user_action_seq
  OWNER TO postgres;


-- Table: user_actions

-- DROP TABLE user_actions;

CREATE TABLE user_actions
(
  id integer NOT NULL DEFAULT nextval('user_action_seq'::regclass),
  db_name text,
  user_name text,
  action text,
  CONSTRAINT user_action_id_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_actions
  OWNER TO postgres;

