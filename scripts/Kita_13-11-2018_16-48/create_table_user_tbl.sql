﻿CREATE TABLE User_tbl (
	UserID SERIAL PRIMARY KEY NOT NULL,
	Username VARCHAR(100) NOT NULL,
	Password VARCHAR(255) NOT NULL
);