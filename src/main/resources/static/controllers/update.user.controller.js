angular.module('iw3')

.controller('updateUser', function($scope, $rootScope, 
		usuarioService,SweetAlert,Notification,
		$localStorage){
	
	$scope.data=[];
	
	$scope.user={name:"", password:"" , passwordConfirmation:""};
	$scope.user.name= $localStorage.userdata.name;
	
	$scope.updateUser=function(usuario) {
		if(usuario.password!=usuario.passwordConfirmation){
			Notification.error("El password ingresado en ambos campos no coincide");
		}
		else{
			usuarioService.update(usuario).then{
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
		}
	}
	
	$scope.closeModal = function(){
		if($rootScope.changeInformationOpen){
			$uibModalInstance.dismiss(true);
			$rootScope.changeInformationOpen = false;
		}
	}
		
			

}); //End main controller