<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PDF-WATERMARK</title>
        <link rel="stylesheet" href="./ultils/css/pdffactory.css">
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
                            <a href="./Login.html">
                                <button type="button">LOGIN</button>
                            </a>
                            <a href="./Register.html">
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
                 <object class="hero-left" data="<%= request.getAttribute("pdfPath") %>" type="application/pdf">
                    Trình duyệt của bạn không hỗ trợ xem file PDF.
                </object>
                <div class="hero-right">
                    <div class="item">
                        <h3>Watermark</h3>
                        <div class="upload">
                            <div class="watermark-type">
                                <input type="radio" id="image-radio" name="watermark-type" value="image">
                                <label for="image-radio">Image</label>
                                <input type="file" name="image-file" accept=".png">
                            </div>
                            <div class="watermark-type">
                                <input type="radio" id="text-radio" name="watermark-type" value="text">
                                <label for="text-radio">Text</label>
                                <input type="text" name="watermark-text">
                            </div>
                        </div>
                    </div>
                    <div class="item">
                        <h3>Custom</h3>
                        <div class="custom">
                            <div class="custom-field">
                                <label for="wm-opacity">Opacity</label>
                                <input type="number" name="wm-opacity" max="1" min="0">
                            </div>
                            <div class="custom-field">
                                <label for="wm-fontsize">Font size</label>
                                <input type="number" name="wm-fontsize" min="0">
                            </div>
                        </div>
                    </div>
                    <div class="item">
                        <button type="button" id="reset-btn">Reset</button>
                        <button type="button" id="preview-btn">Preview</button>
                        <button type="button" id="download-btn">Download</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="./ultils/js/pdffactory.js"></script>
    </html>