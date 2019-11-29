angular.module('iw3')

.config(function($locationProvider, $routeProvider, $localStorageProvider, $httpProvider) {
	$locationProvider.hashPrefix('!');
	
	$localStorageProvider.setKeyPrefix('iw3');
	
	$httpProvider.defaults.withCredentials = true;
	
	$httpProvider.interceptors.push('APIInterceptor');
	
	
	$routeProvider
	
	.when('/',{
		templateUrl: 'views/test.html',
		controller:'DemoController'
	})
	.when('/proyectos',{
		templateUrl: 'views/proyectos.html',
		controller:'proyectos'
	})
	.when('/sprints',{
		templateUrl: 'views/sprints.html',
		controller:'sprints'
	})	
	.when('/tablero',{
		templateUrl: 'views/tablero.html',
		controller:'tablero'
	})
	.when('/test',{
		templateUrl: 'views/test.html',
		controller:'DemoController'
	})
	
	.otherwise({
		redirectTo: '/'
	})
});

