/**
 * AngularJS Controller for Search Results
 */
app.controller('SearchResultController', ['$scope', '$location', '$routeParams', 'SearchResultService',
    function($scope, $location, $routeParams, SearchResultService) {

    $scope.searchResults = [];
    $scope.selectedSearchResult = null;
    $scope.searchResultDetails = [];
    $scope.make = $routeParams.make || '';
    $scope.model = $routeParams.model || '';
    $scope.caravanId = $routeParams.caravanId || null;
    $scope.isCreatingSearch = false;
    $scope.newSearchResult = {};

    // Load all search results or filtered by make/model
    $scope.loadSearchResults = function() {
        if ($scope.make && $scope.model) {
            SearchResultService.getSearchResultsByMakeAndModel($scope.make, $scope.model).then(function(response) {
                $scope.searchResults = response.data;
            }, function(error) {
                console.error('Error loading search results:', error);
                alert('Failed to load search results');
            });
        } else {
            SearchResultService.getAllSearchResults().then(function(response) {
                $scope.searchResults = response.data;
            }, function(error) {
                console.error('Error loading search results:', error);
                alert('Failed to load search results');
            });
        }
    };

    // Select a search result to view details
    $scope.selectSearchResult = function(searchResult) {
        $scope.selectedSearchResult = searchResult;
        SearchResultService.getSearchResultDetails(searchResult.id).then(function(response) {
            $scope.searchResultDetails = response.data;
        }, function(error) {
            console.error('Error loading search result details:', error);
            alert('Failed to load search result details');
        });
    };

    // Show create search form
    $scope.showCreateSearchForm = function() {
        $scope.isCreatingSearch = true;
        $scope.newSearchResult = {
            make: $scope.make || '',
            model: $scope.model || '',
            searchQuery: ($scope.make || '') + ' ' + ($scope.model || '') + ' used caravan',
            totalResults: 0,
            details: []
        };
    };

    // Cancel creating search
    $scope.cancelCreateSearch = function() {
        $scope.isCreatingSearch = false;
        $scope.newSearchResult = {};
    };

    // Add a detail row to the new search
    $scope.addDetailRow = function() {
        if (!$scope.newSearchResult.details) {
            $scope.newSearchResult.details = [];
        }
        $scope.newSearchResult.details.push({
            title: '',
            description: '',
            price: null,
            listingUrl: '',
            location: '',
            seller: '',
            year: null,
            condition: '',
            specifications: '',
            imageUrl: '',
            notes: ''
        });
    };

    // Remove a detail row
    $scope.removeDetailRow = function(index) {
        $scope.newSearchResult.details.splice(index, 1);
    };

    // Save the new search result
    $scope.saveSearchResult = function() {
        if (!$scope.newSearchResult.make || !$scope.newSearchResult.model) {
            alert('Make and Model are required');
            return;
        }

        SearchResultService.createSearchResult($scope.newSearchResult).then(function(response) {
            alert('Search result created successfully!');
            $scope.isCreatingSearch = false;
            $scope.newSearchResult = {};
            $scope.loadSearchResults();
        }, function(error) {
            console.error('Error creating search result:', error);
            alert('Failed to create search result');
        });
    };

    // Delete a search result
    $scope.deleteSearchResult = function(searchResult) {
        if (confirm('Are you sure you want to delete this search result?')) {
            SearchResultService.deleteSearchResult(searchResult.id).then(function() {
                if ($scope.selectedSearchResult && $scope.selectedSearchResult.id === searchResult.id) {
                    $scope.selectedSearchResult = null;
                    $scope.searchResultDetails = [];
                }
                $scope.loadSearchResults();
            }, function(error) {
                console.error('Error deleting search result:', error);
                alert('Failed to delete search result');
            });
        }
    };

    // Back to caravans
    $scope.backToCaravans = function() {
        $location.path('/caravans');
    };

    // Initialize
    $scope.loadSearchResults();
}]);
