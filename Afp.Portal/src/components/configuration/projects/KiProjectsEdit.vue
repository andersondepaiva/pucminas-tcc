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
                v-model="project.name"
                label="Name"
                required
                max-length="255"
                :rules="nomeValidator"
              ></v-text-field>
            </v-flex>
            <v-flex xs12>
              <v-select
                v-model="project.sourceType"
                :items="sourcesType"
                :rules="[v => !!v || 'Source Type is required']"
                label="Source Type"
                item-text="descricao"
                item-value="id"
                @change="verifyIsGit()"
                required
              ></v-select>
            </v-flex>
            <v-flex
              xs12
              v-if="!isSourceTypeGit"
            >
              <v-text-field
                v-model="project.path"
                label="Path Project"
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
              <div
                id="group_git_authenticaiton"
                style="margin-top:32px"
                v-if="isSourceTypeGit"
              >
                <v-toolbar
                  card
                  color="orange accent-1"
                >
                  <v-toolbar-title>
                    <v-icon>cloud_circle</v-icon>
                    GIT Authentication
                  </v-toolbar-title>
                </v-toolbar>
                <v-divider style="margin-bottom:16px"></v-divider>
                <v-flex xs12>
                  <v-text-field
                    v-model="gitAuthentication.uri"
                    label="HTTPS Git"
                    :rules="uriValidator"
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-model="gitAuthentication.user"
                    label="User"
                    browser-autocomplete="off"
                    :rules="userValidator"
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    type="password"
                    v-model="gitAuthentication.password"
                    browser-autocomplete="off"
                    label="Password"
                    :rules="passwordValidator"
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-btn
                    :loading="loading"
                    color="success"
                    id="btn_connect"
                    @click="connect()"
                    :disabled="!gitAuthentication.user || !gitAuthentication.password || !gitAuthentication.uri || project.id != null"
                  >
                    Get Branches
                    <v-icon right dark>flash_on</v-icon>
                  </v-btn>
                </v-flex>
                <v-flex xs12>
                  <v-select
                    v-model="gitAuthentication.branch"
                    :items="branches"
                    label="Branch"
                    item-text="item"
                    item-value="item"
                    return-object
                    :rules="branchValidator"
                    :disabled="project.id != null || branches.length === 0"
                  ></v-select>
                </v-flex>
              </div>
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
import Project from './model/Project'
import GitAuthentication from './model/GitAuthentication'

export default {
  props: {
    projectSelected: {
      type: Object,
      required: false
    }
  },
  data () {
    return {
      valid: false,
      loading: false,
      project: new Project(),
      gitAuthentication: new GitAuthentication(),
      isSourceTypeGit: true,
      branches: [],
      connected: false,
      nomeValidator: [v => !!v || 'Name is required'],
      userValidator: [v => (!!v && this.isSourceTypeGit) || 'User is required'],
      uriValidator: [
        v => (!!v && this.isSourceTypeGit) || 'Https Git is required'
      ],
      passwordValidator: [
        v => (!!v && this.isSourceTypeGit) || 'Password is required'
      ],
      pathValidator: [
        v => (!!v && !this.isSourceTypeGit) || 'Path is required'
      ],
      branchValidator: [
        v => (!!v && this.isSourceTypeGit) || 'Branch is required'
      ],
      sourcesType: [
        {
          id: 'FILE_SYSTEM',
          descricao: 'File System'
        },
        {
          id: 'GIT',
          descricao: 'GIT'
        }
      ]
    }
  },
  computed: {
    isChange: function () {
      return this.$store.getters['projectModule/isChangeProject']
    }
  },
  watch: {
    isChange (newValue, oldValue) {
      if (!newValue) {
        this.initData()
      } else {
        if (this.projectSelected) {
          this.service.getProjectById(this.projectSelected.id).then(res => {
            this.project = res

            if (this.project.gitAuthentication) {
              this.passwordOriginal = this.project.gitAuthentication.password
            }

            if (res.gitAuthentication) {
              this.gitAuthentication = res.gitAuthentication
              this.branches.push(this.gitAuthentication.branch)
            }

            this.verifyIsGit()
          })
        }
      }
    }
  },
  created () {
    this.service = new ConfigurationService(this.$http)
    this.project.sourceType = 'GIT'
  },
  methods: {
    verifyIsGit () {
      this.isSourceTypeGit = this.project.sourceType === 'GIT'
    },
    connect () {
      if (this.isSourceTypeGit) {
        if (this.gitAuthentication.uri && this.gitAuthentication.password && this.gitAuthentication.user) {
          this.loading = true
          this.service.getBranches(new GitAuthentication(this.gitAuthentication.uri, this.gitAuthentication.user, btoa(this.gitAuthentication.password))).then(
            (dado) => {
              this.gitAuthentication.branch = null
              this.branches = dado
              this.loading = false
            },
            () => {
              this.loading = false
            }
          )
        }
      }
    },
    salvar () {
      if (this.valid) {
        if (this.isSourceTypeGit) {
          this.project.gitAuthentication = this.gitAuthentication

          this.project.gitAuthentication.password =
            this.passwordOriginal !== this.project.gitAuthentication.password
              ? btoa(this.project.gitAuthentication.password)
              : this.project.gitAuthentication.password
        } else {
          this.project.gitAuthentication = null
        }
        this.loading = true
        this.service.mergeProject(this.project).then(
          () => {
            this.initData()
            this.$store.dispatch('projectModule/destructor')
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
      this.project = new Project()
      this.gitAuthentication = new GitAuthentication()
      this.isSourceTypeGit = true
      this.project.sourceType = 'GIT'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
