<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PDF-WATERMARK</title>
    <link rel="stylesheet" href="./ultils/css/style.css">
</head>

<body>
    <div class="container">
        <div class="nav-bar">
            <a href="index.jsp" class="nav-bar-logo">
                PDFWATERMARK
            </a>
            <div class="nav-bar-links">
                <a href="index.jsp">HOME</a>
                <a href="#">ABOUT</a>
                <a href="#">HELP</a>
            </div>
            <div class="nav-bar-account-box">
                <img src="./ultils/image/anonymous-user.jfif" alt="">
                <div class="nav-bar-account-box-action">
                    <% if (request.getSession().getAttribute("user")==null) { %>
                        <a href="login">
                            <button type="button">LOGIN</button>
                        </a>
                        <a href="register">
                            <button type="button">SIGN UP</button>
                        </a>
                        <% } else { %>
                            <a href="logout">
                                <button type="button">LOG OUT</button>
                            </a>
                            <% } %>
                </div>
            </div>
        </div>
        <div class="hero">
            <div class="hero-left">
                <p class="hero-left-title">
                    <b>WATERMARK</b><br> PDF with Ease!
                </p>
                <p class="hero-left-description">
                    Protect your documents and showcase your brand effortlessly. Our powerful PDF watermarking tool allows you to add text or image watermarks in just a few clicks. Secure, fast, and user-friendly.
                </p>
                <a href="centre"><button id="hero-left-btn">Get Start!</button></a>
            </div>
            <div class="hero-right">
                <img src="./ultils/image/Report.gif" alt="">
            </div>
        </div>
    </div>
</body>
<script src="./ultils/js/script.js"></script>
</html>