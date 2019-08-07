<template>
  <div>
    <h1 class="ki-title">Executions</h1>
    <v-autocomplete
        style="float: left; margin-top:24px; margin-left:16px"
        v-model="projectSearch"
        :items="projects"
        :loading="isLoadingSearchProject"
        :search-input.sync="searchProjects"
        hide-no-data
        hide-selected
        clearable
        item-text="name"
        item-value="id"
        label="Project"
        placeholder="Start typing to Search"
        prepend-icon="search"
        return-object
    ></v-autocomplete>
    <v-autocomplete
        style="float: left; margin-top:24px; margin-left:16px"
        v-model="userSearch"
        :items="users"
        :loading="isLoadingSearchUser"
        :search-input.sync="searchUsers"
        hide-no-data
        hide-selected
        item-text="pessoa.nome"
        item-value="id"
        label="User"
        clearable
        placeholder="Start typing to Search"
        prepend-icon="search"
        return-object
    ></v-autocomplete>
    <v-btn
      fab
      dark
      @click="openDialog()"
      class="ki-btn-add-grid"
      color="blue"
      id="btn_dialog_katalon_execution"
    >
      <v-icon dark>add</v-icon>
    </v-btn>
    <v-speed-dial
      open-on-hover
      direction="left"
      transition="slide-y-reverse-transition"
      class="ki-btn-add-grid"
    >
      <v-btn
        slot="activator"
        style="margin-top: 0px;"
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
    <v-data-table
      :headers="headers"
      :items="content"
      :pagination.sync="pagination"
      :total-items="totalElements"
      :loading="loading"
      class="elevation-1"
    >
      <template
        slot="items"
        slot-scope="props"
      >
        <td>{{ props.item.dataInclusao | moment("DD/MM/YYYY HH:mm:ss") }}</td>
        <td>{{ props.item.dataAlteracao | moment("DD/MM/YYYY HH:mm:ss") }}</td>
        <td>{{ props.item.incluidoPor ? props.item.incluidoPor.nome : 'Undefined' }}</td>
        <td>{{ props.item.project.name }}</td>
        <td>{{ props.item.profile.name }}</td>
        <td>{{ props.item.testSuite.name }}</td>
        <td>{{ props.item.browser }}</td>
        <td
          class="ki-td-font-style"
          align="center"
        >
          <v-chip
            :color="calculateColor(props.item)"
            text-color="white"
          ><b>{{ props.item.statusExecution }}</b></v-chip>
        </td>
        <td align="center">
          <v-btn
            v-if="showButtonLogFile(props.item)"
            :loading="downloadLog"
            @click="getLogFile(props.item.id)"
            flat
            icon
            color="cyan darken-4"
          >
            <v-icon>file_copy</v-icon>
          </v-btn>
        </td>
        <td align="center">
          <v-btn
            v-if="showButtonReportTest(props.item)"
            :loading="downloadLog"
            @click="getReportTest(props.item.id)"
            flat
            icon
            color="cyan darken-4"
          >
            <v-icon>assessment</v-icon>
          </v-btn>
        </td>
      </template>
    </v-data-table>
    <v-dialog
      v-model="isQueueExecution"
      fullscreen
      transition="dialog-bottom-transition"
      :overlay="false"
    >
      <v-card>
        <v-toolbar
          dark
          color="primary"
        >
          <v-btn
            icon
            @click.native="closeDialog()"
            dark
          >
            <v-icon>close</v-icon>
          </v-btn>
          <v-toolbar-title>
            <v-icon class="ki-icon-title-dialog">playlist_add</v-icon>Queue Execution
          </v-toolbar-title>
        </v-toolbar>
        <ki-execution></ki-execution>
      </v-card>
    </v-dialog>
    <v-dialog
      v-model="isViewLogFile"
      fullscreen
      scrollable
      transition="dialog-bottom-transition"
      :overlay="false"
    >
      <v-card
        dark
        class="mx-auto"
      >
        <v-toolbar
          dark
          color="primary"
          card
          prominent
        >
          <v-btn
            icon
            @click.native="isViewLogFile = false"
            dark
          >
            <v-icon>close</v-icon>
          </v-btn>
          <v-toolbar-title>
            <v-icon class="ki-icon-title-dialog">file_copy</v-icon>Log File
          </v-toolbar-title>
        </v-toolbar>
        <v-divider></v-divider>
        <v-card-text dark>
          <v-textarea
            solo
            dark
            readonly
            label="Log File"
            :value="logFileText"
            auto-grow
          ></v-textarea>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import KatalonExecutionService from './services/katalon-execution-service'
