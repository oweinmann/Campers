/**
 * AngularJS Service for Manufacturer API operations
 */
app.factory('ManufacturerService', ['$http', function($http) {
    var baseUrl = '/api/manufacturers';

    return {
        getAllManufacturers: function() {
            return $http.get(baseUrl);
        },

        getManufacturerById: function(id) {
            return $http.get(baseUrl + '/' + id);
        },

        getManufacturerByBrand: function(brand) {
            return $http.get(baseUrl + '/brand/' + brand);
        },

        getManufacturersByOrigin: function(origin) {
            return $http.get(baseUrl + '/origin/' + origin);
        },

        searchManufacturers: function(term) {
            return $http.get(baseUrl + '/search', {
                params: { term: term }
            });
        },

        createManufacturer: function(manufacturer) {
            return $http.post(baseUrl, manufacturer);
        },

        updateManufacturer: function(id, manufacturer) {
            return $http.put(baseUrl + '/' + id, manufacturer);
        },

        deleteManufacturer: function(id) {
            return $http.delete(baseUrl + '/' + id);
        }
    };
}]);
