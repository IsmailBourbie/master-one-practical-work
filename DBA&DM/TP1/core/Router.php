<?php

class Router {

	public $routes = [];

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

	public function direct($uri)
	{
		if (array_key_exists($uri, $this->routes)) {
			return $this->routes[$uri];
		}  else {
			throw new Exception('No route Match ' . $uri);
			
		}
	}
}