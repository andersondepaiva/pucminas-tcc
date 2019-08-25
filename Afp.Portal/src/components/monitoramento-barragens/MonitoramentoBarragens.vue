<template>
  <div>
    <h1 class="ki-title">Monitoramento de Barragens</h1>
    <div v-show="content && content.barragem">
      <div class="title font-weight-medium"><v-icon>satellite</v-icon> {{barragem}}</div>
      <div class="subtitle-1 font-weight-medium"><v-icon>layers</v-icon> {{minerioPrincipal}}</div>
      <div :class="calculateColorDanoPotecial()"><v-icon>error</v-icon> {{danoPotencial}}</div>
      <div :class="calculateColorRisco()"><v-icon>warning</v-icon> {{risco}}</div>
      <div class="subtitle-2 font-weight-medium"><v-icon>category</v-icon> {{metodoConstrutivo}}</div>
    </div>
    <v-autocomplete
        style="float: right; margin-top:-80px; margin-right:24px"
        v-model="barragemSearch"
        :items="barragens"
        :loading="isLoadingSearchBarragem"
        :search-input.sync="searchBarragens"
        hide-no-data
        hide-selected
        item-text="descricao"
        item-value="id"
        label="Barragem"
        clearable
        placeholder="Comece a digitar para buscar"
        prepend-icon="search"
        return-object
    ></v-autocomplete>
    <v-btn fab dark @click="openDialog()" style="float: right; margin-bottom: 16px !important; margin-top: -26px !important; margin-right: 16px !important;" color="blue" id="btn_dialog_solitacao_mensagem">
      <v-icon dark>add_alert</v-icon>
    </v-btn>
    <v-speed-dial
      open-on-hover
      direction="left"
      transition="slide-y-reverse-transition"
      style="float: right; margin-bottom: 16px !important; margin-top: -32px !important; margin-right: 16px !important;"
    >
      <v-btn
        slot="activator"
        color="blue"
        outline
        dark
        fab
      >
        <v-icon v-if="timeToRefresh === 0" dark>refresh</v-icon>
        {{ showTimerSelected() }}
      </v-btn>
      <v-btn
        fab
        dark
        small
        outline
        @click="get(0)"
        color="blue"
      >
        <v-icon>refresh</v-icon>
      </v-btn>
      <v-btn
        fab
        dark
        small
        color="blue lighten-3"
        @click="get(10000)"
      >
        10s
      </v-btn>
      <v-btn
        fab
        dark
        small
        color="blue lighten-2"
        @click="get(30000)"
      >
        30s
      </v-btn>
      <v-btn
        fab
        dark
        small
        color="blue lighten-1"
        @click="get(60000)"
      >
        1min
      </v-btn>
      <v-btn
        fab
        dark
        small
        color="blue darken-1"
        @click="get(300000)"
      >
        5min
      </v-btn>
    </v-speed-dial>
    <v-container>
        <v-layout>
          <v-flex xs6>
            <div>
              <apexchart type="line" :options="chartOptionsDeslocamento" :series="seriesDeslocamento"></apexchart>
            </div>
          </v-flex>
          <v-flex xs6>
            <div>
              <apexchart type="line" :options="chartOptionsVolume" :series="seriesVolume"></apexchart>
            </div>
          </v-flex>
      </v-layout>
    </v-container>
     <v-dialog v-model="isChange" fullscreen transition="dialog-bottom-transition" :overlay="false">
        <v-card>
            <v-toolbar dark color="primary">
                <v-btn icon @click.native="closeDialog()" dark>
                    <v-icon>close</v-icon>
                </v-btn>
                <v-toolbar-title><v-icon class="ki-icon-title-dialog">add_alert</v-icon>Enviar Aviso</v-toolbar-title>
            </v-toolbar>
            <solicitacao-mensagem></solicitacao-mensagem>
        </v-card>
    </v-dialog>
  </div>
</template>

<script>
import MonitoramentoBarragensService from './services/monitoramento-barragens-service'
import SolicitacaoMensagem from '../solicitacao-mensagem/SolicitacaoMensagem.vue'

