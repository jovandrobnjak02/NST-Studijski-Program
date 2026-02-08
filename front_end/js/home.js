const listEl = document.getElementById("list");
const statusEl = document.getElementById("status");
const errorEl = document.getElementById("error");
const refreshButton = document.getElementById("refreshButton");
const apiConfig = document.getElementById("apiConfig");

renderApiBaseControls(apiConfig);

function showStatus(message) {
  statusEl.textContent = message;
  statusEl.style.display = "block";
}

function clearStatus() {
  statusEl.style.display = "none";
}

function showError(message) {
  errorEl.textContent = message;
  errorEl.style.display = "block";
}

function clearError() {
  errorEl.style.display = "none";
}

function renderList(programs) {
  listEl.innerHTML = "";
  if (!programs.length) {
    showStatus("No programs found.");
    return;
  }
  clearStatus();
  programs.forEach(program => {
    const row = document.createElement("div");
    row.className = "program-row";

    const info = document.createElement("div");
    info.innerHTML = `
      <div><strong>${program.naziv}</strong></div>
      <div>
        <span class="tag">${program.sifra}</span>
        <span class="tag">${program.stepen || ""}</span>
        <span class="tag">${program.ukupnoEspb} ESPB</span>
      </div>
    `;

    const actions = document.createElement("div");
    actions.className = "inline";
    actions.innerHTML = `
      <a href="details.html?sifra=${encodeURIComponent(program.sifra)}" class="secondary">Details</a>
      <button class="danger small" data-sifra="${program.sifra}">Delete</button>
    `;

    row.appendChild(info);
    row.appendChild(actions);
    listEl.appendChild(row);
  });

  listEl.querySelectorAll("button[data-sifra]").forEach(button => {
    button.addEventListener("click", async () => {
      const sifra = button.getAttribute("data-sifra");
      if (!confirm(`Delete program ${sifra}?`)) {
        return;
      }
      try {
        await apiFetch(`/api/studijski-programi/${encodeURIComponent(sifra)}`, {
          method: "DELETE"
        });
        await loadPrograms();
      } catch (err) {
        showError(formatError(err));
      }
    });
  });
}

async function loadPrograms() {
  clearError();
  showStatus("Loading programs...");
  try {
    const programs = await apiFetch("/api/studijski-programi");
    renderList(programs || []);
  } catch (err) {
    clearStatus();
    showError(formatError(err));
  }
}

refreshButton.addEventListener("click", loadPrograms);
loadPrograms();
