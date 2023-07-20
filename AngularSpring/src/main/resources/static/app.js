var app = angular.module('student', []);

	
	//first page

    app.controller('StudentController', function ($scope, $http, $window) {
		
        $http.get('/api/students') // Replace this URL with your Spring Boot API endpoint
            .then(function (response) {
                $scope.students = response.data;
            }, function (error) {
                console.error('Error fetching statuses:', error);
            });
        $scope.deleteStudent = function(studentID) {
			if (confirm('Are you sure you want to delete this task?')) {
				$http.delete('/api/delete/' + studentID) // Replace this URL with the actual backend API endpoint
					.then(function (response) {
						console.log('Task deleted successfully:', response.data);
						// Assuming you want to refresh the task list after deletion
						$window.location.reload();
					})
					.catch(function (error) {
						console.error('Error deleting task:', error);
						// Handle error if the deletion fails
					});
			}
		}

		$scope.doUpdate = function (studentID){
				$scope.id = studentID;
		}

		 $scope.openAddStudentModal = function () {
      		  $('#addStudentModal').modal('show','keyboard','focus');
  		  };
		$scope.doUpdate = function (studentID) {
			// Find the student in the students array based on the studentID
			const studentToUpdate = $scope.students.find(student => student.id === studentID);

			// If the student is found, populate the form fields in the update modal
			if (studentToUpdate) {
				$scope.student = {
					id: studentToUpdate.id,
					fName: studentToUpdate.fName,
					lName: studentToUpdate.lName,
					email: studentToUpdate.email,
					semester: studentToUpdate.semester
				};

				// Show the update modal
				$('#updateStudentModal').modal('show', 'keyboard', 'focus');
			} else {
				console.error('Student not found:', studentID);
			}
		};
		$scope.update = function (){
			$http.put('/api/add', $scope.student).then(function (response) {

				$window.location.reload();
			}).catch(function (error) {
				console.error('Error updating student:', error);
				// Handle error if the update fails
			});
		};

	});
   
     app.controller('AddStudentController', function ($scope, $http, $window) {
	    $scope.student = {};
	
	
	    $scope.addStudent = function () {
	        $http.post('/api/add', $scope.student).then(function (response) {
				$window.location.reload();
	        });
	    };
	});

   

