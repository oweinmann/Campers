app.factory('CaravanService', ['$http', function($http) {
    var baseUrl = '/api/caravans';
    return {
        getAllCaravans: function() { return $http.get(baseUrl); },
        getCaravanById: function(id) { return $http.get(baseUrl + '/' + id); },
        createCaravan: function(caravan) { return $http.post(baseUrl, caravan); },
        updateCaravan: function(id, caravan) { return $http.put(baseUrl + '/' + id, caravan); },
        deleteCaravan: function(id) { return $http.delete(baseUrl + '/' + id); },
        getCaravansByPriority: function(priority) { return $http.get(baseUrl + '/priority/' + priority); },
        getCaravansByOrigin: function(origin) { return $http.get(baseUrl + '/origin/' + origin); }
    };
}]);
