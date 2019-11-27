angular.module('iw3')
.controller('updateProyectos', function($scope, $rootScope, proyectosService,Notification,$uibModalInstance){	
	
	$scope.proyFormTitulo = $scope.SuccessBtnText ="Modificar";
	
	$scope.proy = JSON.parse(JSON.stringify($rootScope.selectedProy));
	//$scope.proy = $rootScope.selectedProy;
	
	$scope.success=function() {		
		proyectosService.update($scope.proy).then(
			function(resp){
				if(resp.status===200){
					Notification.success("Se actualizó con exito");
				}else{
					Notification.error("No se pudo actualizar");					
				}				
			},
			function(err){}
		);
		$scope.closeModal();
	}	
	
	$scope.closeModal = function(){
		if($rootScope.InsertProyOpen){
			$uibModalInstance.dismiss(true);
			$rootScope.InsertProyOpen = false;
		}
	}
});