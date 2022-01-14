'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Parent extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({Student}) {
      // define association here
      this.hasOne(Student,{foreignKey:'parent_id'})
    }
  };
  Parent.init({
    email_address: {
      type:DataTypes.STRING,
      allowNull: false
    },
    first_name: {
      type:DataTypes.STRING,
      allowNull: false
    },
    last_name: {
      type:DataTypes.STRING,
      allowNull: false
    },
    other_names: {
      type:DataTypes.STRING,
      allowNull: true
    },
    dob: {
      type:DataTypes.DATE,
      allowNull: false
    },
    phone_number: {
      type:DataTypes.STRING,
      allowNull: false
    }
  }, {
    sequelize,
    tableName:'parents_table',
    modelName: 'Parent',
  });
  return Parent;
};