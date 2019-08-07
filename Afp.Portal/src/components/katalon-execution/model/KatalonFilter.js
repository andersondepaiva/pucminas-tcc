import Pageable from '../../../shared/model/Pageable'

export default class KatalonFilter extends Pageable {
  constructor (page = 0, size = 20, project = null,
    requestedBy = null) {
    super(page, size)
    this.project = project
    this.requestedBy = requestedBy
  }
}
