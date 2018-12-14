<?php
$app = [];
$app['config'] = require 'config.php';

require 'Router.php';
require 'Request.php';
require 'database/Connection.php';
require 'database/QueryBuilder.php';
require 'helpers.php';


$app['database'] = new QueryBuilder(
    Database::make($app['config']['database'])
);