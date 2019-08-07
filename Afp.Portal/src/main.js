import 'babel-polyfill'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import Vuetify from 'vuetify'
import VueResource from 'vue-resource'
import Vuex from 'vuex'
import VueMoment from 'vue-moment'
import VueLocalStorage from 'vue-ls'
import VueJwtDecode from 'vue-jwt-decode'
import 'vuetify/dist/vuetify.min.css' // Ensure you are using css-loader
import AuthService from './shared/auth/auth-service'

Vue.config.productionTip = false
let options = {
  namespace: 'ki.'
}

Vue.use(VueJwtDecode)
Vue.use(VueLocalStorage, options)
Vue.use(Vuetify)
Vue.use(VueResource)
Vue.use(Vuex)
Vue.use(VueMoment)

Vue.http.options.root = process.env.VUE_APP_ROOT_API
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.auth)) {
    if (!Vue.ls.get('authorizationData')) {
      next({
        path: '/login',
        query: {
          redirect: to.fullPath
        }
      })
      return
    }

    if (to.matched.some(record => record.meta.roles)) {
      let userLogged = new AuthService().getUserLogged()
      if (!userLogged || to.meta.roles.indexOf(userLogged.tipoUsuario) < 0) {
        next({
          path: '/'
        })
        return
      }
    }
    next()
  } else {
    next()
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
