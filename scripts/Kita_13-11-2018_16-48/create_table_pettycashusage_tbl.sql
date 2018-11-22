CREATE TABLE PettyCashUsage_tbl (
	PettyCashUsageID SERIAL PRIMARY KEY NOT NULL,
	PettyCashID INTEGER REFERENCES PettyCash_tbl(PettyCashID) NOT NULL,
	DateTime_Usage TIMESTAMP,
	Usage INTEGER REFERENCES UsageCase_tbl(UsageID),
	Amount NUMERIC(10,2),
	Note VARCHAR(1024)
);

CREATE INDEX PettyCashUsage_PettyCashUsageID_ix
ON PettyCashUsage_tbl(PettyCashUsageID);

CREATE INDEX PettyCashUsage_PettyCashID_ix
ON PettyCashUsage_tbl(PettyCashID);

CREATE INDEX PettyCashUsage_DateTime_Usage_ix
ON PettyCashUsage_tbl(DateTime_Usage);

CREATE INDEX PettyCashUsage_Usage_ix
ON PettyCashUsage_tbl(Usage);

CREATE INDEX PettyCashUsage_Note_ix
ON PettyCashUsage_tbl(Note);