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
                  :disabled="userSelected != null"
                ></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field
                  type="password"
                  v-model="user.senha"
                  browser-autocomplete="off"
                  label="Password"
                  :rules="passwordValidator"
                  v-if="userSelected == null"
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
          </v-container>
        </v-card-actions>
      </v-card>
    </v-form>
  </div>
</template>

<script>
import ConfigurationService from '../services/configuration-service'
import User from './model/User'
import Pessoa from './model/Pessoa'

export default {
  props: {
    userSelected: {
      type: Object,
      required: false
    }
  },
  data () {
    return {
      valid: false,
      loading: false,
      user: new User(),
      person: new Pessoa(),
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
  computed: {
    isChange: function () {
      return this.$store.getters['userModule/isChangeUser']
    }
  },
  watch: {
    isChange (newValue, oldValue) {
      if (!newValue) {
        this.initData()
      } else {
        if (this.userSelected) {
          this.service.getUserById(this.userSelected.id).then(res => {
            this.user = res
            this.person = this.user.pessoa
            this.passwordOriginal = this.user.senha
          })
        }
      }
    }
  },
  created () {
    this.service = new ConfigurationService(this.$http)
    this.user.tipoUsuario = 'USUARIO'
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
        this.service.mergeUser(this.user).then(
          () => {
            this.initData()
            this.$store.dispatch('userModule/destructor')
            this.$store.dispatch('snackBarModule/showMessageSuccess', {
              text: 'Success'
            })
          },
          () => {
            this.loading = false
          }
        )
      }
    },
    initData () {
      this.valid = false
      this.loading = false
      this.user = new User()
      this.person = new Pessoa()
      this.user.tipoUsuario = 'USUARIO'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
