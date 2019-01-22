<?php

namespace App\Models;

use Core\Model;

class Client extends Model {
    public static function getAll()
    {
        try {
            $db = static::getDB();
            $stmt = $db->query("SELECT title, content FROM posts");
            $results = $stmt->fetchAll(\PDO::FETCH_OBJ);
            return $results;
        } catch (\PDOException $e) {
            echo $e->getMessage();
        }
    }

    public static function addClient($data)
    {
       try {
           $db = static::getDB();
           $sql = "INSERT";
           $stmt = $db->prepare($sql);
       } catch (\PDOException $e) {
           echo $e->getMessage();
       }
    }

}