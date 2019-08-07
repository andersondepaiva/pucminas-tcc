import actions from './actions'
import mutations from './mutations'
import getters from './getters'

// initial state
const state = {
  snackbar: false,
  color: '',
  text: ''
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
