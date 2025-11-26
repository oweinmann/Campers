/**
 * AngularJS Controller for Caravan List
 */
app.controller('CaravanController', ['$scope', 'CaravanService', function($scope, CaravanService) {
    $scope.caravans = [];
    $scope.filteredCaravans = [];
    $scope.filter = {
        priority: '',
        origin: '',
        minPrice: null,
        maxPrice: 100000
    };

    // Load all caravans
    $scope.loadCaravans = function() {
        CaravanService.getAllCaravans().then(function(response) {
            $scope.caravans = response.data;
            $scope.filteredCaravans = response.data;
        }, function(error) {
            console.error('Error loading caravans:', error);
            alert('Failed to load caravans');
        });
    };

    // Apply filters
    $scope.applyFilter = function() {
        $scope.filteredCaravans = $scope.caravans.filter(function(caravan) {
            var matchPriority = !$scope.filter.priority || caravan.priority === $scope.filter.priority;
            var matchOrigin = !$scope.filter.origin || caravan.origin === $scope.filter.origin;
            var matchPrice = (!$scope.filter.minPrice || caravan.price >= $scope.filter.minPrice) &&
                           (!$scope.filter.maxPrice || caravan.price <= $scope.filter.maxPrice);
            return matchPriority && matchOrigin && matchPrice;
        });
    };

    // Clear filters
    $scope.clearFilter = function() {
        $scope.filter = {
            priority: '',
            origin: '',
            minPrice: null,
            maxPrice: 100000
        };
        $scope.filteredCaravans = $scope.caravans;
    };

    // Delete caravan
    $scope.deleteCaravan = function(id) {
        if (confirm('Are you sure you want to delete this caravan?')) {
            CaravanService.deleteCaravan(id).then(function() {
                $scope.loadCaravans();
            }, function(error) {
                console.error('Error deleting caravan:', error);
                alert('Failed to delete caravan');
            });
        }
    };

    // Initialize
    $scope.loadCaravans();
}]);

/**
 * AngularJS Controller for Caravan Form (Create/Edit)
 */
app.controller('CaravanFormController', ['$scope', '$routeParams', '$location', 'CaravanService',
    function($scope, $routeParams, $location, CaravanService) {

    $scope.caravan = {
        deleted: false,
        priority: 'MEDIUM',
        status: 'RESEARCHING'
    };
    $scope.isEditMode = false;

    // Load caravan for editing
    if ($routeParams.id) {
        $scope.isEditMode = true;
        CaravanService.getCaravanById($routeParams.id).then(function(response) {
            $scope.caravan = response.data;
        }, function(error) {
            console.error('Error loading caravan:', error);
            alert('Failed to load caravan');
        });
    }

    // Save caravan
    $scope.saveCaravan = function() {
        if ($scope.isEditMode) {
            CaravanService.updateCaravan($scope.caravan.id, $scope.caravan).then(function() {
                $location.path('/caravans');
            }, function(error) {
                console.error('Error updating caravan:', error);
                alert('Failed to update caravan');
            });
        } else {
            CaravanService.createCaravan($scope.caravan).then(function() {
                $location.path('/caravans');
            }, function(error) {
                console.error('Error creating caravan:', error);
                alert('Failed to create caravan');
            });
        }
    };

    // Cancel
    $scope.cancel = function() {
        $location.path('/caravans');
    };
}]);
