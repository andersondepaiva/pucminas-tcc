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
import VueApexCharts from 'vue-apexcharts'

Vue.use(VueApexCharts)
Vue.component('apexchart', VueApexCharts)

Vue.config.productionTip = false
let options = {
  namespace: 'ki.'
}

let authService = new AuthService()

Vue.use(VueJwtDecode)
Vue.use(VueLocalStorage, options)
Vue.use(Vuetify)
Vue.use(VueResource)
Vue.use(Vuex)
Vue.use(VueMoment)

Vue.http.options.root = process.env.VUE_APP_ROOT_API
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  let result = to.matched.some(record => record.meta.auth)
  console.log('result some record', result)
  if (result) {
    authService.getRole().then(
      success => {
        next()
      },
      err => {
        console.log('error success', err)
      }
    )
  } else {
    console.log('else if some record')
    next()
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
