ALTER TABLE categories
    DROP CONSTRAINT fk_categories_user,
    ADD CONSTRAINT fk_categories_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    ADD CONSTRAINT uq_categories_user_name_type UNIQUE (user_id, name, type);

ALTER TABLE transactions
    DROP CONSTRAINT fk_transactions_user,
    DROP CONSTRAINT fk_transactions_category_user,
    DROP CONSTRAINT fk_transactions_recurring_rule,
    DROP CONSTRAINT fk_transactions_rule_user_category,
    ADD CONSTRAINT fk_transactions_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    -- Deferred NO ACTION preserves RESTRICT semantics at commit while allowing
    -- the user's transaction and category cascades to complete in one statement.
    ADD CONSTRAINT fk_transactions_category_user
        FOREIGN KEY (category_id, user_id)
        REFERENCES categories (id, user_id) ON DELETE NO ACTION DEFERRABLE INITIALLY DEFERRED,
    ADD CONSTRAINT fk_transactions_recurring_rule
        FOREIGN KEY (recurring_rule_id) REFERENCES recurring_rules (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_transactions_rule_user_category
        FOREIGN KEY (recurring_rule_id, user_id, category_id)
        REFERENCES recurring_rules (id, user_id, category_id) ON DELETE CASCADE;

ALTER TABLE goals
    DROP CONSTRAINT fk_goals_user,
    ADD COLUMN created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD CONSTRAINT fk_goals_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE goal_contributions
    DROP CONSTRAINT fk_goal_contributions_goal,
    ADD CONSTRAINT fk_goal_contributions_goal
        FOREIGN KEY (goal_id) REFERENCES goals (id) ON DELETE CASCADE;

ALTER TABLE budgets
    DROP CONSTRAINT fk_budgets_category_user,
    ADD CONSTRAINT fk_budgets_category_user
        FOREIGN KEY (category_id, user_id) REFERENCES categories (id, user_id) ON DELETE CASCADE;

ALTER TABLE recurring_rules
    DROP CONSTRAINT fk_recurring_rules_category_user,
    ADD CONSTRAINT fk_recurring_rules_category_user
        FOREIGN KEY (category_id, user_id) REFERENCES categories (id, user_id) ON DELETE CASCADE;
