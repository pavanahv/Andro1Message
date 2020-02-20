<?php
    // this is for creating mysql object
    $mysqli = new mysqli("localhost", "id8169200_ahvpavankumar", "9885501274", "id8169200_global");
    // checking data is there or not via post method
    if (isset($_POST['data']))
    {
        $s = $_POST['data'];
        if ($mysqli->connect_errno) {
            echo "failed to connect to mysql $mysqli->connect_errno";
        }else{
            // inserting data into database table which we got from mobile
            try{
                echo $mysqli->query("insert into data(data) values('".$s."')");
            }catch(Exception $e) {
                echo 'Message: ' .$e->getMessage();
            }
        }
    }else{
        // here we are getting data whatever we have stored above from mobile
        $result=$mysqli->query("select data from data");
        if ($result->num_rows > 0) {
            // output data of each row
            echo "<html> <style>";
            echo "table, th, td { border: 1px solid black;}";
            echo "</style>";
            echo "<body><table><tr><td>ID</td><td>Address</td><td>Message</td><td>State</td><td>Time</td><td>Folder Name</td></tr>";
            while($row = $result->fetch_assoc()) {
                // decoding data from base64 to json format
                $s=base64_decode($row["data"]);
                // decoding data from json to text i.e html
                $a=json_decode($s);
                $len=count($a);
                for($i=0;$i<$len;$i++)
                {
                    echo "<tr>";
                    $obj=$a[$i];

                    echo "<td>".$obj->id."</td>";
                    echo "<td>".$obj->address."</td>";
                    echo "<td>".$obj->msg."</td>";
                    echo "<td>".$obj->state."</td>";
                    echo "<td>".$obj->time."</td>";
                    echo "<td>".$obj->fname."</td>";
                    echo "</tr>";
                }
            }
            echo "</table></body></html>";
        } else {
            echo "0 results";
        }

    }
?>