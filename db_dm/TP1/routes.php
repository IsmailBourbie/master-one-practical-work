<?php

// $router->add('url', 'controller');

$router->get('', 'index.php');
$router->get('home', 'index.php');
$router->get('about', 'about.php');
$router->get('contact', 'contact.php');
$router->get('client', 'client.php');
$router->get('order', 'order.php');
$router->post('names', 'add-name.php');
