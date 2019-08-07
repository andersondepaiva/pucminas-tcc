import Project from '../../../components/configuration/projects/model/Project'

const mutations = {
  destructor (state) {
    state.project = null
    state.isChangeProject = false
  },
  constructor (state, { name, path, sourceType, gitAuthentication, id }) {
    state.project = new Project()
    state.project.name = name
    state.project.sourceType = sourceType
    state.project.gitAuthentication = gitAuthentication
    state.project.path = path
    state.project.id = id
  },
  setChangeProject (state, { isChange }) {
    state.isChangeProject = isChange
  }
}

export default mutations
