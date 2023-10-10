CREATE TABLE Products (
SKU integer,
name varchar(1000),
imgURL varchar(1000),
canonicalURL varchar(1000),
primary key(SKU),
check (SKU > 0 and (name is not null) and (imgURL is not null) and (canonicalURL is not null))
);

CREATE TABLE RetailPrices (
SKU integer,
price integer,
ts timestamptz,
primary key (SKU, ts),
foreign key (SKU) references Products on delete cascade,
check (SKU > 0 and price >= 0 and (ts is not null))
);

CREATE TABLE EmailPrices (
SKU integer,
promoID varchar(100),
couponID varchar(20),
price integer,
ts timestamptz,
expiration date,
primary key (SKU, promoID, couponID),
foreign key (SKU) references Products on delete cascade,
check (SKU > 0 and price >= 0 and (promoID is not null) and (couponID is not null) and (ts is not null) and (expiration is not null))
);
