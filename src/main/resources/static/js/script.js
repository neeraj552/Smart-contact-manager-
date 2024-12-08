console.log("done");
let currentTheme = getTheme();
changeTheme(currentTheme);

function changeTheme() {
    document.querySelector('html').classList.add(currentTheme);
    const changeThemeButton = document.querySelector('#theme_change_button');
    changeThemeButton.addEventListener('click', () => {
        console.log("changeTheme button clicked");
        document.querySelector('html').classList.remove(currentTheme);
        if (currentTheme === "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";
        }
        setTheme(currentTheme);
        document.querySelector('html').classList.add(currentTheme);
    });
}

function getTheme() {
    const theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}
