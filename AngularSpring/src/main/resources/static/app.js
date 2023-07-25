var app = angular.module('student', []);

app.controller('StudentController', function ($scope, $http, $window, $timeout) {
	$scope.currentPage = 0;
	$scope.pageSize = 1;
	$scope.keywordSemester;
	$scope.keywordName;
	$scope.keywordEmail;
	$scope.count = 0;
	var pageRange=10;

	function fetchPaginatedStudents() {
		let apiUrl = '/api/';

		if ($scope.count !== 0) {
			apiUrl += `search?pagesize=${$scope.pageSize}&offset=${$scope.currentPage}`;

			if ($scope.keywordEmail) {
				apiUrl += `&email=${$scope.keywordEmail}`;
			}

			if ($scope.keywordName) {
				apiUrl += `&fName=${$scope.keywordName}`;
			}

			if ($scope.keywordSemester) {
				apiUrl += `&semester=${$scope.keywordSemester}`;
			}
		} else {
			apiUrl += `${$scope.pageSize}/${$scope.currentPage}`;
		}

		console.log('API URL:', apiUrl);

		$http.get(apiUrl)
			.then(function (response) {
				$scope.students = response.data.content;
				$scope.totalItems = response.data.totalElements;
				$scope.getTotalPages = response.data.totalPages;
				$scope.currentPage = response.data.pageable.pageNumber;
				console.log($scope.keywordEmail);
				console.log($scope.keywordName);
			})
			.catch(function (error) {
				console.error('Error fetching paginated students:', error);
			});
	}

	fetchPaginatedStudents();

	$scope.Reset = function () {
		$scope.keywordSemester = null;
		$scope.keywordName = null;
		$scope.keywordEmail = null;
		$scope.count = 0;
		fetchPaginatedStudents();
	};

	$scope.searchKeywords = function (){
		$scope.count = 1;
		fetchPaginatedStudents();
	}

	$scope.goToPage = function (pageNumber) {
		$scope.currentPage = pageNumber;
		fetchPaginatedStudents();
	};

	$scope.getTotalPagesArray = function() {
		var firstPage = Math.max(0, $scope.currentPage - Math.floor(pageRange / 2));
		var lastPage = Math.min(firstPage + pageRange - 1, $scope.getTotalPages - 1);

		// If the last page is less than pageRange, adjust the firstPage accordingly
		if (lastPage - firstPage < pageRange - 1) {
			firstPage = Math.max(0, lastPage - pageRange + 1);
		}

		var pagesArray = [];
		for (var page = firstPage; page <= lastPage; page++) {
			pagesArray.push(page);
		}

		return pagesArray;
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
					}, 1000);
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
				}, 1000);
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
				}, 1000);
			})
			.catch(function (error) {
				console.error('Error updating student:', error);
			});
	};
});
