<?php
/**
 * Created by PhpStorm.
 * User: IsMail BoUrbie
 * Date: 27/05/2018
 * Time: 16:55
 */

namespace Core;


use App\Config;

class Error {


    /**
     * Error Handler, Convert all errors to exception by throwing an ErrorException
     *
     * @param int $level Error level
     * @param String $message Error message
     * @param String $file filename the error raised in
     * @param int $line line number in file
     * @throws \ErrorException
     *
     * @return void
     */
    public static function errorHandler($level, $message, $file, $line)
    {
        if (error_reporting() !== 0) { // to keep @ working
            throw new \ErrorException($message, 0, $level, $file, $line);
        }
    }

    /**
     * Exception handler
     *
     * @param Exception $exception The exception
     *
     * return void
     * @throws \Exception
     */
    public static function exceptionHandler($exception)
    {
        $code = $exception->getCode();
        if ($code != 404) {
            $code = 505;
        }
        http_response_code($code);

        if (Config::SHOW_ERR) {
            echo "<h1>Fatal error</h1>";
            echo "<p>Uncaught exception: '" . get_class($exception) . "'</p>";
            echo "<p>Message: '" . $exception->getMessage() . "'</p>";
            echo "<p>Stack trace:<pre>" . $exception->getTraceAsString() . "</pre></p>";
            echo "<p>Throw in '" . $exception->getFile() . "' on line " . $exception->getLine() . "</p>";
        } else {
            $log = dirname(__DIR__) . '/logs/' . date('Y-m-d') . '.txt';
            ini_set('error_log', $log);

            $message = "\nUncaught exception: '" . get_class($exception) . "'";
            $message .= "Message: '" . $exception->getMessage() . "'";
            $message .= "\nStack trace:" . $exception->getTraceAsString();
            $message .= "\nThrow in '" . $exception->getFile() . "' on line " . $exception->getLine();
            $message .= "\n----------------------------------------------------------";
            error_log($message);

            if ($code == 404) {
                $data = [
                    "Status" => 404,
                    "Message" => 'Page Not Found'
                ];
                View::render('Errors/404.php', $data);
            } else {
                echo "<h1>Something went wrong here</h1>";
            }
        }

    }
}