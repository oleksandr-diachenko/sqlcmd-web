-- Sequence: database_connection_seq

-- DROP SEQUENCE database_connection_seq;

CREATE SEQUENCE database_connection_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE database_connection_seq
  OWNER TO postgres;


-- Table: database_connection

-- DROP TABLE database_connection;

CREATE TABLE database_connection
(
  id integer NOT NULL DEFAULT nextval('database_connection_seq'::regclass),
  db_name text,
  user_name text,
  CONSTRAINT database_connection_id_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE database_connection
  OWNER TO postgres;


-- Column: database_connection_id

-- ALTER TABLE user_actions DROP COLUMN database_connection_id;

ALTER TABLE user_actions ADD COLUMN database_connection_id integer;


-- Foreign Key: user_action_database_connection_fk

-- ALTER TABLE user_actions DROP CONSTRAINT user_action_database_connection_fk;

ALTER TABLE user_actions
  ADD CONSTRAINT user_action_database_connection_fk FOREIGN KEY (database_connection_id)
      REFERENCES database_connection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


--Data migration

INSERT INTO database_connection (user_name,db_name)
  SELECT DISTINCT user_name, db_name FROM user_actions;

UPDATE user_actions
SET database_connection_id = subquery.id
FROM (SELECT id, user_name, db_name FROM database_connection) AS subquery
WHERE user_actions.user_name = subquery.user_name AND user_actions.db_name = subquery.db_name;

--Drop unused columns

ALTER TABLE user_actions DROP COLUMN user_name;

ALTER TABLE user_actions DROP COLUMN db_name;

