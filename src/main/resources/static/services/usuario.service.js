angular.module('iw3').factory('usuarioService',function($http, URL_API_BASE){
	return {	
		update:function(usuario) {
			return $http.put(URL_API_BASE+"usuario",usuario);
		}
	}
});