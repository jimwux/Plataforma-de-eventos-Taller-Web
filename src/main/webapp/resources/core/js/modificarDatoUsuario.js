document.querySelectorAll('.editarDatos').forEach(button => {
    button.addEventListener('click', function () {
        const tarjeta = this.closest('.tarjeta');
        const campo = tarjeta.querySelector('span').getAttribute('data-campo');
        const valorActual = tarjeta.querySelector('span').textContent;

        document.getElementById('campo').value = campo;
        document.getElementById('datoActual').value = valorActual;

        const modal = new bootstrap.Modal(document.getElementById('editarModal'))
        modal.show();
    });
});


