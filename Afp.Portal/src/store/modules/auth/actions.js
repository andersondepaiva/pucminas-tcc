const actions = {
  updateUser (
    { state, commit },
    { user }
  ) {
    commit('updateUser', {
      user: user
    })
  }
}

export default actions
