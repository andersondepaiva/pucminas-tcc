/* import Vue from 'vue'
import ConfigurationService from '../../components/configuration/services/configuration-service'
import store from '../../store/index' */
import Oidc from 'oidc-client'
import Vue from 'vue'
import store from '../../store/index'

var mgr = new Oidc.UserManager({
  userStore: new Oidc.WebStorageStateStore(),
  authority: 'http://localhost:5000',
  client_id: 'sca-portal',
  redirect_uri: window.location.origin + '/callback',
  response_type: 'id_token token',
  scope: 'openid cadastro-ativos-portal monitoramento-barragens-portal seguranca-comunicacao-portal',
  post_logout_redirect_uri: window.location.origin + '/',
  accessTokenExpiringNotificationTime: 10,
  automaticSilentRenew: true,
  filterProtocolClaims: true,
  loadUserInfo: true
})

Oidc.Log.logger = console
Oidc.Log.level = Oidc.Log.INFO

mgr.events.addUserLoaded(function (user) {
  console.log('New User Loaded：', arguments)
  console.log('Acess_token: ', user.access_token)
  Vue.ls.set('authorizationData', {
    token: user.access_token
  })
  console.log('userLocalStorage', user)
  Vue.ls.set('userLogged', {
    user: user.profile.user_name
  })
  store.dispatch('authModule/updateUser', {
    user: user.profile.user_name
  })
})

mgr.events.addAccessTokenExpiring(function () {
  console.log('AccessToken Expiring：', arguments)
})

mgr.events.addAccessTokenExpired(function () {
  console.log('AccessToken Expired：', arguments)
  alert('Session expired. Going out!')
  mgr.signoutRedirect().then(function (resp) {
    console.log('signed out', resp)
  }).catch(function (err) {
    console.log(err)
  })
})

mgr.events.addSilentRenewError(function () {
  console.error('Silent Renew Error：', arguments)
})

mgr.events.addUserSignedOut(function () {
  alert('Going out!')
  console.log('UserSignedOut：', arguments)
  mgr.signoutRedirect().then(function (resp) {
    console.log('signed out', resp)
  }).catch(function (err) {
    console.log(err)
  })
})

export default class AuthService {
  // Renew the token manually
  renewToken () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.signinSilent().then(function (user) {
        if (user == null) {
          self.signIn(null)
        } else {
          return resolve(user)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }

  // Get the user who is logged in
  getUser () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.getUser().then(function (user) {
        if (user == null) {
          self.signInRedirectCallback()
          return resolve(null)
        } else {
          return resolve(user)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }

  getUserLogged () {
    if (Vue.ls.get('userLogged')) {
      let user = Vue.ls.get('userLogged')
      return user.user
    }

    return null
  }

  // Check if there is any user logged in
  getSignedIn () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.getUser().then(function (user) {
        if (user == null) {
          self.signIn()
          return resolve(false)
        } else {
          return resolve(true)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }

  // Redirect of the current window to the authorization endpoint.
  signIn () {
    mgr.signinRedirect().catch(function (err) {
      console.log(err)
    })
  }

  signInRedirectCallback () {
    return new Promise((resolve, reject) => {
      mgr.signinRedirectCallback().then(function (user) {
        return resolve(user)
      }).catch(function (err) {
        console.log('signRedirectCallBack catch', err)
        return reject(err)
      })
    })
  }

  // Redirect of the current window to the end session endpoint
  signOut () {
    mgr.signoutRedirect().then(function (resp) {
      Vue.ls.remove('authorizationData')
      console.log('signed out', resp)
    }).catch(function (err) {
      console.log(err)
    })
  }

  // Get the profile of the user logged in
  getProfile () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.getUser().then(function (user) {
        if (user === null || user === undefined) {
          self.signInRedirectCallback()
          return resolve(null)
        } else {
          return resolve(user.profile)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }

  // Get the token id
  getIdToken () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.getUser().then(function (user) {
        if (user == null) {
          self.signIn()
          return resolve(null)
        } else {
          return resolve(user.id_token)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }

  // Get the session state
  getSessionState () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.getUser().then(function (user) {
        if (user == null) {
          self.signIn()
          return resolve(null)
        } else {
          return resolve(user.session_state)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }

  // Get the access token of the logged in user
  getAcessToken () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.getUser().then(function (user) {
        if (user == null) {
          self.signIn()
          return resolve(null)
        } else {
          return resolve(user.access_token)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }

  // Takes the scopes of the logged in user
  getScopes () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.getUser().then(function (user) {
        if (user == null) {
          self.signIn()
          return resolve(null)
        } else {
          return resolve(user.scopes)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }

  // Get the user roles logged in
  getRole () {
    let self = this
    return new Promise((resolve, reject) => {
      mgr.getUser().then(function (user) {
        if (user == null) {
          self.signIn()
          return resolve(null)
        } else {
          return resolve(user.profile.role)
        }
      }).catch(function (err) {
        console.log(err)
        return reject(err)
      })
    })
  }
}
