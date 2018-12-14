<?php

$page = isset($_GET['page']) ? $_GET['page'] : 'new';

if ($page == 'new') {

    if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['name'])) {
        $name = trim(filter_var($_POST['name'], FILTER_SANITIZE_STRING));
        if (!empty($name)) {
            if ($app['database']->save_name('company', $name)) {
                $_SESSION['msg'] = '<div class="alert alert-success text-center">add company success</div>';
            } else {
                $_SESSION['msg'] = '<div class="alert alert-danger text-center">add company faild</div>';
            }
        } else {
            $_SESSION['msg'] = '<div class="alert alert-danger text-center">Empty name</div>';
        }

        redirect('company');

    } else {
        $title = 'New Company';
        require 'views/company/new.view.php';
    }

} elseif ($page == 'show') {
    // get data from database
    $companies = $app['database']->selectAll('company');

    $title = 'Companies';
    require 'views/company/show.view.php';
} else {
    redirect();
}
