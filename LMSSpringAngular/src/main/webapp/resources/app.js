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

libraryModule.controller('bookCtrl', function($scope, $http, $cookieStore) {

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

});

libraryModule.controller('testCtrl', function($scope, $http, $cookieStore) {
	$scope.testVar = $cookieStore.get('sample');
});
