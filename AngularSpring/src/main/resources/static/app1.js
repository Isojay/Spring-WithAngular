var app = angular.module('student', []);

	app.service('DataService', function () {
    var data = {};
    var studentID = null; // Initialize studentID as null

	    return {
		        getData: function () {
		            return data;
		        },
		        setData: function (newData) {
		            data = newData;
		        },
		        getStudentID: function () {
		            return studentID;
		        },
		        setStudentID: function (id) {
		            studentID = id;
		            console.log('Student ID set to:', studentID);
		        }
	    	};
	});

	
	//first page

    app.controller('StudentController', function ($scope, $http, $window,DataService) {
		
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
		
		$scope.doUpdate = function(studentID){
			
			DataService.setStudentID(studentID);
			$window.location.href = '/add';
			
		}
    });
   
     app.controller('AddStudentController', function ($scope, $http, $window,DataService) {
	    $scope.student = {};
	
	 var studentID = DataService.getStudentID();
	  console.log('Received studentID:', studentID);

    if (studentID) {
        // Fetch the student data based on the selected ID and populate the form for update
        $http.get('/api/students/' + studentID) // Replace this with the actual endpoint to fetch a specific student
            .then(function (response) {
                $scope.student = response.data;
            })
            .catch(function (error) {
                console.error('Error fetching student data:', error);
            });
    }

    $scope.addStudent = function () {
        if (studentID) {
            // Perform update (PUT) if the ID is available
            $http.put('/api/update/' + studentID, $scope.student) // Replace this with the actual endpoint to update a student
                .then(function (response) {
                    console.log('Student updated successfully:', response.data);
                    // Handle success, if required
                })
                .catch(function (error) {
                    console.error('Error updating student:', error);
                    // Handle error if the update fails
                });
        } else {
            // Perform create (POST) if the ID is not available
            $http.post('/api/add', $scope.student)
                .then(function (response) {
                    console.log('Student added successfully:', response.data);
                    // Handle success, if required
                })
                .catch(function (error) {
                    console.error('Error adding student:', error);
                    // Handle error if the create fails
                });
        }
    };
	  
	});
    
   