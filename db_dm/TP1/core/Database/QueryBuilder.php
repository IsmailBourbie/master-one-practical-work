<?php 

class QueryBuilder {
	protected $pdo;

	public function __construct($pdo) {
		$this->pdo = $pdo;
	}

	public function selectAll($table) {
		$stmt = $this->pdo->prepare("select * from {$table}");
		$stmt->execute();
		return $stmt->fetchAll(PDO::FETCH_OBJ);
	}
}