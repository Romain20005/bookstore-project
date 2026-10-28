const API = "http://localhost:8080/api";
const booksContainer = document.querySelector("#books");
const statusElement = document.querySelector("#status");
const dialog = document.querySelector("#book-dialog");
let selectedBook = null;

async function request(path, options = {}) {
  const response = await fetch(`${API}${path}`, {
    headers: { "Content-Type": "application/json", ...(options.headers || {}) },
    ...options
  });
  const data = response.status === 204 ? null : await response.json().catch(() => null);
  if (!response.ok) throw new Error(data?.error || `Request mislukt (${response.status})`);
  return data;
}

async function loadBooks() {
  statusElement.textContent = "Catalogus laden…";
  booksContainer.innerHTML = "";
  try {
    const books = await request("/books");
    if (!books.length) {
      statusElement.textContent = "Nog geen boeken. Voeg er een toe via POST /api/books (zie README).";
      return;
    }
    statusElement.textContent = `${books.length} boek(en) gevonden.`;
    books.forEach(book => {
      const card = document.createElement("article");
      card.className = "book-card";
      const mark = document.createElement("div");
      mark.className = "book-mark";
      mark.textContent = "P.";
      const title = document.createElement("h3");
      title.textContent = book.title;
      const author = document.createElement("p");
      author.textContent = book.authors;
      const meta = document.createElement("p");
      meta.className = "meta";
      meta.textContent = `€ ${Number(book.price).toFixed(2)} · Stock: ${book.stock}`;
      const button = document.createElement("button");
      button.className = "button";
      button.textContent = "Details & reserveren";
      button.addEventListener("click", () => showBook(book));
      card.append(mark, title, author, meta, button);
      booksContainer.append(card);
    });
  } catch (error) {
    statusElement.textContent = `Kan catalogus niet laden: ${error.message}. Controleer of de backend draait.`;
  }
}

async function showBook(book) {
  selectedBook = book;
  const detail = document.querySelector("#book-detail");
  detail.replaceChildren();
  const title = document.createElement("h2");
  title.textContent = book.title;
  const author = document.createElement("p");
  author.textContent = `Auteur(s): ${book.authors}`;
  const description = document.createElement("p");
  description.textContent = book.description || "Geen beschrijving beschikbaar.";
  const isbn = document.createElement("p");
  isbn.textContent = `ISBN: ${book.isbn}`;
  const reviewsTitle = document.createElement("h3");
  reviewsTitle.textContent = "Reviews";
  const reviews = document.createElement("div");
  try {
    const data = await request(`/books/${book.id}/reviews`);
    if (!data.length) reviews.textContent = "Nog geen reviews.";
    data.forEach(review => {
      const item = document.createElement("p");
      item.textContent = `${"★".repeat(review.score)} — ${review.text}`;
      reviews.append(item);
    });
  } catch {
    reviews.textContent = "Reviews konden niet geladen worden.";
  }
  detail.append(title, author, description, isbn, reviewsTitle, reviews);
  document.querySelector("#reserve-status").textContent = "";
  dialog.showModal();
}

document.querySelector("#close-dialog").addEventListener("click", () => dialog.close());
document.querySelector("#refresh").addEventListener("click", loadBooks);

document.querySelector("#register-form").addEventListener("submit", async event => {
  event.preventDefault();
  const form = new FormData(event.currentTarget);
  const status = document.querySelector("#account-status");
  try {
    const user = await request("/users/register", {
      method: "POST",
      body: JSON.stringify({
        name: form.get("name"),
        email: form.get("email"),
        password: form.get("password")
      })
    });
    status.textContent = `Account aangemaakt. Bewaar je gebruikers-ID: ${user.id}.`;
    document.querySelector("#reserve-user").value = user.id;
    event.currentTarget.reset();
  } catch (error) {
    status.textContent = error.message;
  }
});

document.querySelector("#reserve-button").addEventListener("click", async () => {
  const status = document.querySelector("#reserve-status");
  const userId = Number(document.querySelector("#reserve-user").value);
  const quantity = Number(document.querySelector("#reserve-quantity").value);
  if (!selectedBook || !Number.isInteger(userId) || userId < 1 || !Number.isInteger(quantity) || quantity < 1) {
    status.textContent = "Vul een geldig gebruikers-ID en aantal in.";
    return;
  }
  try {
    const reservation = await request("/reservations", {
      method: "POST",
      body: JSON.stringify({ userId, bookId: selectedBook.id, quantity })
    });
    status.textContent = `Reservatie #${reservation.id} aangemaakt. Je boek staat klaar voor afhaling en betaling.`;
    await loadBooks();
  } catch (error) {
    status.textContent = error.message;
  }
});

loadBooks();
