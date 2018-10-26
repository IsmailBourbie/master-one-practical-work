<?php 

return [
	'database' => [
		'name' => 'cleancode',
		'username' => 'root',
		'password' => '',
		'host' => 'mysql:host=localhost',
		'options' => [
			PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
		]
	],
];