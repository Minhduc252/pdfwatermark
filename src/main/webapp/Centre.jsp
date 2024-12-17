<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="models.beans.PdfPreview" %>
        <%@ page import="java.util.List" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>PDF-WATERMARK</title>
                <link rel="stylesheet" href="./ultils/css/centre.css">
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
                        <h1>PDF Factory</h1>
                        <hr>
                        <div class="list-view">
                            <% List<PdfPreview> list = (List<PdfPreview>) request.getAttribute("pdfList");
                                    if(list != null) {
                                    for (PdfPreview pdfPreview : list) {
                                    %>
                                    <a href="PdfFactory?fileId=<%=pdfPreview.getFileId()%>" class="list-view-item">
                                        <img src="./ultils/image/defaut-file.jfif" alt="" class="pdf-preview">
                                        <hr>
                                        <div class="pdf-name">
                                            <%= pdfPreview.getFileName() %>
                                        </div>
                                        <div class="pdf-last-modified">
                                            <%= pdfPreview.getLastModified() %>
                                        </div>
                                    </a>
                                    <% } }%>
                                        <a href="./UploadPdf.jsp" class="list-view-item">
                                            <img src="./ultils/image/plus.png" alt="" class="pdf-preview">
                                        </a>
                        </div>
                    </div>
                </div>
            </body>
            <script src="./ultils/js/centre.js"></script>

            </html>