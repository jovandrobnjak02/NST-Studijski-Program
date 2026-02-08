const apiConfig = document.getElementById("apiConfig");
const subjectList = document.getElementById("subjectList");
const filterInput = document.getElementById("filter");
const moduleForm = document.getElementById("moduleForm");
const modulesEl = document.getElementById("modules");
const saveButton = document.getElementById("saveButton");
const resetButton = document.getElementById("resetButton");
const saveStatus = document.getElementById("saveStatus");
const saveError = document.getElementById("saveError");
const programForm = document.getElementById("programForm");

renderApiBaseControls(apiConfig);

let subjects = [];
let modules = [];
let nextModuleId = 1;
let nextPlanId = 1;
let nextGroupId = 1;

function showStatus(message) {
  saveStatus.textContent = message;
  saveStatus.style.display = "block";
}

function clearStatus() {
  saveStatus.style.display = "none";
}

function showError(message) {
  saveError.textContent = message;
  saveError.style.display = "block";
}

function clearError() {
  saveError.style.display = "none";
}

function getSubjectBySifra(sifra) {
  return subjects.find(item => item.sifra === sifra);
}

function renderSubjects() {
  const filterValue = filterInput.value.trim().toLowerCase();
  subjectList.innerHTML = "";
  const visible = subjects.filter(item => {
    if (!filterValue) {
      return true;
    }
    return item.sifra.toLowerCase().includes(filterValue) || item.naziv.toLowerCase().includes(filterValue);
  });

  if (!visible.length) {
    subjectList.innerHTML = "<div class=\"notice\">No subjects match.</div>";
    return;
  }

  visible.forEach(item => {
    const el = document.createElement("div");
    el.className = "subject";
    el.draggable = true;
    el.textContent = `${item.sifra} - ${item.naziv} (${item.espb} ESPB)`;
    el.addEventListener("dragstart", event => {
      el.classList.add("dragging");
      event.dataTransfer.setData("text/plain", item.sifra);
    });
    el.addEventListener("dragend", () => {
      el.classList.remove("dragging");
    });
    subjectList.appendChild(el);
  });
}

function createDropzone({ onDrop, label }) {
  const wrapper = document.createElement("div");
  wrapper.className = "dropzone";
  wrapper.innerHTML = `<div class=\"inline\"><strong>${label}</strong></div>`;

  wrapper.addEventListener("dragover", event => {
    event.preventDefault();
    wrapper.classList.add("active");
  });
  wrapper.addEventListener("dragleave", () => {
    wrapper.classList.remove("active");
  });
  wrapper.addEventListener("drop", event => {
    event.preventDefault();
    wrapper.classList.remove("active");
    const sifra = event.dataTransfer.getData("text/plain");
    if (sifra) {
      onDrop(sifra);
    }
  });

  return wrapper;
}

