import actions from './actions'
import mutations from './mutations'
import getters from './getters'

// initial state
const state = {
  katalon: null,
  isQueueExecution: false
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
