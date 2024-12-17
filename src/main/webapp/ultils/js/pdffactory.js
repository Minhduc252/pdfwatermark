let navBarAccountBox = document.querySelector(".container .nav-bar .nav-bar-account-box");
let navBarAccountBoxAction = document.querySelector(".container .nav-bar .nav-bar-account-box-action");
let resetBtn = document.getElementById("reset-btn");
let previewBtn = document.getElementById("preview-btn");
let downloadBtn = document.getElementById("download-btn");
let pdf_preview = document.querySelector(".hero .hero-left");
let watermarkTypeElements = document.querySelectorAll('input[name="watermark-type"]');
let fontSizeInput = document.querySelector('input[name="wm-fontsize"]');

function toggleFontSizeInput() {
    let watermarkValue = document.querySelector('input[name="watermark-type"]:checked')?.value;
    let check = watermarkValue !== "text";
    
    fontSizeInput.disabled = check;
    document.querySelector('input[name="watermark-text"]').disabled = check;
}
document.querySelector('input[name="watermark-type"][value="image"]').checked = true;
watermarkTypeElements.forEach(element => element.addEventListener("change", toggleFontSizeInput));
toggleFontSizeInput();

let pdfPath = pdf_preview?.getAttribute("data");

document.addEventListener("DOMContentLoaded", () => console.log("DOM loaded"));

if (navBarAccountBox && navBarAccountBoxAction) {
    navBarAccountBox.addEventListener("click", () => navBarAccountBoxAction.classList.toggle("active"));
}

if (resetBtn) {
    resetBtn.addEventListener("click", () => {
        if (pdf_preview && pdfPath) {
            pdfPath = pdfPath.replace(/\(preview\)\.pdf$/, ".pdf");
            pdf_preview.setAttribute("data", pdfPath);
        }
    });
}

if (previewBtn) {
    previewBtn.addEventListener("click", () => {
        let watermarkValue = document.querySelector('input[name="watermark-type"]:checked')?.value;
        if (!watermarkValue) return;

        let formData = new FormData();
        formData.append("watermark-type", watermarkValue);

        if (watermarkValue === "text") {
            formData.append("watermark-text", document.querySelector('input[name="watermark-text"]')?.value.trim() || "");
        } else if (watermarkValue === "image") {
            let watermarkImageFile = document.querySelector('input[name="image-file"]')?.files[0];
            if (watermarkImageFile) formData.append("image-file", watermarkImageFile);
        }

        formData.append("wm-opacity", document.querySelector('input[name="wm-opacity"]')?.value.trim() || "1");
        formData.append("wm-fontsize", fontSizeInput?.value.trim() || "50");

        if (pdf_preview && pdfPath) {
            formData.append("pdf", pdfPath);
        } else {
            console.error("No PDF data found!");
            return;
        }

        fetch("watermark", {
            method: "POST",
            body: formData,
        })
            .then(response => {
                if (!response.ok) throw new Error("Network response was not ok");
                return response.ok;
            })
            .then(function () {
                let newFilePath = pdfPath.replace(/\.pdf$/, "(preview).pdf");
                pdf_preview.setAttribute("data", newFilePath);
            })
            .catch(error => console.error("Error while processing the watermark:", error));
    });
}

if (downloadBtn) {
    downloadBtn.addEventListener("click", () => {
        let src = pdf_preview?.getAttribute("data");
        if (src) {
            let downloadLink = document.createElement("a");
            downloadLink.href = src;
            downloadLink.download = "watermarked.pdf";
            downloadLink.click();
        } else {
            console.error("No PDF available for download!");
        }
    });
}
