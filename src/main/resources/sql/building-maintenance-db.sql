CREATE DataBase db_curso;
USE manutencao_predial_db;


CREATE TABLE company (
	cnpj VARCHAR(15) PRIMARY KEY,
	name VARCHAR(20)
);

CREATE TABLE `user` (
	cpf VARCHAR(15) PRIMARY KEY,
	rg VARCHAR(7) NOT NULL,
	name VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL,
	fone VARCHAR(20),
	`password` VARCHAR(20) NOT NULL,
	job VARCHAR(15) NOT NULL,
	company VARCHAR(15),
	CONSTRAINT fk_user_company
	FOREIGN KEY (company)
		REFERENCES company(cnpj)
);

CREATE TABLE immobile (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) ,
	address VARCHAR(255),
	company VARCHAR(15),
	CONSTRAINT fk_immobile_company
	FOREIGN KEY (company)
		REFERENCES company(cnpj)
);

CREATE TABLE user_immobile (
	id INT PRIMARY KEY AUTO_INCREMENT,	
	user_cpf VARCHAR(15),
	immobile_id INT,
	FOREIGN KEY (user_cpf)
		REFERENCES `user`(cpf),
	FOREIGN KEY (immobile_id)
		REFERENCES immobile(id)
);

CREATE TABLE room (
	id INT PRIMARY KEY AUTO_INCREMENT,
	`floor` VARCHAR(10),
	description VARCHAR(255),
	immobile_id INT,
	FOREIGN KEY (immobile_id)
		REFERENCES immobile(id)
);

CREATE TABLE building_material (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
	price float NOT NULL
);

CREATE TABLE room_material (
	id INT PRIMARY KEY AUTO_INCREMENT,
	last_maintenance date,
	description VARCHAR(255),
	room_id INT,
	material_id INT,
	FOREIGN KEY (room_id)
		REFERENCES room(id),
	FOREIGN KEY (material_id)
		REFERENCES building_material(id)
);

CREATE TABLE inventory (
	id INT PRIMARY KEY AUTO_INCREMENT,
	material_id INT NOT NULL,
	amount INT NOT NULL,
	FOREIGN KEY (material_id)
		REFERENCES building_material(id)
);

CREATE TABLE service (
	id INT PRIMARY KEY AUTO_INCREMENT,
	title varchar(50) NULL,
	budget FLOAT,
	description VARCHAR(255) NOT NULL,
	term date,
	state VARCHAR(20),
	manager VARCHAR(15) NOT NULL,
	FOREIGN KEY (manager)
		REFERENCES `user`(cpf)
);

CREATE TABLE providers_service (
	id INT PRIMARY KEY AUTO_INCREMENT,
	service INT NOT NULL,
	providers VARCHAR(15) NOT NULL,
	FOREIGN KEY (providers)
		REFERENCES `user`(cpf),
	FOREIGN KEY (service)
		REFERENCES service(id)
);

CREATE TABLE material_service (
	id INT PRIMARY KEY  AUTO_INCREMENT,
	material INT NOT NULL,
	service INT NOT NULL,
	FOREIGN KEY (service)
		REFERENCES service(id),
	FOREIGN KEY (material)
		REFERENCES room_material(id)
);

CREATE TABLE order_service (
	id INT PRIMARY KEY AUTO_INCREMENT,
	title varchar(50) NULL,
	room_material INT NOT NULL,
	description VARCHAR(255),
	FOREIGN KEY (room_material)
		REFERENCES room_material(id)
);

CREATE TABLE supplier (
	cnpj VARCHAR(15) PRIMARY KEY,
	name varchar(50)
);

CREATE TABLE material_supplier (
	id INT PRIMARY KEY AUTO_INCREMENT,
	material INT,
	supplier VARCHAR(15),
	FOREIGN KEY (material)
		REFERENCES building_material(id),
	FOREIGN KEY (supplier)
		REFERENCES supplier(cnpj)
);

INSERT INTO manutencao_predial_db.room
(id, floor, description, immobile_id)
VALUES(1, 'Terreo', 'Sala numero 1 do Bloco D', 1);
INSERT INTO manutencao_predial_db.`user`
(cpf, rg, name, email, password, job, fone)
VALUES('12315645648', '1235648', 'joao', 'joao@gmail.com', 'joao123', 'Gerente', NULL);
INSERT INTO manutencao_predial_db.`user`
(cpf, rg, name, email, password, job, fone)
VALUES('265', '156489', 'lucas', 'lucas@gmail.com', 'lucas123', 'Prestador', NULL);
INSERT INTO manutencao_predial_db.room_material
(id, last_maintenance, description, room_id, material_id)
VALUES(1, '2021-08-19', 'Lampada na parte noroeste da sala', 1, 1);
INSERT INTO manutencao_predial_db.building_material
(id, name, price)
VALUES(1, 'Lampada', 22.25);
INSERT INTO manutencao_predial_db.inventory
(id, material_id, amount)
VALUES(1, 1, 8);
INSERT INTO manutencao_predial_db.service
(id, budget, description, term, state, manager)
VALUES(1, 25.0, 'Troca de lampada', '2021-08-19', 'Executando', '12315645648');
INSERT INTO manutencao_predial_db.providers_service
(id, service, providers)
VALUES(1, 1, '265');
INSERT INTO manutencao_predial_db.immobile
(id, name, address, company)
VALUES(1, 'Instituto Jose', 'Rua tal nao sei', '1654');
INSERT INTO manutencao_predial_db.material_service
(id, material, service)
VALUES(1, 1, 1);
INSERT INTO manutencao_predial_db.company
(cnpj, name)
VALUES('1654', 'Pereira');




