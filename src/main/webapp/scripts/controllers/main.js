'use strict';

restApiDocumeter.controller('MainCtrl', function ($scope, $http) {
    // initialization data
    $scope.data = {"resourcePath": "/animals", "responseList": [
        {"path": "/animals/cat", "operations": {"method": "GET", "type": "Cat", "nickName": "getCat", "parameterList": [], "returnType": "Cat"}},
        {"path": "/animals/cat", "operations": {"method": "GET", "type": "String", "nickName": "getStringAsData", "parameterList": [], "returnType": "String"}},
        {"path": "/animals/dog/{param}", "operations": {"method": "GET", "type": "Dog", "nickName": "getDog", "parameterList": [
            {"name": "param", "type": "String", "paramType": "PATH", "required": true},
            {"name": "token", "type": "String", "paramType": "QUERY", "required": true},
            {"name": "tok", "type": "String", "paramType": "HEADER", "required": true},
            {"type": "String", "paramType": "ENTITY", "required": false}
        ], "returnType": "Dog"}}
    ], "modelList": [
        {"className": "Cat", "propertiesList": [
            {"propertyName": "id", "type": "int", "description": "id should be of  int type "},
            {"propertyName": "name", "type": "String", "description": "name should be of  String type "},
            {"propertyName": "petname", "type": "String", "description": "petname should be of  String type "}
        ]},
        {"className": "String", "propertiesList": [
            {"propertyName": "CASE_INSENSITIVE_ORDER", "type": "Comparator", "description": "CASE_INSENSITIVE_ORDER should be of  Comparator type "}
        ]},
        {"className": "Dog", "propertiesList": [
            {"propertyName": "id", "type": "int", "description": "id should be of  int type "},
            {"propertyName": "name", "type": "String", "description": "name should be of  String type "},
            {"propertyName": "petname", "type": "String", "description": "petname should be of  String type "}
        ]}
    ]}

});

