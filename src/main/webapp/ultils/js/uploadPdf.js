document.addEventListener("DOMContentLoaded", function () {
  const fileInput = document.getElementById("fileInput");
  const uploadButton = document.getElementById("form-btn");
  const progressContainer = document.getElementById("progress-container");
  const progressBar = document.getElementById("progress-bar");

  uploadButton.addEventListener("click", function (event) {
    event.preventDefault();

    const file = fileInput.files[0];
    if (!file) {
      alert("Vui lòng chọn tệp để tải lên.");
      return;
    }

    const chunkSize = 1024 * 500;
    let start = 0;
    let chunkIndex = 0;
    const totalChunks = Math.ceil(file.size / chunkSize);

    progressContainer.style.display = "block";

    function uploadChunk() {
      const end = Math.min(start + chunkSize, file.size);
      const chunk = file.slice(start, end);

      const formData = new FormData();
      formData.append("file", chunk);
      formData.append("chunkIndex", chunkIndex);
      formData.append("totalChunks", totalChunks);
      formData.append("fileName", file.name);

      fetch("UploadPdf", {
        method: "POST",
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Response from server:", data);

          if (data.status === "uploading" || data.status === "success") {
            chunkIndex++;
            start = chunkIndex * chunkSize;

            // Update progress bar
            const percent = Math.min((start / file.size) * 100, 100);
            progressBar.style.width = percent + "%";
            progressBar.textContent = Math.round(percent) + "%";

            if (start < file.size) {
              uploadChunk();
            } else if (data.status === "success") {
              alert("Tải lên thành công!");
              window.location.href = `PdfFactory?fileId=${data.fileId}`;
            }
          } else {
            throw new Error("Có lỗi xảy ra khi tải lên tệp.");
          }
        })
        .catch((error) => {
          console.error("Lỗi khi tải chunk:", error);
          alert("Tải lên thất bại, vui lòng thử lại.");
        });
    }
    uploadChunk();
  });
});
