DELETE FROM transactions;
INSERT INTO transactions (id, amount, type, parent_id)
VALUES (1, 15.5, 'test', null);
INSERT INTO transactions (id, amount, type, parent_id)
VALUES (2, 4.5, 'test1', 1);
INSERT INTO transactions (id, amount, type, parent_id)
VALUES (3, 20.0, 'test', 2);
INSERT INTO transactions (id, amount, type, parent_id)
VALUES (4, 50.0, 'test', 1);
