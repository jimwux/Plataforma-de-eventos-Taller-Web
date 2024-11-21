function inicializarFormularioPagoParaTest() {
    // Crear el formulario y establecer sus propiedades
    const form = document.createElement("form");
    form.id = "formulario";
    form.style.display = "none"; // Ocultar el formulario

    // Crear los campos del formulario
    const fields = [
        { id: "nombre", type: "text", value: "" },
        { id: "apellido", type: "text", value: "" },
        { id: "correo", type: "text", value: "" },
        { id: "correoRep", type: "text", value: "" },
        { id: "telefono", type: "text", value: "" },
        { id: "dni", type: "text", value: "" },
        { id: "aceptacionTerminos", type: "checkbox", value: "" },
    ];

    // Crear y añadir cada campo al formulario
    fields.forEach(field => {
        const input = document.createElement("input");
        input.id = field.id;
        input.type = field.type;
        input.value = field.value; // Establecer valor predeterminado
        if (field.type === "checkbox") {
            input.checked = false; // Checkbox desmarcado por defecto
        }
        form.appendChild(input);
    });

    // Crear elementos para errores
    const errores = [
        "errorNombre",
        "errorApellido",
        "errorCorreo",
        "errorCorreoRep",
        "errorTelefono",
        "errorDNI",
        "errorTYC",
    ];

    errores.forEach((errorId) => {
        const errorDiv = document.createElement("div");
        errorDiv.id = errorId;
        form.appendChild(errorDiv);
    });

    // Añadir botón de envío
    const botonEnviar = document.createElement("button");
    botonEnviar.classList.add("enviar");
    form.appendChild(botonEnviar);

    // Añadir el formulario al body
    document.body.appendChild(form);
}