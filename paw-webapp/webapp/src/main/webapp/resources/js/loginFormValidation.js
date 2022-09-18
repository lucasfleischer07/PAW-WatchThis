function validate() {
    if (document.loginForm.email.value == "" && document.loginForm.password.value == "") {
        alert("Username and password are required");
        document.loginForm.email.focus();
        return false;
    }
    if (document.loginForm.email.value == "") {
        alert("Username is required");
        document.f.username.focus();
        return false;
    }
    if (document.f.password.value == "") {
        alert("Password is required");
        document.f.password.focus();
        return false;
    }
}