import {
	Breadcrumb,
	BreadcrumbItem,
	BreadcrumbLink,
	BreadcrumbList,
	BreadcrumbPage,
	BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Link, useLocation, useNavigate } from "react-router-dom";

const AppBreadcrumb = () => {
	const location = useLocation();

	const path = location.pathname;

	const crumbs = [{ title: "HRMS", path: "/" }];

	const paths = path.split("/");
	let newPath = "/";
	paths.forEach((p) => {
		if (p !== "") {
			const title =
				p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase();
			newPath += p + "/";
			crumbs.push({ title: title, path: newPath });
		}
	});
	console.log(crumbs);

	return (
		<Breadcrumb>
			<BreadcrumbList>
				{crumbs.map((c, idx) => {
					return (
						<>
							<BreadcrumbItem>
								<BreadcrumbPage>
									<Link to={`${c.path}`}>{c.title}</Link>
								</BreadcrumbPage>
							</BreadcrumbItem>
							{crumbs.length - (idx + 1) !== 0 && (
								<BreadcrumbSeparator className="hidden md:block" />
							)}
						</>
					);
				})}
			</BreadcrumbList>
		</Breadcrumb>
	);
};

export default AppBreadcrumb;
