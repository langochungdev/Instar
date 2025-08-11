import { initializeApp } from 'firebase/app'
import { getMessaging, getToken } from 'firebase/messaging'

const firebaseConfig = {
    apiKey: "AIzaSyD9YaZMkdg9JHXEDARCz87WfyDir9aM8Vo",
    authDomain: "instar-f372a.firebaseapp.com",
    projectId: "instar-f372a",
    storageBucket: "instar-f372a.firebasestorage.app",
    messagingSenderId: "755526845850",
    appId: "1:755526845850:web:fa3829825fdd9b0aefa623"
}

export const vapidKey = 'BDmMBLe9n5rYilcK33Mb42Mw26A1dJqnLwRC5mU_7HZpvIRQuObH6fEQ-_rf_LLQQ0g8PHfCKOfrjkV3Y6OLLCc' // thay bằng key Web Push

const app = initializeApp(firebaseConfig)

export const messaging = getMessaging(app)

export async function getDeviceToken(registration) {
    try {
        const permission = await Notification.requestPermission()
        if (permission !== 'granted') return null
        const token = await getToken(messaging, { vapidKey, serviceWorkerRegistration: registration })
        return token
    } catch (err) {
        console.error('Lỗi lấy deviceToken', err)
        return null
    }
}
