// properties are directly passed to `create` method
function State () {

	/* Attributes */
	name 			= '';
	abbreviation	= '';
}

/* GET/SET */
State.prototype.getName = function(){
	return this.name;
};
State.prototype.setName = function(name){
	this.name = name;
};

State.prototype.getAbbreviation = function(){
	return this.abbreviation;
};
State.prototype.setAbbreviation = function(abbreviation){
	this.abbreviation = abbreviation;
};