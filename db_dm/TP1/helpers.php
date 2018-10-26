<?php

function redirect($url = 'home')
{
    header('Location: ' . $url);
    exit;
}