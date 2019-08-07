<template>
  <div>
    <v-btn fab dark @click="openDialog()" class="ki-btn-add-grid" color="blue" id="btn_dialog_project">
      <v-icon dark>add</v-icon>
    </v-btn>
    <v-data-table
      :headers="headers"
      :items="content"
      :pagination.sync="pagination"
      :total-items="totalElements"
      :loading="loading"
      class="elevation-1"
    >
      <template slot="items" slot-scope="props">
        <td>{{ props.item.name }}</td>
        <td>{{ props.item.sourceType }}</td>
        <td>{{ (!props.item.dataAlteracao ? props.item.dataInclusao : props.item.dataAlteracao) | moment("DD/MM/YYYY HH:mm:ss") }}</td>
        <td>{{ !props.item.alteradoPor ? props.item.incluidoPor.nome : props.item.alteradoPor.nome }}</td>
        <td align="center">
          <v-btn @click="openDialog(props.item)" flat icon color="blue">
              <v-icon>edit</v-icon>
          </v-btn>
          <v-btn @click="deleteProject(props.item)" flat icon color="red">
              <v-icon>delete</v-icon>
           </v-btn>
        </td>
      </template>
    </v-data-table>
    <v-dialog v-model="isChange" fullscreen transition="dialog-bottom-transition" :overlay="false">
        <v-card>
            <v-toolbar dark color="primary">
                <v-btn icon @click.native="closeDialog()" dark>
                    <v-icon>close</v-icon>
                </v-btn>
                <v-toolbar-title><v-icon class="ki-icon-title-dialog">folder_open</v-icon>Project</v-toolbar-title>
            </v-toolbar>
            <ki-projects-edit :project-selected="projectSelected"></ki-projects-edit>
        </v-card>
    </v-dialog>
  </div>
</template>

<script>
import ConfigurationService from '../services/configuration-service'
import KiProjectsEdit from './KiProjectsEdit.vue'
import Pageable from '../../../shared/model/Pageable'

export default {
  components: {
    'ki-projects-edit': KiProjectsEdit
  },
  data () {
    return {
      totalElements: 0,
      content: [],
      loading: true,
      projectSelected: null,
      pagination: {},
      headers: [
        { text: 'Name', value: 'name', sortable: false },
        { text: 'Source Type', value: 'sourceType', sortable: false },
        { text: 'Last Modify', value: 'lastModify', sortable: false },
        { text: 'Modified By', value: 'modifiedBy', sortable: false },
        { text: 'Actions', align: 'center', sortable: false }
      ]
    }
  },
  computed: {
    isChange: function () {
      return this.$store.getters['projectModule/isChangeProject']
    }
  },
  watch: {
    pagination: {
      handler () {
        this.get()
      },
      deep: true
    },
    isChange (newValue, oldValue) {
      if (!newValue) {
        this.get()
      }
    }
  },
  created () {
    this.service = new ConfigurationService(this.$http)
  },
  methods: {
    get () {
      this.loading = true
      this.service.getProjects(new Pageable(this.pagination.page - 1, this.pagination.rowsPerPage)).then((dados) => {
        this.content = dados.content
        this.totalElements = dados.totalElements
        this.loading = false
      })
    },
    openDialog (item) {
      if (item) {
        this.projectSelected = item
      } else {
        this.projectSelected = null
      }
      this.$store.dispatch('projectModule/setChangeProject', { isChange: true })
    },
    closeDialog () {
      this.$store.dispatch('projectModule/setChangeProject', { isChange: false })
    },
    deleteProject (item) {
      this.service.deleteProject(item.id).then(() => {
        this.get()
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
