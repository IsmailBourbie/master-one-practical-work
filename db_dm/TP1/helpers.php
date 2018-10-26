<?php

function redirect($url = 'home')
{
    header('Location: ' . $url);
    exit;
}

function dd($statment)
{
    die(var_dump($statment));
}