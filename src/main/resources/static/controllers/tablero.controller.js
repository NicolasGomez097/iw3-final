angular.module('iw3')

.controller('tablero', function($scope, $rootScope, 
		listService,SweetAlert,Notification,
		$localStorage){
	if(!$localStorage.sprint)
		$rootScope.relocate("sprints");
		
	$scope.titulo="Tablero del sprint '" + $localStorage.sprint.nombre+"'";
	$scope.busqueda={text:""};
		
	$scope.backlog=[];
	$scope.toDo=[];
	$scope.inProgress=[];
	$scope.waiting=[];
	$scope.done=[];


	$scope.refresh=function() {
		listService.listBacklog($localStorage.sprint.id).then(
			function(resp){
				$scope.backlog=resp.data;
			},
			function(err){
				if(err.status != 404)
					Notification.error("No se pudo cargar la lista de sprints");
			}
		);
		
		listService.listToDo($localStorage.sprint.id).then(
				function(resp){
					$scope.toDo=resp.data;
				},
				function(err){
					if(err.status != 404)
						Notification.error("No se pudo cargar la lista de sprints");
				}
			);
		
		listService.listInProgress($localStorage.sprint.id).then(
				function(resp){
					$scope.inProgress=resp.data;
				},
				function(err){
					if(err.status != 404)
						Notification.error("No se pudo cargar la lista de sprints");
				}
			);
		
		listService.listWaiting($localStorage.sprint.id).then(
				function(resp){
					$scope.waiting=resp.data;
				},
				function(err){
					if(err.status != 404)
						Notification.error("No se pudo cargar la lista de sprints");
				}
			);
		
		listService.listDone($localStorage.sprint.id).then(
				function(resp){
					$scope.done=resp.data;
				},
				function(err){
					if(err.status != 404)
						Notification.error("No se pudo cargar la lista de sprints");
				}
			);
	}
	
	$scope.openInsertForm=function(){
		$scope.proy={};
		$rootScope.openProyectForm(true);
	}
	
	$scope.openUpdateForm=function(proy) {
		$rootScope.selectedproy = proy;
		$rootScope.openProyectForm(false);
	}
		
	$scope.refresh();
	
	$scope.selectSprint = function(sprint){
		$localStorage.sprint = sprint;
		$rootScope.relocate("/tablero");
	}
	
	
	
			
	/*$scope.eliminar=function(proy) {
		SweetAlert.swal({
			  title: "Eliminar proyecto",
			  text: "Está seguro que desea eliminar el proyecto <strong>"+proy.nombre+"</strong>?",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Si, eliminar proyecto!",
			  cancelButtonText: "No",
			  closeOnConfirm: true,
			  html: true
			}, function(confirm){
				if(confirm) {
					proyectosService.delete(proy.id).then(
						function(resp){
							if(resp.status===200){
								Notification.success("Se elimino correctamente");
								$scope.refresh();
							}else{
								Notification.error("No se pudo eliminar");
							}
						}
					);
				}
			});
	};*/
	
}); //End main controller