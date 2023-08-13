var app = angular.module('student', ['ngTable']);

app.factory('JwtInterceptor', ['$window', '$q', function ($window) {
	return {
		request: function (config) {
			var token = $window.localStorage.getItem('jwtToken');
			if (token) {
				config.headers['Authorization'] = 'Bearer ' + token;
			}
			return config;
		}
	};
}]);

app.config(['$httpProvider', function ($httpProvider) {
	$httpProvider.interceptors.push('JwtInterceptor');
}]);

app.directive('fileModel', ['$parse', '$rootScope', function ($parse, $rootScope) {
	return {
		restrict: 'A',
		link: function (scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function () {
				scope.$apply(function () {
					modelSetter(scope, element[0].files[0]);
					$rootScope.imageFile = element[0].files[0]; // Set in $rootScope
				});
			});
		}
	};
}]);

app.controller('LibraryController', function ($scope, $http,NgTableParams, $window,  $rootScope,$timeout) {

	$scope.logInData = function () {
		$http.post('/api/auth/login', $scope.login)
			.then(function (response) {
				$scope.loginStatus = true;
				var token = response.data.token;
				$window.localStorage.setItem('jwtToken', token);
				$scope.role = response.data.role;
				$scope.currentSTDid = response.data.id;
				$scope.query.email = response.data.email;
				$('#logInModal').modal('hide');
				$scope.byStatus(0,'query')
				// if ($scope.role === "USER"){
				// 	$('#imgModal').modal('show');
				// }

				getnumber($scope.currentSTDid)//for show details
			})
			.catch(function (error) {
				$scope.logInErrorStatus = true;
				$scope.logInError = error.data.message;
				console.error('Error in Logging in user ', error);
			});

	}




	$scope.addImg = function (){
		$('#imgModal').modal('hide');
		console.log('IMGfile in addImg function:', $rootScope.imageFile);
		var formData = new FormData();
		formData.append('file',  $rootScope.imageFile);
		formData.append('id', $scope.currentSTDid);

		$http.post('/api/upByImg', formData, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		}).then(function (response){
				$scope.successMessage1 = response.data.message;
				$('#addSuccessModal').modal('show');
				$scope.student.imgName = null;
				$scope.student = {};
				fetchProfile();
			})
			.catch(function (error){
				$scope.errorMessage = error.data.message;
				$('#errorModal').modal('show');
				console.log("Error Uploading the Photo")
			});
	}

	function getnumber(currentSTDid) {
		$('#logInModal').modal('hide');
		$http.get('/api/books/getDetails/'+ currentSTDid)
			.then(function (response){
				$scope.numberOfElements = response.data.length;
			})
	}

	$scope.showDetails= function (stdID){
		if (stdID){
			 $scope.currentSTDid = stdID;
		}
		$http.get('/api/books/getDetails/'+ $scope.currentSTDid)
			.then(function (response){
				$scope.details = response.data;
				$scope.numberOfElements = response.data.length;
				$('#showDetailsModal').modal('show', 'keyboard', 'focus');
			})
			.catch(function (error) {
				console.error('Error Not found :', error);
			});
	}

	$scope.changePassword = function (){
		if ($scope.student.npassword === $scope.student.ncpassword) {
			if ($scope.student.npassword !== $scope.student.password) {
				$http.put(`/api/change?password=${$scope.student.ncpassword}`, $scope.student)
					.then(function (response) {
						$scope.successMessage = response.data.message;
						$scope.twoModal('Success', 'cPassword')
					})
					.catch(function (error) {
						$scope.logInErrorStatus = true;
						$scope.passError = error.data.message;
					});
				}else{
					$scope.logInErrorStatus = true;
					$scope.passError = "Old Password And New Password must not be Same";
				}
		}else{
			$scope.logInErrorStatus = true;
			$scope.passError = "New Password And Confirm Password do not match";
		}
	}

	function fetchProfile(){
		$http.get('/api/profileById/'+$scope.currentSTDid)
			.then(function (response){
				$scope.student = response.data;
				$scope.img = response.data.imgName + '?' + new Date().getTime();
			})
			.catch(function (error) {
				$('#errorModal').modal('show');
				$scope.errorMessage = error.data.message;
				console.log("Student ID not found", $scope.errorMessage)
			})
	}


	$scope.showProfile = function (){
		$('#profileModal').modal('show');
		fetchProfile();
	}

	$scope.showProfileForId = function (){
		$http.get('/api/profileById/'+$scope.currentSTDid)
			.then(function (response){
				$scope.student = response.data;
				$('#idCardModal').modal('show');
			})
			.catch(function (error) {
				$('#errorModal').modal('show');
				$scope.errorMessage = error.data.message;
				console.log("Student ID not found", $scope.errorMessage)
			})
	}

	$scope.logOut = function () {
		$window.localStorage.removeItem('jwtToken');
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
		console.log($scope.keywordName)
		$http.get(apiUrl)
			.then(function (response) {
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
		$scope.adminMenu = true;
		$scope.byStatus(0,'query')
		fetchStudents();
	}

	$scope.triggerStudent = function () {
		$scope.showLibrary = true;
		$scope.showTable = true;
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
					$scope.successMessage = 'Data deleted sucessfully.';
					$scope.openModal('Success');
					fetchStudents();
				})
				.catch(function (error) {
					console.error('Error deleting Student Data:', error);
				});
		}
	};

	$scope.deleteBook = function (BookID) {
		if (confirm('Are you sure you want to delete this task?')) {
			$http.delete('/api/books/deleteBook/' + BookID)
				.then(function () {
					$scope.successMessage = 'Data deleted sucessfully.';
					$scope.openModal('Success');
					fetchBook();
				})
				.catch(function (error) {
					console.error('Error deleting Book:', error);
				});
		}
	};

	$scope.deleteQuery = function (statusID) {
		if (confirm('Are you sure you want to delete this task?')) {
			$http.delete('api/query/deletebyId/'+statusID)
				.then(function () {
					$scope.successMessage = 'Data deleted sucessfully.';
					$scope.openModal('Success');
					fetchqueries();
				})
				.catch(function (error) {
					console.error('Error deleting Book:', error);
				});
		}
	};


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
				$scope.twoModal('Success','updateStudent')
				$scope.student = {};
				fetchProfile();
			})
			.catch(function (error) {
				var from = "studentUpdate"
				errorMessage(from);
				console.error('Error updating student:', error);
			});
	};

	$scope.updateself = function () {
		$http.put('/api/add', $scope.student)
			.then(function (response) {
				$scope.successMessage = 'Student data updated successfully.';
				$scope.twoModal('Success','updateStudent')
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
					$scope.triggerBook();
				}
			})
			.catch(function (error) {
				var from = "bookUpdate"
				errorMessage(from);
			});
	}

	function errorMessage(msg){
		if(msg === "bookUpdate"){
			$('#updateBookModal').modal('hide');
			$('#errorModal').modal('show');
			$scope.errorMessage = 'Student ID not Found';
		}else if (msg ==="emailError" || msg ==="studentUpdate"){
			$('#updateStudentModal').modal('hide');
			$('#errorModal').modal('show');
			$scope.errorMessage = 'Email Id already Registered';
		}else if (msg ==="addBook"){
			$('#addBookModal').modal('hide');
			$('#errorModal').modal('show');
			$scope.errorMessage = 'Book Code already Registered';
		}
	}
	$scope.addStudentModal = function () {
		$scope.student = {};
		$('#addStudentModal').modal('show', 'keyboard', 'focus');
	};


	//self user registration
	$scope.addStudent = function () {
		$('#registerModal').modal('hide');
		$('#addStudentModal').modal('hide');
		$http.post('/api/auth/student/register', $scope.student)
			.then(function (response) {
				if (response.status === 200 || response.status === 201) {
					$scope.successMessage1 = response.data.message;
					console.log($scope.successMessage1)
					$('#addSuccessModal').modal('show');
					fetchStudents();
					$scope.student = {};
				}
			})
			.catch(function (error) {
				var from = "emailError"
				errorMessage(from);
				$scope.errorMessage = error.data.message;
				console.error('Error updating student:', error);
			});
	};

	$scope.addStaff = function () {
		$http.post('/api/auth/staff/register', $scope.staff)
			.then(function (response) {
				if (response.status === 200 || response.status === 201) {
					$scope.successMessage1 = response.data.message;
					$scope.twoModal('addSuccess','register');
					$scope.staff = {};
					fetchStudents();
				}
			})
			.catch(function (error) {
				var from = "emailError"
				errorMessage(from);
				$('#registerModal').modal('hide');
				$scope.errorMessage = error.data.message;
				console.error('Error Adding Staff:', error.data.message);
			});
	};


	$scope.addBookModal = function () {
		$scope.book = {};
		$('#addBookModal').modal('show');
	};

	$scope.addBook = function (){
		$http.post('/api/books/addBooks', $scope.book )
			.then(function (response){
				if( response.status === 200){
					$scope.successMessage1 = 'Book data added successfully.';
					$scope.twoModal('addSuccess','addBook');
					// $('#addBookModal').modal('hide');
					// $('#addSuccessModal').modal('show');
					$scope.book = {};
					$timeout(function () {
						$('#addSuccessModal').modal('hide');
						$scope.triggerBook();
					}, 1000);
				}
			})
			.catch(function (error) {
				var from = "addBook"
				errorMessage(from)
			});
	}

	$scope.byStatus = function(statusID,from){
		if(statusID !== "null"){
			$scope.statusId = statusID;
			if(from ==='query'){
				fetchqueries();
			}else{
				fetchBook();
			}

		}else{
			$scope.statusId = null;
			if(from ==='query'){
				fetchqueries();
			}else{
				fetchBook();
			}
		}
	}
	function emptyThem(){
		$scope.student = {};
		$scope.book = {};
	}

	$scope.openModal = function (modalName) {
		$scope.query = {};
		var modalID = '#'+ modalName + 'Modal';
		$(modalID).modal('show');
	}
	$scope.closeModal = function (modalName) {
		var modalID = '#'+ modalName + 'Modal';
		$(modalID).modal('hide');
	}

	$scope.twoModal = function (modalName,modalName2){
		var modalID2 = '#'+ modalName2 + 'Modal';
		$(modalID2).modal('hide');

		var modalID = '#'+ modalName + 'Modal';
		$(modalID).modal('show');
	}



	function initialize() {
		$window.localStorage.removeItem('jwtToken');
		$scope.logInErrorStatus = false;
		$scope.studentRegister = true;
		passwordMismatch = false;
		$scope.enableRegister = false;
		$scope.showLibrary = false;
		$scope.currentPage = 0;
		$scope.pageSize = 10000;
		$scope.keywordSemester = '';
		$scope.keywordName = '';
		$scope.keywordEmail = '';
		$scope.count = 0;
		$scope.statusId = null;
		$scope.loginStatus = false;
		$scope.role = null;
		$scope.currentSTDid = null;
		$scope.login = {};
		$scope.student = {};
		$scope.staff = {};
		$scope.book = {};
		$scope.query= {};
		$scope.students = [];
		$scope.books = [];
		$scope.queries = [];
		$scope.adminMenu = true;
		$scope.newQuery = 0;
		$scope.defaultImg = "default.png";
	}

	initialize();

	$scope.goBack = function (){

	}

	$scope.validatePassword = function(pass1 , pass2) {
		$scope.passwordMismatch = pass1 !== pass2;
	};

	$scope.msg2Admin = function (){
		$scope.closeModal('contactAdmin');
		$http.post("/api/query/add", $scope.query)
			.then(function (){
				$scope.openModal('contactSucess');
				$scope.closeModal('contactAdmin');
				$scope.query = {};
			})
			.catch(function (error) {
				$('#errorModal').modal('show');
				$scope.errorMessage = 'Server Error. Try Again Later';
			});
	}
	$scope.triggerQueries = function (){
		$scope.adminMenu = false;
		fetchqueries();
	}

	function fetchqueries(){
		let apiUrl1 = '/api/query/';
		if($scope.statusId === null){
			apiUrl1 += 'getQueries';
		}else{
			apiUrl1 += 'byStatus/'+$scope.statusId ;
		}
		$http.get(apiUrl1)
			.then(function (response) {
				$scope.queries =  response.data;
				$scope.count = response.data.length;
				const data2 = response.data;
				$scope.statusId = null;
				$scope.tableParams2 = new NgTableParams(
					{
						page: 1, // Show the first page
						count: 10, // Number of items per page
						sorting: {
							date: 'asc' // Default sorting by 'First Name' column in ascending order
						}
					},
					{
						dataset: data2 // Set the fetched data as the dataset
					}
				);
			})
			.catch(function (error) {
				console.error('Error fetching Queries:', error);
			});
	}

	$scope.queryInDetails = function (id){
		var qId = id ;
		$http.get("/api/query/getQuerybyId/" + qId)
			.then(function (response){
				if( response.status === 200) {
					$('#queryDetailsModal').modal('show');
					$scope.query = response.data;
				}
			})
			.catch(function (error) {
				$scope.errorMessage = error.data.message;
				$('#errorModal').modal('show');
				console.log("Error in fetching Query");
			});
	}


});