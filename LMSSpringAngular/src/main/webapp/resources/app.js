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
	}).when("/test", {
		templateUrl : "test.html"
	})
} ]);

libraryModule.controller('authorCtrl', function($scope, $http, $cookieStore) {
	$scope.author;
	// get all authors and display initially

	$http.get('http://localhost:8080/lms/author/get').success(function(data) {
		$scope.authors = data;
		console.log($scope.authors);

	});

	$scope.addAuthor = function addAuthor() {
		alert('Adding author');
		// if ($scope.addAuthorFrm.$valid) {
		// alert('is valid');
		$http.post('http://localhost:8080/lms/author/add', {
			authorName : $scope.authorName
		}).success(function(data) {
			alert('Author Added Successfully');
			$scope.authors = data;
			// showAddAuthor();
		});
		// }
	};

	$scope.editAuthor = function editAuthor() {
		alert('here');
		window.location.href = "http://localhost:8080/lms/#/editAuthor";

		// alert($scope.authorName);
		// alert($scope.author);
		//
		// $http.post('http://localhost:8080/lms/author/update', {
		// 'authorName' : $scope.authorName,
		// 'authorId' : $scope.author.authorId
		// }).success(function(data) {
		// alert('Author Edited Successfully');
		// $scope.authors = data;
		// });
	};

	$scope.showEditAuthor = function showEditAuthor(authorId) {
		$http.post('http://localhost:8080/lms/author/getOne', {
			'authorId' : authorId
		}).success(function(data) {
			$scope.author = data;
			console.log($scope.author);

			// go to the edit
			window.location.href = "http://localhost:8080/lms/#/editAuthor";

			alert($scope.author.authorName);

		});

		// alert($scope.author);
		// alert('Editing author');
		// $http.post('http://localhost:8080/lms/author/update', {
		// authorId : $scope.authorId
		// }).success(function(data) {
		// alert('Author Edited Successfully');
		// $scope.authors = data;
		// });

	};

});

libraryModule.controller('bookCtrl', function($scope, $http, $cookieStore) {
	// get all authors and display initially
	$http.get('http://localhost:8080/lms/author/get').success(function(data) {
		$scope.authors = data;
		console.log($scope.authors);
	});

	// $scope.addAuthor = function addAuthor() {
	// if($scope.addAuthorFrm.$valid){
	// $http.post('addAuthor/' +$scope.authorName).
	// success(function(data) {
	// alert('Author Added');
	// $scope.authors = data;
	// showAddAuthor();
	// });
	// }
	// };
});

libraryModule.controller('testCtrl', function($scope, $http, $cookieStore) {
	$scope.testVar = $cookieStore.get('sample');
});
