CREATE TABLE budgets (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id integer NOT NULL,
    category_id integer NOT NULL,
    amount numeric(12, 2) NOT NULL CHECK (amount > 0),
    budget_month date NOT NULL CHECK (EXTRACT(DAY FROM budget_month) = 1),
    CONSTRAINT uq_budgets_user_category_month UNIQUE (user_id, category_id, budget_month),
    CONSTRAINT fk_budgets_category_user FOREIGN KEY (category_id, user_id)
        REFERENCES categories (id, user_id)
);

CREATE TABLE recurring_rules (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id integer NOT NULL,
    category_id integer NOT NULL,
    amount numeric(12, 2) NOT NULL CHECK (amount > 0),
    description text,
    frequency varchar(10) NOT NULL CHECK (frequency IN ('DAILY', 'MONTHLY')),
    start_date date NOT NULL,
    next_occurrence date NOT NULL,
    end_date date,
    active boolean NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_recurring_rules_date_range CHECK (end_date IS NULL OR end_date >= start_date),
    CONSTRAINT ck_recurring_rules_next_date CHECK (next_occurrence >= start_date),
    CONSTRAINT ck_monthly_recurring_dates CHECK (
        frequency <> 'MONTHLY'
        OR (EXTRACT(DAY FROM start_date) = 1 AND EXTRACT(DAY FROM next_occurrence) = 1)
    ),
    CONSTRAINT uq_recurring_rules_id_user_category UNIQUE (id, user_id, category_id),
    CONSTRAINT fk_recurring_rules_category_user FOREIGN KEY (category_id, user_id)
        REFERENCES categories (id, user_id)
);

ALTER TABLE transactions
    ADD COLUMN recurring_rule_id integer,
    ADD COLUMN occurrence_date date,
    ADD CONSTRAINT ck_transactions_recurring_occurrence CHECK (
        (recurring_rule_id IS NULL AND occurrence_date IS NULL)
        OR (recurring_rule_id IS NOT NULL AND occurrence_date IS NOT NULL)
    ),
    ADD CONSTRAINT uq_transactions_recurring_occurrence UNIQUE (recurring_rule_id, occurrence_date),
    ADD CONSTRAINT fk_transactions_recurring_rule FOREIGN KEY (recurring_rule_id)
        REFERENCES recurring_rules (id),
    ADD CONSTRAINT fk_transactions_rule_user_category
        FOREIGN KEY (recurring_rule_id, user_id, category_id)
        REFERENCES recurring_rules (id, user_id, category_id);

CREATE INDEX ix_transactions_user_date ON transactions (user_id, transaction_date);
CREATE INDEX ix_recurring_rules_due ON recurring_rules (next_occurrence)
    WHERE active = true;
