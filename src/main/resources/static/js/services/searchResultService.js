/**
 * AngularJS Service for Search Results
 */
app.service('SearchResultService', ['$http', function($http) {
    var baseUrl = '/api/search-results';

    this.getAllSearchResults = function() {
        return $http.get(baseUrl);
    };

    this.getSearchResultById = function(id) {
        return $http.get(baseUrl + '/' + id);
    };

    this.getSearchResultDetails = function(id) {
        return $http.get(baseUrl + '/' + id + '/details');
    };

    this.getSearchResultsByMakeAndModel = function(make, model) {
        return $http.get(baseUrl + '/make/' + encodeURIComponent(make) + '/model/' + encodeURIComponent(model));
    };

    this.getSearchResultsByMake = function(make) {
        return $http.get(baseUrl + '/make/' + encodeURIComponent(make));
    };

    this.createSearchResult = function(searchResult) {
        return $http.post(baseUrl, searchResult);
    };

    this.updateSearchResult = function(id, searchResult) {
        return $http.put(baseUrl + '/' + id, searchResult);
    };

    this.deleteSearchResult = function(id) {
        return $http.delete(baseUrl + '/' + id);
    };

    this.addSearchResultDetail = function(searchResultId, detail) {
        return $http.post(baseUrl + '/' + searchResultId + '/details', detail);
    };

    this.deleteSearchResultDetail = function(detailId) {
        return $http.delete(baseUrl + '/details/' + detailId);
    };

    this.performAutomatedSearch = function(make, model) {
        return $http.post(baseUrl + '/automated-search', null, {
            params: { make: make, model: model }
        });
    };
}]);
