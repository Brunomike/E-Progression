const express = require('express')
const router=express.Router()
const userController=require('../controllers/userController')


router.get('/',userController.root)
router.get('/view/:uuid',userController.view_user)
router.get('/edit/:uuid',userController.edit_user)
router.post('/edit/:uuid',userController.edit_user)
router.post('/upload-profile-image/:uuid',userController.upload_photo)
router.get('/delete/:uuid',userController.delete_user)
router.get('/account-status/:uuid/:status',userController.set_status)
router.post('/change-password/:uuid',userController.change_password)
router.post('/assign-role/:uuid',userController.set_role)

module.exports =router;
