<?php

namespace Core;
class View {

   /**
    * Render a view file
    *
    * @param String $view the view file
    * @param array $data
    * @throws \Exception if file not found
    *
    * return void
    */
   public static function render($view, $data = []) {
      $file = "../App/Views/$view";
      if (is_readable($file)) {
          header('Content-type: application/json');
         require $file;
      } else {
         throw new \Exception("$file not found");
      }

   }
}