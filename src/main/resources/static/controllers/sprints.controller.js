angular.module('iw3')

.controller('sprints', function($scope, $rootScope, 
		sprintService,SweetAlert,Notification,
		$localStorage){
	if(!$localStorage.proyecto)
		$rootScope.relocate("proyectos");
		
	$scope.titulo="Sprints del proyecto '" + $localStorage.proyecto.nombre+"'";
	$scope.busqueda={text:""};
	
	$scope.data=[];
	
	
	
	$scope.refresh=function() {
		sprintService.list($localStorage.proyecto.id).then(
			function(resp){
				$scope.data=resp.data;
			},
			function(err){
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