function renderModules() {
  modulesEl.innerHTML = "";
  modules.forEach(module => {
    const card = document.createElement("div");
    card.className = "module";

    const header = document.createElement("header");
    header.innerHTML = `
      <h3>${module.naziv}</h3>
      <div class="inline">
        <span class="tag">${module.oznaka}</span>
        <span class="tag">Godina ${module.godina}</span>
        <button class="danger small" data-remove-module="${module.id}">Remove module</button>
      </div>
    `;
    card.appendChild(header);

    const obavezniSection = document.createElement("div");
    obavezniSection.innerHTML = `
      <div class="inline">
        <strong>Obavezni predmeti</strong>
        <label class="label">Semestar</label>
        <select data-default-semestar="${module.id}">
          ${[1,2,3,4,5,6,7,8].map(sem => `<option value="${sem}">${sem}</option>`).join("")}
        </select>
      </div>
    `;

    const obavezniDrop = createDropzone({
      label: "Prevuci predmet ovde",
      onDrop: sifra => addObavezni(module.id, sifra)
    });

    const obavezniList = document.createElement("div");
    obavezniList.className = "list";
    const obavezniStavke = module.planStavke.filter(item => item.tip === "OBAVEZAN");
    if (!obavezniStavke.length) {
      obavezniList.innerHTML = "<div class=\"notice\">Nema obaveznih predmeta.</div>";
    } else {
      obavezniStavke.forEach(item => {
        const subject = getSubjectBySifra(item.predmetSifra);
        const row = document.createElement("div");
        row.className = "program-row";
        row.innerHTML = `
          <div>
            <div><strong>${subject ? subject.naziv : item.predmetSifra}</strong></div>
            <div>${item.predmetSifra} - ${subject ? subject.espb : ""} ESPB</div>
          </div>
          <div class="inline">
            <label class="label">Semestar</label>
            <input type="number" min="1" max="8" value="${item.semestar}" data-plan-semestar="${item.id}" />
            <button class="danger small" data-remove-plan="${item.id}">Remove</button>
          </div>
        `;
        obavezniList.appendChild(row);
      });
    }

    obavezniSection.appendChild(obavezniDrop);
    obavezniSection.appendChild(obavezniList);
    card.appendChild(obavezniSection);

    const groupSection = document.createElement("div");
    groupSection.innerHTML = `
      <div class="inline" style="margin-top: 12px;">
        <strong>Izborne grupe</strong>
      </div>
    `;

    const groupForm = document.createElement("form");
    groupForm.className = "grid three";
    groupForm.innerHTML = `
      <div>
        <label class="label">Semestar</label>
        <input type="number" min="1" max="8" value="1" name="semestar" required />
      </div>
      <div>
        <label class="label">Potrebni ESPB</label>
        <input type="number" min="1" name="espb" required />
      </div>
      <div>
        <label class="label">Broj predmeta</label>
        <input type="number" min="1" name="broj" required />
      </div>
      <button type="submit">Add izborna grupa</button>
    `;

    groupForm.addEventListener("submit", event => {
      event.preventDefault();
      const data = new FormData(groupForm);
      const semestar = Number(data.get("semestar"));
      const espb = Number(data.get("espb"));
      const broj = Number(data.get("broj"));
      if (!semestar || !espb || !broj) {
        showError("Semestar, ESPB i broj predmeta su obavezni.");
        return;
      }
      addGroup(module.id, semestar, espb, broj);
      groupForm.reset();
    });

    groupSection.appendChild(groupForm);

    const groups = module.planStavke.filter(item => item.tip === "IZBORNI");
    if (!groups.length) {
      const empty = document.createElement("div");
      empty.className = "notice";
      empty.textContent = "Nema izbornih grupa.";
      groupSection.appendChild(empty);
    } else {
      groups.forEach(item => {
        const groupCard = document.createElement("div");
        groupCard.className = "group";
        groupCard.innerHTML = `
          <div class="inline">
            <strong>Grupa</strong>
            <span class="tag">Semestar ${item.semestar}</span>
            <span class="tag">ESPB ${item.izbornaGrupa.potrebniEspb}</span>
            <span class="tag">Broj predmeta ${item.izbornaGrupa.brojPredmeta}</span>
            <button class="danger small" data-remove-plan="${item.id}">Remove</button>
          </div>
        `;

        const drop = createDropzone({
          label: "Prevuci predmet u grupu",
          onDrop: sifra => addSubjectToGroup(module.id, item.id, sifra)
        });

        const list = document.createElement("div");
        list.className = "list";
        if (!item.izbornaGrupa.predmetSifre.length) {
          list.innerHTML = "<div class=\"notice\">Nema predmeta.</div>";
        } else {
          item.izbornaGrupa.predmetSifre.forEach(sifra => {
            const subject = getSubjectBySifra(sifra);
            const row = document.createElement("div");
            row.className = "program-row";
            row.innerHTML = `
              <div>
                <div><strong>${subject ? subject.naziv : sifra}</strong></div>
                <div>${sifra} - ${subject ? subject.espb : ""} ESPB</div>
              </div>
              <button class="danger small" data-remove-group-item="${item.id}" data-sifra="${sifra}">Remove</button>
            `;
            list.appendChild(row);
          });
        }

        groupCard.appendChild(drop);
        groupCard.appendChild(list);
        groupSection.appendChild(groupCard);
      });
    }

    card.appendChild(groupSection);
    modulesEl.appendChild(card);
  });

  modulesEl.querySelectorAll("[data-remove-module]").forEach(button => {
    button.addEventListener("click", () => {
      const id = Number(button.getAttribute("data-remove-module"));
      modules = modules.filter(mod => mod.id !== id);
      renderModules();
    });
  });

  modulesEl.querySelectorAll("[data-remove-plan]").forEach(button => {
    button.addEventListener("click", () => {
      const planId = Number(button.getAttribute("data-remove-plan"));
      modules.forEach(mod => {
        mod.planStavke = mod.planStavke.filter(item => item.id !== planId);
      });
      renderModules();
    });
  });

  modulesEl.querySelectorAll("[data-plan-semestar]").forEach(input => {
    input.addEventListener("change", () => {
      const planId = Number(input.getAttribute("data-plan-semestar"));
      const value = Number(input.value);
      modules.forEach(mod => {
        const plan = mod.planStavke.find(item => item.id === planId);
        if (plan) {
          plan.semestar = value;
        }
      });
    });
  });

  modulesEl.querySelectorAll("[data-remove-group-item]").forEach(button => {
    button.addEventListener("click", () => {
      const planId = Number(button.getAttribute("data-remove-group-item"));
      const sifra = button.getAttribute("data-sifra");
      modules.forEach(mod => {
        const plan = mod.planStavke.find(item => item.id === planId);
        if (plan && plan.izbornaGrupa) {
          plan.izbornaGrupa.predmetSifre = plan.izbornaGrupa.predmetSifre.filter(item => item !== sifra);
        }
      });
      renderModules();
    });
  });
}

