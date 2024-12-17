<!-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> -->
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Upload</title>
    <link rel="stylesheet" href="./ultils/css/upload.css" />
  </head>
  <body>
    <form method="post" enctype="multipart/form-data" id="upload-form">
      <h1>UPLOAD NEW PDF</h1>
      <input type="file" id="fileInput" name="file" />
      <div id="progress-container" style="display: none; width: 100%; background-color: #ddd;">
        <div id="progress-bar" style="
              height: 30px;
              width: 0%;
              background-color: #4caf50;
              text-align: center;
              color: white;
            ">
          0%
        </div>
      </div>
      <button type="button" id="form-btn">Tải lên</button>
    </form>
    <script src="./ultils/js/uploadPdf.js"></script>
  </body>
</html>
