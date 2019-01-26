<?php

namespace App\Controllers;

use Core\Controller;
use Core\View;

class Clients extends Controller {
    public function addAction()
    {
//        $data = [
//            'society' => $_POST['society'],
//            'civility' => $_POST['civility'],
//            'fName' => $_POST['fName'],
//            'lName' => $_POST['lName'],
//            'address' => $_POST['address'],
//            'city' => $_POST['city'],
//            'postal' => $_POST['postal'],
//            'country' => $_POST['country'],
//            'phone' => $_POST['phone'],
//            'mobile' => $_POST['mobile'],
//            'fax' => $_POST['fax'],
//            'email' => $_POST['email'],
//            'type' => $_POST['type'],
//            'observation' => $_POST['observation'],
//            'exemptTVA' => $_POST['exemptTVA'],
//            'typedBy' => 'Android-Client',
//            'author' => 'Android-Client',
//            'livreSameAddress' => $_POST['livreSameAddress'],
//            'facSameAddress' => $_POST['facSameAddress'],
//        ];
        $response = [
            'title' => 'Clients',
            'status' => 200,
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