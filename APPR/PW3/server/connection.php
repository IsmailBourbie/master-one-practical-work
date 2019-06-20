<?php 

$dsn = 'mysql:host=sql205.epizy.com;dbname=epiz_23932209_appr_pw3';
$user = 'epiz_23932209';
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