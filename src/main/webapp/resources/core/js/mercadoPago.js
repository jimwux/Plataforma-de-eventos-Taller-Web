document.addEventListener("DOMContentLoaded", () => {
    const mp = new MercadoPago("TEST-b2ab0f07-0bc6-4abd-8137-fe676931fc4a", {
        locale: 'es-AR'
    });
});
const MP = async () => {
    try {
        miEntrada = {}
        miEntrada.nombreEvento = document.getElementById('nombreEvento').value;
        miEntrada.cantidad = document.getElementById('cantidad').innerHTML;
        miEntrada.tipoEntrada = document.getElementById('tipo-entrada').innerHTML;
        miEntrada.precioUnidad = document.getElementById('precio-entrada').innerHTML;

        const response = await fetch('/equipomokito/mp', {
            method: 'POST',
            headers: {
                'Accept': 'Application/json',
                'Content-Type': 'Application/json'
            },
            body: JSON.stringify(miEntrada)
        })
        const preference = await response.text()
        createCheckoutButton(preference)
    } catch (e) {
        alert("error: " + e)
    }

    const createCheckoutButton = (preferenceId_OK)=>{
        const bricksBuilder = mp.bricks();
        const generarBoton = async () =>{
            if (window.checkoutBottom) window.checkoutBottom.unmount()
            bricksBuilder.create("wallet", "wallet_container", {
                initialization: {
                    preferenceId: preferenceId_OK,
                    buttonBackground: 'black',
                    redirectMode: "blank"
                },
                customization: {
                    texts: {
                        action: 'buy',
                        valueProp: 'security_details',
                    },
                },
            });
        }
        generarBoton()
    }
}