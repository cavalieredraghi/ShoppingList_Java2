INSERT INTO java2.users (email, password, full_name, active, created_utc, modified_utc, role) VALUES ('test@test.com', 'test', 'Test Test', true, '2016-11-30 10:25:00', '2016-11-30 10:25:00', 'ADMIN');
INSERT INTO java2.users (email, password, full_name, active, created_utc, modified_utc, role) VALUES ('test2@test.com', 'test', 'Test2 Test', true, '2016-11-30 10:25:00', '2016-11-30 10:25:00', 'ADMIN');

INSERT INTO java2.shopping_list_groups (name, created_utc, modified_utc) VALUES ('Clothing', '2016-11-30 10:25:00', '2016-11-30 10:25:00');

INSERT INTO java2.shopping_lists (user_id, name, color, created_utc, modified_utc) VALUES (1, 'Shopping List for User 1', '#000000', '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_lists (user_id, name, color, created_utc, modified_utc) VALUES (2, 'Shopping List for User 2', '#000000', '2016-11-30 10:25:00', '2016-11-30 10:25:00');

INSERT INTO java2.shopping_list_item_priorities (name, created_utc, modified_utc) VALUES ('It can wait', '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_item_priorities (name, created_utc, modified_utc) VALUES ('Need it soon', '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_item_priorities (name, created_utc, modified_utc) VALUES ('Grab it now', '2016-11-30 10:25:00', '2016-11-30 10:25:00');

INSERT INTO java2.shopping_list_item_notes (body, created_utc, modified_utc) VALUES ('Note 1', '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_item_notes (body, created_utc, modified_utc) VALUES ('Note 2', '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_item_notes (body, created_utc, modified_utc) VALUES ('Note 3', '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_item_notes (body, created_utc, modified_utc) VALUES ('Note 1', '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_item_notes (body, created_utc, modified_utc) VALUES ('Note 2', '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_item_notes (body, created_utc, modified_utc) VALUES ('Note 3', '2016-11-30 10:25:00', '2016-11-30 10:25:00');

INSERT INTO java2.shopping_list_items (shopping_list_id, name, shopping_list_item_priority_id, is_checked, shopping_list_item_note_id, created_utc, modified_utc) VALUES (1, 'Item 1 for User 1', 1, false, 1, '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_items (shopping_list_id, name, shopping_list_item_priority_id, is_checked, shopping_list_item_note_id, created_utc, modified_utc) VALUES (1, 'Item 2 for User 1', 2, false, 2, '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_items (shopping_list_id, name, shopping_list_item_priority_id, is_checked, shopping_list_item_note_id, created_utc, modified_utc) VALUES (1, 'Item 3 for User 1', 3, false, 3, '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_items (shopping_list_id, name, shopping_list_item_priority_id, is_checked, shopping_list_item_note_id, created_utc, modified_utc) VALUES (2, 'Item 1 for User 2', 1, false, 4, '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_items (shopping_list_id, name, shopping_list_item_priority_id, is_checked, shopping_list_item_note_id, created_utc, modified_utc) VALUES (2, 'Item 2 for User 2', 2, false, 5, '2016-11-30 10:25:00', '2016-11-30 10:25:00');
INSERT INTO java2.shopping_list_items (shopping_list_id, name, shopping_list_item_priority_id, is_checked, shopping_list_item_note_id, created_utc, modified_utc) VALUES (2, 'Item 3 for User 2', 3, false, 6, '2016-11-30 10:25:00', '2016-11-30 10:25:00');
