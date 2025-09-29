// Select button and body
const toggleButton = document.getElementById('theme-toggle');
const body = document.body;
const icon = toggleButton.querySelector('i');

// Function to set theme
function setTheme(theme) {
    if (theme === 'dark') {
        body.classList.add('dark-theme');
        icon.classList.remove('bi-sun');
        icon.classList.add('bi-moon');
    } else {
        body.classList.remove('dark-theme');
        icon.classList.remove('bi-moon');
        icon.classList.add('bi-sun');
    }
    localStorage.setItem('theme', theme); // save to localStorage
}

// Toggle theme on button click
toggleButton.addEventListener('click', () => {
    const currentTheme = body.classList.contains('dark-theme') ? 'dark' : 'light';
    setTheme(currentTheme === 'dark' ? 'light' : 'dark');
});

// Load saved theme on page load
window.addEventListener('DOMContentLoaded', () => {
    const savedTheme = localStorage.getItem('theme') || 'light';
    setTheme(savedTheme);
    console.log("Dark theme script loaded");
});
/**
 * 
 */