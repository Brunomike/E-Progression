const {User}=require("../models")
const bcrypt=require('bcrypt')
const saltRounds=10
const jwt=require('jsonwebtoken')
const jwtSecret=process.env.JWT_SECRET

const date = new Date();
const [month, day, year] = [date.getMonth(), date.getDate(), date.getFullYear()];
const today=`${year}-${month}-${day}`

exports.root_route=(req,res)=>{
    jwt.verify(req.cookies.loginToken,jwtSecret,(err,decoded) => {
        //console.log("Decoded :"+decoded)
        if(err) {
            res.status(200).redirect('/auth/login')
        }else{
            res.status(200).redirect('/admin/home')
            // res.redirect(url.format({
            //     pathname:"/admin/home",
            //     query: {
            //        "first_name": decoded.first_name,
            //        "last_name": decoded.last_name,
            //        "email_address":decoded.email_address
            //      }
            //   }));         
        }
    })
}

function validatePassword(p){    
    const errors = [];
    if (p.length < 8) {
        errors.push("❌ Password must be at least 8 characters\n");        
    }
    if (p.length > 32) {
        errors.push("❌ Password must be at max 32 characters\n");        
    }
    if (p.search(/[a-z]/) < 0) {
        errors.push("❌ Password must contain at least one lower case letter.\n");         
    }
    if (p.search(/[A-Z]/) < 0) {
        errors.push("❌ Password must contain at least one upper case letter.\n");         
    }

    if (p.search(/[0-9]/) < 0) {
        errors.push("❌ Password must contain at least one digit.\n");        
    }
   if (p.search(/[!@#\$%\^&\*_]/) < 0) {
        errors.push("❌ Password must contain at least one special char from -[ ! @ # $ % ^ & * _ ]\n");         
    }
    if (errors.length > 0) {
        //console.log(errors.join("\n"));
        //return false;
        //return errors.join('\n')        
        return [false,errors]
    }else{
        return [true];
    }                
}

exports.login=async (req,res)=>{
    if (req.method==="GET") {
        res.status(200).render('auth/login', {
            msgSuccessLogin: req.flash("msgSuccessLogin"),
            msgFailureLogin: req.flash("msgFailureLogin"),
            csrfToken: req.csrfToken() 
          })
    }else{        
        const {email_address,password} = req.body                  
        try {        
            const user=await User.findOne({where:{email_address: email_address}})
            if (user!=null) {            
                if(user.user_status==="disabled"){
                    req.flash("msgFailureLogin","❌ Account Disabled")
                    res.redirect('/auth/login')
                }else if(user.user_status==="active"){
                    let userPass=user.user_password
                    if (user.user_role==="admin" || user.user_role=="superadmin") {
                        bcrypt.compare(password,userPass,(err,passResult)=>{
                            if(err){
                                console.log(err.message)
                            }else{                            
                                if(passResult==true){ 
                                    const currentUser ={first_name:user.first_name,last_name:user.last_name,uuid:user.uuid,user_gender:user.user_gender,user_profile_image_link:user.user_profile_image_link,phone_number:user.phone_number,birthday:user.year_of_birth}
                                    //console.log(currentUser);
                                    const app=req.app
                                    app.set("currentUser", currentUser);                           
                                    let cookieExpiration=60*50*1000
                                    res.cookie("loginToken",jwt.sign({first_name:user.first_name,last_name:user.last_name,email_addres:user.email_address},jwtSecret),{httpOnly:true,maxAge:cookieExpiration})
                                    req.flash('msgSuccessDashboard',"✔ Login Successful")
                                    res.redirect('/admin/home')                               
                                }else{
                                    req.flash("msgFailureLogin", " ❌ Wrong Password");
                                    res.redirect("/auth/login");
                                }
                            }
                        })
                    }else{
                        req.flash("msgFailureLogin",`❌ You're not an admin.`)
                        res.redirect('/auth/login')
                    }               
                }           
            }else{
                req.flash("msgFailureLogin",`❌ User ${email_address} not Found!`)
                res.redirect('/auth/login')
            }                       
        } catch (error) {
            res.status(500).json(error.message)
        } 
    }
}

exports.logout=(req,res)=>{
    res.clearCookie("loginToken")
    req.flash("msgSuccessLogin","✔ You are Logged out!")
    res.redirect('/')   
}

exports.registration=async (req,res)=>{
    if (req.method==="GET") {
        res.render('auth/registration',{        
            msgSuccessReg: req.flash("msgSuccessReg"),
            msgFailureReg: req.flash("msgFailureReg"),
            msgError: req.flash("msgError"),
            user_Info:[],
            msgError:[],
            csrfToken: req.csrfToken() 
        })
    }else{
        //console.log(req.body);
        const {first_name,phone_number,last_name,year_of_birth,email_address,user_password,user_gender,user_confirm_password,sent_number}=req.body
        const user_Info=[first_name,phone_number,last_name,year_of_birth,email_address,user_password,user_gender,user_confirm_password]        
        try {
            const users= await User.findAll()
            //console.log(users);
            if(users.length>0){
                //App contains several users
                users.forEach(user => {
                    if(user.email_address===email_address){
                        req.flash("msgFailureReg",`❌ User ${email_address} already exists!`)
                        res.redirect('/auth/registration')
                    }                
                })    
                if(user_password===user_confirm_password){
                    let passCheck=validatePassword(user_password)
                    if (passCheck[0]===true) {
                        bcrypt.hash(user_password,saltRounds,async(err,hash) => {
                            if(err){
                                console.log(err.message);
                            }else{
                                //console.log(hash);
                              try {
                                const newUser=await User.create({
                                    email_address:email_address,
                                    first_name:first_name,
                                    last_name:last_name,
                                    phone_number:sent_number,
                                    user_gender:user_gender,
                                    user_role:"admin",
                                    user_status:"disabled",
                                    year_of_birth:year_of_birth,
                                    user_password:hash,
                                    user_confirm_password:hash
                                })
                                //return res.json(newUser)
                                req.flash("msgSuccessLogin","✔ Account created successfully!")
                                return res.redirect('/auth/login')                                    
                              } catch (error) {
                                res.status(500).json(error,message)
                              }                                    
                            }
                        })
                    }else{
                        res.render('auth/registration',{        
                             msgSuccessReg: req.flash("msgSuccessReg"),
                            msgFailureReg: req.flash("msgFailureReg"),
                            msgError: req.flash("msgError"),
                            user_Info:[user_Info],
                            msgError:passCheck[1],
                            csrfToken: req.csrfToken() 
                        })
                    }
                }else{
                    req.flash('msgFailureReg',"❌ Passwords do not match!")
                    res.redirect('/auth/registration')
                } 
                         
            }else{
                //No user in the database
                if(user_password===user_confirm_password){
                    let passCheck=validatePassword(user_password)
                    if (passCheck[0]===true) {
                        bcrypt.hash(user_password,saltRounds,async(err,hash) => {
                            if(err){
                                console.log(err.message);
                            }else{                            
                              try {
                                const newUser=await User.create({
                                    email_address:email_address,
                                    first_name:first_name,
                                    last_name:last_name,
                                    phone_number:sent_number,
                                    user_gender:user_gender,
                                    user_role:"admin",
                                    user_status:"disabled",
                                    year_of_birth:year_of_birth,
                                    user_password:hash,
                                    user_confirm_password:hash
                                })
                                return res.json(newUser)
                              } catch (error) {
                                  res.status(500).json(error)
                              }
                                
                            }
                        })
                    }else{
                        res.render('auth/registration',{        
                             msgSuccessReg: req.flash("msgSuccessReg"),
                            msgFailureReg: req.flash("msgFailureReg"),
                            msgError: req.flash("msgError"),
                            user_Info:[user_Info],
                            msgError:passCheck[1],
                            csrfToken: req.csrfToken() 
                        })
                    }
                }else{
                    req.flash('msgFailureReg',"❌ Passwords do not match!")
                    res.redirect('/auth/registration')
                }
            }
        } catch (error) {
            return res.status(500).json(error)
        }
    }
}

exports.forgot_password=async(req,res)=>{
    if (req.method==="GET") {
        res.render('auth/forgot-password',{
            msgSuccessReset:req.flash("msgSuccessReset"),
            msgFailureReset:req.flash("msgFailureReset"),
            msgError: req.flash("msgError"),
            csrfToken: req.csrfToken()
        })
    }else{
        const {email_address,user_password,user_confirm_password}=req.body
        let user
        try {
            const user=await User.findOne({where:{email_address: email_address}})
            if(user!==null){
                if (user_password===user_confirm_password) {
                    let passCheck=validatePassword(user_password)
                    if (passCheck[0]===true){
                        bcrypt.hash(user_password,saltRounds,async(err,hash) => {
                            if(err){
                                console.log(err.message);
                            }else{
                                try {
                                    const newUserPass=await User.update({user_password:hash,user_confirm_password:hash},
                                        {where:{email_address:email_address}})
                                    req.flash("msgSuccessLogin","✔ Password changed successfully!")
                                    console.log("Password changed successfully!");
                                    res.status(200).redirect('/')
                                } catch (error) {
                                    res.status(500).json(error.message)
                                }
                            }
                        })
                    }else{
                        //Password did not meet criteria set
                        res.render('auth/forgot-password',{        
                        msgSuccessReg: req.flash("msgSuccessReset"),
                           msgFailureReg: req.flash("msgFailureReset"),
                           msgError:passCheck[1],
                           csrfToken: req.csrfToken() 
                       })
                    }
                }else{
                    req.flash("msgFailureReset",`❌ Passwords do not match!`)
                    res.redirect('/forgot-password') 
                }
            }else{
                req.flash("msgFailureReset",`❌ User ${email_address} not found. Create an account!`)
                res.redirect('/forgot-password')
            }
        } catch (error) {
            res.status(500).json(error.message)
        }
        res.redirect('/')
    }
}

