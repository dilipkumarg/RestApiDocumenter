'use strict';

restApiDocumeter.controller('MainCtrl', function ($scope, $http) {
    // initialization data
    $scope.data = {"resourcePath": "/animals", "responseList": [
        {"path": "/animals/dog/{param}", "operations": {"method": "GET", "type": "Dog", "nickName": "getDog", "parameterList": [
            {"name": "param", "type": "class java.lang.String", "paramType": "PathParam", "required": true}
        ], "returnType": "Dog"}},
        {"path": "/animals/cat", "operations": {"method": "GET", "type": "Cat", "nickName": "getCat", "parameterList": [], "returnType": "Cat"}}
    ], "modelList": [
        {"className": "Dog", "propertiesList": [
            {"propertyName": "id", "type": "int", "description": "id should be of  int type "},
            {"propertyName": "name", "type": "String", "description": "name should be of  String type "},
            {"propertyName": "petname", "type": "String", "description": "petname should be of  String type "}
        ]},
        {"className": "Cat", "propertiesList": [
            {"propertyName": "id", "type": "int", "description": "id should be of  int type "},
            {"propertyName": "name", "type": "String", "description": "name should be of  String type "},
            {"propertyName": "petname", "type": "String", "description": "petname should be of  String type "}
        ]}
    ]}


});

