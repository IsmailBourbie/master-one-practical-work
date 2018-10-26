<?php

class Router {

	public $routes = [
		'GET' => [],
		'POST' => [],
	];

	public static function load($file)
	{
		// create instance of Router to access it with routes.php file
		$router = new static;
		require $file;

		return $router;
	}

	public function add($uri, $controller)
	{
		$this->routes[$uri] = 'controllers/' . $controller;
	}

	public function get($uri, $controller)
	{
		$this->routes['GET'][$uri] = 'controllers/' . $controller;
	}

	public function post($uri, $controller)
	{
		$this->routes['POST'][$uri] = 'controllers/' . $controller;
	}

	public function direct($uri, $requestType)
	{
		if (array_key_exists($uri, $this->routes[$requestType])) {
			return $this->routes[$requestType][$uri];
		}  else {
			throw new Exception('No route Match ' . $uri);
			
		}
	}
}