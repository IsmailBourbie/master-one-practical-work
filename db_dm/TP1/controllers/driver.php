<?php

$page = isset($_GET['page']) ? $_GET['page'] : 'new';

if ($page == 'new') {

    if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['name'])) {

        $name = trim(filter_var($_POST['name'], FILTER_SANITIZE_STRING));
        $societe = filter_var($_POST['societe'], FILTER_SANITIZE_NUMBER_INT);
        if (!empty($name) && !empty($societe)) {
            if ($app['database']->save_two_param('driver', [$name, $societe])) {
                $_SESSION['msg'] = '<div class="alert alert-success text-center">add driver success</div>';
            } else {
                $_SESSION['msg'] = '<div class="alert alert-danger text-center">add driver faild</div>';
            }
        } else {
            $_SESSION['msg'] = '<div class="alert alert-danger text-center">Empty name</div>';
        }

        redirect('driver');

    } else {
        // get data from database
        $companies = $app['database']->selectAll('company');
            // dd($companies);
        $title = 'New driver';
        require 'views/driver/new.view.php';
    }

} elseif ($page == 'show') {
    // get data from database
    $drivers = $app['database']->selectAllJoin('driver', 'company');

    $title = 'drivers';
    require 'views/driver/show.view.php';
} else {
    redirect();
}
