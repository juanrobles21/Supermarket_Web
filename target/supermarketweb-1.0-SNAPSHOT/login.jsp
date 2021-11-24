<%-- 
    Document   : login
    Created on : 24/11/2021, 02:32:46 AM
    Author     : USUARIO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styleLogin.css" rel="stylesheet" type="text/css"/>
        <title>Login</title>
    </head>
    <body>
        <div class="character-box">
            <div class="avatar">
                <a href="#"><i class="fas fa-user"></i></a>
            </div>

            <form action="faces/index.xhtml" method="post">
                <!-- USER INPUT -->
                <label>Usuario</label>
                <input type="text" placeholder="User" name="username">
                <!-- PASSWORD INPUT -->
                <label>Password</label>
                <input type="password" placeholder="Password" name="password">
                <input type="submit" value="Iniciar sesión">
            </form>

        </div>
        <script src="https://kit.fontawesome.com/2c36e9b7b1.js" crossorigin="anonymous"></script>
    </body>
</html>
