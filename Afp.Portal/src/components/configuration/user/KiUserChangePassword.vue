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
                  type="password"
                  v-model="user.oldSenha"
                  browser-autocomplete="off"
                  label="Current Password"
                  :rules="oldPasswordValidator"
                ></v-text-field>
              </v-flex>
               <v-flex xs12>
                <v-text-field
                  type="password"
                  v-model="newSenha"
                  browser-autocomplete="off"
                  label="New Password"
                  :rules="newPasswordValidator"
                ></v-text-field>
              </v-flex>
               <v-flex xs12>
                <v-text-field
                  type="password"
                  v-model="user.senha"
                  browser-autocomplete="off"
                  label="Confirm New Password"
                  :rules="confirmNewPasswordValidator"
                ></v-text-field>
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
              id="btn_change_password"
            >
              Change Password
            </v-btn>
          </v-container>
        </v-card-actions>
      </v-card>
    </v-form>
  </div>
</template>

<script>
import ConfigurationService from '../services/configuration-service'
import UserChangePassword from './model/UserChangePassword'

export default {
  data () {
    return {
      valid: false,
      loading: false,
      user: new UserChangePassword(),
      newSenha: null,
      oldPasswordValidator: [
        v => (!!v) || 'Old Password is required'
      ],
      newPasswordValidator: [
        v => (!!v) || 'New Password is required'
      ],
      confirmNewPasswordValidator: [
        v => (!!v) || 'Confirm Password is required',
        v => (!(v !== this.newSenha)) || 'Password is not confirmed'
      ]
    }
  },
  created () {
    this.service = new ConfigurationService(this.$http)
  },
  methods: {
    salvar () {
      if (this.valid) {
        this.loading = true
        this.service.changePassword(new UserChangePassword(btoa(this.user.oldSenha), btoa(this.user.senha), this.$store.getters['authModule/userLogged'].id)).then(
          () => {
            this.$store.dispatch('snackBarModule/showMessageSuccess', {
              text: 'Password changed with Success'
            })
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
