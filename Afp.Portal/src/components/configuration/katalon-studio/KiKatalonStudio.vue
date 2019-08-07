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
            <v-flex xs12>
              <v-text-field
                v-model="katalonStudio.path"
                label="Path Katalon Studio"
                required
                :rules="pathValidator"
              >
                <v-tooltip
                  bottom
                  color="warning"
                  slot="append"
                >
                  <v-icon
                    slot="activator"
                    color="warning"
                    dark
                    class="ki-icon-title-dialog"
                  >warning</v-icon>
                  <span slot="activator">Make sure the application has access to this directory</span>
                </v-tooltip>
              </v-text-field>
            </v-flex>
            <v-flex xs12>
              <v-text-field
                v-model="katalonStudio.workPath"
                label="Work Path"
                required
                :rules="workPathValidator"
              >
                <v-tooltip
                  bottom
                  color="warning"
                  slot="append"
                >
                  <v-icon
                    slot="activator"
                    color="warning"
                    dark
                    class="ki-icon-title-dialog"
                  >warning</v-icon>
                  <span slot="activator">Make sure the application has access to this directory</span>
                </v-tooltip>
              </v-text-field>
            </v-flex>
            <v-flex xs12>
              <v-text-field
                v-model="katalonStudio.lastModify"
                label="Last Modify"
                disabled
                v-if="katalonStudio.id !== null && katalonStudio.id !== undefined"
              >
              </v-text-field>
            </v-flex>
            <v-flex xs12>
              <v-text-field
                v-model="katalonStudio.modifiedBy"
                label="Modified By"
                disabled
                v-if="katalonStudio.id !== null && katalonStudio.id !== undefined"
              >
              </v-text-field>
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
import KatalonStudio from './model/KatalonStudio'

export default {
  data () {
    return {
      valid: false,
      loading: false,
      katalonStudio: new KatalonStudio(),
      pathValidator: [v => !!v || 'Path Katalon Studio is required'],
      workPathValidator: [v => !!v || 'Work Path is required']
    }
  },
  created () {
    this.service = new ConfigurationService(this.$http)
    this.get()
  },
  methods: {
    salvar () {
      if (this.valid) {
        this.loading = true
        this.service.mergeKatalonStudio(this.katalonStudio).then(
          () => {
            this.loading = false
            this.$store.dispatch('snackBarModule/showMessageSuccess', {
              text: 'Save with Success'
            })
            this.get()
          },
          () => {
            this.loading = false
          }
        )
      }
    },
    get () {
      this.service.getKatalonStudioParameters().then(res => {
        if (res) {
          this.katalonStudio = res
          this.katalonStudio.lastModify = this.$moment((res.dataAlteracao === null ? res.dataInclusao : res.dataAlteracao)).format('DD/MM/YYYY HH:mm:ss')
          this.katalonStudio.modifiedBy = !res.alteradoPor ? res.incluidoPor.nome : res.alteradoPor.nome
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
