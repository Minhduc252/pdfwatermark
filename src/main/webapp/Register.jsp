<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create an account</title>
    <link rel="stylesheet" href="./ultils/css/login.css">
</head>
<body>
    <form action="register" method="post">
        <h1>SIGN UP</h1>
        <div class="input-box">
            <label for="username">Username</label>
            <input type="text" name="username" id="username">
        </div>
        <div class="input-box">
            <label for="password">Password</label>
            <input type="password" name="password" id="password">
        </div>
        <div class="input-box">
            <label for="fullname">Full Name</label>
            <input type="text" name="fullname" id="fullname">
        </div>
        <div class="links">
             <a href="login">Have an account?</a>
        </div>
        <button type="submit">Create an account</button>
    </form>
</body>
</html>