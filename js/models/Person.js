// properties are directly passed to `create` method
function Person () {

	/* Attributes */
	name 			= '';
	email 			= '';
	password 		= '';
	typeOfAccount	= '';
	typeOfPerson 	= '';
	cnpj 			= '';
	cpf 			= '';
	gender 			= '';

}

/* GET/SET */
Person.prototype.getName = function(){
	return this.name;
};
Person.prototype.setName = function(name){
	this.name = name;
};
  	
Person.prototype.getEmail = function(){
	return this.email;
};
Person.prototype.setEmail = function(email){
	this.email = email;
};

Person.prototype.getPassword = function(){
	return this.password;
};
Person.prototype.setPassword = function(password){
	this.password = password;
};

Person.prototype.getTypeOfAccount = function(){
	return this.typeOfAccount;
};
Person.prototype.setTypeOfAccount = function(typeOfAccount){
	this.typeOfAccount = typeOfAccount;
};

Person.prototype.getTypeOfPerson = function(){
	return this.typeOfPerson;
};
Person.prototype.setTypeOfPerson = function(typeOfPerson){
	this.typeOfPerson = typeOfPerson;
};

Person.prototype.getCNPJ = function(){
	return this.cnpj;
};
Person.prototype.setCNPJ = function(cnpj){
	this.cnpj = cnpj;
};

Person.prototype.getCPF = function(){
	return this.cpf;
};
Person.prototype.setCPF = function(cpf){
	this.cpf = cpf;
};

Person.prototype.getGender = function(){
	return this.gender;
};
Person.prototype.setGender = function(gender){
	this.gender = gender;
};