<?php  
class Database{
	
	public static function make($config)
	{
		try {
			return new PDO(
				$config['host'] . ';dbname=' . $config['name'],
				$config['username'],
				$config['password'],
				$config['options']
			);
		} catch(PDOException $e) {
			echo $e->getMessage();
		}
	}
}