CREATE TABLE UsageCase_tbl (
	UsageID SERIAL PRIMARY KEY NOT NULL,
	Usage VARCHAR(255) NOT NULL
);

CREATE INDEX UsageCase_UsageID_ix
ON UsageCase_tbl(UsageID);

CREATE INDEX UsageCase_Usage_ix
ON UsageCase_tbl(Usage);