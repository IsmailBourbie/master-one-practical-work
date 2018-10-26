<?php

$page = isset($_GET['page']) ? $_GET['page'] : 'new';


if($page == 'new') {
    $title = 'New Order';
    require 'views/order/new.view.php';
}elseif ($page == 'show') {
    $title = 'Orders';
    require 'views/order/show.view.php';
}



else {
    header('Location: home');
}