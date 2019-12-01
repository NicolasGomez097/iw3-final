angular.module('iw3').factory('sprintsService',function($http, URL_API_BASE){
	return {
		list:function(idProyecto) {
			return $http.get(URL_API_BASE+"sprints?id_proyecto="+idProyecto);
		},
	
		insert:function(sprint) {
			return $http.post(URL_API_BASE+"sprints",sprint);
		},
		
		update:function(sprint) {
			return $http.put(URL_API_BASE+"sprints",sprint);
		},
		
		delete:function(id){
			return $http.delete(URL_API_BASE+"sprints/"+id);
		}
	}
});