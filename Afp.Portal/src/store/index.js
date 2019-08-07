import Vue from 'vue'
import Vuex from 'vuex'
import ProjectModule from './modules/project/index'
import SnackBarModule from './modules/snack-bar/index'
import KatalonModule from './modules/katalon/index'
import UserModule from './modules/user/index'
import AuthModule from './modules/auth/index'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    projectModule: ProjectModule,
    snackBarModule: SnackBarModule,
    katalonModule: KatalonModule,
    userModule: UserModule,
    authModule: AuthModule
  },
  strict: process.env.NODE_ENV !== 'production'
})
