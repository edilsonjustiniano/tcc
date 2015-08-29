app.service('PartnerService', function($http){
	
	this.getAllPartners = function(success, error) {
		var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/partner/allpartners/' + token).
		then(success, error);
	};

	this.addPartner = function(partner, success, error) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/add', {partner: partner.email, token: token}).
		then(success, error);
	}

	this.cancelPartner = function(partner, callback) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/cancel', {partner: partner.email, token: token}).
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

	this.searchNewPartners = function(partnerName, success, error) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/searchnewpartners', {partner: partnerName, token: token}).
		then(success, error);	
	};

	this.searchNewPartnersOnlyByName = function(partnerName, success, error) {
		var token = window.localStorage['token'];
		$http.post('http://localhost:8080/WebService/partner/searchnewpartnersonlybyname', {partner: partnerName, token: token}).
		then(success, error);
	};
    
    this.getPossiblePartners = function(success, error) {
        var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/partner/possiblepartners/' + token).
		then(success, error);
    };
    
    this.getCommonsPartners = function(partner, success, error) {
        var token = window.localStorage['token'];
		$http.get('http://localhost:8080/WebService/partner/commonspartner/' + partner.email + '?token=' + token).
		then(success, error);
    };
});