export default {
  components: {
    'solicitacao-mensagem': SolicitacaoMensagem
  },
  data () {
    return {
      content: [],
      loading: true,
      barragem: '',
      minerioPrincipal: '',
      danoPotencial: '',
      risco: '',
      metodoConstrutivo: '',
      isLoadingSearchBarragem: false,
      searchBarragens: null,
      barragemSearch: null,
      barragens: [],
      timeToRefresh: 0,
      chartOptionsDeslocamento: {
        annotations: {
            yaxis: []
        },
        chart: {
          type: 'line',
          shadow: {
            enabled: true,
            color: '#000',
            top: 18,
            left: 7,
            blur: 10,
            opacity: 1
          },
          id: 'monitoramento-barragens-deslocamento',
          toolbar: {
            show: false
          }
        },
        stroke: {
            curve: 'smooth'
          },
        dataLabels: {
          enabled: true,
        },
        title: {
          text: 'Deslocamento do Solo',
          align: 'left'
        },
        markers: {
          size: 6
        },
        xaxis: {
          categories: []
        },
        legend: {
            position: 'top',
            horizontalAlign: 'right',
            floating: true,
            offsetY: -25,
            offsetX: -5
          }
      },
      chartOptionsVolume: {
        annotations: {
            yaxis: []
        },
        chart: {
          type: 'line',
          shadow: {
            enabled: true,
            color: '#000',
            top: 18,
            left: 7,
            blur: 10,
            opacity: 1
          },
          id: 'monitoramento-barragens-volume',
          toolbar: {
            show: false
          }
        },
        stroke: {
            curve: 'smooth'
          },
        dataLabels: {
          enabled: true,
        },
        title: {
          text: 'Volume',
          align: 'left'
        },
        markers: {
          size: 6
        },
        xaxis: {
          categories: []
        },
        legend: {
            position: 'top',
            horizontalAlign: 'right',
            floating: true,
            offsetY: -25,
            offsetX: -5
          }
      },
      seriesDeslocamento: [],
      seriesVolume: []
    }
  },
  computed: {
    isChange: function () {
      return this.$store.getters['solicitacaoMensagemModule/isChangeSolicitacao']
    }
  },
  watch: {
    timeToRefresh (newValue, oldValue) {
      clearInterval(this.timer)
      if (newValue > 0) {
        this.timer = setInterval(this.get, this.timeToRefresh)
      }
    },
    content () {
      this.calculateColorDanoPotecial()
    },  
    searchBarragens (val) {
      // Items have already been requested
      if (this.isLoadingSearchBarragem) return
      this.isLoadingSearchBarragem = true
      // Lazily load input items
      this.monitoramentoBarragensService.getBarragens({ 'descricao': val })
        .then(res => {
          this.barragens = res.content
        })
        .catch(err => {
          console.log(err)
        })
        .finally(() => (this.isLoadingSearchBarragem = false))
    },
    barragemSearch () {
      this.get()
    }
  },
  created () {
    this.monitoramentoBarragensService = new MonitoramentoBarragensService(this.$http)
  },
  methods: {
    get (refreshTime) {
      this.loading = true
      if (refreshTime !== undefined && refreshTime !== null) {
        this.timeToRefresh = refreshTime
      }
      this.monitoramentoBarragensService
        .getMonitoramentos(this.barragemSearch.id)
        .then(dados => {
          this.content = dados
          this.barragem = this.content.barragem.descricao
          this.minerioPrincipal = `Minério Principal ${this.content.barragem.minerioPrincipal.descricao}`
          this.danoPotencial = `Dano Potencial ${this.content.barragem.danoPotencial}`
          this.risco = `Risco ${this.content.barragem.risco}`
          this.metodoConstrutivo = `Método Construtivo ${this.content.barragem.metodoConstrutivo.descricao}`
          this.seriesDeslocamento = []
          this.seriesVolume = []
          this.chartOptionsVolume.annotations.yaxis = []
          this.chartOptionsDeslocamento.annotations.yaxis = []
          this.chartOptionsVolume.xaxis.categories = this.content.monitoramentos.map(a => new Date(a.dataInclusao).getTime())
          this.chartOptionsDeslocamento.xaxis.categories = this.content.monitoramentos.map(a => new Date(a.dataInclusao).getTime())

          this.seriesDeslocamento.push(
          {
            name: 'Deslocamento Solo',
            data: this.content.monitoramentos.map(a => a.deslocamentoSolo)
          })
          this.seriesVolume.push({
            name: 'Volume Atual',
            data: this.content.monitoramentos.map(a => a.volumeAtual)
          })
          this.loading = false
        })
    },
    calculateColorDanoPotecial () {
      if (this.content && this.content.barragem){
        switch (this.content.barragem.danoPotencial) {
          case 'BAIXO':
            return 'subtitle-2 blue--text text--lighten-1 font-weight-bold'
          case 'MEDIO':
            return 'subtitle-2 amber--text text--darken-2 font-weight-bold'
          case 'ALTO':
            return 'subtitle-2 red--text font-weight-bold'
          case 'EXTREMO':
            return 'subtitle-2 red--text text--accent-4 font-weight-bold'
        }
      }
    },
    calculateColorRisco () {
      if (this.content && this.content.barragem){
        switch (this.content.barragem.risco) {
          case 'BAIXO':
            return 'subtitle-2 blue--text text--lighten-1 font-weight-bold'
          case 'MEDIO':
            return 'subtitle-2 amber--text text--darken-2 font-weight-bold'
          case 'ALTO':
            return 'subtitle-2 red--text font-weight-bold'
          case 'CRITICO':
            return 'subtitle-2 red--text text--accent-4 font-weight-bold'
        }
      }
    },
    showTimerSelected () {
      switch (this.timeToRefresh) {
        case 10000:
          return '10s'
        case 30000:
          return '30s'
        case 60000:
          return '1min'
        case 300000:
          return '5min'
        default:
          return ''
      }
    },
    openDialog (item) {
      if (item) {
        this.projectSelected = item
      } else {
        this.projectSelected = null
      }
      this.$store.dispatch('solicitacaoMensagemModule/setChangeSolicitacao', { isChange: true })
    },
    closeDialog () {
      this.$store.dispatch('solicitacaoMensagemModule/setChangeSolicitacao', { isChange: false })
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>