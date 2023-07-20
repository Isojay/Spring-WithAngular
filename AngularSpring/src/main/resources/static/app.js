var app = angular.module('student', []);

	
	//first page

    app.controller('StudentController', function ($scope, $http, $window) {
		
        $http.get('/api/students') // Replace this URL with your Spring Boot API endpoint
            .then(function (response) {
                $scope.students = response.data;
            }, function (error) {
                console.error('Error fetching statuses:', error);
            });
        $scope.deleteStudent = function(studentID){
			if (confirm('Are you sure you want to delete this task?')) {
		      $http.delete('/api/delete/' + studentID) // Replace this URL with the actual backend API endpoint
		        .then(function(response) {
		          console.log('Task deleted successfully:', response.data);
		          // Assuming you want to refresh the task list after deletion
		          $window.location.reload();
		         })
		        .catch(function(error) {
		          console.error('Error deleting task:', error);
		          // Handle error if the deletion fails
		        });
		    }
		}
		 $scope.openAddStudentModal = function () {
      		  $('#addStudentModal').modal('show');
  		  };
    });
   
     app.controller('AddStudentController', function ($scope, $http, $window) {
	    $scope.student = {};
	
	
	    $scope.addStudent = function () {
	        $http.post('/api/add', $scope.student).then(function (response) {
	            $window.location.href = '/';
	        });
	    };
	});
    /*
  app.controller('updateStudentController',function ($scope, $http, $window){
	  
	  $scope.doUpdate = function(){
		  $http.put('/api/add',$scope.student)
		  
	  }
	  
	  
  });*/
   

