var app = angular.module('student', ['ngTable']);


app.controller('LibraryController', function ($scope, $http,NgTableParams, $window, $timeout) {
	$scope.showLibrary = false;
	$scope.currentPage = 0;
	$scope.pageSize = 10000;
	$scope.keywordSemester;
	$scope.keywordName;
	$scope.keywordEmail;
	$scope.count = 0;

	function fetchStudents() {
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

		$http.get(apiUrl)
			.then(function (response) {
				$scope.students = response.data.content;
				var data = response.data.content;
				console.log("I was Here!!")
				$scope.tableParams = new NgTableParams(
					{
						page: 1, // Show the first page
						count: 10, // Number of items per page
						sorting: {
							fName: 'asc' // Default sorting by 'First Name' column in ascending order
						}
					},
					{
						dataset: data // Set the fetched data as the dataset
					}
				);
			})
			.catch(function (error) {
				console.error('Error fetching paginated students:', error);
			});
	}

	function fetchBook() {
		let apiUrl1 = '/api/getBook';
		$http.get(apiUrl1)
			.then(function (response) {
				$scope.books = response.data;
				var data1 = response.data;
				console.log("I was also Here!!")
				$scope.tableParams1 = new NgTableParams(
					{
						page: 1, // Show the first page
						count: 10, // Number of items per page
						sorting: {
							bcode: 'asc' // Default sorting by 'First Name' column in ascending order
						}
					},
					{
						dataset: data1 // Set the fetched data as the dataset
					}
				);
			})
			.catch(function (error) {
				console.error('Error fetching paginated students:', error);
			});
	}

	$scope.triggerHome = function () {
		$scope.showTable = null;
		$scope.showLibrary = false;
		fetchStudents();
	}

	$scope.triggerStudent = function () {
		$scope.showTable = true;
		$scope.showLibrary = true;
		fetchStudents();
	}
	$scope.triggerBook = function () {
		$scope.showLibrary = true;
		$scope.showTable = false;
		fetchBook();
	}

	$scope.Reset = function () {
		$scope.keywordSemester = null;
		$scope.keywordName = null;
		$scope.keywordEmail = null;
		$scope.count = 0;
		fetchStudents();
	};

	$scope.searchKeywords = function () {
		$scope.count = 1;
		fetchStudents();
	}

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
					console.error('Error deleting Student Data:', error);
				});
		}
	};

	$scope.deleteBook = function (BookID) {
		if (confirm('Are you sure you want to delete this task?')) {
			console.log(BookID)
			$http.delete('/api/deleteBook/' + BookID)
				.then(function () {
					$scope.successMessage = 'Book deleted sucessfully.';
					$('#SuccessModal').modal('show');
					$scope.book = null;
					$scope.isUpdateSuccess = true;
					$timeout(function () {
						$window.location.reload();
					}, 1000);
				})
				.catch(function (error) {
					console.error('Error deleting Book:', error);
				});
		}
	};

	$scope.openAddStudentModal = function () {
		console.log("I was Here!!")
		$('#addStudentModal').modal('show', 'keyboard', 'focus');
	};


//Update Student
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
				updateMessage(true);
			})
			.catch(function (error) {
				console.error('Error updating student:', error);
			});
	};

	$scope.doUpdateBook = function (bookid) {
		const bookToUpdate = $scope.books.find(book => book.bcode === bookid);
		if (bookToUpdate) {
			$scope.book = {
				bcode: bookToUpdate.bcode,
				bname: bookToUpdate.bname,
				bauthor: bookToUpdate.bauthor,
				year: bookToUpdate.year,
				status: bookToUpdate.status,
				studentDetails: bookToUpdate.studentDetails
			};

			$('#updateBookModal').modal('show', 'keyboard', 'focus');
		} else {
			console.error('Student not found:', studentID);
		}
	};

	$scope.updateBook = function () {
		$http.put('/api/addBook', $scope.book)
			.then(function () {
				updateMessage(false);
			})
			.catch(function (error) {
				console.error('Error updating Book:', error);
			});
	}

	function updateMessage(from) {
		$scope.successMessage = 'Updated successful.';
		$('#updateStudentModal').modal('hide');
		$('#updateBookModal').modal('hide');
		$('#SuccessModal').modal('show');
		$scope.book = null;
		$timeout(function () {
			$('#SuccessModal').modal('hide');
			if (from){
				fetchStudents();
			}else{
				$scope.triggerBook();
			}
		}, 1000);
	};

//Add Student
	$scope.addStudent = function () {
		$http.post('/api/add', $scope.student)
			.then(function (response) {
				$scope.successMessage1 = 'Student data added successfully.';
				$('#addSuccessModal').modal('show');
				$scope.student = null;
				$timeout(function () {
					$window.location.reload();
				}, 1000);
			})
			.catch(function (error) {
				console.error('Error updating student:', error);
			});
	}
}	);