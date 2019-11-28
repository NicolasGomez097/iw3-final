angular.module('iw3').factory('coreService',function($http,URL_BASE){
	return {
		
		authInfo: function() {
			 return $http.get(URL_BASE+"authinfo");
		},
		version: function() {
			 return $http.get(URL_BASE+"version");
		},
		
		loginJwt: function(user) {
			var req = {
				method: 'POST',
				url: URL_BASE+'loginJwt',
				headers : { 'Content-Type': 'application/json' },
				data: user
			};
			return $http(req);
		}
	}
});