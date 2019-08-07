<template>
  <div
    id="app"
    class="back"
  >
    <v-app id="inspire">
      <v-content>
        <v-container
          fluid
          fill-height
        >
          <v-layout
            align-center
            justify-center
          >
            <v-flex
              xs12
              sm8
              md4
            >
              <v-form @submit.prevent="logar()">
                <v-card class="elevation-12">
                  <v-toolbar
                    dark
                    color="primary"
                  >
                    <v-toolbar-title>Login - SCA - PUCMinas</v-toolbar-title>
                    <v-spacer></v-spacer>
                  </v-toolbar>
                  <v-card-text>
                    <v-text-field
                      prepend-icon="person"
                      v-model="login"
                      name="login"
                      label="E-mail"
                      type="text"
                    ></v-text-field>
                    <v-text-field
                      prepend-icon="lock"
                      name="password"
                      v-model="senha"
                      label="Senha"
                      id="password"
                      type="password"
                    ></v-text-field>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                      type="submit"
                      :loading="loading"
                      color="primary"
                    >Login</v-btn>
                  </v-card-actions>
                </v-card>
              </v-form>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>
    </v-app>
  </div>
</template>

<script>
import LoginService from './services/login-service'
import Login from './model/Login'
import Vue from 'vue'
import AuthService from '../../shared/auth/auth-service'

export default {
  data: () => ({
    login: null,
    senha: null,
    loading: false
  }),
  methods: {
    logar () {
      if (this.login && this.senha) {
        this.loading = true
        this.service.login(new Login(this.login, btoa(this.senha))).then(
          res => {
            this.loading = false
            if (
              res.headers.get('Authorization') !== null &&
              res.headers.get('Authorization') !== undefined
            ) {
              this.authService.setToken(res.headers.get('Authorization'))
              this.$router.push({
                name: 'Execution'
              })
            }
          }, () => {
            this.loading = false
          })
      }
    }
  },
  created () {
    this.service = new LoginService(Vue.http)
    this.authService = new AuthService()
  },
  mounted () {
    this.authService.getSignedIn().then(
      signIn => {
        this.signedIn = signIn
      },
      err => {
        console.log(err)
      }
    )    
  }
}
</script>

<style scoped>
.back {
  background-image: url('~@/assets/background.jpg');
  background-size: cover;
}
#inspire {
  background: none;
}
</style>
