const express = require('express');
const router=express.Router();
const authController=require('../controllers/authController')


router.get('/',authController.root_route)
router.get('/login',authController.login)
router.post('/login',authController.login)
router.get('/logout',authController.logout)
router.get('/registration',authController.registration)
router.post('/registration',authController.registration)
router.get('/forgot-password',authController.forgot_password)
router.post('/forgot-password',authController.forgot_password)

module.exports =router
