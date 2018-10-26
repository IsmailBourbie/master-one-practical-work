<?php

class Request {

	public static function uri()
	{
		$parts = explode('&', $_SERVER['QUERY_STRING'], 2);
		return trim($parts[0], '/');
	}

	public static function method()
	{
		return $_SERVER['REQUEST_METHOD'];
	}
}