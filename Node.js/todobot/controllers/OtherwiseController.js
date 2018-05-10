const TelegramBot = require('telegram-node-bot');

class OtherwiseController extends TelegramBot.TelegramBaseController {
    handle($) {
        $.sendMessage('제가 할수 없는 일입니다. 잘 모르겠습니다.')
    }
}

module.exports = OtherwiseController