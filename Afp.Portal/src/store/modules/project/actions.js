const actions = {
  destructor ({ commit }) {
    commit('destructor')
  },
  constructor (
    { state, commit },
    { name, path, sourceType, id, gitAuthentication }
  ) {
    commit('constructor', {
      name: name,
      path: path,
      sourceType: sourceType,
      id: id,
      gitAuthentication: gitAuthentication
    })
  },
  setChangeProject ({ state, commit }, { isChange }) {
    commit('setChangeProject', { isChange: isChange })
  }
}

export default actions
