INSERT INTO users (name, surname, nickname, age) VALUES ('Anton', 'Jazbec', 'jazbo', 23);
INSERT INTO users (name, surname, nickname, age) VALUES ('Mario', 'Rossi', 'ciccio', 25);
INSERT INTO users (name, surname, nickname, age) VALUES ('Ian', 'McDough', 'hotdude33', 30);
INSERT INTO grocery_lists (user_id) VALUES (1);
INSERT INTO grocery_lists (user_id) VALUES (2);
INSERT INTO grocery_lists (user_id) VALUES (1);
INSERT INTO grocery_lists (user_id) VALUES (3);
INSERT INTO grocery_items (item_name, item_description) VALUES ('Tuna', '160-package of tuna with olive oil');
INSERT INTO grocery_items (item_name, item_description) VALUES ('Tuna', '320-package of tuna with olive oil');
INSERT INTO grocery_items (item_name, item_description) VALUES ('Sugar', '500g package of sugar');
INSERT INTO grocery_items (item_name, item_description) VALUES ('Toothpaste', 'Mentadent toothpaste');
INSERT INTO list_item_mapping VALUES (1, 1);
INSERT INTO list_item_mapping VALUES (1, 2);
INSERT INTO list_item_mapping VALUES (1, 3);
INSERT INTO list_item_mapping VALUES (2, 3);
INSERT INTO list_item_mapping VALUES (3, 2);
