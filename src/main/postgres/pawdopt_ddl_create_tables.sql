CREATE TABLE userdetail(
   userid serial PRIMARY KEY,
   first_name VARCHAR (50) NOT NULL,
   last_name VARCHAR (50) NOT NULL,
   email VARCHAR (355) UNIQUE NOT NULL,
   phone VARCHAR (10) NOT NULL,
   image VARCHAR,
   dob DATE
);

CREATE TABLE address(
   addressid serial PRIMARY KEY,
   street1 VARCHAR (255) NOT NULL,
   street2 VARCHAR (255),
   city VARCHAR (50) NOT NULL,
   state VARCHAR (50) NOT NULL,
   country VARCHAR (50) NOT NULL,
   zipcode VARCHAR (10) NOT NULL
);

CREATE TABLE organization(
    organizationid serial PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    address_id INTEGER REFERENCES address (addressid),
    email VARCHAR (355) NOT NULL,
    phone VARCHAR (10) NOT NULL,
    image VARCHAR,
    bio VARCHAR,
    weblink VARCHAR NOT NULL,
    fblink VARCHAR,
    twitterlink VARCHAR,
    instalink VARCHAR,
    youtubelink VARCHAR
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
    organizationid INTEGER REFERENCES organization (organizationid)
);

CREATE TABLE user_like (
    userlikeid serial PRIMARY KEY,
    userid INTEGER REFERENCES userdetail (userid),
    petid INTEGER REFERENCES pet (petid)
);