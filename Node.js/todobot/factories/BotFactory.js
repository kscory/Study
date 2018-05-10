// 봇들을 생성하는 bot (나중에 카카오톡/네이버 등 연계 가능)

const TelegramBot = require('telegram-node-bot');

class BotFactory {
    static getBot(type) {
        switch (type) {
            case BotType.TELEGRAM:
                return getTelegramBot();
            default:
                throw new Error('not supported bot');
        }
    }
}

// 싱글톤처럼 쓰기 위함
let tg;

const getTelegramBot = () => {
    if(tg) return tg;
    tg = new TelegramBot.Telegram('523446603:AAGxSoKwOMMFo8obJEgFsUNSOy0p2rLPGUo');
    return tg;
};

// enum을 정의
const BotType = {
  TELEGRAM: 'telegram',
  KAKAO: 'kakao'
};

module.exports = {BotFactory, BotType};