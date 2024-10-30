const formu = document.getElementById("formulario");

formu.addEventListener("submit", function(e) {
    e.preventDefault();

    const botonPresionado = e.submitter; // Directamente usar e.submitter
    if (botonPresionado.classList.contains('enviar')) {
        validacionFormularioRegistro();
    }
});

function validacionFormularioRegistro() {
    let nombre = document.getElementById("nombre").value;
    let apellido = document.getElementById("apellido").value;
    let correo = document.getElementById("correo").value;
    let correoRep = document.getElementById("correoRep").value;
    let telefono = document.getElementById("telefono").value;
    let dni = document.getElementById("dni").value;
    let acepto = document.getElementById("acepto").checked;

    let error = false;

    let exp = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;

    if (!exp.test(nombre) || nombre === "") {
        error = true;
    }

    if (!exp.test(apellido) || apellido === "") {
        error = true;
    }

    let expCorreo = /^(.+\@.+\..+)$/;

    if (!expCorreo.test(correo) || correo === "") {
        error = true;
    }

    if (correo !== correoRep && (!expCorreo.test(correoRep) || correoRep === "")) {
        error = true;
    }

    let expNumero = /^11\d{8}$/;

    if (!expNumero.test(telefono) || telefono === "") {
        error = true;
    }

    let expDni = /^\d{8}$/;

    if (!expDni.test(dni) || dni === "") {
        error = true;
    }

    if (!acepto) {
        error = true;
    }

    if (error) {
        alert("Datos invalidos");
    } else {
        formu.submit();
        alert("Compra realizada");
    }
}

const botonesBorrar = document.querySelectorAll('.borrar');
botonesBorrar.forEach(boton => {
    boton.addEventListener('click', (event) => {
        event.preventDefault();

        const fila = event.target.closest('tr');
        const cantidadInput = fila.querySelector('.cantidad-entrada');
        let cantidad = parseInt(cantidadInput.textContent);
        const subtotalElement = fila.querySelector('.subtotal-entrada');
        const precioUnitario = parseFloat(fila.querySelector('.precioEntrada').value);
        const cantidadOculta = fila.querySelector('.cantidad');

        if (cantidad > 1) {
            cantidad--;
            cantidadInput.textContent = cantidad;
            cantidadOculta.value = cantidad; // Sincroniza la cantidad oculta
            actualizarSubtotal(subtotalElement, cantidad, precioUnitario);
        } else {
            if (confirm("¿Está seguro de que desea eliminar esta entrada?")) {
                fila.remove();
            }
        }
        actualizarPrecioFinal();
    });
});

function actualizarSubtotal(subtotal, cantidad, precioUnitario) {
    subtotal.textContent = (cantidad * precioUnitario).toFixed(2);
}

function actualizarPrecioFinal() {
    let total = 0;
    const subtotales = document.querySelectorAll('.subtotal-entrada');
    subtotales.forEach(subtotal => {
        total += parseFloat(subtotal.textContent) || 0;
    });
    document.getElementById('precio-final').querySelector('span').textContent = total.toFixed(2);
}
