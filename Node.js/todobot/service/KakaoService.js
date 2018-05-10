const {Papago} = require('../utils');
const config = require('../config');

const papago = new Papago({
    clientId: config.papago.client.id,
    clientSecret: config.papago.client.secret
})

class KakaoService {

    static changeLanguage (msg) {
        const splitMsgs = msg.split('->')
        papago.sourceLangCode = Papago.getSupportedLangCode(splitMsgs[0])
        papago.targetLangCode = Papago.getSupportedLangCode(splitMsgs[1])

        return {
            message : {
                text: `${msg}로 언어가 변경되었습니다.`
            }
        }
    }

    static getChangeLanguage () {
        return {
            message: {
                text: '변역 가능한 언어 목록'
            },
            keyboard: {
                type: 'buttons',
                buttons: ['한국어->영어', '한국어->프랑스어']
            }
        }
    }

    // 메시지를 던지는 역할을 해준다.
    static translateText(text) {
        return papago.translate(text)
            .then(data => {
                return {
                    message: {
                        text: data
                    }
                }
            })
    }
}

module.exports = KakaoService;