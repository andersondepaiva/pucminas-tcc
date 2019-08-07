import Vue from 'vue'
import Router from 'vue-router'
const KiExecutionList = () => import('./components/katalon-execution/KiExecutionList.vue')
const KiConfiguration = () => import('./components/configuration/KiConfiguration.vue')
const KiUserProfileEdit = () => import('./components/configuration/user/KiUserProfileEdit.vue')
const KiLogin = () => import('./components/login/KiLogin.vue')
const KiBaseView = () => import('./components/KiBaseView.vue')

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: KiLogin,
      menu: false,
      meta: { auth: false }
    },
    {
      path: '/',
      component: KiBaseView,
      children: [
        {
          path: '/',
          name: 'Execution',
          component: KiExecutionList,
          icon: 'play_arrow',
          menu: true,
          meta: { auth: true }
        },
        {
          path: '/configuration',
          name: 'Configuration',
          component: KiConfiguration,
          icon: 'build',
          menu: true,
          meta: { auth: true, roles: ['ADMIN'] }
        },
        {
          path: '/profile',
          name: 'My Profile',
          component: KiUserProfileEdit,
          icon: 'person',
          menu: true,
          meta: { auth: true }
        }
      ]
    }
  ],
  mode: 'history'
})
