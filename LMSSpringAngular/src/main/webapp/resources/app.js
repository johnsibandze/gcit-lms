var libraryModule = angular.module('libraryApp', [ 'ngRoute', 'ngCookies' ]);

libraryModule.config([ "$routeProvider", function($routeProvider) {
	return $routeProvider.when("/", {
		redirectTo : "/home"
	}).when("/home", {
		templateUrl : "home.html"
	}).when("/listAuthors", {
		templateUrl : "listAuthors.html"
	}).when("/addAuthor", {
		templateUrl : "addAuthor.html"
	}).when("/editAuthor", {
		templateUrl : "editAuthor.html"
	}).when("/listBooks", {
		templateUrl : "listBooks.html"
	}).when("/addBook", {
		templateUrl : "addBook.html"
	}).when("/editBook", {
		templateUrl : "editBook.html"
	}).when("/listPublishers", {
		templateUrl : "listPublishers.html"
	}).when("/addPublisher", {
		templateUrl : "addPublisher.html"
	}).when("/editPublisher", {
		templateUrl : "editPublisher.html"
	}).when("/listBorrowers", {
		templateUrl : "listBorrowers.html"
	}).when("/addBorrower", {
		templateUrl : "addBorrower.html"
	}).when("/editBorrower", {
		templateUrl : "editBorrower.html"
	}).when("/listLibraries", {
		templateUrl : "listLibraries.html"
	}).when("/addLibrary", {
		templateUrl : "addLibrary.html"
	}).when("/editLibrary", {
		templateUrl : "editLibrary.html"
	}).when("/test", {
		templateUrl : "test.html"
	})
} ]);

libraryModule.controller('authorCtrl', function($rootScope, $scope, $route,
		$http, $cookieStore) {

	// get all authors and display initially
	$http.get('http://localhost:8080/lms/author/get').success(function(data) {
		$scope.authors = data;
		console.log($scope.authors);
	});

	$scope.addAuthor = function addAuthor() {
		$http.post('http://localhost:8080/lms/author/add', {
			authorName : $scope.authorName
		}).success(function(data) {
			alert('Author Added Successfully');
			$scope.authors = data;
		});

		window.location.href = "http://localhost:8080/lms/#/listAuthors";

	};

	$scope.editAuthor = function editAuthor() {
		$http.post('http://localhost:8080/lms/author/update', {
			'authorName' : $scope.authorName,
			'authorId' : $scope.author.authorId
		}).success(function(data) {
			alert('Author Edited Successfully');
			$scope.authors = data;
		});

		window.location.href = "http://localhost:8080/lms/#/listAuthors";

	};

	$scope.showEditAuthor = function showEditAuthor(authorId) {

		$http.post('http://localhost:8080/lms/author/getOne', {
			'authorId' : authorId
		}).success(function(data) {
			$rootScope.author = data;
			console.log($scope.author);

			// go to the edit
			window.location.href = "http://localhost:8080/lms/#/editAuthor";
		});
	};

	$scope.deleteAuthor = function deleteAuthor(authorId) {

		$http.post('http://localhost:8080/lms/author/delete', {
			'authorId' : authorId
		}).success(function(data) {
			alert('Author Deleted Successfully');
			// console.log('author deleted');
			$scope.authors = data;
		});
		// reload the listAuthors page
		$route.reload();
	};

});

libraryModule.controller('bookCtrl', function($scope, $rootScope, $route,
		$http, $cookieStore) {

	// get all books and display initially
	$http.get('http://localhost:8080/lms/book/get').success(function(data) {
		$scope.books = data;
		console.log($scope.books);
	});

	$scope.addBook = function addBook() {
		alert($scope.title);
		$http.post('http://localhost:8080/lms/book/add', {
			title : $scope.title
		}).success(function(data) {
			alert('Book Added Successfully');
			$scope.books = data;
		});

		window.location.href = "http://localhost:8080/lms/#/listBooks";

	};

	$scope.editBook = function editBook() {
		// alert($rootScope.book.title);
		$http.post('http://localhost:8080/lms/book/update', {
			'title' : $scope.book.title,
			'bookId' : $scope.book.bookId
		}).success(function(data) {
			alert('Book Edited Successfully');
		});

		window.location.href = "http://localhost:8080/lms/#/listBooks";

	};

	$scope.showEditBook = function showEditBook(bookId) {
		$http.post('http://localhost:8080/lms/book/getOne', {
			'bookId' : bookId
		}).success(function(data) {
			$rootScope.book = data;
			console.log($scope.book);

			// go to the edit
			window.location.href = "http://localhost:8080/lms/#/editBook";
		});
	};

	$scope.deleteBook = function deleteBook(bookId) {
		$http.post('http://localhost:8080/lms/book/delete', {
			'bookId' : bookId
		}).success(function(data) {
			alert('Book Deleted Successfully');
			// console.log('author deleted');
			$scope.books = data;
		});
		// reload the listAuthors page
		$route.reload();
	};

});

