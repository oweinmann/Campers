/**
 * AngularJS Controller for Caravan List
 */
app.controller('CaravanController', ['$scope', '$location', 'CaravanService', 'SearchResultService', function($scope, $location, CaravanService, SearchResultService) {
    $scope.caravans = [];
    $scope.filteredCaravans = [];
    $scope.filter = {
        priority: '',
        origin: '',
        minPrice: null,
        maxPrice: 100000
    };

    // Sorting properties
    $scope.sortColumn = 'make';
    $scope.sortReverse = false;

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

    // Sort by column
    $scope.sortBy = function(column) {
        if ($scope.sortColumn === column) {
            $scope.sortReverse = !$scope.sortReverse;
        } else {
            $scope.sortColumn = column;
            $scope.sortReverse = false;
        }
    };

    // Get sort icon for column
    $scope.getSortIcon = function(column) {
        if ($scope.sortColumn !== column) {
            return '';
        }
        return $scope.sortReverse ? '▼' : '▲';
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

    // Search for used models
    $scope.searchUsedModels = function(caravan) {
        // Show confirmation and start automated search
        if (confirm('Search for used ' + caravan.make + ' ' + caravan.model + ' listings online?\n\nThis will search across multiple Australian caravan websites.')) {
            // Set loading state
            $scope.searchingCaravan = caravan;

            // Perform automated search
            SearchResultService.performAutomatedSearch(caravan.make, caravan.model).then(function(response) {
                $scope.searchingCaravan = null;
                var results = response.data;

                // Show results summary
                alert('Search completed!\n\nFound ' + results.totalResults + ' results for ' + caravan.make + ' ' + caravan.model + '\n\nClick OK to view the results.');

                // Navigate to search results page to view the results
                $location.path('/search-results').search({make: caravan.make, model: caravan.model, searchId: results.id});
            }, function(error) {
                $scope.searchingCaravan = null;
                console.error('Error performing automated search:', error);
                alert('Failed to perform automated search. Please try again or add results manually.');
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
