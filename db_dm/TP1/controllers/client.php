<?php

$page = isset($_GET['page']) ? $_GET['page'] : 'new';


if($page == 'new') {
    $title = 'New Client';
    require 'views/client/new.view.php';
}elseif ($page == 'show') {
    $title = 'Clients';
    require 'views/client/show.view.php';
}



else {
    header('Location: home');
}