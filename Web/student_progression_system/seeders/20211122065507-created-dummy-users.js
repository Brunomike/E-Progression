'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    /**
     * Add seed commands here.
     *
     * Example:
     * await queryInterface.bulkInsert('People', [{
     *   name: 'John Doe',
     *   isBetaMember: false
     * }], {});
    */
     await queryInterface.bulkInsert('users', [{
        uuid:"90f65c26-31fc-43c6-8b22-1e7188a53110",
        email_address: 'brunomike234@gmail.com',
        first_name:'Michael',
        last_name:'Bruno',
        phone_number:'+254716573061',
        user_gender:'male',
        user_role:'superadmin',
        user_status:'active',
        year_of_birth:'2000-05-22 00:00:00',
        user_password:'$2b$10$wfh0tUdOF8N9cwFzO4aQP.Y86RM.G3azTGbVSAQORKuaurvCh/wby',
        user_confirm_password:'$2b$10$wfh0tUdOF8N9cwFzO4aQP.Y86RM.G3azTGbVSAQORKuaurvCh/wby'
       
       }], {});
  },

  down: async (queryInterface, Sequelize) => {
    /**
     * Add commands to revert seed here.
     *
     * Example:
     * await queryInterface.bulkDelete('People', null, {});
     */
     await queryInterface.bulkDelete('users', null, {});
  }
};
