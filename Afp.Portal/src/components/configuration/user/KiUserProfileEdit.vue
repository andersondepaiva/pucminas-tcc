<template>
  <div>
    <v-form
      v-model="valid"
      @submit.prevent="salvar()"
    >
      <v-card
        flat
        color="white"
      >
        <v-card-text>
          <v-container grid-list-md>
            <v-layout
              row
              wrap
            >
              <v-flex xs12>
                <v-text-field
                  v-model="person.nome"
                  label="Name"
                  required
                  max-length="255"
                  :rules="nomeValidator"
                ></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field
                  v-model="user.login"
                  label="Email"
                  required
                  max-length="255"
                  :rules="emailValidator"
                  disabled
                ></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-select
                  v-model="user.tipoUsuario"
                  :items="tiposUsuario"
                  :rules="[v => !!v || 'User Profile is required']"
                  label="User Profile"
                  item-text="descricao"
                  item-value="id"
                  disabled
                  required
                ></v-select>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-container grid-list-md>
            <v-btn
              type="submit"
              :disabled="!valid || loading"
              :loading="loading"
              color="primary"
              id="btn_salvar"
            >
              Save
            </v-btn>
            <v-btn
              :disabled="!valid || loading"
              :loading="loading"
              color="success"
              id="btn_salvar"
              style="float: right;"
              @click="isChangePassword = true"
            >
              Change Password
            </v-btn>
          </v-container>
        </v-card-actions>
      </v-card>
    </v-form>
    <v-dialog v-model="isChangePassword" fullscreen transition="dialog-bottom-transition" :overlay="false">
        <v-card>
            <v-toolbar dark color="primary">
                <v-btn icon @click.native="isChangePassword = false" dark>
                    <v-icon>close</v-icon>
                </v-btn>
                <v-toolbar-title><v-icon class="ki-icon-title-dialog">vpn_key</v-icon>Change Password</v-toolbar-title>
            </v-toolbar>
            <ki-user-change-password></ki-user-change-password>
        </v-card>
    </v-dialog>
  </div>
</template>

<script>
import ConfigurationService from '../services/configuration-service'
import User from './model/User'
import Pessoa from './model/Pessoa'
import AuthService from '../../../shared/auth/auth-service'
import KiUserChangePassword from './KiUserChangePassword.vue'

export default {
  components: {
    'ki-user-change-password': KiUserChangePassword
  },
  data () {
    return {
      valid: false,
      loading: false,
      user: new User(),
      person: new Pessoa(),
      isChangePassword: false,
      tiposUsuario: [
        {
          id: 'USER',
          descricao: 'User'
        },
        {
          id: 'ADMIN',
          descricao: 'Administrator'
        }
      ],
      nomeValidator: [v => !!v || 'Name is required'],
      passwordValidator: [
        v => (!!v) || 'Password is required'
      ],
      emailValidator: [
        v => !!v || 'Email is required',
        v => /.+@.+/.test(v) || 'E-mail must be valid'
      ]
    }
  },
  created () {
    this.service = new ConfigurationService(this.$http)
    this.authService = new AuthService()
    this.service.getUserById(this.authService.getIdUserLogged()).then(res => {
      this.user = res
      this.person = this.user.pessoa
      this.passwordOriginal = this.user.senha
    })
  },
  methods: {
    salvar () {
      if (this.valid) {
        this.user.senha =
            this.passwordOriginal !== this.user.senha
              ? btoa(this.user.senha)
              : this.user.senha

        this.user.pessoa = this.person

        this.loading = true
        this.service.updateProfile(this.user).then(
          (res) => {
            this.$store.dispatch('snackBarModule/showMessageSuccess', {
              text: 'Success'
            })
            this.authService.setUserLogged(res)
            this.$router.push({
              path: '/'
            })
          },
          () => {
            this.loading = false
          }
        )
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
