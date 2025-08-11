import FingerprintJS from '@fingerprintjs/fingerprintjs'
import { UAParser } from 'ua-parser-js'
import { getDeviceToken } from '@/utils/firebase'

export async function getDeviceIdentity() {
    const parser = new UAParser()
    const deviceName = parser.getDevice().model || parser.getBrowser().name

    const deviceToken = await getDeviceToken()

    const fp = await FingerprintJS.load()
    const result = await fp.get()
    const fingerprint = result.visitorId

    return { deviceName, deviceToken, fingerprint }
}
