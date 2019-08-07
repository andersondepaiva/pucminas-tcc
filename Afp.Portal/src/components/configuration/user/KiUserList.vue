<template>
  <div>
    <v-btn fab dark @click="openDialog()" class="ki-btn-add-grid" color="blue" id="btn_dialog_user">
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
        <td>{{ props.item.pessoa.nome }}</td>
        <td>{{ props.item.login }}</td>
        <td>{{ props.item.tipoUsuario }}</td>
        <td>{{ (!props.item.dataAlteracao ? props.item.dataInclusao : props.item.dataAlteracao) | moment("DD/MM/YYYY HH:mm:ss") }}</td>
        <td>{{ !props.item.alteradoPor ? props.item.incluidoPor.nome : props.item.alteradoPor.nome }}</td>
        <td align="center">
          <v-btn @click="openDialog(props.item)" flat icon color="blue">
              <v-icon>edit</v-icon>
          </v-btn>
          <v-btn @click="deleteUser(props.item)" flat icon color="red">
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
                <v-toolbar-title><v-icon class="ki-icon-title-dialog">folder_open</v-icon>User</v-toolbar-title>
            </v-toolbar>
            <ki-user-edit :user-selected="userSelected"></ki-user-edit>
        </v-card>
    </v-dialog>
  </div>
</template>

<script>
import ConfigurationService from '../services/configuration-service'
import KiUserEdit from './KiUserEdit.vue'
import Pageable from '../../../shared/model/Pageable'

export default {
  components: {
    'ki-user-edit': KiUserEdit
  },
  data () {
    return {
      totalElements: 0,
      content: [],
      loading: true,
      userSelected: null,
      pagination: {},
      headers: [
        { text: 'Name', value: 'name', sortable: false },
        { text: 'Email', value: 'login', sortable: false },
        { text: 'Profile', value: 'tipoUsuario', sortable: false },
        { text: 'Last Modify', value: 'lastModify', sortable: false },
        { text: 'Modified By', value: 'modifiedBy', sortable: false },
        { text: 'Actions', align: 'center', sortable: false }
      ]
    }
  },
  computed: {
    isChange: function () {
      return this.$store.getters['userModule/isChangeUser']
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
      this.service.getUsers(new Pageable(this.pagination.page - 1, this.pagination.rowsPerPage)).then((dados) => {
        this.content = dados.content
        this.totalElements = dados.totalElements
        this.loading = false
      })
    },
    openDialog (item) {
      if (item) {
        this.userSelected = item
      } else {
        this.userSelected = null
      }
      this.$store.dispatch('userModule/setChangeUser', { isChange: true })
    },
    closeDialog () {
      this.$store.dispatch('userModule/setChangeUser', { isChange: false })
    },
    deleteUser (item) {
      this.service.deleteUser(item.id).then(() => {
        this.get()
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
