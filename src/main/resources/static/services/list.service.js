angular.module('iw3').factory('listService',function($http, URL_API_BASE){
	return {
		list:function(idSprint) {
			return $http.get(URL_API_BASE+"listas?id_sprint="+idSprint);
		},
		
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
	
		insert:function(lista) {
			return $http.post(URL_API_BASE+"listas",lista);
		},
		
		moveToDo:function(idTarea) {
			return $http.put(URL_API_BASE+"listas/move_todo/"+idTarea);
		},
		moveInProgress:function(idTarea) {
			return $http.put(URL_API_BASE+"listas/move_in_progress/"+idTarea);
		},
		moveWaiting:function(idTarea) {
			return $http.put(URL_API_BASE+"listas/move_waiting/"+idTarea);
		},
		moveDone:function(idTarea) {
			return $http.put(URL_API_BASE+"listas/move_done/"+idTarea);
		},
		
		update:function(sprint) {
			return $http.put(URL_API_BASE+"sprints",sprint);
		},
		
		delete:function(id){
			return $http.delete(URL_API_BASE+"sprints/"+id);
		}
	}
});