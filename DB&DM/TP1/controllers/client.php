<?php

$page = isset($_GET['page']) ? $_GET['page'] : 'new';

if ($page == 'new') {

    if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['name'])) {
        $name = trim(filter_var($_POST['name'], FILTER_SANITIZE_STRING));
        if (!empty($name)) {
            if ($app['database']->save_name('client', $name)) {
                $_SESSION['msg'] = '<div class="alert alert-success text-center">add client success</div>';
            } else {
                $_SESSION['msg'] = '<div class="alert alert-danger text-center">add client faild</div>';
            }
        } else {
            $_SESSION['msg'] = '<div class="alert alert-danger text-center">Empty name</div>';
        }

        redirect('client');

    } else {
        $title = 'New Client';
        require 'views/client/new.view.php';
    }

} elseif ($page == 'show') {
    // get data from database
    $clients = $app['database']->selectAll('client');

    $title = 'Clients';
    require 'views/client/show.view.php';
} else {
    header('Location: home');
}
