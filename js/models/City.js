// properties are directly passed to `create` method
function City () {

	/* Attributes */
	name 			= '';
}

/* GET/SET */
City.prototype.getName = function(){
	return this.name;
};
City.prototype.setName = function(name){
	this.name = name;
};