libraryModule.controller('publisherCtrl', function($rootScope, $scope, $route,
		$http, $cookieStore) {

	// get all authors and display initially
	$http.get('http://localhost:8080/lms/publisher/get').success(
			function(data) {
				$scope.publishers = data;
				console.log($scope.publishers);
			});

	$scope.addPublisher = function addPublisher() {
		$http.post('http://localhost:8080/lms/publisher/add', {
			publisherName : $scope.publisherName
		}).success(function(data) {
			alert('Publisher Added Successfully');
		});

		window.location.href = "http://localhost:8080/lms/#/listPublishers";

	};

	$scope.editPublisher = function editPublisher() {
		alert('here');
		$http.post('http://localhost:8080/lms/publisher/update', {
			'publisherName' : $scope.publisherName,
			'publisherId' : $scope.publisher.publisherId
		}).success(function(data) {
			alert('Publisher Edited Successfully');
		});

		window.location.href = "http://localhost:8080/lms/#/listPublishers";

	};

	$scope.showEditPublisher = function showEditPublisher(publisherId) {
		$http.post('http://localhost:8080/lms/publisher/getOne', {
			'publisherId' : publisherId
		}).success(function(data) {
			$rootScope.publisher = data;

			window.location.href = "http://localhost:8080/lms/#/editPublisher";
		});
	};

	$scope.deletePublisher = function deletePublisher(publisherId) {
		$http.post('http://localhost:8080/lms/publisher/delete', {
			'publisherId' : publisherId
		}).success(function(data) {
			alert('Publisher Deleted Successfully');
			// console.log('author deleted');
			// $scope.authors = data;
		});
		// reload the listAuthors page
		$route.reload();
	};

});

libraryModule.controller('borrowerCtrl', function($rootScope, $scope, $route,
		$http, $cookieStore) {

	// get all authors and display initially
	$http.get('http://localhost:8080/lms/borrower/get').success(function(data) {
		$scope.borrowers = data;
		console.log($scope.borrowers);
	});

	$scope.addBorrower = function addBorrower() {
		$http.post('http://localhost:8080/lms/borrower/add', {
			'name' : $scope.name
		}).success(function(data) {
			alert('Borrower Added Successfully');
			// $scope.authors = data;
		});

		window.location.href = "http://localhost:8080/lms/#/listBorrowers";

	};

	$scope.editBorrower = function editBorrower() {
		$http.post('http://localhost:8080/lms/borrower/update', {
			'name' : $scope.name,
			'cardNo' : $scope.borrower.cardNo
		}).success(function(data) {
			alert('Borrower Edited Successfully');
			// $scope.authors = data;
		});

		window.location.href = "http://localhost:8080/lms/#/listBorrowers";

	};

	$scope.showEditBorrower = function showEditBorrower(cardNo) {
		$http.post('http://localhost:8080/lms/borrower/getOne', {
			'cardNo' : cardNo
		}).success(function(data) {
			$rootScope.borrower = data;
			console.log($scope.borrower);

			// go to the edit
			window.location.href = "http://localhost:8080/lms/#/editBorrower";
		});
	};

	$scope.deleteBorrower = function deleteBorrower(cardNo) {
		$http.post('http://localhost:8080/lms/borrower/delete', {
			'cardNo' : cardNo
		}).success(function(data) {
			alert('Borrower Deleted Successfully');
			// console.log('author deleted');
			// $scope.authors = data;
		});
		// reload the listAuthors page
		$route.reload();
	};

});

libraryModule.controller('libraryCtrl', function($rootScope, $scope, $route,
		$http, $cookieStore) {

	// get all the library branches for display
	$http.get('http://localhost:8080/lms/library/get').success(function(data) {
		$scope.libraries = data;
		console.log($scope.libraries);
	});

	$scope.addLibrary = function addLibrary() {
		$http.post('http://localhost:8080/lms/library/add', {
			branchName : $scope.branchName
		}).success(function(data) {
			alert('Branch Added Successfully');
			$scope.libraries = data;
		});

		window.location.href = "http://localhost:8080/lms/#/listLibraries";

	};

	$scope.editLibrary = function editLibrary() {
		alert($scope.library.branchId);
		$http.post('http://localhost:8080/lms/library/update', {

			'branchId' : $scope.library.branchId,
			'branchName' : $scope.branchName,
			'branchAddress' : $scope.branchAddress
		}).success(function(data) {
			alert('Branch Edited Successfully');
			// $scope.authors = data;
		});

		window.location.href = "http://localhost:8080/lms/#/listLibraries";

	};

	$scope.showEditLibrary = function showEditLibrary(branchId) {
		$http.post('http://localhost:8080/lms/library/getOne', {
			'branchId' : branchId
		}).success(function(data) {
			$rootScope.library = data;

			// go to the edit
			window.location.href = "http://localhost:8080/lms/#/editLibrary";
		});
	};

	$scope.deleteLibrary = function deleteLibrary(branchId) {
		$http.post('http://localhost:8080/lms/library/delete', {
			'branchId' : branchId
		}).success(function(data) {
			alert('Branch Deleted Successfully');
			// console.log('author deleted');
			// $scope.authors = data;
		});
		// reload the listAuthors page
		$route.reload();
	};

});

libraryModule.controller('testCtrl', function($scope, $http, $cookieStore) {
	$scope.testVar = $cookieStore.get('sample');
});
