angular.module('iw3')
.controller('updateTarea', function($scope, $rootScope, tareasService,Notification,$uibModalInstance, $localStorage){	
	
	$scope.tareaFormTitulo = $scope.SuccessBtnText ="Editar";
	$scope.task ={nombre:$rootScope.selectedTask.nombre, id:$rootScope.selectedTask.id, lista:{},  prioridad:$rootScope.selectedTask.prioridad,
			estimacion:$rootScope.selectedTask.estimacion};
	
	
/*	$scope.deleteTask = function($scope.task){
		console.log(task)
		tareasService.deleteTask($scope.task.id).then(
				function(resp){
					console.log(resp);
					if(resp.status===201){
						Notification.success("Se elimino con exito");
					}
				},
				function(err){
					Notification.error(err.headers("error"));
				}
		);
		$scope.closeModal();
		
	}*/
	$scope.success=function() {		
		
		if($scope.task.nombre == ""){
			Notification.error("El nombre no puede ser vacio");
			return;
		}
		console.log($scope.task);
		tareasService.update($scope.task).then(
			function(resp){
				console.log(resp);
				if(resp.status===201){
					Notification.success("Se modifico con exito");
				}
			},
			function(err){
				Notification.error(err.headers("error"));
			}
		);
		$scope.closeModal();
	}	
	
	$scope.closeModal = function(){
		if($rootScope.UpdateTareaOpen){
			$uibModalInstance.dismiss(true);
			$rootScope.UpdateTareaOpen = false;
		}
	}
});