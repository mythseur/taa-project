(function () {
    'use strict';
    angular
        .module('taaProjectApp')
        .factory('Stage', Stage)
        .factory('StageEtudiant', StageEtudiant)
        .factory('StageEnseignant', StageEnseignant)
        .factory('StageContact', StageContact)
        .factory('StageEntreprise', StageEntreprise);

    Stage.$inject = ['$resource', 'DateUtils'];
    StageEtudiant.$inject = ['$resource'];
    StageEnseignant.$inject = ['$resource'];
    StageContact.$inject = ['$resource'];
    StageEntreprise.$inject = ['$resource'];

    function Stage($resource, DateUtils) {
        var resourceUrl = 'api/stages/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datedebut = DateUtils.convertLocalDateFromServer(data.datedebut);
                        data.datefin = DateUtils.convertLocalDateFromServer(data.datefin);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.datedebut = DateUtils.convertLocalDateToServer(data.datedebut);
                    data.datefin = DateUtils.convertLocalDateToServer(data.datefin);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.datedebut = DateUtils.convertLocalDateToServer(data.datedebut);
                    data.datefin = DateUtils.convertLocalDateToServer(data.datefin);
                    return angular.toJson(data);
                }
            }
        });
    }

    function StageEtudiant($resource) {
        var resourceUrl = 'api/stages/etudiant/:id';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET', isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }

    function StageEntreprise($resource) {
        var resourceUrl = 'api/stages/entreprise/:id';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET', isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }

    function StageEnseignant($resource) {
        var resourceUrl = 'api/stages/enseignant/:id';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET', isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }

    function StageContact($resource) {
        var resourceUrl = 'api/stages/contact/:id';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET', isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
