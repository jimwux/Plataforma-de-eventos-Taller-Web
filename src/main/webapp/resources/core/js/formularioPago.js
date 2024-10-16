

let formu = document.getElementById("formulario");


formu.addEventListener("submit", function(e){
    e.preventDefault();
    validacionFormularioRegistro();

});


function validacionFormularioRegistro(){

    let nombre = document.getElementById("nombre").value;
    let apellido = document.getElementById("apellido").value;
    let correo = document.getElementById("correo").value;
    let correoRep = document.getElementById("correoRep").value;
    let telefono = document.getElementById("telefono").value;
    let dni = document.getElementById("dni").value;
    let acepto = document.getElementById("acepto").checked;

    let error = false;

    let exp = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;

    if(!exp.test(nombre) || nombre === ""){
        error = true;
    }

    if(!exp.test(apellido) || apellido === ""){
        error = true;
    }

    let expCorreo = /^(.+\@.+\..+)$/;

    if(!expCorreo.test(correo) || correo === ""){
        error = true;
    }

    if(correo !== correoRep && (!expCorreo.test(correoRep) || correoRep === "")){
        error = true;
    }

    let expNumero = /^11\d{8}$/;

    if(!expNumero.test(telefono) || telefono === ""){
        error = true;
    }

    let expDni = /^\d{8}$/;


    if(!expDni.test(dni) || dni === ""){
        error = true;
    }

    if(!acepto){
      error = true;
    }

    if(error){
        alert("Datos invalidos")
    }else{
        formu.submit();
        alert("Compra realizada")
    }

}