import KiExecution from './KiExecution.vue'
import ConfigurationService from '../configuration/services/configuration-service'
import KatalonFilter from './model/KatalonFilter'

export default {
  components: {
    'ki-execution': KiExecution
  },
  data () {
    return {
      totalElements: 0,
      content: [],
      loading: true,
      logFileText: null,
      isViewLogFile: false,
      downloadLog: false,
      timeToRefresh: 0,
      projectSearch: null,
      userSearch: null,
      searchProjects: null,
      searchUsers: null,
      isLoadingSearchProject: false,
      isLoadingSearchUser: false,
      projects: [],
      users: [],
      pagination: {},
      headers: [
        { text: 'Requested In', value: 'dataInclusao', sortable: false },
        { text: 'Last Update', value: 'dataAlteracao', sortable: false },
        { text: 'Requested By', value: 'inseridoPor', sortable: false },
        { text: 'Project', value: 'project', sortable: false },
        { text: 'Profile', value: 'profile', sortable: false },
        { text: 'Test Suite', value: 'testSuite', sortable: false },
        { text: 'Browser', value: 'browser', sortable: false },
        { text: 'Execution Status', value: 'statusExecution', sortable: false },
        { text: 'Log File', value: 'logFile', sortable: false },
        { text: 'Test Report', value: 'logFile', sortable: false }
      ]
    }
  },
  computed: {
    isQueueExecution: function () {
      return this.$store.getters['katalonModule/isQueueExecution']
    }
  },
  watch: {
    pagination: {
      handler () {
        this.get()
      },
      deep: true
    },
    isQueueExecution (newValue, oldValue) {
      if (!newValue) {
        this.get()
      }
    },
    timeToRefresh (newValue, oldValue) {
      clearInterval(this.timer)
      if (newValue > 0) {
        this.timer = setInterval(this.get, this.timeToRefresh)
      }
    },
    searchProjects (val) {
      // Items have already been requested
      if (this.isLoadingSearchProject) return

      this.isLoadingSearchProject = true

      // Lazily load input items
      this.configurationService.getProjects({ 'name': val })
        .then(res => {
          this.projects = res.content
        })
        .catch(err => {
          console.log(err)
        })
        .finally(() => (this.isLoadingSearchProject = false))
    },
    searchUsers (val) {
      // Items have already been requested
      if (this.isLoadingSearchUser) return

      this.isLoadingSearchUser = true

      // Lazily load input items
      this.configurationService.getUsers({ 'nome': val })
        .then(res => {
          this.users = res.content
        })
        .catch(err => {
          console.log(err)
        })
        .finally(() => (this.isLoadingSearchUser = false))
    }
  },
  created () {
    this.service = new KatalonExecutionService(this.$http)
    this.configurationService = new ConfigurationService(this.$http)
  },
  methods: {
    get (refreshTime) {
      this.loading = true
      if (refreshTime !== undefined && refreshTime !== null) {
        this.timeToRefresh = refreshTime
      }

      this.service
        .get(
          this.buildParameters()
        )
        .then(dados => {
          this.content = dados.content
          this.totalElements = dados.totalElements
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
    buildParameters () {
      let params = new KatalonFilter(this.pagination.page - 1, this.pagination.rowsPerPage)
      if (this.userSearch) {
        params.requestedBy = this.userSearch.id
      }

      if (this.projectSearch) {
        params.project = this.projectSearch.id
      }

      return params
    },
    openDialog () {
      this.$store.dispatch('katalonModule/setQueueExecution', {
        isQueueExecution: true
      })
    },
    closeDialog () {
      this.$store.dispatch('katalonModule/setQueueExecution', {
        isQueueExecution: false
      })
    },
    getReportTest (id) {
      this.downloadLog = true
      this.service.getReportTestExecution(id).then(
        res => {
          let link = document.createElement('a')
          link.href = URL.createObjectURL(res.blobFile)
          link.download = res.fileName
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link)
          this.downloadLog = false
        },
        () => {
          this.downloadLog = false
        }
      )
    },
    getLogFile (id) {
      this.service.getLogFileExecution(id).then(logFile => {
        this.isViewLogFile = true
        this.logFileText = logFile
      })
    },
    showButtonLogFile (item) {
      return item.pathLogFile !== null && item.pathLogFile !== undefined
    },
    showButtonReportTest (item) {
      return item.pathReportTest !== null && item.pathReportTest !== undefined
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
