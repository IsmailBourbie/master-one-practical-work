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


    // get data from drivers table
    public function selectDrivers()
    {
        $stmt = $this->pdo->prepare("SELECT driver._id, driver.driver_name, company.company_name FROM (driver INNER JOIN company ON driver.id_societe = company._id)");
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


    // get and insert data for orders table
    public function save_order($data)
    {
        $stmt = $this->pdo->prepare("INSERT INTO `order` VALUES(NULL, ?, ?, ?, ?, ?)");
        if ($stmt->execute($data)) {
            return true;
        }

        return false;
    }

    public function selectOrders()
    {
        $stmt = $this->pdo->prepare("SELECT `order`._id, `order`.price, `order`.city_start, `order`.city_end,
                                                     company.company_name, client.name FROM ((`order`
                                                        INNER JOIN company ON `order`.id_societe = company._id)
                                                        INNER JOIN client ON `order`.id_client = client._id)
                                                        ");
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_OBJ);
    }
}
