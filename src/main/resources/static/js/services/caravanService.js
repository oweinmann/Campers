/**
 * AngularJS Service for Caravan API operations
 */
app.factory('CaravanService', ['$http', function($http) {
    var baseUrl = '/api/caravans';

    return {
        getAllCaravans: function() {
            return $http.get(baseUrl);
        },

        getCaravanById: function(id) {
            return $http.get(baseUrl + '/' + id);
        },

        getCaravansByPriority: function(priority) {
            return $http.get(baseUrl + '/priority/' + priority);
        },

        getCaravansByOrigin: function(origin) {
            return $http.get(baseUrl + '/origin/' + origin);
        },

        getCaravansByMake: function(make) {
            return $http.get(baseUrl + '/make/' + make);
        },

        getCaravansByPriceRange: function(minPrice, maxPrice) {
            return $http.get(baseUrl + '/price-range', {
                params: { minPrice: minPrice, maxPrice: maxPrice }
            });
        },

        getCaravansByMinBunkBeds: function(minBeds) {
            return $http.get(baseUrl + '/bunk-beds/' + minBeds);
        },

        createCaravan: function(caravan) {
            return $http.post(baseUrl, caravan);
        },

        updateCaravan: function(id, caravan) {
            return $http.put(baseUrl + '/' + id, caravan);
        },

        deleteCaravan: function(id) {
            return $http.delete(baseUrl + '/' + id);
        },

        restoreCaravan: function(id) {
            return $http.put(baseUrl + '/' + id + '/restore');
        }
    };
}]);
