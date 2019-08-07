import User from '../../../components/configuration/user/model/User'

const mutations = {
  updateUser (state, { user }) {
    state.userLogged = new User()
    state.userLogged.login = user.login
    state.userLogged.tipoUsuario = user.tipoUsuario
    state.userLogged.pessoa = user.pessoa
    state.userLogged.id = user.id
  }
}

export default mutations
