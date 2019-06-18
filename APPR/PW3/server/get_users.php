<?php

include 'connection.php';

if($_SERVER['REQUEST_METHOD'] === 'GET') {
    $response = [
        "status" => 404,
        "data" => ""
    ];
    $stmt = $db->prepare("SELECT * FROM users");

    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_OBJ);
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