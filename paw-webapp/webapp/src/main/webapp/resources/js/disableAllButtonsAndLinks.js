function disableButtons() {
  const links = document.querySelectorAll("a");
  const buttons = document.querySelectorAll("button");

  links.forEach(function(link) {
    link.classList.add('disabled');
  });

  buttons.forEach(function(buttons) {
    buttons.setAttribute('disabled', 'true');
  });
}
