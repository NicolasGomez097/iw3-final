angular.module('iw3').factory('listService',function($http, URL_API_BASE){
	return {
		listBacklog:function(idSprint) {
			return $http.get(URL_API_BASE+"listas/backlog?id_sprint="+idSprint);
		},
		
		listToDo:function(idSprint) {
			return $http.get(URL_API_BASE+"listas/to_do?id_sprint="+idSprint);
		},
		
		listInProgress:function(idSprint) {
			return $http.get(URL_API_BASE+"listas/in_progress?id_sprint="+idSprint);
		},
		
		listWaiting:function(idSprint) {
			return $http.get(URL_API_BASE+"listas/waiting?id_sprint="+idSprint);
		},
		
		listDone:function(idSprint) {
			return $http.get(URL_API_BASE+"listas/done?id_sprint="+idSprint);
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