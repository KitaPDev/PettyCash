CREATE TABLE PettyCash_tbl (
	PettyCashID SERIAL PRIMARY KEY NOT NULL,
	Date_Posting DATE DEFAULT CURRENT_DATE NOT NULL,
	Amount NUMERIC(10,2) NOT NULL,
	UserID_Payer INTEGER REFERENCES User_tbl(UserID) NOT NULL,
	UserID_Payee INTEGER REFERENCES User_tbl(UserID) NOT NULL,
	Amount_Return NUMERIC(10,2) NULL,
	St_Return TIMESTAMP NULL,
	UserID_Return INTEGER REFERENCES User_tbl(UserID) NULL,
	UserID_ReturnReceived INTEGER REFERENCES User_tbl(UserID) NULL
);

CREATE INDEX PettyCash_PettyCashID_ix
ON PettyCash_tbl(PettyCashID);

CREATE INDEX PettyCash_Date_Posting_ix
ON PettyCash_tbl(Date_Posting);

CREATE INDEX PettyCash_UserID_Payer_ix
ON PettyCash_tbl(UserID_Payer);

CREATE INDEX PettyCash_UserID_Payee_ix
ON PettyCash_tbl(UserID_Payee);

CREATE INDEX PettyCash_Amount_Return_ix
ON PettyCash_tbl(Amount_Return);

CREATE INDEX PettyCash_St_Return_ix
ON PettyCash_tbl(St_Return);

CREATE INDEX PettyCash_UserID_Return_ix
ON PettyCash_tbl(UserID_Return);

CREATE INDEX PettyCash_UserID_ReturnReceived_ix
ON PettyCash_tbl(UserID_ReturnReceived);