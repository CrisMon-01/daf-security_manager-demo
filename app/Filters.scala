/**
  * Created by ale on 06/06/17.
  */
import javax.inject.Inject

import it.gov.***REMOVED***.Filter.CredentialFilter
import it.gov.***REMOVED***.common.filters.authentication.SecurityFilter
import play.api.***REMOVED***.DefaultHttpFilters
import play.filters.cors.CORSFilter

class Filters @Inject()(corsFilter: CORSFilter, securityFilter :SecurityFilter, credentialFilter: CredentialFilter)
  extends DefaultHttpFilters(corsFilter, securityFilter, credentialFilter)
