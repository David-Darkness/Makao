const wrapper = document.querySelector('.wrapper');
const registerLink = document.querySelector('.register-link');
const loginLink = document.querySelector('.login-link');
const loginForm = document.querySelector('.form-box.login');
const registerForm = document.querySelector('.form-box.register');

// Agregar la clase 'active' al formulario de registro
registerLink.onclick = () => {
    wrapper.classList.add('active');
    loginForm.classList.add('move-left');  // Aplica animación para que el login se mueva a la izquierda
    registerForm.classList.add('move-right'); // Aplica animación para que el registro entre desde la derecha
};

// Eliminar la clase 'active' y volver a la vista del formulario de login
loginLink.onclick = () => {
    wrapper.classList.remove('active');
    loginForm.classList.remove('move-left');  // Restablece el formulario de login
    registerForm.classList.remove('move-right'); // Restablece el formulario de registro
};
