import AuthService from '../../../shared/auth/auth-service'
const getters = {
  userLogged: (state) => {
    let userLogged = state.userLogged
    if (userLogged) {
      return userLogged
    }
    return new AuthService().getUserLogged()
  }
}

export default getters
