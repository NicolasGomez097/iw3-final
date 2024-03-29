angular.module('iw3')

.controller('tablero', function($scope, $rootScope, 
		listService,SweetAlert,Notification,
		$localStorage){
	if(!$localStorage.sprint)
		$rootScope.relocate("sprints");
		
	$scope.titulo="Tablero del sprint '" + $localStorage.sprint.nombre+"'";
	$scope.orden = "none";
	$scope.form={};
	$scope.form.tipo = "asc";
	
	
	$scope.listas = {
			Backlog:[], 
			"To Do": [],
			"In Progress":[],
			Waiting:[],
			Done:[]};
	
	$scope.checkColor = function(prioridad){
		if(prioridad == 'Alta'){
			return 'redBackground';
		}
		if(prioridad == 'Media'){
			return 'yellowBackground';
		}
		else{
			return 'greenBackground';
		}
	}
	$scope.onDrop = function(nombreListaOrigen, srcIndex, nombreListaDestino, targetIndex) {
		var valid = false;
		var idTarea = $scope.listas[nombreListaOrigen][srcIndex].id;
		var promise;
		
		if(nombreListaOrigen == "Backlog"){
			if(nombreListaDestino != "To Do"){
				Notification.error("Solo se puede mover de Backlog a To Do");
				return false;
			}	
			promise = listService.moveToDo(idTarea);
		}
		
		if(nombreListaOrigen == "To Do"){
			if(nombreListaDestino != "In Progress" && nombreListaDestino != "Waiting"){
				Notification.error("Solo se puede mover de To Do a In Progress o Waiting");
				return false;
			}	
			
			if(nombreListaDestino == "In Progress")
				promise = listService.moveInProgress(idTarea);
			else
				promise = listService.moveWaiting(idTarea);			
		}
		
		if(nombreListaOrigen == "In Progress"){
			if(nombreListaDestino != "Waiting" && nombreListaDestino != "Done"){
				Notification.error("Solo se puede mover de In Progress a Waiting o Done");
				return false;
			}	
			
			if(nombreListaDestino == "Waiting")
				promise = listService.moveWaiting(idTarea);
			else
				promise = listService.moveDone(idTarea);			
		}
		
		if(nombreListaOrigen == "Waiting"){
			if(nombreListaDestino != "In Progress" && nombreListaDestino != "Done"){
				Notification.error("Solo se puede mover de Waiting a In Progress o Done");
				return false;
			}	
			
			if(nombreListaDestino == "In Progress")
				promise = listService.moveInProgress(idTarea);
			else
				promise = listService.moveDone(idTarea);			
		}
		
		if(nombreListaOrigen == "Done"){
			Notification.error("Las tareas de Done no pueden ser alteradas");
			return false;		
		}
		
		promise.then(
				function(resp){
					if(resp.status == 200){
						var srcList = $scope.listas[nombreListaOrigen];
						var targetList = $scope.listas[nombreListaDestino];		
						
					    targetList.splice(targetIndex, 0, srcList[srcIndex]);
					    if (srcList == targetList && targetIndex <= srcIndex) 
					    	srcIndex++;
					    srcList.splice(srcIndex, 1);
					    Notification.success("Se actualizo correctamente");
					}
				},
				function(badResp){
					if(badResp.status == 400)
						Notification.error(badResp.headers("error"));
				}
		)
		
		return true;
    };
    
	$scope.refresh=function() {
		var promise;
		
		listService.list($localStorage.sprint.id).then(
				function(resp){
					$localStorage.id_backlog=resp.data[0].id;
				},
				function(err){
					if(err.status == 404){
						var lista = {nombre:'backlog',sprint:{}};
						lista.sprint.id=$localStorage.sprint.id;
						listService.insert(lista).then(
								function(resp) {
									if(resp.status == 201){
										$localStorage.id_backlog=resp.headers("id_lista");
										Notification.success("Se creo la lista backlog");
									}
								},
								function(error) {
									if(error.status == 400)
										Notification.error(error.headers("error"));
								}
						);
					}
				}
			);
				
		if($scope.orden == "none")
			promise = listService.listBacklog($localStorage.sprint.id);
		else
			promise = listService.listOrder(
					$localStorage.sprint.id,"backlog",$scope.orden,$scope.form.tipo);
		
		promise.then(
			function(resp){
				$scope.listas.Backlog=resp.data;
				$rootScope.actualListInsert = $scope.listas.Backlog;
			},
			function(err){
				if(err.status != 404)
					Notification.error("No se pudo cargar la lista de backlog");
			}
		);

		if($scope.orden == "none")
			promise = listService.listToDo($localStorage.sprint.id);
		else
			promise = listService.listOrder(
					$localStorage.sprint.id,"to_do",$scope.orden,$scope.form.tipo);
		promise.then(
				function(resp){
					$scope.listas["To Do"]=resp.data;
				},
				function(err){
					if(err.status != 404)
						Notification.error("No se pudo cargar la lista de sprints");
				}
			);
		
		if($scope.orden == "none")
			promise = listService.listInProgress($localStorage.sprint.id);
		else
			promise = listService.listOrder(
					$localStorage.sprint.id,"in_progress",$scope.orden,$scope.form.tipo);
		
		promise.then(
				function(resp){
					$scope.listas["In Progress"]=resp.data;
				},
				function(err){
					if(err.status != 404)
						Notification.error("No se pudo cargar la lista de sprints");
				}
			);
		
		if($scope.orden == "none")
			promise = listService.listWaiting($localStorage.sprint.id);
		else
			promise = listService.listOrder(
					$localStorage.sprint.id,"waiting",$scope.orden,$scope.form.tipo);
		promise.then(
				function(resp){
					$scope.listas.Waiting=resp.data;
				},
				function(err){
					if(err.status != 404)
						Notification.error("No se pudo cargar la lista de sprints");
				}
			);
		
		if($scope.orden == "none")
			promise = listService.listDone($localStorage.sprint.id);
		else
			promise = listService.listOrder(
					$localStorage.sprint.id,"done",$scope.orden,$scope.form.tipo);
		promise.then(
				
				function(resp){
					$scope.listas.Done=resp.data;
				},
				function(err){
					if(err.status != 404)
						Notification.error("No se pudo cargar la lista de sprints");
				}
			);
	}
	
	$scope.openInsertTareaForm=function(){
		$rootScope.openModalTareaForm(true);
	}
	
	$scope.openUpdateTareaForm=function(task,nombreLista) {
		if(nombreLista != "Backlog"){
			Notification.error("Solo se puede modificar una tarea en el Backlog.");
			return;
		}
		$rootScope.selectedTask = task;
		$rootScope.openModalTareaForm(false);
	}
		
	$scope.refresh();
	
	$scope.selectSprint = function(sprint){
		$localStorage.sprint = sprint;
		$rootScope.relocate("/tablero");
	}
	
	
	
			
	/*$scope.eliminar=function(proy) {
		SweetAlert.swal({
			  title: "Eliminar proyecto",
			  text: "Está seguro que desea eliminar el proyecto <strong>"+proy.nombre+"</strong>?",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Si, eliminar proyecto!",
			  cancelButtonText: "No",
			  closeOnConfirm: true,
			  html: true
			}, function(confirm){
				if(confirm) {
					proyectosService.delete(proy.id).then(
						function(resp){
							if(resp.status===200){
								Notification.success("Se elimino correctamente");
								$scope.refresh();
							}else{
								Notification.error("No se pudo eliminar");
							}
						}
					);
				}
			});
	};*/
	
}); //End main controller