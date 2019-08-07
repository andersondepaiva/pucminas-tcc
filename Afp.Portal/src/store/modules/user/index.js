import actions from './actions'
import mutations from './mutations'
import getters from './getters'

// initial state
const state = {
  user: null,
  isChangeUser: false
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
