'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Dorm extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({Student}) {
      // define association here
      this.hasOne(Student,{foreignKey:'dorm_id'})
    }
  };
  Dorm.init({
    dorm_name:{
      type:DataTypes.STRING,
      allowNull: false
    },
    dorm_capacity: {
      type:DataTypes.INTEGER,
      allowNull: false
    }
  }, {
    sequelize,
    tableName:'dorms_table',
    modelName: 'Dorm',
  });
  return Dorm;
};