let navBarAccountBox = document.querySelector(".container .nav-bar .nav-bar-account-box");
let navBarAccountBoxAction = document.querySelector(".container .nav-bar .nav-bar-account-box-action");
navBarAccountBox.addEventListener("click", function () {
    navBarAccountBoxAction.classList.toggle("active");
});


