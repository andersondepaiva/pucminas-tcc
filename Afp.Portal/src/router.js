import Vue from 'vue'
import Router from 'vue-router'
const MonitoramentoBarragens = () => import('./components/monitoramento-barragens/MonitoramentoBarragens.vue')
const KiLogin = () => import('./components/login/KiLogin.vue')
const KiBaseView = () => import('./components/KiBaseView.vue')
const UnauthorizedView = () => import('./components/login/Unauthorized.vue')
const CallbackView = () => import('./components/login/Callback.vue')

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
          name: 'Monitoramento Barragens',
          component: MonitoramentoBarragens,
          icon: 'bar_chart',
          menu: true,
          meta: { auth: true }
        },
        {
          path: '/unauthorized',
          name: 'Unauthorized',
          component: UnauthorizedView,
          menu: false,
          meta: { auth: false }
        },
        {
          path: '/callback',
          name: 'Callback',
          component: CallbackView,
          menu: false,
          meta: { auth: false }
        }
      ]
    }
  ],
  mode: 'history'
})
