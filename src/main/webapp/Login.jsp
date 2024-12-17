<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LOGIN</title>
    <link rel="stylesheet" href="./ultils/css/login.css">
</head>

<body>
    <form action="login" method="post">
        <h1>SIGN IN</h1>
        <div class="input-box">
            <label for="username">Username</label>
            <input type="text" name="username" id="username">
        </div>
        <div class="input-box">
            <label for="password">Password</label>
            <input type="password" name="password" id="password">
        </div>
        <% String error=(String) request.getAttribute("error"); if (error !=null) { %>
            <p class="error">
                <%= error %>
            </p>
            <% } %>

                <div class="links">
                    <a href="register">Haven't got an account?</a>
                </div>
                <button type="submit">Login</button>

    </form>
</body>

</html>