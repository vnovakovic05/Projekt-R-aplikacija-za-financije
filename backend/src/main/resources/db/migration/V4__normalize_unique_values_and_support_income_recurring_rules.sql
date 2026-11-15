DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM users
        GROUP BY lower(btrim(email))
        HAVING count(*) > 1
    ) THEN
        RAISE EXCEPTION
            'Cannot create case-insensitive email uniqueness: duplicate normalized email addresses exist';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM categories
        GROUP BY user_id, lower(btrim(name)), type
        HAVING count(*) > 1
    ) THEN
        RAISE EXCEPTION
            'Cannot create case-insensitive category uniqueness: duplicate normalized category names exist';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM budgets
        WHERE category_id IN (
            SELECT c.id
            FROM categories c
            WHERE c.type <> 'expense'
        )
    ) THEN
        RAISE EXCEPTION
            'Cannot restrict budgets to expense categories: existing budgets reference income categories';
    END IF;
END
$$;

CREATE UNIQUE INDEX uq_users_email_normalized
    ON users (lower(btrim(email)));

CREATE UNIQUE INDEX uq_categories_user_name_type_normalized
    ON categories (user_id, lower(btrim(name)), type);

ALTER TABLE categories
    ADD CONSTRAINT uq_categories_id_user_type UNIQUE (id, user_id, type);

ALTER TABLE budgets
    ADD COLUMN category_type varchar(10);

UPDATE budgets b
SET category_type = c.type
FROM categories c
WHERE c.id = b.category_id
  AND c.user_id = b.user_id;

ALTER TABLE budgets
    ALTER COLUMN category_type SET NOT NULL,
    ALTER COLUMN category_type SET DEFAULT 'expense',
    ADD CONSTRAINT ck_budgets_expense_category CHECK (category_type = 'expense'),
    DROP CONSTRAINT fk_budgets_category_user,
    ADD CONSTRAINT fk_budgets_category_user_type
        FOREIGN KEY (category_id, user_id, category_type)
        REFERENCES categories (id, user_id, type) ON DELETE CASCADE;

ALTER TABLE recurring_rules
    ADD COLUMN category_type varchar(10);

UPDATE recurring_rules r
SET category_type = c.type
FROM categories c
WHERE c.id = r.category_id
  AND c.user_id = r.user_id;

ALTER TABLE recurring_rules
    ALTER COLUMN category_type SET NOT NULL,
    ADD CONSTRAINT ck_recurring_rules_category_type
        CHECK (category_type IN ('income', 'expense')),
    DROP CONSTRAINT fk_recurring_rules_category_user,
    ADD CONSTRAINT fk_recurring_rules_category_user_type
        FOREIGN KEY (category_id, user_id, category_type)
        REFERENCES categories (id, user_id, type) ON DELETE CASCADE;
