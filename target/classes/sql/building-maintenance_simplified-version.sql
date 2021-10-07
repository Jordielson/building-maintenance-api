CREATE DataBase manutencao_predial_db;
USE manutencao_predial_db;

CREATE TABLE company (
	cnpj VARCHAR(20) PRIMARY KEY,
	name VARCHAR(20)
);

CREATE TABLE `user` (
	cpf VARCHAR(15) PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	email VARCHAR(40) NOT NULL,
	fone VARCHAR(20),
	`password` VARCHAR(20) NOT NULL,
	job VARCHAR(15) NOT NULL
);

CREATE TABLE immobile (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) ,
	address VARCHAR(255),
	company VARCHAR(20),
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
	price float NOT NULL,
	amount INT
);

CREATE TABLE service (
	id INT PRIMARY KEY AUTO_INCREMENT,
	title varchar(50) NULL,
	room INT,
	budget FLOAT,
	description VARCHAR(255) NOT NULL,
	term VARCHAR(10),
	state VARCHAR(20),
	manager VARCHAR(15),
	FOREIGN KEY (manager)
		REFERENCES `user`(cpf),
	FOREIGN KEY (room)
		REFERENCES room(id)
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
		REFERENCES building_material(id)
);

CREATE TABLE supplier (
	cnpj VARCHAR(20) PRIMARY KEY,
	name varchar(50)
);

CREATE TABLE material_supplier (
	id INT PRIMARY KEY AUTO_INCREMENT,
	material INT,
	supplier VARCHAR(20),
	FOREIGN KEY (material)
		REFERENCES building_material(id),
	FOREIGN KEY (supplier)
		REFERENCES supplier(cnpj)
);

