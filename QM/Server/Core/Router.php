<?php

namespace Core;
class Router {
   protected $routes = [
       'POST' => [],
       'GET' => [],
       'PUT' => [],
       'DELETE' => [],
   ];
   protected $params = [];

   public function convert2regex($route, $params = [])
   {
      // convert the route to a regex: escape '/'
      $route = preg_replace('/\//', '\\/', $route);

      // convert custom variables like id or maybe lang
      $route = preg_replace('/\{([a-z]+):([^\}]+)\}/', '(?P<\1>\2)', $route);

      // add start ^ and the end $;
      $route = '/^' . $route . '$/i';
        return $route;

   }
   public function post($route, $params = []) {
        $route = $this->convert2regex($route);
       $this->routes['POST'][$route] = $params;

   }
   public function get($route, $params = []) {
        $route = $this->convert2regex($route);
       $this->routes['GET'][$route] = $params;

   }
   public function put($route, $params = []) {
        $route = $this->convert2regex($route);
       $this->routes['PUT'][$route] = $params;

   }
   public function delete($route, $params = []) {
        $route = $this->convert2regex($route);
       $this->routes['DELETE'][$route] = $params;

   }

   /*
    * if match the url so add the params of url to params of Router
    * match url with table of routes
    */
   public function match($url, $request_type)
   {
      foreach ($this->routes[$request_type] as $route => $params) {
         if (preg_match($route, $url, $matches)) {
            foreach ($matches as $key => $match) {
               if (is_string($key)) {
                  $params[$key] = $match;
               }
            }
            $this->params = $params;
            return true;
         }
      }
      return false;
   }

    /**
     * @param $url
     * @param $request_type
     * @throws \Exception
     */
    public function dispatch($url, $request_type)
   {
      $url = $this->removeQueryStringVariables($url);
      if ($this->match($url, $request_type)) {
         $controller = $this->params["controller"];
         $controller = $this->convertToStudlyCaps($controller);
         $controller = $this->getNamespace() . $controller;

         if (class_exists($controller)) {
            $controller_object = new $controller($this->params);
            $action = $this->params['action'];
            $action = $this->convertToCamelCase($action);

            if (preg_match('/action$/i', $action) == 0) {
               $controller_object->$action();
            } else {
               throw new \Exception("Method {$action} not exist");
            }

         } else {
            throw new \Exception("Controller {$controller} not exist");
         }

      } else {
         throw new \Exception("Not Route Found for url: {$url}", 404);
      }
   }

   public function getParams()
   {
      return $this->params;
   }

   protected function convertToStudlyCaps($string)
   {
      return str_replace(' ', '', ucwords(str_replace('-', ' ', $string)));
   }

   protected function convertToCamelCase($string)
   {
      return lcfirst($this->convertToStudlyCaps($string));
   }

   // for remove ?page=1&view=....
   protected function removeQueryStringVariables($url)
   {
      if ($url != '') {
         $parts = explode('&', $url, 2);
         if (strpos($parts[0], '=') === false) {
            $url = $parts[0];
         } else {
            $url = '';
         }
      }
      return $url;
   }

   protected function getNamespace()
   {
      $namespace = 'App\Controllers\\';
      if (array_key_exists('namespace', $this->params)) {
         $namespace .= $this->params['namespace'] . '\\';
      }
      return $namespace;
   }
}
