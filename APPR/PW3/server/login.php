<?php

include 'connection.php';

if($_SERVER['REQUEST_METHOD'] === 'POST') {
    $response = [
        "status" => 404,
        "data" => ""
    ];
    $email = filter_var($_POST['email'], FILTER_SANITIZE_EMAIL);
    $password = filter_var($_POST['password'], FILTER_SANITIZE_STRING);


    $stmt = $db->prepare("SELECT * FROM users WHERE email = :email AND password = :password");

    $stmt->bindValue(':email', $email, PDO::PARAM_STR);
    $stmt->bindValue(':password', $password, PDO::PARAM_STR);
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_OBJ);
    if($result) {
        $response["status"] = 200;
        $response["data"] = $result;
    }
    header("Content-type: application/json");
    echo json_encode($response);
} else {
    header("Content-type: application/json");
    echo json_encode(['Status' => 404, 'message' => "access denied"]);
}