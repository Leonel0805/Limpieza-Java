import { cargarHeader } from './utils/generarHeader.js';
import { isLogin } from './utils/isLogin.js';
import { sendFormSearchParam } from './utils/sendFormSearchParam.js';


async function init() {
    await cargarHeader();
    isLogin();
    sendFormSearchParam();
}

document.addEventListener('DOMContentLoaded', init);