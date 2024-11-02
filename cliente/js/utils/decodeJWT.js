export function decodeJwt(token) {

    const parts = token.split('.');
    if (parts.length !== 3) {
        throw new Error('El token JWT debe tener 3 partes');
    }

    const payload = parts[1];
    const decodedPayload = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));

    // Parsear el JSON decodificado
    return JSON.parse(decodedPayload);
}
