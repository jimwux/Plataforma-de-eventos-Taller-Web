const formu = document.getElementById("formulario");

formu.addEventListener("submit", function(e) {
    e.preventDefault();

    const botonPresionado = e.submitter; //permite obtener directamente el botón que activó el envío
    if (botonPresionado.classList.contains('enviar')) { // si ese tiene la clase enviar entonces quiere decir que hubo intento de enviar el form y valido
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
    let aceptacionTerminos = document.getElementById("aceptacionTerminos").checked;

    document.getElementById("errorNombre").innerHTML = "";
    document.getElementById("errorApellido").innerHTML = "";
    document.getElementById("errorCorreo").innerHTML = "";
    document.getElementById("errorCorreoRep").innerHTML = "";
    document.getElementById("errorTelefono").innerHTML = "";
    document.getElementById("errorDNI").innerHTML = "";
    document.getElementById("errorTYC").innerHTML = "";

    let error = false;

    let exp = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;

    if (!exp.test(nombre) || nombre === "") {
        error = true;
        document.getElementById("errorNombre").innerHTML = "<p> El nombre debe contener solo letras </p>"
    }

    if (!exp.test(apellido) || apellido === "") {
        error = true;
        document.getElementById("errorApellido").innerHTML = "<p> El apellido debe contener solo letras  </p>"
    }

    let expCorreo = /^(.+@.+\..+)$/;

    if (!expCorreo.test(correo) || correo === "") {
        error = true;
        document.getElementById("errorCorreo").innerHTML = "<p> El email debe cumplir el formato de un email </p>"
    }

    if (correoRep === "" || correo !== correoRep || (!expCorreo.test(correoRep))) {
        error = true;
        document.getElementById("errorCorreoRep").innerHTML = "<p> El email debe cumplir el formato de un email y coincidir con el primero </p>"
    }

    let expNumero = /^11\d{8}$/;

    if (!expNumero.test(telefono) || telefono === "") {
        error = true;
        document.getElementById("errorTelefono").innerHTML = "<p> El telefono debe cumplir con los 10 digitos </p>"
    }

    let expDni = /^\d{8}$/;

    if (!expDni.test(dni) || dni === "") {
        error = true;
        document.getElementById("errorDNI").innerHTML = "<p> El DNI debe cumplir el formato de los 8 digitos </p>"
    }

    if (!aceptacionTerminos) {
        error = true;
        document.getElementById("errorTYC").innerHTML = "<p> Debe marcar los terminos y condiciones </p>"
    }

    if (error) {

    } else {
        formu.submit();
    }
}

//seleccionamos todos los botones con la clase .borrar, los cuales esten
//en el front cargados

let botones = document.querySelectorAll('.borrar');

//recorre los botones que seleccionamos anteriormente
botones.forEach(boton => {
    //se le agrega un evento click a cada boton, es decir que cuando se clickee cualquiera
    //de ellos, los mismos realizaran lo asignado a ese evento
    boton.addEventListener('click', (event) => {
        //previene el envio del formulario
        event.preventDefault();

        //al boton que se clickeo, le buscamos su elemento tr MAS cercano que contenga
        //el boton, es decir la fila que representamos
        let fila = event.target.closest('tr');

        //selecciona la cantidad que muestra en la pantalla
        let cantidadInput = fila.querySelector('.cantidad-entrada');
        //parseamos su contenido a int para no generar conflictos
        let cantidad = parseInt(cantidadInput.textContent);
        //selecciona el subtotal mostrado en pantalla
        let subtotalElement = fila.querySelector('.subtotal-entrada');
        //nos traemos el precio unitario oculto en la pagina para luego realizar calculos con el
        let precioUnitario = parseFloat(fila.querySelector('.precioEntrada').value);
        //seleccionamos la cantidad oculta, la cual pasara por nuestro array que llega a mercado pago
        let cantidadOculta = fila.querySelector('.cantidad');


        //verificamos esa cantidad para que en el caso que tengamos + de 1
        //se descuente de la cantidad y se modifique su precio subtotal
        //DEBEMOS CAMBIAR EL OCULTO PARA NO GENERAR CONFLICTO EN EL ARRAY QUE LLEGA A MP
        let botonBorrar = document.getElementById("tachito");
        let precioTotal = parseFloat(document.getElementById('precio-final-valor-original').textContent) || 0;

        if (precioTotal > precioUnitario) {
            if(cantidad == 1){
                fila.remove();
            }
            cantidad--;
            cantidadInput.textContent = cantidad;
            cantidadOculta.value = cantidad; // Sincroniza la cantidad oculta
            actualizarSubtotal(subtotalElement, cantidad, precioUnitario);
        } else {
            botonBorrar.disabled();
        }
        //actualizamos el total
        actualizarPrecioFinal();
    });
});

function actualizarSubtotal(subtotal, cantidad, precioUnitario) {
    subtotal.textContent = (cantidad * precioUnitario).toFixed(2);
    //nos traemos el subtotal, la cantidad y el precio unitario que tenemos dentro del evento
}


function actualizarPrecioFinal() {
    let total = 0;
    //nos traemos todos los subtotales y los recorremos para ir creando nuestro nuevo total en caso
    // de haber eliminado alguna entrada
    let subtotales = document.querySelectorAll('.subtotal-entrada');
    subtotales.forEach(subtotal => {
        total += parseFloat(subtotal.textContent) || 0;
        //caso de devolver un NaN,mostrara 0 en pantalla
    });
    //modificamos precio total en la vista
    document.getElementById('precio-final-valor-original').textContent = total.toFixed(2);
}

