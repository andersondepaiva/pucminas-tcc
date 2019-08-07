const actions = {
  showMessageWarning ({ state, commit }, { text }) {
    commit('showMessage', { color: 'warning', text: text })
  },
  showMessageError ({ state, commit }, { text }) {
    commit('showMessage', { color: 'error', text: text })
  },
  showMessageFatal ({ state, commit }, { text }) {
    commit('showMessage', { color: '#B71C1C', text: text })
  },
  showMessageSuccess ({ state, commit }, { text }) {
    commit('showMessage', { color: 'success', text: text })
  },
  showMessageInfo ({ state, commit }, { text }) {
    commit('showMessage', { color: 'info', text: text })
  },
  destructor ({ commit }) {
    commit('destructor')
  }
}

export default actions
