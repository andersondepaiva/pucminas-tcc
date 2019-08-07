import actions from './actions'
import mutations from './mutations'
import getters from './getters'

// initial state
const state = {
  userLogged: null
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
