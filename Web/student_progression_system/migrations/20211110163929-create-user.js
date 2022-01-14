'use strict';
module.exports = {
  up: async (queryInterface, DataTypes) => {
    await queryInterface.createTable('users', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: DataTypes.INTEGER
      }, uuid:{
        type:DataTypes.UUID,
        defaultValue:DataTypes.UUIDV4
      },
      email_address: {
        type: DataTypes.STRING,
        allowNull: false
      },
      first_name: {
        type: DataTypes.STRING,
        allowNull: false
      },
      last_name: {
        type: DataTypes.STRING,
        allowNull: false
      },
      other_names: {
        type: DataTypes.STRING,
        allowNull: true
      },
      phone_number: {
        type: DataTypes.STRING,
        allowNull: false
      },
      user_gender: {
        type: DataTypes.STRING,
        allowNull: false
      },
      user_role: {
        type: DataTypes.STRING,
        allowNull: false
      },
      user_status: {
        type: DataTypes.STRING,
        allowNull: false
      },
      user_profile_image_link: {
        type: DataTypes.STRING,
        allowNull: true
      },     
      delete_account_request_date: {
        type: DataTypes.DATE,
        allowNull: true
      },
      year_of_birth: {
        type: DataTypes.DATE,
        allowNull: false
      },
      user_password: {
        type: DataTypes.STRING,
        allowNull: false
      },
      user_confirm_password: {
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
    await queryInterface.dropTable('users');
  }
};