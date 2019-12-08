angular.module('iw3')

.controller('updateUser', function($scope, $rootScope, 
		usuarioService,SweetAlert,Notification,
		$localStorage,$uibModalInstance){
	
	$scope.data=[];
	
	$scope.user={username:"", password:"" , passwordConfirmation:""};
	$scope.user.username= $localStorage.userdata.name;
	
	$scope.updateUser=function(usuario) {
		if(usuario.password!=usuario.passwordConfirmed){
			Notification.error("El password ingresado en ambos campos no coincide");
		}
		else{
			usuarioService.update(usuario).then(
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
	
	
	$scope.closeModal = function(){
		if($rootScope.changeUserInformationOpen){
			$uibModalInstance.dismiss(true);
			$rootScope.changeUserInformationOpen = false;
		}
	}
		
			

}); //End main controller