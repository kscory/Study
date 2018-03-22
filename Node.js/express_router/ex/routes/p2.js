var express = require('express');
var router = express.Router();

router.get('/r1', function(req, res){
    res.send('Hello /p2/r1');
});
router.get('/r2', function(req, res){
    res.send('Hello /p2/r2');
});

module.exports = router;