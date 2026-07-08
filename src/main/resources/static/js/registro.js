document.addEventListener("DOMContentLoaded", function () {
  const inputEdad = document.getElementById("inputEdad");
  const selectGrupo = document.getElementById("selectGrupo");

  if (inputEdad && selectGrupo) {
    inputEdad.addEventListener("input", function () {
      const edad = parseInt(this.value, 10);

      if (isNaN(edad)) {
        selectGrupo.value = "";
        return;
      }

      if (edad >= 2 && edad <= 5) {
        selectGrupo.value = "Preescolares";
      } else if (edad >= 6 && edad <= 10) {
        selectGrupo.value = "Escolares";
      } else if (edad >= 11 && edad <= 15) {
        selectGrupo.value = "Jóvenes";
      } else {
        selectGrupo.value = ""; // Si ponen algo fuera de rango
      }
    });
  }
});