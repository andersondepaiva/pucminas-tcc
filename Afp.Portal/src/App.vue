<template>
  <v-app>
    <v-content>
      <router-view></router-view>
      <v-snackbar
        :timeout="5000"
        :color="color"
        v-model="snackbar"
      >
        <b>{{ text }}</b>
        <v-btn
          dark
          flat
          @click.native="snackbar = false"
        >Close</v-btn>
      </v-snackbar>
    </v-content>
  </v-app>
</template>

<script>
import Vue from 'vue'
import router from './router'
import AuthService from './shared/auth/auth-service'

export default {
  data () {
    return {
      snackbar: false
    }
  },
  computed: {
    text: function () {
      return this.$store.getters['snackBarModule/text']
    },
    color: function () {
      return this.$store.getters['snackBarModule/color']
    },
    snackbarStore: function () {
      return this.$store.getters['snackBarModule/snackbar']
    }
  },
  watch: {
    snackbarStore (newValue, oldValue) {
      this.snackbar = newValue
    },
    snackbar (newValue, oldValue) {
      if (!newValue) {
        this.$store.dispatch('snackBarModule/destructor')
      }
    }
  },
  created () {
    this.authService = new AuthService()
    Vue.http.interceptors.push((req, next) => {
      req.headers.set('x-api-key', 'cd5eecc64a0a79b2ca5be934fce41fcf')
      req.headers.set('Authorization', this.authService.getToken())
      next(res => {
        if (res.status !== 200) {
          if (res.status === 401 || res.status === 403) {
            router.push({
              name: 'Login'
            })
            this.$store.dispatch('snackBarModule/showMessageError', {
              text: 'Invalid Authentication'
            })
          } else if (res.status === 400) {
            let textMessage = ''
            res.body.errors.forEach(e => {
              textMessage = textMessage + ' ' + e.defaultMessage
            })
            this.$store.dispatch('snackBarModule/showMessageError', {
              text: textMessage
            })
          } else if (res.status === 500) {
            this.$store.dispatch('snackBarModule/showMessageFatal', {
              text: 'Sorry, unexpected error occurred on our servers :('
            })
          }
        }
      })
    })
  }
}
</script>

<style>
.ki-title {
  margin-bottom: 72px;
}
.ki-btn-add-grid {
  float: right;
  margin-bottom: 16px !important;
  margin-top: 16px !important;
  margin-right: 16px !important;
}
.ki-td-font-style {
  font-weight: bold !important;
}
.ki-icon-title-dialog {
  margin-right: 16px !important;
}
.ki-icon-title-tab {
  margin-right: 16px !important;
  margin-bottom: 0px !important;
}
</style>
