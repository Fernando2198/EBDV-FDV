function guardarAsistenciaInstantanea(checkbox, ninoId, diaSemana) {
    const asistio = checkbox.checked;
    // Buscamos la palomita verde pequeña que pertenece a esta celda
    const indicador = checkbox.parentElement.querySelector('.status-saved');

    // Extraemos el Token de seguridad CSRF inyectado por Spring Security de las metas
    const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content") || "";
    const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content") || "X-CSRF-TOKEN";

    // Empaquetamos los datos
    const formData = new URLSearchParams();
    formData.append('ninoId', ninoId);
    formData.append('dia', diaSemana);
    formData.append('asistio', asistio);

    // Enviamos la petición HTTP en segundo plano
    fetch('/api/asistencia/marcar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [header]: token
        },
        body: formData
    })
    .then(response => {
        if (response.ok) {
            // Si todo sale bien, mostramos un destello con la mini palomita verde
            indicador.classList.remove('hidden');
            setTimeout(() => {
                indicador.classList.add('hidden');
            }, 1200);
        } else {
            alert('No se pudo guardar la asistencia de este día. Intenta de nuevo.');
            checkbox.checked = !asistio; // Revierte el estado del checkbox
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error de conexión con el servidor.');
        checkbox.checked = !asistio; // Revierte en caso de fallo crítico
    });
}