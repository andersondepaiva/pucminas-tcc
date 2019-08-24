import Vue from 'vue'
import Vuex from 'vuex'
import SolicitacaoMensagemModule from './modules/solicitacaoMensagem/index'
import SnackBarModule from './modules/snack-bar/index'
import UserModule from './modules/user/index'
import AuthModule from './modules/auth/index'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    solicitacaoMensagemModule: SolicitacaoMensagemModule,
    snackBarModule: SnackBarModule,
    userModule: UserModule,
    authModule: AuthModule
  },
  strict: process.env.NODE_ENV !== 'production'
})
