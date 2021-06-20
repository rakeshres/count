create schema countries;

create table countries.audit_logs
(
	id varchar(50) not null
		constraint audit_logs_pk
			primary key,
	entity varchar(50) not null,
	entity_key varchar(50) not null,
	action varchar(50) not null,
	status varchar(50) not null,
	new_data jsonb not null,
	old_data jsonb,
	changes text,
	requested_by varchar(255) not null,
	requested_on timestamp not null,
	responded_by varchar(255),
	responded_on timestamp,
	request_remarks varchar(255),
	response_remarks varchar(255)
);


create table countries.countries (
    id varchar(50) not null constraint countries_pk primary key,
    name varchar(255) not null ,
    numeric_code varchar not null,
    iso2 varchar(255) not null,
    iso3 varchar(255) not null,
    sanction_filter float ,
    currencies jsonb,
    operation_types jsonb,
    created_on TIMESTAMP,
    created_by varchar(255) not null ,
    last_modified_on TIMESTAMP,
    last_modified_by varchar(255)  ,
    deleted_on TIMESTAMP,
    deleted_by varchar(255) ,
    is_active bool

);


create table countries.country_dynamic_form (
    id varchar not null constraint country_dynamic_form_pk primary key,
    name varchar not null,
    country_iso3 varchar not null,
    type varchar not null,
    effective_from date not null,
    effective_to date not null,
    field_info_list jsonb,
    created_on timestamp,
	created_by varchar,
	last_modified_on timestamp,
	last_modified_by varchar,
	deleted_on timestamp,
	deleted_by varchar
);

alter table countries.countries
	add corridors jsonb;

alter table countries.countries
	add payment_methods jsonb;

create table country_configs
(
    country_iso3_code      varchar(255) not null
        constraint country_configs_pk
            primary key,
    id_types               jsonb,
    purpose_of_remittances jsonb,
    source_of_funds        jsonb,
    relationships          jsonb,
    occupations            jsonb
);



