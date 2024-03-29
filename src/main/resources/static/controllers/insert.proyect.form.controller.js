angular.module('iw3')
.controller('insertProyectos', function($scope, $rootScope, proyectosService,Notification,$uibModalInstance){	
	
	$scope.proyFormTitulo = $scope.SuccessBtnText ="Insertar";
	$scope.proy ={nombre:""};
	
	$scope.success=function() {		
		if($scope.proy.nombre == ""){
			Notification.error("El nombre no puede ser vacio");
			return;
		}
				
		proyectosService.insert($scope.proy).then(
			function(resp){
				if(resp.status===201){
					Notification.success("Se inserto con exito");
					$scope.proy.id = resp.headers("id_proyecto");
					$rootScope.actualListInsert.push($scope.proy);
				}
			},
			function(err){
				Notification.error(err.headers("error"));
			}
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