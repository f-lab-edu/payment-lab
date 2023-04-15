rootProject.name = "payments-lab"

include("app")
include("common")

include("account-api")
include("account-api:account-domain")
findProject(":account-api:account-domain")?.name = "account-domain"
include("account-api:account-application")
findProject(":account-api:account-application")?.name = "account-application"
include("account-api:account-infrastructure")
findProject(":account-api:account-infrastructure")?.name = "account-infrastructure"
