const actions = {
  destructor ({ commit }) {
    commit('destructor')
  },
  constructor (
    { state, commit },
    { mensagem, motivo, escopos }
  ) {
    commit('constructor', {
      mensagem: mensagem,
      motivo: motivo,
      escopos: escopos
    })
  },
  setChangeSolicitacao ({ state, commit }, { isChange }) {
    commit('setChangeSolicitacao', { isChange: isChange })
  }
}

export default actions
