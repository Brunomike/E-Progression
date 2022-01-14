'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class News extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  };
  News.init({
    news_title: {
      type:DataTypes.STRING,
      allowNull: false
    },
    news_sub_title: {
      type:DataTypes.STRING,
      allowNull: false
    },
    news_content: {
      type:DataTypes.TEXT,
      allowNull: false
    },
    news_image_link: {
      type:DataTypes.STRING,
      allowNull: true
    },
    news_category: {
      type:DataTypes.STRING,
      allowNull: false
    }
  }, {
    sequelize,
    tableName:'news_table',
    modelName: 'News',
  });
  return News;
};