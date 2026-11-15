CREATE TABLE users (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    password_hash text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id integer NOT NULL,
    name varchar(100) NOT NULL,
    type varchar(10) NOT NULL CHECK (type IN ('income', 'expense')),
    CONSTRAINT uq_categories_id_user UNIQUE (id, user_id),
    CONSTRAINT fk_categories_user FOREIGN KEY (user_id)
        REFERENCES users (id)
);

CREATE TABLE transactions (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id integer NOT NULL,
    category_id integer NOT NULL,
    amount numeric(12, 2) NOT NULL CHECK (amount > 0),
    transaction_date date NOT NULL,
    description text,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transactions_user FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT fk_transactions_category_user FOREIGN KEY (category_id, user_id)
        REFERENCES categories (id, user_id)
);

CREATE TABLE goals (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id integer NOT NULL,
    name varchar(100) NOT NULL,
    target_amount numeric(12, 2) NOT NULL CHECK (target_amount > 0),
    target_date date,
    CONSTRAINT fk_goals_user FOREIGN KEY (user_id)
        REFERENCES users (id)
);

CREATE TABLE goal_contributions (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    goal_id integer NOT NULL,
    amount numeric(12, 2) NOT NULL CHECK (amount > 0),
    contribution_date date NOT NULL,
    CONSTRAINT fk_goal_contributions_goal FOREIGN KEY (goal_id)
        REFERENCES goals (id)
);
