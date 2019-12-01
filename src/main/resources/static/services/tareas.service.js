angular.module('iw3').factory('tareasService',function($http, URL_API_BASE){
	return {
		/*list:function(idProyecto) {
			return $http.get(URL_API_BASE+"tareas?id_proyecto="+idProyecto);
		},*/
	
		insert:function(tarea) {
			return $http.post(URL_API_BASE+"tareas",tarea);
		},
		
		update:function(tarea) {
			return $http.put(URL_API_BASE+"tareas",tarea);
		},
		
		deleteTask:function(id){
			return $http.delete(URL_API_BASE+"tareas/"+id);
		}
	}
});