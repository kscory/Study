class CommandStr {
    static getCommandArgs (text) {
        return text.split(' ').splice(1);
    }
}

module.exports = CommandStr;