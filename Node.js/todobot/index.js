const TelegramBot = require('telegram-node-bot');
const {BotFactory, BotType} = require('./factories/BotFactory');
const tg = BotFactory.getBot(BotType.TELEGRAM);
// const tg = new TelegramBot.Telegram('523446603:AAGxSoKwOMMFo8obJEgFsUNSOy0p2rLPGUo');

const TextCommand = TelegramBot.TextCommand;

const TodoCtrl = require('./controllers/TodoController');
const OtherwiseController = require('./controllers/OtherwiseController');
const todoCtrl = new TodoCtrl();

tg.router // router를 불러올 수 있다.
    .when(new TextCommand('/list','getCommand'), todoCtrl) // when 은 라우팅 처리 함수 (어떤 커맨드를 보고 어떤식으로 할것인가 : textcommand)
    .when(new TextCommand('/add', 'addCommand'), todoCtrl)
    .when(new TextCommand('/done', 'doneCommand'), todoCtrl)
    .otherwise(new OtherwiseController) // 만든 명령어가 아닌경우 처리할 수 있다.

// 서버는  "localhost:7777" 에서 확인 가능