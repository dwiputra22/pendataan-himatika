// Toggle collapsed class on sidebar when clicking the toggle button
const toggleButton = document.getElementById('toggle-button');
const sidebar = document.querySelector('.sidebar');

toggleButton.addEventListener('click', function () {
  sidebar.classList.toggle('collapsed');
});
