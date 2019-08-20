<template>
  <div
    @mouseleave="mini = true"
    @mouseover="mini = false"
  >
    <v-navigation-drawer
      app
      permanent
      light
      fixed
      :mini-variant.sync="mini"
    >

      <v-toolbar
        flat
        class="transparent"
      >

        <v-list class="pa-0">

          <v-list-tile>

            <v-list-tile-avatar>

              <img src="~@/assets/logo.png" />

            </v-list-tile-avatar>

            <v-list-tile-content>
              <v-list-tile-title><b>{{userLogged}}</b></v-list-tile-title>
            </v-list-tile-content>

            <v-list-tile-action>

              <v-btn
                icon
                @click.native.stop="mini = !mini"
              >

                <v-icon>chevron_left</v-icon>

              </v-btn>

            </v-list-tile-action>

          </v-list-tile>

        </v-list>

      </v-toolbar>

      <v-list
        class="pt-0"
        dense
      >

        <v-list-tile
          v-for="item in items"
          :key="item.name"
          @click="redirect(item.path)"
        >

          <v-list-tile-action>

            <v-icon>{{ item.icon }}</v-icon>

          </v-list-tile-action>

          <v-list-tile-content>

            <v-list-tile-title>{{ item.name }}</v-list-tile-title>

          </v-list-tile-content>

        </v-list-tile>

        <v-divider></v-divider>

        <v-list-tile @click="logout()">

          <v-list-tile-action>

            <v-icon>lock_open</v-icon>

          </v-list-tile-action>

          <v-list-tile-content>

            <v-list-tile-title>Exit</v-list-tile-title>

          </v-list-tile-content>

        </v-list-tile>

      </v-list>

    </v-navigation-drawer>

  </div>

</template>

<script>
import Vue from 'vue'
import router from '../../router'
import AuthService from '../auth/auth-service'

export default {
  data () {
    return {
      items: [],

      mini: true,

      right: null,
      userLogged: null
    }
  },
  computed: {
    userLoggedValue: function () {
      return this.$store.getters['authModule/userLogged']
    }
  },
  watch: {
    userLoggedValue (newValue, oldValue) {
      this.userLogged = newValue ? newValue : 'Katalon Integration'
    }
  },
  mounted () {
    if (router.options.routes != null) {
      this.buildItemsMenu(router.options.routes)
    }
    router.options.routes.filter(r => r.menu)
  },
  created () {
    this.authService = new AuthService()
    let userLocalStorage = Vue.ls.get('userLogged')
    if (userLocalStorage) {
      this.userLogged = userLocalStorage.user
    }  
  },
  methods: {
    redirect (path) {
      this.mini = false
      this.$router.push(path)
    },

    logout () {
      this.authService.signOut()
    },

    buildItemsMenu (routes) {
      routes.forEach(route => {
        if (route.menu && this.isPermitted(route)) {
          this.items.push(route)
        }
        if (route.children != null && route.children.length > 0) {
          this.buildItemsMenu(route.children)
        }
      })
    },

    isPermitted (menu) {
      if (menu.meta === null ||
      menu.meta === undefined ||
      menu.meta.roles === null ||
      menu.meta.roles === undefined ||
      !menu.meta.roles.length) {
        return true
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
