angular.module('iw3')
.controller('LoginFormController', 
		function LoginFormController(
				$rootScope, $scope, $localStorage,
				$uibModalInstance, coreService,
				$log,Notification) {
	
			$scope.title="Ingreso";
			
			$scope.user={name:"",password:""};
			
			
			$scope.login = function () {
				
				var apiResponse;
				
				apiResponse = coreService.loginJwt($scope.user);
				
				apiResponse.then(							
					function(resp){ 
						if(resp.status===200) {
							$localStorage.userdata=resp.data;
							$localStorage.logged=true;
							$rootScope.autenticado=true;	
							$rootScope.loginOpen = false;
							$uibModalInstance.dismiss(true);
							Notification.success("Login correcto");
						}else{
							$rootScope.autenticado=false;	
							delete $localStorage.userdata;
							$localStorage.logged=false;
						}
					},
					
					function(respErr){
						Notification.error("Datos de usuario incorrecto");
						$log.log(respErr);
					}						
				)				
			  };  
			  
		}); //End LoginFormController