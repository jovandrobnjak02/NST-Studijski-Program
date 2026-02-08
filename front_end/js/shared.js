const STORAGE_KEY = "studijskiProgramApiBase";

function getApiBase() {
  const stored = localStorage.getItem(STORAGE_KEY);
  if (stored) {
    return stored;
  }
  return "";
}

function setApiBase(value) {
  if (value) {
    localStorage.setItem(STORAGE_KEY, value);
  } else {
    localStorage.removeItem(STORAGE_KEY);
  }
}

async function apiFetch(path, options = {}) {
  const base = getApiBase();
  const response = await fetch(`${base}${path}`, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {})
    },
    ...options
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || `Request failed: ${response.status}`);
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

function renderApiBaseControls(container) {
  const wrapper = document.createElement("div");
  wrapper.className = "inline";
  wrapper.innerHTML = `
    <label class="label">API Base (optional)</label>
    <input type="text" id="apiBaseInput" placeholder="http://localhost:8080" />
    <button class="secondary small" id="apiBaseSave">Save</button>
  `;
  container.appendChild(wrapper);

  const input = wrapper.querySelector("#apiBaseInput");
  const button = wrapper.querySelector("#apiBaseSave");
  input.value = getApiBase();

  button.addEventListener("click", () => {
    setApiBase(input.value.trim());
  });
}

function qs(name) {
  return new URLSearchParams(window.location.search).get(name);
}

function formatError(err) {
  if (err instanceof Error) {
    return err.message;
  }
  return String(err);
}
