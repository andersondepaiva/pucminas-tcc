import Vue from 'vue'
import ConfigurationService from '../../components/configuration/services/configuration-service'
import store from '../../store/index'

export default class AuthService {
  constructor () {
    this.configurationService = new ConfigurationService(Vue.http)
  }

  getToken () {
    if (Vue.ls.get('authorizationData')) {
      let authData = Vue.ls.get('authorizationData')
      return authData.token
    }

    return null
  }

  setUserLogged (userLogged) {
    Vue.ls.set('userLogged', {
      user: userLogged
    })
    store.dispatch('authModule/updateUser', {
      user: userLogged
    })
  }

  getUserLogged () {
    if (Vue.ls.get('userLogged')) {
      let user = Vue.ls.get('userLogged')
      return user.user
    }

    return null
  }

  logout () {
    Vue.ls.remove('authorizationData')
    Vue.ls.remove('userLogged')
  }

  getIdUserLogged () {
    let token = this.getToken()
    return this.decodeToken(token).userId
  }

  getTypeUserLogged () {
    let token = this.getToken()
    return this.decodeToken(token).authorities
  }

  decodeToken (tokenEncoded) {
    return Vue.jwtDec.decode(tokenEncoded.replace('Bearer ', ''))
  }

  setToken (tokenData) {
    Vue.ls.set('authorizationData', {
      token: tokenData
    })

    this.configurationService.getUserById(this.decodeToken(tokenData).userId).then((res) => {
      this.setUserLogged(res)
    })
  }
}
