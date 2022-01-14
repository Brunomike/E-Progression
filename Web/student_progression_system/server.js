require("dotenv").config()
const createError = require('http-errors');
const express=require('express');
const fileUpload = require("express-fileupload")
const path=require('path');
const logger=require('morgan')
const cookieParser = require('cookie-parser');
const session=require('express-session')
const flash=require('connect-flash')
const cors=require('cors')
const fs=require("fs")
const csrf=require('csurf')
const port=process.env.PORT ||5000
const{sequelize}=require('./models')
const firebase=require('./middleware/firebase')
const xsrf=require('./middleware/xsrf-token')
// const pdf = require('./lib/pdf').pdf
// const doc=new pdf()

const app = express();

app.use(express.urlencoded({ extended:true }))

//ANDROID ROUTER
const apiRouter=require('./routes/api')

app.use(express.json())

//ANDROID ENDPOINTS
app.use('/api',apiRouter)


//MIDDLEWARE && VIEW ENGINE SETUP
app.use(logger('dev'))
app.use(cors({
  origin:process.env.ORIGIN,
  credentials:true
}))

app.set("view engine", "ejs")
app.use(fileUpload())

app.use(cookieParser())
app.use(session({secret: process.env.COOKIE_SECRET,cookeie: { maxage: 60000 },resave: false,saveUninitialized: false,}))
app.use(csrf({cookie:{httpOnly:true}}))
app.use(flash())
app.use(express.static(path.join(__dirname, 'public')))
app.use(firebase)
//app.use(csrfMiddleware)

//routes
const authRouter=require('./routes/auth')
const adminRouter=require('./routes/admin')
const userRouter=require('./routes/user')

//WEB-ENDPOINTS
app.use('/',xsrf,authRouter)
app.use('/auth',xsrf,authRouter)
app.use('/admin',xsrf,adminRouter)
app.use('/user',xsrf,userRouter)


//TEST ENDPOINTS

app.get('/test',(req, res) =>{
  res.render('admin/test',{csrfToken: req.csrfToken() })
})

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  //res.clearCookie("loginToken")
  next(createError(404));
});

// error handler
app.use(function(err, req, res) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500).render('error',{error:err})
});

app.use(function (err, req, res, next) {
  if (err.code !== 'EBADCSRFTOKEN') return next(err)
 
  // handle CSRF token errors here
  res.status(403)
  res.send('form tampered with')
})


app.listen(port,async(err)=>{
  err?console.log(err):console.log(`Server listening on http://localhost:5000`)
  try {
    await sequelize.sync()
    await sequelize.authenticate()
    console.log('Database Connected Successfully!');
  } catch (error) {
    console.log(error.message);
  }

})
module.exports=app

//TODO : Add functionality for test users