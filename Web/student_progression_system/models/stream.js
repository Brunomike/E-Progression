'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Stream extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate({Student}) {
      // define association here
      this.hasOne(Student,{foreignKey:'stream_allocated_id'})
    }
  };
  Stream.init({
    name: {
      type:DataTypes.STRING,
      allowNull:false
    },
    abbreviation: {
      type:DataTypes.STRING,
      allowNull:false
    }
  }, {
    sequelize,
    tableName:'streams_table',
    modelName: 'Stream',
  });
  return Stream;
};