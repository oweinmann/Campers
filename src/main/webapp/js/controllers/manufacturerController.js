/**
 * AngularJS Controller for Manufacturer List
 */
app.controller('ManufacturerController', ['$scope', 'ManufacturerService', function($scope, ManufacturerService) {
    $scope.manufacturers = [];
    $scope.filteredManufacturers = [];
    $scope.searchTerm = '';
    $scope.originFilter = '';

    // Load all manufacturers
    $scope.loadManufacturers = function() {
        ManufacturerService.getAllManufacturers().then(function(response) {
            $scope.manufacturers = response.data;
            $scope.filteredManufacturers = response.data;
        }, function(error) {
            console.error('Error loading manufacturers:', error);
            alert('Failed to load manufacturers');
        });
    };

    // Search manufacturers
    $scope.search = function() {
        if ($scope.searchTerm) {
            ManufacturerService.searchManufacturers($scope.searchTerm).then(function(response) {
                $scope.filteredManufacturers = response.data;
            });
        } else {
            $scope.loadManufacturers();
        }
    };

    // Filter by origin
    $scope.filterByOrigin = function() {
        if ($scope.originFilter) {
            ManufacturerService.getManufacturersByOrigin($scope.originFilter).then(function(response) {
                $scope.filteredManufacturers = response.data;
            });
        } else {
            $scope.loadManufacturers();
        }
    };

    // Delete manufacturer
    $scope.deleteManufacturer = function(id) {
        if (confirm('Are you sure you want to delete this manufacturer?')) {
            ManufacturerService.deleteManufacturer(id).then(function() {
                $scope.loadManufacturers();
            }, function(error) {
                console.error('Error deleting manufacturer:', error);
                alert('Failed to delete manufacturer');
            });
        }
    };

    // Initialize
    $scope.loadManufacturers();
}]);

/**
 * AngularJS Controller for Manufacturer Form (Create/Edit)
 */
app.controller('ManufacturerFormController', ['$scope', '$routeParams', '$location', 'ManufacturerService',
    function($scope, $routeParams, $location, ManufacturerService) {

    $scope.manufacturer = {};
    $scope.isEditMode = false;

    // Load manufacturer for editing
    if ($routeParams.id) {
        $scope.isEditMode = true;
        ManufacturerService.getManufacturerById($routeParams.id).then(function(response) {
            $scope.manufacturer = response.data;
        }, function(error) {
            console.error('Error loading manufacturer:', error);
            alert('Failed to load manufacturer');
        });
    }

    // Save manufacturer
    $scope.saveManufacturer = function() {
        if ($scope.isEditMode) {
            ManufacturerService.updateManufacturer($scope.manufacturer.id, $scope.manufacturer).then(function() {
                $location.path('/manufacturers');
            }, function(error) {
                console.error('Error updating manufacturer:', error);
                alert('Failed to update manufacturer');
            });
        } else {
            ManufacturerService.createManufacturer($scope.manufacturer).then(function() {
                $location.path('/manufacturers');
            }, function(error) {
                console.error('Error creating manufacturer:', error);
                alert('Failed to create manufacturer');
            });
        }
    };

    // Cancel
    $scope.cancel = function() {
        $location.path('/manufacturers');
    };
}]);
