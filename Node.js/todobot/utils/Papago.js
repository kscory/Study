// papago api 사용해보기
const request = require('request-promise');
// langcode를 가져오기 위해 사용
const SUPPORTED_LANG_CODE = {
    ['한국어']: 'ko',
    ['영어']: 'en',
    ['프랑스어']: 'fr',
    ['스페인어']: 'es'
};

const TRANSLATE_API_URL = require('../config').papago.api_url;


class Papago {
    // 생성자
    constructor ({clientId, clientSecret}) {
        this._clientId = clientId;
        this._clientSecret = clientSecret;
        this._sourceLangCode = SUPPORTED_LANG_CODE['한국어'];
        this._targetLangCode = SUPPORTED_LANG_CODE['영어'];
    }
    // getter & setter
    get clientId () {
        return this._clientId;
    }
    set clientId (clientId) {
        throw new Error('아이디를 바꿀 수 없습니다. 인스턴스 초기화 때만 가능')
    }

    get clientSecret () {
        return this._clientSecret;
    }
    set clientSecret (clientSecret) {
        throw new Error('Secret을 바꿀 수 없습니다. 인스턴스 초기화 때만 가능')
    }

    get sourceLangCode () {
        return this._sourceLangCode;
    }
    set sourceLangCode (sourceLangCode) {
        this._sourceLangCode = sourceLangCode;
    }

    get targetLangCode () {
        return this._targetLangCode;
    }
    set targetLangCode (targetLangCode) {
        this._targetLangCode = targetLangCode;
    }

    // 함수 선언
    static getSupportedLangCode (key) {
        return SUPPORTED_LANG_CODE[key];
    }

    getHeaders () {
        return {
            'X-Naver-Client-Id': this._clientId,
            'X-Naver-Client-Secret': this._clientSecret
        }
    }

    translate (text) {
        const options = {
            method: "POST",
            uri: TRANSLATE_API_URL,
            headers: this.getHeaders(),
            body: {
                source: this._sourceLangCode,
                target: this._targetLangCode,
                text: text
            },
            json: true
        }

        return request(options)
            .then(data => data.message.result.translatedText)
            .catch(err => {
                console.log(err);
                throw err;
            })
    }
}

module.exports = Papago;