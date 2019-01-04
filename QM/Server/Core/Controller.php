<?php
/**
 * Created by PhpStorm.
 * User: IsMail BoUrbie
 * Date: 26/05/2018
 * Time: 16:11
 */

namespace Core;


abstract class Controller {
   protected $route_params = [];

   /**
    * Controller constructor.
    * @param $route_params
    */
   public function __construct($route_params) {
      $this->route_params = $route_params;
   }

   public function __call($name, $args) {
      $method = $name . 'Action';
      if (method_exists($this, $method)) {
         if ($this->before() !== false) {
            call_user_func_array([$this, $method], $args);
            $this->after();
         }
      } else {
         throw new \Exception("Method {$name} not found in controller " . get_class($this));
      }
   }

   protected function before() {

   }

   protected function after() {

   }

}