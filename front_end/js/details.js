const apiConfig = document.getElementById("apiConfig");
const updateForm = document.getElementById("updateForm");
const updateStatus = document.getElementById("updateStatus");
const updateError = document.getElementById("updateError");
const deleteButton = document.getElementById("deleteButton");
const modulesEl = document.getElementById("modules");
const sifraInput = document.getElementById("sifra");
const nazivInput = document.getElementById("naziv");
const stepenInput = document.getElementById("stepen");
const ukupnoEspbInput = document.getElementById("ukupnoEspb");

renderApiBaseControls(apiConfig);

let currentProgram = null;

function showStatus(message) {
  updateStatus.textContent = message;
  updateStatus.style.display = "block";
}

function clearStatus() {
  updateStatus.style.display = "none";
}

function showError(message) {
  updateError.textContent = message;
  updateError.style.display = "block";
}

function clearError() {
  updateError.style.display = "none";
}

function renderModules(moduli = []) {
  modulesEl.innerHTML = "";
  if (!moduli.length) {
    modulesEl.innerHTML = "<div class=\"notice\">No modules attached.</div>";
    return;
  }

  moduli.forEach(modul => {
    const moduleCard = document.createElement("div");
    moduleCard.className = "module";
    moduleCard.innerHTML = `
      <header>
        <h3>${modul.naziv} (${modul.oznaka || ""})</h3>
        <div class="tag">Godina ${modul.godina}</div>
      </header>
    `;

    const list = document.createElement("div");
    list.className = "list";

    if (!modul.planStavke || modul.planStavke.length === 0) {
      const empty = document.createElement("div");
      empty.className = "notice";
      empty.textContent = "Nema plan stavki.";
      list.appendChild(empty);
    } else {
      modul.planStavke.forEach(stavka => {
        const row = document.createElement("div");
        row.className = "program-row";

        let detail = "";
        if (stavka.predmetSifra) {
          detail = `Predmet: ${stavka.predmetSifra}`;
        } else if (stavka.izbornaGrupa) {
          const grupa = stavka.izbornaGrupa;
          detail = `Izborna grupa (${grupa.brojPredmeta} od ${grupa.predmetSifre.length}) - ESPB ${grupa.potrebniEspb}`;
        }

        row.innerHTML = `
          <div>
            <div><strong>${stavka.tip}</strong> - Semestar ${stavka.semestar}</div>
            <div>${detail}</div>
          </div>
          <div class="tag">${stavka.aktivan ? "Aktivan" : "Neaktivan"}</div>
        `;
        list.appendChild(row);

        if (stavka.izbornaGrupa && stavka.izbornaGrupa.predmetSifre) {
          const predmetList = document.createElement("div");
          predmetList.className = "inline";
          predmetList.style.marginLeft = "8px";
          stavka.izbornaGrupa.predmetSifre.forEach(sifra => {
            const tag = document.createElement("span");
            tag.className = "tag";
            tag.textContent = sifra;
            predmetList.appendChild(tag);
          });
          list.appendChild(predmetList);
        }
      });
    }

    moduleCard.appendChild(list);
    modulesEl.appendChild(moduleCard);
  });
}

function fillForm(program) {
  sifraInput.value = program.sifra || "";
  nazivInput.value = program.naziv || "";
  stepenInput.value = program.stepen || "OSNOVNE";
  ukupnoEspbInput.value = program.ukupnoEspb || 0;
}

async function loadProgram() {
  clearError();
  clearStatus();
  const sifra = qs("sifra");
  if (!sifra) {
    showError("Missing sifra in query string.");
    return;
  }
  try {
    currentProgram = await apiFetch(`/api/studijski-programi/${encodeURIComponent(sifra)}`);
    fillForm(currentProgram);
    renderModules(currentProgram.moduli || []);
  } catch (err) {
    showError(formatError(err));
  }
}

updateForm.addEventListener("submit", async event => {
  event.preventDefault();
  if (!currentProgram) {
    return;
  }
  clearError();
  showStatus("Saving update...");

  const payload = {
    ...currentProgram,
    naziv: nazivInput.value.trim(),
    stepen: stepenInput.value,
    ukupnoEspb: Number(ukupnoEspbInput.value)
  };

  try {
    const updated = await apiFetch(`/api/studijski-programi/${encodeURIComponent(payload.sifra)}`, {
      method: "PUT",
      body: JSON.stringify(payload)
    });
    currentProgram = updated;
    fillForm(updated);
    renderModules(updated.moduli || []);
    showStatus("Updated successfully.");
  } catch (err) {
    clearStatus();
    showError(formatError(err));
  }
});

deleteButton.addEventListener("click", async () => {
  if (!currentProgram) {
    return;
  }
  if (!confirm(`Delete program ${currentProgram.sifra}?`)) {
    return;
  }
  try {
    await apiFetch(`/api/studijski-programi/${encodeURIComponent(currentProgram.sifra)}`, {
      method: "DELETE"
    });
    window.location.href = "index.html";
  } catch (err) {
    showError(formatError(err));
  }
});

loadProgram();
