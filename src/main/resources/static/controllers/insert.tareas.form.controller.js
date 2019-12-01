angular.module('iw3')
.controller('insertTarea', function($scope, $rootScope, tareasService,Notification,$uibModalInstance, $localStorage){	
	
	$scope.tareaFormTitulo = $scope.SuccessBtnText ="Insertar";
	$scope.task ={nombre:"",lista:{},prioridad:"Baja",estimacion:'1'};
	
	$scope.success=function() {		
		if($scope.task.nombre == ""){
			Notification.error("El nombre no puede ser vacio");
			return;
		}
		
		$scope.task.lista.id= $localStorage.id_backlog;

				
		tareasService.insert($scope.task).then(
			function(resp){
				console.log(resp);
				if(resp.status===201){
					Notification.success("Se inserto con exito");
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
});