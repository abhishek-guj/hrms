import TravelDocumentTypeSection from "../../components/admin/TravelDocumentTypeSection"
import TravelExpenseTypeSection from "../../components/admin/TravelExpenseTypeSection"
import UserRoleSection from "../../components/admin/UserRoleSection"
import { Separator } from "../../components/ui/separator"

const AdminPanelPage = () => {
	return (
		<div className="h-fit flex flex-col justify-start items-start w-full">
			<UserRoleSection />
			<Separator />
			<TravelDocumentTypeSection />
			<Separator />
			<TravelExpenseTypeSection />
			<Separator />
		</div>
	)
}

export default AdminPanelPage