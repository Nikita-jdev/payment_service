CREATE TABLE transactions (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_from VARCHAR(64) NOT NULL,
    account_to VARCHAR(64) NOT NULL,
    currency_shortname VARCHAR(8) NOT NULL,
    money_sum DECIMAL(15, 2) NOT NULL,
    expense_category VARCHAR(16) NOT NULL,
    datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    limit_exceeded BOOLEAN,
    limit_sum DECIMAL(15, 2),
    limit_datetime TIMESTAMP WITH TIME ZONE,
    limit_currency_shortname VARCHAR(8)

    CONSTRAINT fk_limit_sum FOREIGN KEY limit_sum REFERENCES monthly_limit(amount),
    CONSTRAINT fk_limit_datetime FOREIGN KEY limit_datetime REFERENCES monthly_limit(start_date),
    CONSTRAINT fk_limit_currency_shortname FOREIGN KEY limit_currency_shortname REFERENCES monthly_limit(currency)
);
