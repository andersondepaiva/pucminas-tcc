import User from '../../../components/configuration/user/model/User'

const mutations = {
  destructor (state) {
    state.user = null
    state.isChangeUser = false
  },
  constructor (state, { login, senha, tipoUsuario, id, pessoa }) {
    state.user = new User()
    state.user.login = login
    state.user.senha = senha
    state.user.tipoUsuario = tipoUsuario
    state.user.pessoa = pessoa
    state.user.id = id
  },
  setChangeUser (state, { isChange }) {
    state.isChangeUser = isChange
  }
}

export default mutations
