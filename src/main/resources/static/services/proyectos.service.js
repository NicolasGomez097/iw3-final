angular.module('iw3').factory('proyectosService',function($http, URL_API_BASE){
	return {
		list:function() {
			return $http.get(URL_API_BASE+"proyectos");
		},
	
		insert:function(proy) {
			return $http.post(URL_API_BASE+"proyectos",proy);
		},
		
		update:function(proy) {
			return $http.put(URL_API_BASE+"proyectos",proy);
		},
		
		delete:function(id){
			return $http.delete(URL_API_BASE+"proyectos/"+id);
		}
	}
});