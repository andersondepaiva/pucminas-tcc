const actions = {
  destructor ({ commit }) {
    commit('destructor')
  },
  constructor (
    { state, commit },
    { project, profile, testSuite }
  ) {
    commit('constructor', { project, profile, testSuite })
  },
  setQueueExecution ({ state, commit }, { isQueueExecution }) {
    commit('setQueueExecution', { isQueueExecution: isQueueExecution })
  }
}

export default actions
