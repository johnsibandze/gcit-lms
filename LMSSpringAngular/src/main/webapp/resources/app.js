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
		alert('show');
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
		$http.post('http://localhost:8080/lms/book/update', {
			'title' : $scope.title,
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

libraryModule.controller('testCtrl', function($scope, $http, $cookieStore) {
	$scope.testVar = $cookieStore.get('sample');
});
