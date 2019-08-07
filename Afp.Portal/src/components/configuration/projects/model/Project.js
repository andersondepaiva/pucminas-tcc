export default class Project {
  constructor (name = null,
    path = null,
    sourceType = null,
    gitAuthentication = null,
    id = null) {
    this.id = id
    this.name = name
    this.path = path
    this.sourceType = sourceType
    this.gitAuthentication = gitAuthentication
  }
}
