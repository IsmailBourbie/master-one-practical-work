<?php

namespace App\Controllers;

use Core\Controller;
use Core\View;

class Clients extends Controller {
    public function addAction()
    {
        $data = [
            'fname' => $_POST['fname'],
            'lname' => $_POST['lname'],
            'address' => $_POST['address'],
        ];
        $response = [
            'title' => 'Clients',
            'status' => 200,
            'data' => $data,
        ];
        View::render("Clients/index.php", $response);
    }


    protected function before()
    {
//      echo '(before) ';
//      return false ; // if you don't want execute the last of code
    }

    protected function after()
    {
//      echo ' (after)';
    }
}