const express=require('express');
const router=express.Router();
const apiController=require('../controllers/apiController')

router.post('/registration',apiController.registration)
router.get('/login',apiController.login)
router.post('/login',apiController.login)
router.get('/home',apiController.home)
router.get('/school/news',apiController.news)
router.get('/school/news/:id',apiController.newsItem)
router.get('/school/news/latest/one',apiController.latestNews)
router.get('/user/profile/:uuid',apiController.user_profile)
router.get('/student/results/:uuid',apiController.results)
router.get('/get/all-exam-type',apiController.exam_types)
router.get('/student/fees/:uuid',apiController.fees)
//router.get('/student/fees/:uuid/:id')
router.post('/reset-password',apiController.resetPassword)
router.get('/student/no/:uuid',apiController.students)

module.exports=router;