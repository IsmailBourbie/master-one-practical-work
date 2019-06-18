<?php 

$dsn = 'mysql:host=localhost;dbname=appr_pw3';
$user = 'root';
$pass = '';
$options = [
	PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8"
];
try {
	$db = new PDO($dsn,$user,$pass);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
	echo "ERROR CONNECTION DATABASES";
	die($e);
}