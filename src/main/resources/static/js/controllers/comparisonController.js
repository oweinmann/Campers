/**
 * AngularJS Controller for Caravan Comparison
 */
app.controller('ComparisonController', ['$scope', 'CaravanService', function($scope, CaravanService) {
    $scope.caravans = [];
    $scope.selectedCaravans = [];
    $scope.comparisonList = [];

    // Load all caravans
    $scope.loadCaravans = function() {
        CaravanService.getAllCaravans().then(function(response) {
            $scope.caravans = response.data;
        }, function(error) {
            console.error('Error loading caravans:', error);
            alert('Failed to load caravans');
        });
    };

    // Add caravan to comparison
    $scope.addToComparison = function(caravanId) {
        if (!caravanId) return;

        if ($scope.comparisonList.length >= 5) {
            alert('You can only compare up to 5 caravans at a time');
            $scope.selectedCaravan = '';
            return;
        }

        var caravan = $scope.caravans.find(c => c.id == caravanId);
        if (caravan && $scope.comparisonList.findIndex(c => c.id === caravan.id) === -1) {
            $scope.comparisonList.push(caravan);
        }

        // Reset selection
        $scope.selectedCaravan = '';
    };

    // Remove caravan from comparison
    $scope.removeFromComparison = function(index) {
        $scope.comparisonList.splice(index, 1);
    };

    // Clear comparison
    $scope.clearComparison = function() {
        $scope.comparisonList = [];
    };

    // Initialize
    $scope.loadCaravans();
}]);
