// properties are directly passed to `create` method
function City () {

	/* Attributes */
	name 			= '';
	state 			= {};
}

/* GET/SET */
City.prototype.getName = function(){
	return this.name;
};
City.prototype.setName = function(name){
	this.name = name;
};

City.prototype.getState = function(){
	return this.state;
};
City.prototype.setState = function(state){
	this.state = state;
};
