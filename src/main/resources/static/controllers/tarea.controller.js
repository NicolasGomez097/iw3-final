angular.module('iw3')

.controller('tareas', function($scope, $rootScope, 
		tareasService,SweetAlert,Notification,
		$localStorage){
	if(!$localStorage.sprint)
		$rootScope.relocate("sprints");
		
	$scope.titulo="Tareas del sprint '" + $localStorage.sprint.nombre+"'";
	$scope.busqueda={text:""};
	
	$scope.data=[];
	
	
	/*
	$scope.refresh=function() {
		sprintsService.list($localStorage.proyecto.id).then(
			function(resp){
				$scope.data=resp.data;
			},
			function(err){
				Notification.error("No se pudo cargar la lista de sprints");
			}
		);
	}*/
	
	$scope.openInsertTareaForm=function(){
		$scope.task={};
		$rootScope.openTareaForm(true);
	}
	
	$scope.openUpdateForm=function(task) {
		$rootScope.selectedTarea = task;
		$rootScope.openTareaForm(false);
	}
		
	$scope.refresh();
	
	$scope.selectSprint = function(task){
		$localStorage.task = task;
		$rootScope.relocate("/tarea");
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