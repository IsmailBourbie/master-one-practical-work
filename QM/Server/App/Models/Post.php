<?php

namespace App\Models;

use Core\Model;

class Post extends Model {
   public static function getAll() {
      try {
         $db = static::getDB();
         $stmt = $db->query("SELECT title, content FROM posts");
         $results = $stmt->fetchAll(\PDO::FETCH_OBJ);
         return $results;
      } catch (\PDOException $e) {
         echo $e->getMessage();
      }
   }
}