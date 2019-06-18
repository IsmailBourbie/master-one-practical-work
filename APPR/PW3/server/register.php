<?php

include 'connection.php';

if($_SERVER['REQUEST_METHOD'] === 'POST') {
    $response = [
        "status" => 404,
        "data" => ""
    ];

    $email = filter_var($_POST['email'], FILTER_SANITIZE_EMAIL);
    $name = filter_var($_POST['name'], FILTER_SANITIZE_STRING);
    $password = filter_var($_POST['password'], FILTER_SANITIZE_STRING);


    $stmt = $db->prepare("INSERT INTO users VALUES(null, :name, :email, :password)");

    $stmt->bindValue(':name', $name, PDO::PARAM_STR);
    $stmt->bindValue(':email', $email, PDO::PARAM_STR);
    $stmt->bindValue(':password', $password, PDO::PARAM_STR);

    if($stmt->execute()) {
        $response["status"] = 200;
        $response["data"] = $db->lastInsertId();
    }
    header("Content-type: application/json");
    echo json_encode($response);
} else {
    header("Content-type: application/json");
    echo json_encode(['Status' => 404, 'message' => "access denied"]);
}