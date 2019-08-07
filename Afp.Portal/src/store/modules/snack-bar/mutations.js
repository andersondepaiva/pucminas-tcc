const mutations = {
  destructor (state) {
    state.snackbar = false
    state.color = ''
    state.text = ''
  },
  showMessage (state, { color, text }) {
    state.snackbar = true
    state.color = color
    state.text = text
  }
}

export default mutations
