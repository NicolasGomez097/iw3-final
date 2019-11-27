angular.module('iw3').factory('proyectosService',function($http, URL_API_BASE){
	return {
		/*list : function() {
			return [ {
				id : 1,
				descripcion : 'Arroz',
				precio : 55
			},
			{
				id : 2,
				descripcion : 'Leche',
				precio : 70
			},
			{
				id : 3,
				descripcion : 'Chupetín',
				precio : 7
			}];
		}*/
		list:function() {
			//return $http.get("/db/productos.js");
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