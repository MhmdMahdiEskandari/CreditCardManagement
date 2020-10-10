CREATE TABLE creditcardinfo
(
ccnumber VARCHAR NOT NULL,
pin VARCHAR NOT NULL,
cvv2 VARCHAR NOT NULL,
exp_date VARCHAR NOT NULL,
fname VARCHAR NOT NULL,
lname VARCHAR NOT NULL,
credit_amount VARCHAR NOT NULL,
primary key (ccnumber)
);