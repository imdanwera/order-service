

CREATE TABLE IF NOT EXISTS `Orders` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `order_id` VARCHAR(45),
  `customer_name` VARCHAR(45),
  `item_id` VARCHAR(45),
  `quantity` INT
);

INSERT INTO `Orders` (`id`, `order_id`, `customer_name`, `item_name`, `quantity`) VALUES ('1', 'ORD-001', 'Dan', 'abc', '3');
INSERT INTO `Orders` (`id`, `order_id`, `customer_name`, `item_name`, `quantity`) VALUES ('2', 'ORD-002', 'Wera', 'pqr', '2');
INSERT INTO `Orders` (`id`, `order_id`, `customer_name`, `item_name`, `quantity`) VALUES ('3', 'ORD-004', 'Dan', 'cbd', '5');
