'use strict';
module.exports = {
  up: async (queryInterface, DataTypes) => {
    await queryInterface.createTable('News', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: DataTypes.INTEGER
      },
      news_title: {
        type: DataTypes.STRING,
        allowNull: false
      },
      news_sub_title: {
        type: DataTypes.STRING,
        allowNull: false
      },
      news_content: {
        type: DataTypes.TEXT,
        allowNull: false
      },
      news_image_link: {
        type: DataTypes.STRING,
        allowNull: true
      },
      news_category: {
        type: DataTypes.STRING,
        allowNull: false
      },
      createdAt: {
        allowNull: false,
        type: DataTypes.DATE
      },
      updatedAt: {
        allowNull: false,
        type: DataTypes.DATE
      }
    });
  },
  down: async (queryInterface, DataTypes) => {
    await queryInterface.dropTable('News');
  }
};