angular.module('iw3').factory('coreService',function($http,URL_BASE){
	return {
			
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