function addObavezni(moduleId, sifra) {
  clearError();
  const module = modules.find(mod => mod.id === moduleId);
  if (!module) {
    return;
  }
  const subject = getSubjectBySifra(sifra);
  if (!subject) {
    showError("Predmet nije pronadjen.");
    return;
  }
  const already = module.planStavke.some(item => item.predmetSifra === sifra);
  if (already) {
    showError("Predmet je vec dodat.");
    return;
  }

  const semestarSelect = modulesEl.querySelector(`[data-default-semestar="${moduleId}"]`);
  const semestar = semestarSelect ? Number(semestarSelect.value) : 1;

  module.planStavke.push({
    id: nextPlanId++,
    tip: "OBAVEZAN",
    semestar,
    aktivan: true,
    predmetSifra: sifra,
    izbornaGrupa: null
  });
  renderModules();
}

function addGroup(moduleId, semestar, espb, broj) {
  clearError();
  const module = modules.find(mod => mod.id === moduleId);
  if (!module) {
    return;
  }
  module.planStavke.push({
    id: nextPlanId++,
    tip: "IZBORNI",
    semestar,
    aktivan: true,
    predmetSifra: null,
    izbornaGrupa: {
      id: nextGroupId++,
      potrebniEspb: espb,
      brojPredmeta: broj,
      predmetSifre: []
    }
  });
  renderModules();
}

function addSubjectToGroup(moduleId, planId, sifra) {
  clearError();
  const module = modules.find(mod => mod.id === moduleId);
  if (!module) {
    return;
  }
  const plan = module.planStavke.find(item => item.id === planId);
  if (!plan || !plan.izbornaGrupa) {
    return;
  }
  const subject = getSubjectBySifra(sifra);
  if (!subject) {
    showError("Predmet nije pronadjen.");
    return;
  }
  if (subject.espb !== plan.izbornaGrupa.potrebniEspb) {
    showError("ESPB predmeta se ne poklapa sa zahtjevom izborne grupe.");
    return;
  }
  if (plan.izbornaGrupa.predmetSifre.includes(sifra)) {
    showError("Predmet je vec u grupi.");
    return;
  }
  if (plan.izbornaGrupa.predmetSifre.length >= plan.izbornaGrupa.brojPredmeta) {
    showError("Izborna grupa je popunjena.");
    return;
  }
  plan.izbornaGrupa.predmetSifre.push(sifra);
  renderModules();
}

function buildPayload() {
  const formData = new FormData(programForm);
  const sifra = formData.get("sifra").trim();
  const naziv = formData.get("naziv").trim();
  const stepen = formData.get("stepen");
  const ukupnoEspb = Number(formData.get("ukupnoEspb"));

  return {
    sifra,
    naziv,
    stepen,
    ukupnoEspb,
    moduli: modules.map(mod => ({
      id: null,
      naziv: mod.naziv,
      oznaka: mod.oznaka,
      godina: mod.godina,
      planStavke: mod.planStavke.map(plan => ({
        id: null,
        semestar: plan.semestar,
        tip: plan.tip,
        aktivan: true,
        predmetSifra: plan.tip === "OBAVEZAN" ? plan.predmetSifra : null,
        izbornaGrupa: plan.tip === "IZBORNI" ? {
          id: null,
          potrebniEspb: plan.izbornaGrupa.potrebniEspb,
          brojPredmeta: plan.izbornaGrupa.brojPredmeta,
          predmetSifre: plan.izbornaGrupa.predmetSifre
        } : null
      }))
    }))
  };
}

moduleForm.addEventListener("submit", event => {
  event.preventDefault();
  clearError();
  const data = new FormData(moduleForm);
  const naziv = data.get("moduleNaziv").trim();
  const oznaka = data.get("moduleOznaka").trim();
  const godina = Number(data.get("moduleGodina"));
  if (!naziv || !oznaka || !godina) {
    showError("Naziv, oznaka i godina su obavezni.");
    return;
  }
  modules.push({
    id: nextModuleId++,
    naziv,
    oznaka,
    godina,
    planStavke: []
  });
  moduleForm.reset();
  renderModules();
});

filterInput.addEventListener("input", renderSubjects);

saveButton.addEventListener("click", async () => {
  clearError();
  clearStatus();
  const payload = buildPayload();
  if (!payload.sifra || !payload.naziv) {
    showError("Sifra i naziv su obavezni.");
    return;
  }
  if (!payload.moduli.length) {
    showError("Dodaj bar jedan modul.");
    return;
  }
  showStatus("Creating program...");
  try {
    const created = await apiFetch("/api/studijski-programi", {
      method: "POST",
      body: JSON.stringify(payload)
    });
    showStatus("Created successfully.");
    if (created && created.sifra) {
      window.location.href = `details.html?sifra=${encodeURIComponent(created.sifra)}`;
    }
  } catch (err) {
    clearStatus();
    showError(formatError(err));
  }
});

resetButton.addEventListener("click", () => {
  programForm.reset();
  moduleForm.reset();
  modules = [];
  renderModules();
});

async function loadSubjects() {
  clearError();
  try {
    const data = await apiFetch("/api/predmeti");
    subjects = Array.isArray(data) ? data : [];
    renderSubjects();
  } catch (err) {
    showError(formatError(err));
  }
}

loadSubjects();
renderModules();
