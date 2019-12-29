CREATE DATABASE IF NOT EXISTS bakery;

USE bakery;

CREATE TABLE IF NOT EXISTS employee(
	empId VARCHAR(4) PRIMARY KEY NOT NULL,
	empName VARCHAR(100) NOT NULL,
	empAddress VARCHAR(150),
	empContact VARCHAR(20),
	empAvailability VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS ingredient(
	ingId VARCHAR(4) PRIMARY KEY NOT NULL,
	ingName VARCHAR(100) NOT NULL,
	ingQty DECIMAL(8,3)
);

CREATE TABLE IF NOT EXISTS recipe(
	rcpId VARCHAR(4) PRIMARY KEY NOT NULL,
	rcpDes VARCHAR(100) NOT NULL,
	rcpDate DATE
);

CREATE TABLE IF NOT EXISTS recipeIngredient(
	rcpId VARCHAR(4)  NOT NULL,
	ingId VARCHAR(4) NOT NULL,
	ingQty DECIMAL(8,3),
	CONSTRAINT PRIMARY KEY(rcpId,ingId),
	CONSTRAINT FOREIGN KEY(rcpId) REFERENCES recipe(rcpId) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FOREIGN KEY(ingId) REFERENCES ingredient(ingId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS product(
	productId VARCHAR(4)  NOT NULL,
	rcpId VARCHAR(4) NOT NULL,
	productName VARCHAR(50) NOT NULL,
	productQty DECIMAL(8,3),
	CONSTRAINT PRIMARY KEY(productId),
	CONSTRAINT FOREIGN KEY(rcpId) REFERENCES recipe(rcpId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS task(
	taskId VARCHAR(4) NOT NULL,
	taskDescription VARCHAR(250),
	taskStatus VARCHAR(2),
	taskDate DATE,
	taskStartTime DATETIME,
	taskEndTime DATETIME,
	productId VARCHAR(4),
	expectQty INT,
	actualQty INT,
	CONSTRAINT PRIMARY KEY(taskId),
	CONSTRAINT FOREIGN KEY(productId) REFERENCES product(productId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS taskIngredient(
	taskId VARCHAR(4)  NOT NULL,
	ingId VARCHAR(4) NOT NULL,
	ingQty DECIMAL(8,3),
	CONSTRAINT PRIMARY KEY(taskId,ingId),
	CONSTRAINT FOREIGN KEY(taskId) REFERENCES task(taskId) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FOREIGN KEY(ingId) REFERENCES ingredient(ingId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS employeeRegister(	
	empId VARCHAR(4)  NOT NULL,
	date DATE NOT NULL,
	inTime DATETIME,
	outTime DATETIME,
	CONSTRAINT PRIMARY KEY(empId,date,inTime),
	CONSTRAINT FOREIGN KEY(empId) REFERENCES employee(empId) ON DELETE CASCADE ON UPDATE CASCADE	
);

CREATE TABLE IF NOT EXISTS employeeAssign(	
	empId VARCHAR(4)  NOT NULL,
	taskId VARCHAR(4) NOT NULL,
	startTime TIME,
	finishTime TIME,
	CONSTRAINT PRIMARY KEY(empId,taskId,startTime),
	CONSTRAINT FOREIGN KEY(empId) REFERENCES employee(empId) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FOREIGN KEY(taskId) REFERENCES task(taskId) ON DELETE CASCADE ON UPDATE CASCADE
);
