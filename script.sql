-- Database: tenantdb

-- DROP DATABASE tenantdb;

CREATE DATABASE tenantdb
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
-- Database: masterdb

-- DROP DATABASE masterdb;

CREATE DATABASE masterdb
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;    

-- Table: public.master_tenant

-- DROP TABLE public.master_tenant;

CREATE TABLE public.master_tenant
(
    id bigint NOT NULL DEFAULT nextval('master_tenant_id_seq'::regclass),
    password character varying(30) COLLATE pg_catalog."default",
    tenant_id character varying(30) COLLATE pg_catalog."default",
    url character varying(256) COLLATE pg_catalog."default",
    username character varying(30) COLLATE pg_catalog."default",
    version integer NOT NULL,
    CONSTRAINT master_tenant_pkey PRIMARY KEY (id)
)

-- INSERT TENANTS
INSERT INTO master_tenant
(id,
password,
tenant_id,
url,
username,
version)

VALUES
(1,
'postgres',
'acc',
'jdbc:postgresql://localhost:5433/tenantdb?currentSchema=acc',
'postgres',
'0');


INSERT INTO master_tenant
(id,
password,
tenant_id,
url,
username,
version)
VALUES
(2,
'postgres',
'flamengo',
'jdbc:postgresql://localhost:5433/tenantdb?currentSchema=flamengo',
'postgres',
'0');

select * from master_tenant;

--TENANT DB
-- SCHEMA: acc

-- DROP SCHEMA acc ;

CREATE SCHEMA acc
    AUTHORIZATION postgres;

-- SCHEMA: flamengo

-- DROP SCHEMA flamengo ;

CREATE SCHEMA flamengo
    AUTHORIZATION postgres;    
            
--------MIGRATION NA UNHA            
    create table flamengo.role (
       role_id int4 not null,
        role varchar(255),
        primary key (role_id)
    );
	    create table flamengo.user_role (
       user_id int4 not null,
        role_id int4 not null,
        primary key (user_id, role_id)
    );
	
	    create table flamengo.user_system (
       user_id int4 not null,
        active boolean,
        password varchar(255) not null,
        tenant varchar(255),
        username varchar(255) not null,
        primary key (user_id)
    );

    alter table if exists flamengo.user_role 
       add constraint FKa68196081fvovjhkek5m97n3y 
       foreign key (role_id) 
       references flamengo.role;
	   
	    
    alter table if exists flamengo.user_role 
       add constraint FK61gpx5mjt3qnfo7lktj8s7auy 
       foreign key (user_id) 
       references flamengo.user_system;            
       
create sequence flamengo.role_seq start 1 increment 1;
create sequence flamengo.user_seq start 1 increment 1;

INSERT INTO flamengo.user_system(
	user_id, active, password, tenant, username)
	VALUES ((select nextval('flamengo.user_seq')), true, '$2y$12$RzF1DjLr2b8XZvBFOvGf0eo2q8jWaL7ZaLVq0/jk..BlO0SMaiMJW', 'flamengo', 'fla');
	
INSERT INTO flamengo.role(
	role_id, role)
	VALUES ((select nextval('flamengo.role_seq')), 'ADMIN');
	
INSERT INTO flamengo.user_role(
	user_id, role_id)
	VALUES ((select user_id from flamengo.user_system where tenant='flamengo'), 
			(select role_id from flamengo.role where role='ADMIN'));	

INSERT INTO acc.user_system(
	user_id, active, password, tenant, username)
	VALUES ((select nextval('acc.user_seq')), true, '$2y$12$RzF1DjLr2b8XZvBFOvGf0eo2q8jWaL7ZaLVq0/jk..BlO0SMaiMJW', 'acc', 'acc');
	
INSERT INTO acc.role(
	role_id, role)
	VALUES ((select nextval('acc.role_seq')), 'ADMIN');
	
INSERT INTO acc.user_role(
	user_id, role_id)
	VALUES ((select user_id from acc.user_system where tenant='acc'), 
			(select role_id from acc.role where role='ADMIN'));		
            
            
