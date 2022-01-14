'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Fees extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({Student}) {
      // define association here
      this.belongsTo(Student,{foreignKey:'student_id'})      
    }
  };
  Fees.init({
    payment_description: {
      type:DataTypes.STRING,
      allowNull:false
    },
    transaction_date:  {
      type:DataTypes.DATE,
      allowNull:false
    },
    debit:  {
      type:DataTypes.DECIMAL,
      allowNull:false
    },
    credit:  {
      type:DataTypes.DECIMAL,
      allowNull:false
    },
    balance:  {
      type:DataTypes.DECIMAL,
      allowNull:false
    }
  }, {
    sequelize,
    tableName:'fees_table',
    modelName: 'Fees',
  });
  return Fees;
};