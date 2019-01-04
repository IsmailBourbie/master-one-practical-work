<?php

namespace App\Controllers;

use Core\Controller;
use Core\View;

class Profile extends Controller {
    public function indexAction()
    {
        $data = [
            'title' => 'Profile',
            'status' => 200,
            'data' => ['red', 'green', 'blue'],
        ];
        View::render("Profile/index.php", $data);
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