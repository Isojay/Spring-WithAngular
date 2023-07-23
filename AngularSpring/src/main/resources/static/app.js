var app = angular.module('student', []);

app.controller('StudentController', function ($scope, $http, $window, $timeout) {
	$scope.currentPage = 0;
	$scope.pageSize = 2;
	$scope.keyword = null;
	$scope.SearchCategory = null;

	function fetchPaginatedStudents() {
		if ($scope.SearchCategory !== null) {
			$http.get(`/api/${$scope.SearchCategory}/${$scope.keyword}/${$scope.pageSize}/${$scope.currentPage}`)
				.then(function (response) {
					$scope.students = response.data.content;
					$scope.totalItems = response.data.totalElements;
					$scope.getTotalPages = response.data.totalPages;
				})
				.catch(function (error) {
					console.error('Error fetching paginated students:', error);
				});
		} else {
			$http.get(`/api/${$scope.pageSize}/${$scope.currentPage}`)
				.then(function (response) {
					$scope.students = response.data.content;
					$scope.totalItems = response.data.totalElements;
					$scope.getTotalPages = response.data.totalPages;
					$scope.pageSize = response.data.size;
				})
				.catch(function (error) {
					console.error('Error fetching paginated students:', error);
				});
		}
	}
	fetchPaginatedStudents();

	$scope.Reset = function () {
		$scope.SearchCategory = null;
		fetchPaginatedStudents();
	};

	$scope.searchWord = function (word) {
		$scope.keyword = word;
		fetchPaginatedStudents();
	};

	$scope.setCategory = function (size) {
		$scope.SearchCategory = size;
	};

	$scope.goToPage = function (pageNumber) {
		$scope.currentPage = pageNumber;
		fetchPaginatedStudents();
	};

	$scope.getTotalPagesArray = function () {
		var totalPages = $scope.getTotalPages;
		return Array.from({ length: totalPages }, (_, index) => index);
	};

	$scope.deleteStudent = function (studentID) {
		if (confirm('Are you sure you want to delete this task?')) {
			$http.delete('/api/delete/' + studentID)
				.then(function (response) {
					$scope.successMessage = 'Student data deleted successfully.';
					$('#SuccessModal').modal('show');
					$scope.student = null;
					$scope.isUpdateSuccess = true;
					$timeout(function () {
						$window.location.reload();
					}, 2000);
				})
				.catch(function (error) {
					console.error('Error deleting task:', error);
				});
		}
	};

	$scope.openAddStudentModal = function () {
		$('#addStudentModal').modal('show', 'keyboard', 'focus');
	};

	$scope.doUpdate = function (studentID) {
		const studentToUpdate = $scope.students.find(student => student.id === studentID);

		if (studentToUpdate) {
			$scope.student = {
				id: studentToUpdate.id,
				fName: studentToUpdate.fName,
				lName: studentToUpdate.lName,
				email: studentToUpdate.email,
				semester: studentToUpdate.semester
			};

			$('#updateStudentModal').modal('show', 'keyboard', 'focus');
		} else {
			console.error('Student not found:', studentID);
		}
	};

	$scope.update = function () {
		$http.put('/api/add', $scope.student)
			.then(function (response) {
				$scope.successMessage = 'Student data updated successfully.';
				$('#SuccessModal').modal('show');
				$scope.student = null;
				$scope.isUpdateSuccess = true;
				$timeout(function () {
					$window.location.reload();
				}, 2000);
			})
			.catch(function (error) {
				console.error('Error updating student:', error);
			});
	};
});

app.controller('AddStudentController', function ($scope, $http, $window, $timeout) {
	$scope.student = {};

	$scope.addStudent = function () {
		$http.post('/api/add', $scope.student)
			.then(function (response) {
				$scope.successMessage1 = 'Student data added successfully.';
				$('#addSuccessModal').modal('show');
				$scope.student = null;
				$scope.isUpdateSuccess = true;
				$timeout(function () {
					$window.location.reload();
				}, 2000);
			})
			.catch(function (error) {
				console.error('Error updating student:', error);
			});
	};
});
