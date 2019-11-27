angular.module('iw3', [ 'ngRoute', 'ngSanitize', 'ngAnimate', 'ngTouch',
		'ui.bootstrap', 'ngSanitize', 'angularUtils.directives.dirPagination',
		'angucomplete-alt', 'ngLoadingSpinner', 'ui.select', 'ngDragDrop',
		'ui-notification', 'chart.js', 'ngStomp', 'uiSwitch', 'xeditable',
		'ngStorage', 'oitozero.ngSweetAlert', 'dialogs.main' ]);


var app = angular.module('iw3');

app.constant('URL_API_BASE', '/api/v1/')
app.constant('URL_BASE', '/')


app.run(function($rootScope, $location, $uibModal, coreService, $localStorage, $stomp) {
	
	$rootScope.stomp=$stomp;
	
	$rootScope.oldLoc=false;
	$rootScope.relocate=function(loc) {
		$rootScope.oldLoc=$location.$$path;
		$location.path(loc);
	};
	
	
	$rootScope.loginOpen = false;
	$rootScope.user={};
	$rootScope.cleanLoginData = function() {
		$rootScope.autenticado = false;
		$rootScope.user = {
				name : "",
				password : "",
				roles : []
			};
	};
	
	$rootScope.openLoginForm = function() {
		if (!$rootScope.loginOpen) {
			$rootScope.cleanLoginData();
			$rootScope.loginOpen = true;
			$uibModal.open({
				animation : true,
				backdrop : 'static',
				keyboard : false,
				templateUrl : 'views/loginForm.html',
				controller : 'LoginFormController',
				size : 'md'
			});
		}
	};
	
	$rootScope.InsertProyOpen = false;
	$rootScope.openProyectForm = function(insert) {		
		var control;	
		console.log($rootScope.InsertProyOpen);
		if(insert)
			control = "insertProyectos";
		else
			control = "updateProyectos"
		
		if (!$rootScope.InsertProyOpen) {
			$rootScope.InsertProyOpen = true;
			$uibModal.open({
				animation : true,
				backdrop : 'static',
				keyboard : false,
				templateUrl : 'views/ProyectForm.html',
				controller : control,
				size : 'md'
			});
		}
	};
	
	$rootScope.authInfo=function(cb,rolif,cbrolif) {
		//Si el usuario está en el rol indicado en rolif, se ejecuta la callback cbrolif
		if(rolif && cbrolif && $rootScope.inRole('ROLE_'+rolif) )
			cb=cbrolif;
		if(cb)
			$rootScope.cbauth=cb;
		if($rootScope.cbauth && $localStorage.logged)
			$rootScope.cbauth();
	}

	
	coreService.authInfo();
}); //End run


app.controller('controlador1', function($scope, $timeout, proyectosService, $log, $interval) {


	$scope.datos=[];

	
	$scope.refresh=function(){
		proyectosService.list().then(
				function(resp){
					$log.log(resp)
					$scope.datos=resp.data;
					
				}
				,
				function(err){
					$log.log(err)
				}
		);
	};
	
	//$scope.refresh();
	
	
	
	
	$scope.numero = 13;

	$timeout(function() {
		$scope.numero = 28;
	}, 5000);
	
	$interval(function() {
		$scope.refresh();
	}, 5000);
}

);