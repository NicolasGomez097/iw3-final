angular.module('iw3')
.controller('updateTarea', function($scope, $rootScope, tareasService,Notification,$uibModalInstance, $localStorage){	
	
	$scope.tareaFormTitulo = $scope.SuccessBtnText ="Editar";
	$scope.task ={nombre:$rootScope.selectedTask.nombre, id:$rootScope.selectedTask.id, lista:{},  
			prioridad:$rootScope.selectedTask.prioridad};
	
	if($rootScope.selectedTask.estimacion)
		$scope.task.estimacion = ""+$rootScope.selectedTask.estimacion;
	else
		$scope.task.estimacion = "1";
	
	$scope.success=function() {		
		
		if($scope.task.nombre == ""){
			Notification.error("El nombre no puede ser vacio");
			return;
		}
		
		$scope.task.lista.id = $localStorage.id_backlog;
		
		tareasService.update($scope.task).then(
			function(resp){
				if(resp.status===200){
					Notification.success("Se modifico con exito");
					updateTarea();
				}
			},
			function(err){
				Notification.error(err.headers("error"));
			}
		);
		$scope.closeModal();
	}	
	
	$scope.closeModal = function(){
		if($rootScope.modalTareaOpen){
			$uibModalInstance.dismiss(true);
			$rootScope.modalTareaOpen = false;
		}
	}
	
	var updateTarea = function(){
		for(var clave in $rootScope.selectedTask)
			$rootScope.selectedTask[clave] = $scope.task[clave];
	}
});