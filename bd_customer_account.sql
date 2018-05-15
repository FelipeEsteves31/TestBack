CREATE database bd_customer_account;
USE bd_customer_account;

CREATE TABLE tb_customer_account(
id_customer INTEGER PRIMARY KEY,
nm_customer VARCHAR(30),
cpf_cnpj INT,
is_active VARCHAR(30),
vl_total INT
);
