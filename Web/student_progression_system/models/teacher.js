'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Teacher extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({Classroom}) {
      // define association here
      this.hasMany(Classroom,{foreignKey:'class_teacher_id'})
    }
  };
  Teacher.init({
    first_name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    last_name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    other_names: {
      type:DataTypes.STRING,
      allowNull:true
    },
    email_address: {
      type:DataTypes.STRING,
      allowNull:false
    },
    phone_number: {
      type:DataTypes.STRING,
      allowNull:false
    },
    gender: {
      type:DataTypes.STRING,
      allowNull:false
    },
    dob: {
      type:DataTypes.DATE,
      allowNull:false
    }
  }, {
    sequelize,
    tableName:'teachers_table',
    modelName: 'Teacher',
  });
  return Teacher;
};