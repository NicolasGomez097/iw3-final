angular.module('iw3')

.controller('proyectos', function($scope, $rootScope, 
		proyectosService,SweetAlert,Notification,
		$localStorage){
	$scope.titulo="Proyectos";
	$scope.busqueda={text:""};
	
	$scope.data=[];
	
	$scope.refresh=function() {
		proyectosService.list().then(
			function(resp){
				$scope.data=resp.data;
				$rootScope.actualListInsert = $scope.data;
			},
			function(err){
				Notification.error("No se pudo cargar la lista de proyectos");
			}
		);
	}
	
	$scope.openInsertForm=function(){
		$scope.proy={};
		$rootScope.openProyectForm(true);
	}
	
	$scope.openUpdateForm=function(proy) {
		$rootScope.selectedProy = proy;
		$rootScope.openProyectForm(false);
	}
		
	$scope.refresh();
	
	$scope.selectProyect = function(proyecto){
		$localStorage.proyecto = proyecto;
		$rootScope.relocate("/sprints");
		delete $localStorage.sprint;
	}
	
			
	$scope.eliminar=function(proy) {
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
	};

}); //End main controller