CREATE TABLE userdetail(
   userid serial PRIMARY KEY,
   first_name VARCHAR (50) NOT NULL,
   last_name VARCHAR (50) NOT NULL,
   email VARCHAR (355) UNIQUE NOT NULL,
   image VARCHAR,
   secuid BIGINT REFERENCES security_user (secuid)
);

CREATE TABLE address(
   addressid serial PRIMARY KEY,
   street1 VARCHAR (255),
   street2 VARCHAR (255),
   city VARCHAR (50) NOT NULL,
   state VARCHAR (50) NOT NULL,
   country VARCHAR (50) NOT NULL,
   zipcode VARCHAR (10) NOT NULL
);

CREATE TABLE organization(
    organizationid serial PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    address_id BIGINT REFERENCES address (addressid),
    email VARCHAR (355),
    phone VARCHAR (15),
    image VARCHAR,
    bio VARCHAR,
    weblink VARCHAR NOT NULL,
    fblink VARCHAR,
    twitterlink VARCHAR,
    instalink VARCHAR,
    youtubelink VARCHAR,
    petfinder_code VARCHAR (15)
);

CREATE TABLE pet(
    petid serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    breed VARCHAR(50),
    gender VARCHAR(10) NOT NULL,
    age VARCHAR(10) NOT NULL,
    color VARCHAR (20) NOT NULL,
    coat VARCHAR (20),
    size VARCHAR (10),
    bio VARCHAR NOT NULL,
    image VARCHAR NOT NULL,
    vaccinated VARCHAR (1),
    spayedneutered VARCHAR (1),
    goodwithcats VARCHAR (1),
    goodwithchildren VARCHAR (1),
    goodwithdogs VARCHAR (1),
    organizationid BIGINT REFERENCES organization (organizationid),
    adoptable VARCHAR (1),
    housetrained VARCHAR (1),
    declawed VARCHAR (1),
    specialneeds VARCHAR (1),
    tags VARCHAR (255),
    pettypeid BIGINT REFERENCES pet_type (pettypeid) NOT NULL,
    petfinderid BIGINT
);

CREATE TABLE user_like (
    userlikeid serial PRIMARY KEY,
    userid BIGINT REFERENCES userdetail (userid),
    petid BIGINT REFERENCES pet (petid)
);

CREATE TABLE pet_type (
    pettypeid serial PRIMARY KEY,
    type_code VARCHAR (4) NOT NULL UNIQUE,
    type_desc VARCHAR(15) NOT NULL
);

CREATE TABLE security_user (
    secuid serial PRIMARY KEY,
    username VARCHAR (20) NOT NULL UNIQUE,
    password VARCHAR (100) NOT NULL
);

CREATE TABLE role (
    roleid serial PRIMARY KEY,
    rolename VARCHAR (10) NOT NULL UNIQUE
);

CREATE TABLE user_role (
    secuserroleid serial PRIMARY KEY,
    secuid BIGINT REFERENCES security_user (secuid) NOT NULL,
    roleid BIGINT REFERENCES role (roleid) NOT NULL
);