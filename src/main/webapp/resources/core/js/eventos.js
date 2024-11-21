// CARGAR PROVINCIAS

document.addEventListener("DOMContentLoaded", () => {
    cargarProvincias();
});

function cargarProvincias() {
    fetch('/equipomokito/provincias')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la red');
            }
            return response.json();
        })
        .then(provincias => {
            const selectProvincia = document.getElementById('provincia');
            selectProvincia.innerHTML = ''; // Limpiar el select

            // Agregar la opción predeterminada
            const optionDefault = document.createElement('option');
            optionDefault.value = '';
            optionDefault.text = 'Seleccione una provincia';
            selectProvincia.appendChild(optionDefault);

            // Llenar el select con las provincias obtenidas
            provincias.forEach(provincia => {
                const option = document.createElement('option');
                option.value = provincia.nombre; // o province.id si necesitas el ID
                option.text = provincia.nombre;
                selectProvincia.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar las provincias:', error));
}

// CARGAR CIUDADES

let provinciaSelect = document.getElementById("provincia");
let ciudadesSelect = document.getElementById("ciudad");

provinciaSelect.addEventListener("change", cargarCiudades);

function cargarCiudades() {
    let provinciaNombre = provinciaSelect.value;

    // Si no elegiste provincia no te sale ciudades
    if (!provinciaNombre || provinciaNombre === "Seleccione una provincia") {
        ciudadesSelect.innerHTML = "<option value=''>Seleccione una ciudad</option>"; // Resetea las ciudades
        return; // No continuar con la búsqueda
    }

    let url = "/equipomokito/ciudades/" + encodeURIComponent(provinciaNombre);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor: ' + response.status);
            }
            return response.json();
        })
        .then(ciudades => {
            ciudadesSelect.innerHTML = ""; // Limpiar opciones previas una vez carguen las nuevas ciudades

            // Agregar la opción inicial "Seleccione una ciudad"
            let optionInicial = document.createElement("option");
            optionInicial.value = "";
            optionInicial.text = "Seleccione una ciudad";
            ciudadesSelect.appendChild(optionInicial);

            // Agregar las nuevas ciudades al select
            ciudades.forEach(ciudad => {
                let option = document.createElement("option");
                option.value = ciudad.nombre;
                option.text = ciudad.nombre;
                ciudadesSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            ciudadesSelect.innerHTML = "<option value=''>Seleccione una ciudad</option>";
        });
};


