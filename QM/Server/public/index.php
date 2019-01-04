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

// add router with controller: Clients, Action: add and the POST request
$router->post('clients', ['controller' => 'Clients', 'action' => 'add']);




$url = rtrim($_SERVER['QUERY_STRING'], '/');
$router->dispatch($url, $_SERVER['REQUEST_METHOD']);