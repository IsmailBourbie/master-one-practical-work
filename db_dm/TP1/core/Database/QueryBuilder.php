<?php

class QueryBuilder
{
    protected $pdo;

    public function __construct($pdo)
    {
        $this->pdo = $pdo;
    }

    public function selectAll($table)
    {
        $stmt = $this->pdo->prepare("select * from {$table}");
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_OBJ);
    }

    public function selectAllJoin($table1, $table2)
    {
        $stmt = $this->pdo->prepare("select * from {$table1}, {$table2} WHERE {$table1}._id = {$table2}._id");
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_OBJ);
    }

    // save name in specific table
	
    public function save_name($table, $name)
    {
        $stmt = $this->pdo->prepare("INSERT INTO {$table} VALUES(NULL, ?)");
        if ($stmt->execute([$name])) {
            return true;
        }

        return false;
    }

    // save in table has 2 params
	
    public function save_two_param($table, $param)
    {
        $stmt = $this->pdo->prepare("INSERT INTO {$table} VALUES(NULL, ?, ?)");
        if ($stmt->execute($param)) {
            return true;
        }

        return false;
    }
}
