CREATE TYPE eyes AS ENUM (
'Blue',
'Gray',
'Swamp',
'Green',
'Amber',
'Brown',
'Yellow',
'Black'
);

CREATE TYPE status AS ENUM (
'ChiefPoliceOfficer',
'OfficerAssistant',
'Jailbird',
'Suspect'
);

CREATE TYPE hair AS ENUM (
'Blond',
'DarkBrown',
'Red',
'Rusyi',
'Brunette',
'Grey'
);


CREATE TABLE IF NOT EXISTS cards(
date TEXT NOT NULL ,
name TEXT NOT NULL ,
eyes eyes NOT NULL,
hair hair NOT NULL,
status status NOT NULL ,
cardheight double precision NOT NULL,
cardwidth double precision NOT NULL,
height double precision NOT NULL ,
headsize double precision NOT NULL ,
nosesize double precision NOT NULL ,
owner TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
email VARCHAR NOT NULL ,
login VARCHAR PRIMARY KEY NOT NULL ,
password VARCHAR NOT NULL
);

