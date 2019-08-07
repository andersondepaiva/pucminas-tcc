<template>
  <div>
    <v-form v-model="valid" @submit.prevent="salvar()">
      <v-card flat color="white">
        <v-card-text>
           <v-container grid-list-md>
             <v-flex xs12 >
              <v-select
                v-model="katalon.project"
                :items="projects"
                :rules="[v => !!v || 'Project is required']"
                label="Project"
                item-text="name"
                item-value="id"
                return-object
                @change="loadResourcesFromProject()"
                required
              ></v-select>
             </v-flex>
             <v-flex xs12 >
              <v-select
                v-model="katalon.testSuite"
                :items="testSuites"
                :rules="[v => !!v || 'Test Suite is required']"
                label="Test Suite"
                item-text="name"
                item-value="name"
                return-object
                v-if="katalon.project != null && katalon.project != undefined"
                required
              ></v-select>
             </v-flex>
             <v-flex xs12 >
              <v-select
                v-model="katalon.profile"
                :items="profiles"
                :rules="[v => !!v || 'Profile is required']"
                label="Profile"
                item-text="name"
                item-value="name"
                return-object
                v-if="katalon.project != null && katalon.project != undefined"
                required
              ></v-select>
             </v-flex>
             <v-flex xs12 >
              <v-select
                v-model="katalon.browser"
                :items="browsers"
                :rules="[v => !!v || 'Browser is required']"
                label="Browser"
                item-text="item"
                item-value="item"
                return-object
                v-if="katalon.project != null && katalon.project != undefined"
                required
              ></v-select>
             </v-flex>
           </v-container>
        </v-card-text>
        <v-card-actions>
             <v-container grid-list-md>
                <v-btn
                  type="submit"
                  :disabled="!valid || loading"
                  :loading="loading"
                  color="primary"
                  id="btn_send_execution">
                Send
                </v-btn>
             </v-container>
        </v-card-actions>
      </v-card>
    </v-form>
  </div>
</template>

<script>
import KatalonExecutionService from './services/katalon-execution-service'
import ConfigurationService from '../configuration/services/configuration-service.js'
import Katalon from './model/Katalon'

export default {
  data () {
    return {
      katalon: new Katalon(),
      projects: [],
      testSuites: [],
      profiles: [],
      browsers: [],
      valid: false,
      loading: false
    }
  },
  created () {
    this.service = new KatalonExecutionService(this.$http)
    this.projectService = new ConfigurationService(this.$http)
  },
  mounted () {
    this.projectService.getProjects().then((dados) => {
      this.projects = dados.content
    })
  },
  methods: {
    loadResourcesFromProject () {
      this.projectService.getProjectItemsById(this.katalon.project.id).then((dados) => {
        this.testSuites = dados.testSuites
        this.profiles = dados.profiles
        this.browsers = dados.browsers
      })
    },
    salvar () {
      if (this.valid) {
        this.loading = true
        this.service.queueExecution(this.katalon).then(() => {
          this.initData()
          this.$store.dispatch('katalonModule/destructor')
          this.$store.dispatch('snackBarModule/showMessageSuccess', { text: 'Queued Execution with Success' })
        }, () => {
          this.loading = false
        })
      }
    },
    initData () {
      this.katalon = new Katalon()
      this.testSuites = []
      this.profiles = []
      this.valid = false
      this.loading = false
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
