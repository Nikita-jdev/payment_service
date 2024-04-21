CREATE TABLE transactions (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_from VARCHAR(64) NOT NULL,
    account_to VARCHAR(64) NOT NULL,
    currency_shortname VARCHAR(8) NOT NULL,
    money_sum DECIMAL(15, 2) NOT NULL,
    expense_category VARCHAR(16) NOT NULL,
    datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    limit_exceeded BOOLEAN DEFAULT false,
    monthly_limit_id BIGINT NOT NULL,
    FOREIGN KEY (monthly_limit_id) REFERENCES monthly_limit(id)
);
