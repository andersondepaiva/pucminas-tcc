<template>
  <div>
    <h1 class="ki-title">Monitoramento de Barragens</h1>
    <h2>{{title}}</h2>
    <h3>{{subtitle}}</h3>
    <v-speed-dial
      open-on-hover
      direction="left"
      transition="slide-y-reverse-transition"
      style=" float: right; margin-bottom: 16px !important; margin-top: -64px !important; margin-right: 16px !important;"
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
    <div>
      <apexchart type="line" :options="chartOptions" :series="series"></apexchart>
    </div>
  </div>
</template>

<script>
import MonitoramentoBarragensService from './services/monitoramento-barragens-service'

export default {
  data () {
    return {
      content: [],
      loading: true,
      title: '',
      subtitle: '',
      timeToRefresh: 0,
      chartOptions: {
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
          id: 'monitoramento-barragens',
          toolbar: {
            show: false
          }
        },
        stroke: {
            curve: 'straight'
          },
        dataLabels: {
          enabled: false,
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
      series: []
    }
  },
  computed: {
    isQueueExecution: function () {
      return this.$store.getters['katalonModule/isQueueExecution']
    }
  },
  watch: {
    timeToRefresh (newValue, oldValue) {
      clearInterval(this.timer)
      if (newValue > 0) {
        this.timer = setInterval(this.get, this.timeToRefresh)
      }
    }
  },
  created () {
    this.monitoramentoBarragensService = new MonitoramentoBarragensService(this.$http)
    this.get(null);
  },
  methods: {
    get (refreshTime) {
      this.loading = true
      if (refreshTime !== undefined && refreshTime !== null) {
        this.timeToRefresh = refreshTime
      }
      this.monitoramentoBarragensService
        .getMonitoramentos()
        .then(dados => {
          this.content = dados
          this.series = []
          this.title = `Monitoramento ${this.content.barragem.descricao}`
          this.subtitle = `Minério Principal ${this.content.barragem.minerioPrincipal.descricao}`
          this.chartOptions.annotations.yaxis = []
          this.chartOptions.xaxis.categories = this.content.monitoramentos.map(a => new Date(a.dataInclusao).getTime())

          this.chartOptions.annotations.yaxis.push({
              y: this.content.barragem.volume,
              borderColor: '#00E396',
              label: {
                borderColor: '#00E396',
                style: {
                  color: '#fff',
                  background: '#00E396',
                },
                text: 'Volume',
              }
          })

          this.series.push(
          {
            name: 'Deslocamento Solo',
            data: this.content.monitoramentos.map(a => a.deslocamentoSolo)
          })
          this.series.push({
            name: 'Volume Atual',
            data: this.content.monitoramentos.map(a => a.volumeAtual)
          })
          this.loading = false
        })
    },
    calculateColor (item) {
      switch (item.statusExecution) {
        case 'QUEUED':
          return 'blue lighten-1'
        case 'EXECUTING':
          return 'amber darken-2'
        case 'SUCCESS':
          return 'green'
        case 'FAIL':
          return 'red'
        default:
          return 'blue lighten-1'
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
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>