angular.module('iw3')
.controller('insertProyectos', function($scope, $rootScope, proyectosService,Notification,$uibModalInstance){	
	
	$scope.prodFormTitulo = $scope.SuccessBtnText ="Insertar";
	
	
	$scope.success=function() {		
		proyectosService.insert($scope.proy).then(
			function(resp){
				if(resp.status===201){
					Notification.success("Se inserto con exito");
				}else{
					Notification.error("No se pudo insertar");
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