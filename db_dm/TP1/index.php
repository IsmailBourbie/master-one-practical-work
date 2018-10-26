<?php
session_start();
require 'core/bootstrap.php';

require Router::load('routes.php')
			->direct(Request::uri());

