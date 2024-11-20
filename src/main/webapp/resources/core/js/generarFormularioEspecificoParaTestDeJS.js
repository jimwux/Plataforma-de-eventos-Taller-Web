function inicializarFormularioPagoParaTest() {
    let formContainer = document.createElement("div");
    formContainer.style.display = "none"; // Oculta el contenedor
    formContainer.id = "formContainer";

    let form, nombre, apellido, correo, correoRep, telefono, dni, aceptacionTerminos, botonEnviar;

    form = document.createElement("form");
    form.id = "formulario";

    nombre = document.createElement("input");
    nombre.id = "nombre";
    form.appendChild(nombre);

    apellido = document.createElement("input");
    apellido.id = "apellido";
    form.appendChild(apellido);

    correo = document.createElement("input");
    correo.id = "correo";
    form.appendChild(correo);

    correoRep = document.createElement("input");
    correoRep.id = "correoRep";
    form.appendChild(correoRep);

    telefono = document.createElement("input");
    telefono.id = "telefono";
    form.appendChild(telefono);

    dni = document.createElement("input");
    dni.id = "dni";
    form.appendChild(dni);

    aceptacionTerminos = document.createElement("input");
    aceptacionTerminos.id = "aceptacionTerminos";
    aceptacionTerminos.type = "checkbox";
    form.appendChild(aceptacionTerminos);

    const errores = ["errorNombre", "errorApellido", "errorCorreo", "errorCorreoRep", "errorTelefono", "errorDNI", "errorTYC"];
    errores.forEach((errorId) => {
        const errorDiv = document.createElement("div");
        errorDiv.id = errorId;
        form.appendChild(errorDiv);
    });

    botonEnviar = document.createElement("button");
    botonEnviar.classList.add("enviar");
    form.appendChild(botonEnviar);

    formContainer.appendChild(form);
    document.body.appendChild(formContainer);
}