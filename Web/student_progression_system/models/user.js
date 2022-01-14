'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
    toJSON(){
      return {...this.get(),id:undefined,delete_account_request_date:undefined,user_confirm_password:undefined,createdAt:undefined,updatedAt:undefined}
    }
  };
  User.init({
    uuid:{
      type:DataTypes.UUID,
      defaultValue:DataTypes.UUIDV4
    },
    email_address: {
      type:DataTypes.STRING,
      allowNull:false
    },
    first_name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    last_name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    other_names:{
      type:DataTypes.STRING,
      allowNull:true
    },
    phone_number: {
      type:DataTypes.STRING,
      allowNull:false
    },
    user_gender: {
      type:DataTypes.STRING,
      allowNull:false
    },
    user_role: {
      type:DataTypes.STRING,
      allowNull:false
    },
    user_status: {
      type:DataTypes.STRING,
      allowNull:false
    },
    user_profile_image_link: {
      type:DataTypes.STRING,
      allowNull:true
    },  
    delete_account_request_date: {
      type:DataTypes.DATE,
      allowNull:true
    },
    year_of_birth: {
      type:DataTypes.DATE,
      allowNull:false
    },
    user_password: {
      type:DataTypes.STRING,
      allowNull:false
    },
    user_confirm_password: {
      type:DataTypes.STRING,
      allowNull:false
    },
  }, {
    sequelize,
    tableName: 'users',
    modelName: 'User',
  });
  return User;
};