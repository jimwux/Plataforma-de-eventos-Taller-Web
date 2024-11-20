document.addEventListener("DOMContentLoaded", function () {
    const formu = document.getElementById("formulario");

    if (formu) {
        formu.addEventListener("submit", function (e) {
            e.preventDefault();

            const botonPresionado = e.submitter; // Directamente usar e.submitter
            if (botonPresionado.classList.contains('enviar')) {
                validacionFormularioRegistro();
            }
        });
    }

    // Seleccionamos todos los botones con la clase .borrar
    const botones = document.querySelectorAll('.borrar');

    botones.forEach(boton => {
        boton.addEventListener('click', (event) => {
            event.preventDefault();

            const fila = event.target.closest('tr');
            const cantidadInput = fila.querySelector('.cantidad-entrada');
            let cantidad = parseInt(cantidadInput.textContent);
            const subtotalElement = fila.querySelector('.subtotal-entrada');
            const precioUnitario = parseFloat(fila.querySelector('.precioEntrada').value);
            const cantidadOculta = fila.querySelector('.cantidad');

            const botonBorrar = document.getElementById("tachito");
            const precioTotal = parseFloat(document.getElementById('precio-final-valor-original').textContent) || 0;

            if (precioTotal > precioUnitario) {
                if (cantidad === 1) {
                    fila.remove();
                } else {
                    cantidad--;
                    cantidadInput.textContent = cantidad;
                    cantidadOculta.value = cantidad; // Sincroniza la cantidad oculta
                    actualizarSubtotal(subtotalElement, cantidad, precioUnitario);
                }
            } else {
                botonBorrar.disabled = true;
            }

            // Actualizamos el total
            actualizarPrecioFinal();
        });
    });
});

function validacionFormularioRegistro() {
    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value;
    const correo = document.getElementById("correo").value;
    const correoRep = document.getElementById("correoRep").value;
    const telefono = document.getElementById("telefono").value;
    const dni = document.getElementById("dni").value;
    const aceptacionTerminos = document.getElementById("aceptacionTerminos").checked;

    let error = false;

    const exp = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
    if (!exp.test(nombre) || nombre === "") error = true;
    if (!exp.test(apellido) || apellido === "") error = true;

    const expCorreo = /^(.+@.+\..+)$/;
    if (!expCorreo.test(correo) || correo === "") error = true;
    if (correo !== correoRep || (!expCorreo.test(correoRep) || correoRep === "")) error = true;

    const expNumero = /^11\d{8}$/;
    if (!expNumero.test(telefono) || telefono === "") error = true;

    const expDni = /^\d{8}$/;
    if (!expDni.test(dni) || dni === "") error = true;

    if (!aceptacionTerminos) error = true;

    if (error) {
        alert("Datos invalidos");
    } else {
        const formu = document.getElementById("formulario");
        formu.submit();
    }
}

function actualizarSubtotal(subtotal, cantidad, precioUnitario) {
    subtotal.textContent = (cantidad * precioUnitario).toFixed(2);
}

function actualizarPrecioFinal() {
    let total = 0;
    const subtotales = document.querySelectorAll('.subtotal-entrada');
    subtotales.forEach(subtotal => {
        total += parseFloat(subtotal.textContent) || 0;
    });
    document.getElementById('precio-final-valor-original').textContent = total.toFixed(2);
}