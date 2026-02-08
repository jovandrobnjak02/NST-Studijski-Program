async function apiFetch(path, options = {}) {
  const response = await fetch(path, {
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

function qs(name) {
  return new URLSearchParams(window.location.search).get(name);
}

function formatError(err) {
  if (err instanceof Error) {
    return err.message;
  }
  return String(err);
}
