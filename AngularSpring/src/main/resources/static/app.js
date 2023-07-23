var app = angular.module('student', []);

    app.controller('StudentController', function ($scope, $http, $window, $timeout) {

		$scope.currentPage = 0;
		$scope.pageSize = 2;
		$scope.keyword = 0;

		function fetchPaginatedStudents() {
			$http.get(`/api/${$scope.pageSize}/${$scope.currentPage}`)
				.then(function (response) {
					$scope.students = response.data.content;
					$scope.totalItems = response.data.totalElements;
					$scope.getTotalPages = response.data.totalPages;
					console.log($scope.getTotalPages)
				})
				.catch(function (error) {
					console.error('Error fetching paginated students:', error);
				});
		}

		fetchPaginatedStudents();

		$scope.setPageSize = function (size){
			$scope.pageSize = size;
			fetchPaginatedStudents();

		}

		$scope.goToPage = function (pageNumber) {
			$scope.currentPage = pageNumber;
			fetchPaginatedStudents(); // Fetch data for the new page

		};

		// Function to create an array of page numbers for pagination links
		$scope.getTotalPagesArray = function () {
			var totalPages = $scope.getTotalPages;
			return Array.from({ length: totalPages }, (_, index) => index);
		};



		$scope.deleteStudent = function(studentID) {
			if (confirm('Are you sure you want to delete this task?')) {
				$http.delete('/api/delete/' + studentID) // Replace this URL with the actual backend API endpoint
					.then(function (response) {
						$scope.successMessage = 'Student data deleted successfully.';
						$('#SuccessModal').modal('show');
						// Reset the form fields after successful update
						$scope.student = null;
						// Set the flag to true to hide the form and show the success message
						$scope.isUpdateSuccess = true;
						$timeout(function () {
							$window.location.reload();
						}, 2000);
					})
					.catch(function (error) {
						console.error('Error deleting task:', error);
						// Handle error if the deletion fails
					});
			}
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
		$scope.update = function () {
			$http.put('/api/add', $scope.student).then(function (response) {
				$scope.successMessage = 'Student data updated successfully.';
				$('#SuccessModal').modal('show');
				// Reset the form fields after successful update
				$scope.student = null;
				// Set the flag to true to hide the form and show the success message
				$scope.isUpdateSuccess = true;
				$timeout(function () {
					$window.location.reload();
				}, 2000);

			})
				.catch(function (error) {
					console.error('Error updating student:', error);
					// Handle error if the update fails
				});
		};

	});
   
     app.controller('AddStudentController', function ($scope, $http, $window, $timeout) {
	    $scope.student = {};
	
	
	    $scope.addStudent = function () {
	        $http.post('/api/add', $scope.student).then(function (response) {
				$scope.successMessage1 = 'Student data added successfully.';
				$('#addSuccessModal').modal('show');
				// Reset the form fields after successful update
				$scope.student = null;
				// Set the flag to true to hide the form and show the success message
				$scope.isUpdateSuccess = true;
				$timeout(function () {
					$window.location.reload();
				}, 2000);
	        })
				.catch(function (error) {
					console.error('Error updating student:', error);
					// Handle error if the update fails
				});
	    };
	});

   

