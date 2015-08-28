app.service('PartnerService', function($http){
	
	this.getAllPartners = function(success, error) {
		var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/partner/allpartners/' + token).
		then(success, error);
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

	this.isMyPartner = function(partnerEmail, success, error) {
		var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/partner/ismypartner/' + partnerEmail + '?token=' + token).
		then(success, error);
	};

	this.getPartnerData = function(partnerEmail, success, error) {
		var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/person/persondata/' + partnerEmail + '?token=' + token).
		then(success, error);
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
    
    this.getPossiblePartners = function(callback, error) {
        var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/partner/possiblepartners/' + token).
		then(callback, error);
    };
    
    this.getCommonsPartners = function(partner, success, error) {
        var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/partner/commonspartner/' + partner.email + '?token=' + token).
		then(success, error);
    };
});