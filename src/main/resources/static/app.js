const API = '/api';

const roomsList = document.getElementById('rooms-list');
const roomSelect = document.getElementById('room-select');
const bookingsBody = document.getElementById('bookings-body');
const form = document.getElementById('booking-form');
const message = document.getElementById('form-message');

function formatDateTime(value) {
    if (!value) return '';
    return value.replace('T', ' ').slice(0, 16);
}

function showMessage(text, type) {
    message.textContent = text;
    message.className = 'message ' + (type || '');
}

async function loadRooms() {
    const res = await fetch(`${API}/rooms`);
    const rooms = await res.json();
    roomsList.innerHTML = rooms
        .map(r => `<li><strong>${r.name}</strong> — ${r.location}, до ${r.capacity} чел.</li>`)
        .join('');
    roomSelect.innerHTML = rooms
        .map(r => `<option value="${r.id}">${r.name} (до ${r.capacity})</option>`)
        .join('');
}

async function loadBookings() {
    const res = await fetch(`${API}/bookings`);
    const bookings = await res.json();
    bookingsBody.innerHTML = bookings.map(b => `
        <tr>
            <td>${b.id}</td>
            <td>${b.roomName ?? b.roomId}</td>
            <td>${b.organizer}</td>
            <td>${b.title}</td>
            <td>${formatDateTime(b.startTime)}</td>
            <td>${formatDateTime(b.endTime)}</td>
            <td>${b.attendees}</td>
            <td>${b.status}</td>
            <td><button class="link" onclick="deleteBooking(${b.id})">Удалить</button></td>
        </tr>
    `).join('');
}

async function deleteBooking(id) {
    const res = await fetch(`${API}/bookings/${id}`, {method: 'DELETE'});
    if (res.ok) {
        await loadBookings();
    } else {
        alert('Не удалось удалить бронирование');
    }
}

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    showMessage('', '');
    const payload = {
        roomId: Number(roomSelect.value),
        organizer: document.getElementById('organizer').value,
        title: document.getElementById('title').value,
        startTime: document.getElementById('start-time').value,
        endTime: document.getElementById('end-time').value,
        attendees: Number(document.getElementById('attendees').value)
    };
    const res = await fetch(`${API}/bookings`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(payload)
    });
    const data = await res.json().catch(() => ({}));
    if (res.ok) {
        showMessage(`Бронирование #${data.id} создано`, 'ok');
        await loadBookings();
    } else {
        showMessage(`Ошибка ${res.status}: ${data.message || 'неизвестно'}`, 'error');
    }
});

(function prefillTomorrow() {
    const d = new Date();
    d.setDate(d.getDate() + 1);
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, '0');
    const dd = String(d.getDate()).padStart(2, '0');
    document.getElementById('start-time').value = `${yyyy}-${mm}-${dd}T11:00`;
    document.getElementById('end-time').value = `${yyyy}-${mm}-${dd}T13:00`;
})();

loadRooms();
loadBookings();