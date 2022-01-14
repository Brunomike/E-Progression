'use strict';
module.exports = {
  up: async (queryInterface, DataTypes) => {
    await queryInterface.createTable('Teachers', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: DataTypes.INTEGER
      },
      first_name: {
        type: DataTypes.STRING,
        allowNull:false
      },
      last_name: {
        type: DataTypes.STRING,
        allowNull:false
      },
      other_names: {
        type: DataTypes.STRING,
        allowNull:true
      },
      email_address: {
        type: DataTypes.STRING,
        allowNull:false
      },
      phone_number: {
        type: DataTypes.STRING,
        allowNull:false
      },
      gender: {
        type: DataTypes.STRING,
        allowNull:false
      },
      dob: {
        type: DataTypes.DATE,
        allowNull:false
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
    await queryInterface.dropTable('Teachers');
  }
};