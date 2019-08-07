import Katalon from '../../../components/katalon-execution/model/Katalon'

const mutations = {
  destructor (state) {
    state.katalon = null
    state.isQueueExecution = false
  },
  constructor (state, { project, profile, testSuite }) {
    state.katalon = new Katalon(project, profile, testSuite)
  },
  setQueueExecution (state, { isQueueExecution }) {
    state.isQueueExecution = isQueueExecution
  }
}

export default mutations
