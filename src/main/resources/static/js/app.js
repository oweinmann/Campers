/**
 * AngularJS Application Configuration
 */
var app = angular.module('caravanApp', ['ngRoute']);

// Configure routes
app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/home.html',
            controller: 'HomeController'
        })
        .when('/caravans', {
            templateUrl: 'views/caravans.html',
            controller: 'CaravanController'
        })
        .when('/caravans/new', {
            templateUrl: 'views/caravan-form.html',
            controller: 'CaravanFormController'
        })
        .when('/caravans/edit/:id', {
            templateUrl: 'views/caravan-form.html',
            controller: 'CaravanFormController'
        })
        .when('/manufacturers', {
            templateUrl: 'views/manufacturers.html',
            controller: 'ManufacturerController'
        })
        .when('/manufacturers/new', {
            templateUrl: 'views/manufacturer-form.html',
            controller: 'ManufacturerFormController'
        })
        .when('/manufacturers/edit/:id', {
            templateUrl: 'views/manufacturer-form.html',
            controller: 'ManufacturerFormController'
        })
        .when('/comparison', {
            templateUrl: 'views/comparison.html',
            controller: 'ComparisonController'
        })
        .when('/search-results', {
            templateUrl: 'views/search-results.html',
            controller: 'SearchResultController'
        })
        .otherwise({
            redirectTo: '/'
        });

    // Use hashbang mode for routing
    $locationProvider.hashPrefix('!');
}]);

// Home Controller
app.controller('HomeController', ['$scope', function($scope) {
    $scope.title = 'Australian Off-Road Caravan Research';
    $scope.description = 'Find and compare the perfect off-road caravan for your family adventures';
}]);
