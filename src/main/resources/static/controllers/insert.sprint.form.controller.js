angular.module('iw3')
.controller('insertSprints', function($scope, $rootScope, sprintsService,Notification,$uibModalInstance, $localStorage){	
	
	$scope.sprintFormTitulo = $scope.SuccessBtnText ="Insertar";
	$scope.sprint ={nombre:"",proyecto:{}};
	
	$scope.success=function() {		
		if($scope.sprint.nombre == ""){
			Notification.error("El nombre no puede ser vacio");
			return;
		}
		
		$scope.sprint.proyecto.id= $localStorage.proyecto.id;

				
		sprintsService.insert($scope.sprint).then(
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
		if($rootScope.InsertSprintOpen){
			$uibModalInstance.dismiss(true);
			$rootScope.InsertSprintOpen = false;
		}
	}
});