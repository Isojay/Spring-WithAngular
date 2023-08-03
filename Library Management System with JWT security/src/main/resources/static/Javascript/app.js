var app = angular.module('student', ['ngTable']);


app.controller('LibraryController', function ($scope, $http,NgTableParams, $window, $timeout) {
	$scope.login = {};
	$scope.showLibrary = false;
	$scope.currentPage = 0;
	$scope.pageSize = 10000;
	$scope.keywordSemester = '';
	$scope.keywordName = '';
	$scope.keywordEmail = '';
	$scope.count = 0;
	$scope.statusId = null;
	$scope.loginStatus = false;

	$scope.logInModal = function () {
		$('#logInModal').modal('show');
	}
	$scope.logInData = function () {
		$http.post('/api/auth/login', $scope.login)
			.then(function (response) {
				$scope.loginStatus = true;
				$scope.token = response.data.token;
				$scope.role = response.data.role;
				$scope.currentSTDid = null;
				$('#logInModal').modal('hide');
				console.log($scope.role)
			})
			.catch(function (error) {
				console.error('Error in Logging in user ', error);
			});

	}

	$scope.logOutModal = function () {
		$('#logOutModal').modal('show');
	}


	$scope.logOut = function () {
		$scope.loginStatus = false;
		$scope.token = null;
		$scope.role = null;
		$scope.currentSTDid = null;
		$('#logOutModal').modal('hide');
		$window.location.reload();
	}

	function fetchStudents() {
		let apiUrl = '/api/students/';

		if ($scope.count !== 0) {
			apiUrl += `search?pagesize=${$scope.pageSize}&offset=${$scope.currentPage}`;

			if ($scope.keywordEmail) {
				apiUrl += `&email=${$scope.keywordEmail}`;
			}

			if ($scope.keywordName) {
				apiUrl += `&fname=${$scope.keywordName}`;
			}

			if ($scope.keywordSemester) {
				apiUrl += `&semester=${$scope.keywordSemester}`;
			}
		} else {
			apiUrl += `${$scope.pageSize}/${$scope.currentPage}`;
		}

		$http.get(apiUrl)
			.then(function (response) {
				console.log("Reset")
				$scope.students = response.data.content;
				const data = response.data.content;
				$scope.tableParams = new NgTableParams(
					{
						page: 1, // Show the first page
						count: 10, // Number of items per page
						sorting: {
							fname: 'asc' // Default sorting by 'First Name' column in ascending order
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
		let apiUrl1 = '/api/books/';
		if($scope.statusId === null){
			apiUrl1 += 'getBook';
		}else{
			apiUrl1 += 'getByStatus/'+$scope.statusId ;
		}

		$http.get(apiUrl1)
			.then(function (response) {
				$scope.books = response.data;
				const data1 = response.data;
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
		console.log("Reset")
		$scope.keywordSemester = null;
		$scope.keywordName = null;
		$scope.keywordEmail = null;
		$scope.count = 0;
		$scope.triggerStudent();
	};

	$scope.searchKeywords = function () {
		console.log("Keywords")
		$scope.count = 1;
		$scope.triggerStudent();
	}

	$scope.deleteStudent = function (studentID) {
		if (confirm('Are you sure you want to delete this task?')) {
			$http.delete('/api/students/delete/' + studentID)
				.then(function (response) {
					deleteMessage(true)
				})
				.catch(function (error) {
					console.error('Error deleting Student Data:', error);
				});
		}
	};

	$scope.deleteBook = function (BookID) {
		if (confirm('Are you sure you want to delete this task?')) {
			console.log(BookID)
			$http.delete('/api/books/deleteBook/' + BookID)
				.then(function () {
					deleteMessage(false);
				})
				.catch(function (error) {
					console.error('Error deleting Book:', error);
				});
		}
	};

	$scope.showDetails= function (stdId){
		$http.get('/api/books/getDetails/'+ stdId)
			.then(function (response){
				console.log("I am in Details")
				$scope.details = response.data;
				$('#showDetailsModal').modal('show', 'keyboard', 'focus');
			})
			.catch(function (error) {
				console.error('Error Not found :', error);
			});
	}

//Update Student
	$scope.doUpdate = function (studentID) {
		const studentToUpdate = $scope.students.find(student => student.id === studentID);
		if (studentToUpdate) {
			$scope.student = {
				id: studentToUpdate.id,
				fname: studentToUpdate.fname,
				lname: studentToUpdate.lname,
				email: studentToUpdate.email,
				semester: studentToUpdate.semester
			};

			$('#updateStudentModal').modal('show', 'keyboard', 'focus');
		} else {
			console.error('Student not found:', studentID);
		}
	};

	$scope.update = function () {
		$http.put('/api/students/add', $scope.student)
			.then(function (response) {
				$scope.successMessage = 'Student data updated successfully.';
				$('#updateStudentModal').modal('hide');
				$('#SuccessModal').modal('show');
				$scope.student = {};
				$timeout(function () {
					$('#SuccessModal').modal('hide');
					fetchStudents();
				}, 1000);
			})
			.catch(function (error) {
				var from = "studentUpdate"
				errorMessage(from);
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
		$http.put('/api/books/addBook', $scope.book, { responseType: 'text' })
			.then(function (response) {
				if(response.status === 200) {
					$scope.successMessage = 'Data updated successfully.';
					$('#updateBookModal').modal('hide');
					$('#SuccessModal').modal('show');
					$scope.book = null;
					$timeout(function () {
						$('#SuccessModal').modal('hide');
						$scope.triggerBook();
					}, 1000);
				}
			})
			.catch(function (error) {
				var from = "bookUpdate"
				errorMessage(from);
			});
	}

	function deleteMessage(from){
		$scope.successMessage = 'Data deleted sucessfully.';
		$('#SuccessModal').modal('show');
		$scope.book = null;
		$scope.student = null;
		$timeout(function () {
			$('#SuccessModal').modal('hide');
			if (from){
				fetchStudents();
			}else{
				$scope.triggerBook();
			}
		}, 1000);
	}

	function errorMessage(msg){
		if(msg === "bookUpdate"){
			$('#updateBookModal').modal('hide');
			$('#errorModal').modal('show');
			$scope.errorMessage = 'Student ID not Found';
		}else if (msg ==="addStudent"){
			$('#addStudentModal').modal('hide');
			$('#errorModal').modal('show');
			$scope.errorMessage = 'Email Id already Registered';
		}else if (msg ==="addBook"){
			$('#addBookModal').modal('hide');
			$('#errorModal').modal('show');
			$scope.errorMessage = 'Book Code already Registered';
		}else if (msg ==="studentUpdate"){
			$('#updateStudentModal').modal('hide');
			$('#errorModal').modal('show');
			$scope.errorMessage = 'Email Id already Registered';
		}

	}
	$scope.addStudentModal = function () {
		console.log('Opening Add Student modal...');
		$scope.student = {};
		$('#addStudentModal').modal('show', 'keyboard', 'focus');
	};

//Add Student
	$scope.addStudent = function () {
		$http.post('/api/students/add', $scope.student)
			.then(function (response) {
				$scope.successMessage1 = 'Student data added successfully.';
				$('#addStudentModal').modal('hide');
				$('#addSuccessModal').modal('show');
				$scope.student = {};
				$timeout(function () {
					$('#addSuccessModal').modal('hide');
					fetchStudents();
				}, 1000);
			})
			.catch(function (error) {
				var from = "addStudent"
				errorMessage(from)
				console.error('Error updating student:', error);
			});
	};
	$scope.addBookModal = function () {
		$scope.book = null;
		$('#addBookModal').modal('show');
	};

	$scope.addBook = function (){
		$http.post('api/books/addBooks', $scope.book, { responseType: 'text' })
			.then(function (response){
				if( response.status === 200){
					$scope.successMessage1 = 'Book data added successfully.';
					$('#addBookModal').modal('hide');
					$('#addSuccessModal').modal('show');
					$scope.book = {};
					$timeout(function () {
						$('#addSuccessModal').modal('hide');
						$scope.triggerBook();
					}, 1000);
				}else{

				}

			})
			.catch(function (error) {
				var from = "addBook"
				errorMessage(from)
			});
	}

	$scope.byStatus = function(statusID){
		if(statusID !== "null"){
			$scope.statusId = statusID;
			fetchBook();
		}else{
			console.log("nullify")
			$scope.statusId = null;
			fetchBook();
		}

	}

	});