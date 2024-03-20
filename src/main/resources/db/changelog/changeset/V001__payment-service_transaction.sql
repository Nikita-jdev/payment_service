CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    idempotency_key UUID NOT NULL UNIQUE,
    sender_account_number VARCHAR(64) NOT NULL,
    receiver_account_number VARCHAR(64) NOT NULL,
    money_amount NUMERIC NOT NULL,
    money_currency VARCHAR(16) NOT NULL,
    request_status VARCHAR(64) NOT NULL,
    request_type VARCHAR(64) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    clear_scheduled_at TIMESTAMPTZ NOT NULL,
    version BIGINT DEFAULT 1 NOT NULL,
    user_id BIGINT NOT NULL
);
