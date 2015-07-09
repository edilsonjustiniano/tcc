// properties are directly passed to `create` method
function Company () {

	/* Attributes */
	name 			= '';
	locatedIn		= {};
}

/* GET/SET */
Company.prototype.getName = function(){
	return this.name;
};
Company.prototype.setName = function(name){
	this.name = name;
};

Company.prototype.getLocatedIn = function(){
	return this.locatedIn;
};
Company.prototype.setLocatedIn = function(locatedIn){
	this.locatedIn = locatedIn;
};