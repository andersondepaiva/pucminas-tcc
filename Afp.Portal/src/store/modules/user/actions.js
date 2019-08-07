const actions = {
  destructor ({ commit }) {
    commit('destructor')
  },
  constructor (
    { state, commit },
    { login, senha, tipoUsuario, id, pessoa }
  ) {
    commit('constructor', {
      login: login,
      senha: senha,
      tipoUsuario: tipoUsuario,
      id: id,
      pessoa: pessoa
    })
  },
  setChangeUser ({ state, commit }, { isChange }) {
    commit('setChangeUser', { isChange: isChange })
  }
}

export default actions
