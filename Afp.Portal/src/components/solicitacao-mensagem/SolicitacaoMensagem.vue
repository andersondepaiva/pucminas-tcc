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
            <v-layout row wrap>
                <v-flex xs12>
                <v-text-field
                    v-model="solicitacaoMensagem.mensagem"
                    label="Mensagem"
                    required
                    max-length="100"
                    :rules="mensagemValidator"
                ></v-text-field>
                </v-flex>
                <v-flex xs12>
                <v-text-field
                    v-model="solicitacaoMensagem.motivo"
                    label="Motivo"
                    required
                    max-length="255"
                    :rules="motivoValidator"
                ></v-text-field>
                </v-flex>
                <v-flex xs12>
                    <v-select
                        v-model="solicitacaoMensagem.escoposId"
                        :items="escopos"
                        attach
                        chips
                        item-text="descricao"
                        item-value="id"
                        label="Escopos"
                        multiple
                        :rules="escoposValidator"
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
              Enviar
            </v-btn>
          </v-container>
        </v-card-actions>
      </v-card>
    </v-form>
  </div>
</template>

<script>
import SegurancaComunicacaoService from './services/seguranca-comunicao-service'
import SolicitacaoMensagem from './model/solicitacao-mensagem'

export default {
  data () {
    return {
      valid: false,
      loading: false,
      solicitacaoMensagem: new SolicitacaoMensagem(),
      escopos: [],
      mensagemValidator: [v => !!v || 'Mensagem é obrigatório'],
      motivoValidator: [v => !!v || 'Motivo é obrigatório'],
      escoposValidator: [v => !!v || 'Escopos são obrigatórios'],
    }
  },
  created () {
    this.segurancaComunicacaoService = new SegurancaComunicacaoService(this.$http)
  },
  mounted () {
      this.segurancaComunicacaoService.getEscopos().then(dados => {
          this.escopos = dados
      })
  },
  methods: {
    salvar () {
      if (this.valid) {
        this.loading = true
        this.segurancaComunicacaoService.postSolicitacaoMensagem(this.solicitacaoMensagem).then(
          () => {
            this.initData()
            this.$store.dispatch('solicitacaoMensagemModule/destructor')
            this.$store.dispatch('snackBarModule/showMessageSuccess', {
              text: 'Aviso disparado com sucesso'
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
      this.solicitacaoMensagem = new SolicitacaoMensagem()
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>