<?php	

include "Upload.class.php"; 

if ((isset($_POST["submit"])) && (!empty($_FILES['foto']))){ 
    $upload = new Upload($_FILES['foto'], 1000, 800, "upload/"); 
    $var = $upload->salvar();
    header("Location:http://localhost/tcc/home.html#/upload-photo/".$var);
    exit;
} 

?>
