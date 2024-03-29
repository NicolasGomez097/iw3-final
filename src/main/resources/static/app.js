angular.module('iw3', [ 'ngRoute', 'ngSanitize', 'ngAnimate', 'ngTouch',
		'ui.bootstrap', 'ngSanitize', 'angularUtils.directives.dirPagination',
		'angucomplete-alt', 'ngLoadingSpinner', 'ui.select', 'ngDragDrop',
		'ui-notification', 'chart.js', 'ngStomp', 'uiSwitch', 'xeditable',
		'ngStorage', 'oitozero.ngSweetAlert', 'dialogs.main','dndLists']);


var app = angular.module('iw3');

app.constant('URL_API_BASE', '/api/v1/')
app.constant('URL_BASE', '/')


app.run(function($rootScope, $location, $uibModal, coreService, $localStorage) {
		
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
	
	
	if(!$localStorage.logged){
		$localStorage.logged = false;
		$localStorage.userdata = {};
		$rootScope.autenticado = false;
		$rootScope.openLoginForm();
	}
		
	$rootScope.InsertProyOpen = false;
	$rootScope.openProyectForm = function(insert) {	
		var control;	
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
	
	$rootScope.InsertSprintOpen = false;
	$rootScope.openSprintForm = function(insert) {		
		var control;	
		if(insert)
			control = "insertSprints";
		else
			control = "updateSprints"
		
		if (!$rootScope.InsertSprintOpen) {
			$rootScope.InsertSprintOpen = true;
			$uibModal.open({
				animation : true,
				backdrop : 'static',
				keyboard : false,
				templateUrl : 'views/SprintForm.html',
				controller : control,
				size : 'md'
			});
		}
	};
	
	
	$rootScope.modalTareaOpen = false;
	$rootScope.openModalTareaForm = function(insert) {		
		var control;	
		if(insert)
			control = "insertTarea";
		else
			control = "updateTarea"
		
		if (!$rootScope.modalTareaOpen) {
			$rootScope.modalTareaOpen = true;
			$uibModal.open({
				animation : true,
				backdrop : 'static',
				keyboard : false,
				templateUrl : 'views/TareaForm.html',
				controller : control,
				size : 'md'
			});
		}
	};
	
	$rootScope.changeUserInformationOpen= false;
	$rootScope.openChangeUserInformationForm = function() {		
		if (!$rootScope.changeUserInformationOpen) {
			$rootScope.changeUserInformationOpen = true;
			$uibModal.open({
				animation : true,
				backdrop : 'static',
				keyboard : false,
				templateUrl : 'views/userInformationForm.html',
				controller : 'updateUser',
				size : 'md'
			});
		}
	};
		
	$rootScope.isProyectSelected = function(){
		return $localStorage.proyecto
	}
	
	$rootScope.isSprintSelected = function(){
		return $localStorage.sprint
	}
	
	$rootScope.actualListInsert = null;
	
});