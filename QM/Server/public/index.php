<?php
/**
 * Composer
 */
require '../vendor/autoload.php';

/**
 * Error and Exception handling
 * */
error_reporting(E_ALL);
set_error_handler('Core\Error::errorHandler');
set_exception_handler('Core\Error::exceptionHandler');

/** @var \Core\Router $router */
$router = new Core\Router();

// router with controller: Profile, Id: dynamic, Action: Index and the GET request
$router->get('profile/{id:\d+}', ['controller' => 'Profile', 'action' => 'index']);

// router with controller: Profile, Action: Index and the POST request
$router->post('profile', ['controller' => 'Profile', 'action' => 'index']);



$url = rtrim($_SERVER['QUERY_STRING'], '/');
$router->dispatch($url, $_SERVER['REQUEST_METHOD']);