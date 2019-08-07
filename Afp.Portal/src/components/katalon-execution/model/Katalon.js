export default class Katalon {
  constructor (project = null,
    profile = null,
    testSuite = null) {
    this.project = project
    this.profile = profile
    this.testSuite = testSuite
  }
}
