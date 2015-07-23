app.service('PartnerService', function($http){
	
	this.getAllPartners = function(limit, offset, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/getAllPartners', {limit: limit, offset: offset, token: token}).
		success(callback);
	};

	this.addPartner = function(partner, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/addPartner', {partner: partner.email, token: token}).
		success(callback);
	}

	this.cancelPartner = function(partner, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/cancelPartner', {partner: partner.email, token: token}).
		success(callback);
	};

	this.encodePartnerEmail = function (partner) {

		// Encode the String
		var encodedString = btoa(partner.email + "|" + partner.name);
		console.log(encodedString);

		// Decode the String
		var decodedString = atob(encodedString);
		console.log(decodedString);

		var slash = encodedString.indexOf("/");
		
		if (slash > -1) {
			encodedString = encodedString.substr(0, slash) + '__' + encodedString.substr(slash + 1);
		}

		return encodedString;
	};

	this.isMyPartner = function(partnerEmail, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/isMyPartner', {partner: partnerEmail, token: token}).
		success(callback);
	};

	this.getPartnerData = function(partnerEmail, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/person/getPersonData', {partner: partnerEmail, token: token}).
		success(callback);
	};

	this.searchNewPartners = function(limit, offset, partnerName, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/searchNewPartners', {limit: limit, offset: offset, partner: partnerName, token: token}).
		success(callback);	
	};

	this.searchNewPartnersOnlyByName = function(partnerName, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/searchNewPartnersOnlyByName', {partner: partnerName, token: token}).
		success(callback);
	};
});