const router = require('express').Router();
const Papago = require('../utils/Papago');
const KakaoService = require('../service/KakaoService');

router.get('/keyboard', (req, res) => {
    res.send({
        type: 'buttons',
        buttons: ['번역언어 변경']
    })
});

router.post('/message', (req, res) => {
    // content에 말이 들어가고 type에 이미지인지, 텍스트인지 등을 알려준다.
    const {content, type} = req.body;

    if(content === '번역언어 변경') {
        return res.send(KakaoService.getChangeLanguage())
    }

    if(content.includes('->')) {
        return res.send(KakaoService.changeLanguage(content));
    }

    KakaoService.translateText(content)
        .then(data => {
            res.send(data)
        })

});



module.exports = router;