var app = angular.module('caravanApp', ['ngRoute']);

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
        .otherwise({
            redirectTo: '/'
        });
    $locationProvider.hashPrefix('!');
}]);

app.controller('HomeController', ['$scope', function($scope) {
    $scope.title = 'Caravan Research Application';
    $scope.description = 'Your comprehensive tool for researching off-road caravans in Australia';
}]);
