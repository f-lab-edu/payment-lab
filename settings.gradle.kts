rootProject.name = "payments-lab"

include("app")
include("common")

/** account **/
include("account-api")
include("account-api:account-domain")
findProject(":account-api:account-domain")?.name = "account-domain"
include("account-api:account-application")
findProject(":account-api:account-application")?.name = "account-application"
include("account-api:account-infrastructure")
findProject(":account-api:account-infrastructure")?.name = "account-infrastructure"

/** payment **/
include("payment-api")
include("payment-api:payment-domain")
findProject(":payment-api:payment-domain")?.name = "payment-domain"
include("payment-api:payment-application")
findProject(":payment-api:payment-application")?.name = "payment-application"
include("payment-api:payment-infrastructure")
findProject(":payment-api:payment-infrastructure")?.name = "payment-infrastructure"
