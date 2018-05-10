const TelegramBot = require('telegram-node-bot');
const CommandStr = require('../utils/CommandStr');
const CloudStorage = require('../db/CloudStorage');

// controler의 class를 만든다
class TodoController extends TelegramBot.TelegramBaseController {

    // getCommand가 어떤일을 하는지 알려주는 handler
    getHandler($) { // scope를 받아오고
        CloudStorage.getTodos() // 이번에는 cloudStorage에서 가져옴
        // $.getUserSession('todos') // getUserSession : 메모리에 할당시킨 값을 가져옴
            .then(todos => { // 그 todos를 가지고 어떤 일을 할 것!!!

                $.sendMessage(this.parseTodos(todos), {parse_mode: 'Markdown'}) // telegram에 메세지롤 보내주는 API (markdown 언어로 변환 가능, parse 한것을 넘겨준다.)
            })
    }

    addHandler($) {
        // message.text 로 입력된 text 를 가져온다.
        const todo = CommandStr.getCommandArgs($.message.text);
        if (!todo) return $.sendMessage('todo를 입력해 주세요');

        const addTodo = {
            desc: todo[0] || '', // todo가 없으면 ''를 넣는다.
            due_date: todo[1] || new Date().toLocaleString(),
            is_done: false
        };

        CloudStorage.insertTodo(addTodo)
            .then(result => {
                $.sendMessage(`${todo[0]}가 할 일에 추가 되었습니다.`)
            })
            .catch(err => {
                $.sendMessage(`서버에러 입니다.`)
                console.error(err);
            })

        // todos를 가져옴
        // $.getUserSession('todos')
        //     .then(todos => {
        //         // 배열이 없다면 만든다.
        //         if(!Array.isArray(todos)) $.setUserSession('todos', [todo]);
        //         // 있다면 todo배열에 concat 시킨다.
        //         else $.setUserSession('todos', todos.concat([todo]));
        //
        //         // 추가되었다고 메시지를 날린다.
        //         $.sendMessage(`${todo}가 할 일에 추가 되었습니다.`)
        //     })
    }

    // done 0 하면 0번째가 없어지도록 설정
    doneHandler($) {
        // const index = parseInt($.message.text.split(' ').splice(1)[0]); // index를 가져와서
        let id = CommandStr.getCommandArgs($.message.text); // keyword를 보냄
        if (!id) return $.sendMessage('없는 번호입니다.')

        CloudStorage.updateTodo(id, {is_done: true})
            .then(_ => {
                $.sendMessage('완료 했습니다.`')
            })
            .catch(err => {
                $.sendMessage(`서버에러 입니다.`)
                console.error(err);
            })

        // 숫자가 아닐경우 예외처리
        // if (isNaN(index)) {
        //     return $.sendMessage('번호를 입력해주세요')
        // }
        //
        // $.getUserSession('todos')
        //     .then(todos => {
        //         const todo = todos[index];
        //         // 없는 번호를 입력한 경우
        //         if (!todo) return $.sendMessage('없는 번호입니다.')
        //         todos.splice(index, 1) // index에서 1개 자름
        //
        //         $.setUserSession('todos', todos);
        //         $.sendMessage(`${todo} 를 완료 했습니다.`)
        //     })
    }

    // 배열 형태로 들어간 todos 를 파싱
    parseTodos(todos) {
        if (!todos.length) return '할일이 없습니다.';
        // 참고 : 텔레그램에서는 마크다운 언어를 지원한다.
        let result = `* 할일 목록 * \n\n`;
        todos.sort((a, b) => {
            return a.due_date > b.due_date;
        });
        todos.forEach((todo, i) => {
            // 객체 형태를 바꿔야 함
            result += `* ${todo.id} * - ${todo.desc} : ${todo.due_date}\n`
        });
        return result;
    }

    get routes() {
        return {
            getCommand: 'getHandler',
            addCommand: 'addHandler',
            doneCommand: 'doneHandler'
        }
    }
}

module.exports = TodoController;