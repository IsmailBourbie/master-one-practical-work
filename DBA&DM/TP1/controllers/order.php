<?php

$page = isset($_GET['page']) ? $_GET['page'] : 'new';


if($page == 'new') {

    if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['price'])) {
    
        $price = filter_var($_POST['price'], FILTER_SANITIZE_NUMBER_FLOAT);
        $city_start = trim(filter_var($_POST['city_start'], FILTER_SANITIZE_STRING));
        $city_end = trim(filter_var($_POST['city_end'], FILTER_SANITIZE_STRING));
        $client = filter_var($_POST['client'], FILTER_SANITIZE_NUMBER_INT);
        $societe = filter_var($_POST['societe'], FILTER_SANITIZE_NUMBER_INT);


        if (!empty($price) && !empty($city_start) && !empty($city_end) && !empty($client) && !empty($societe)) {
            if ($app['database']->save_order([$price, $city_start, $city_end, $client, $societe])) {
                $_SESSION['msg'] = '<div class="alert alert-success text-center">Add order success</div>';
            } else {
                $_SESSION['msg'] = '<div class="alert alert-danger text-center">Add order faild</div>';
            }
        } else {
            $_SESSION['msg'] = '<div class="alert alert-danger text-center">Fill all the inputs please</div>';
        }

        redirect('order');

    } else {
        // get data from database
        $companies = $app['database']->selectAll('company');
        $clients = $app['database']->selectAll('client');
        // dd($companies);
        $title = 'New order';
        require 'views/order/new.view.php';
    }

}elseif ($page == 'show') {

    $orders = $app['database']->selectOrders();
    $title = 'Orders';
    require 'views/order/show.view.php';
}



else {
    header('Location: home');
}