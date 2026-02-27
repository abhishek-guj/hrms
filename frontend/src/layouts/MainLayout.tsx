import { Separator } from "@/components/ui/separator";

import {
	SidebarInset,
	SidebarProvider,
	SidebarTrigger,
} from "@/components/ui/sidebar";

import { Outlet } from "react-router-dom";
import AppBreadcrumb from "../components/layout/AppBreadcrumb";
import { AppSidebar } from "../components/layout/AppSidebar";

const MainLayout = () => {
	return (
		<SidebarProvider className="bg-primary p-1">
			<AppSidebar />
			<SidebarInset className="rounded-2xl w-full max-w-full overflow-hidden">
				<header className="flex h-16 shrink-0 items-center gap-2 border-b px-4">
					{/* showing hamburger only on mobile devices */}
					<div className="flex items-center gap-2 lg:hidden ">
						<SidebarTrigger className="-ml-1" />
						<Separator
							orientation="vertical"
							className="mr-2 data-[orientation=vertical]:h-6"
						/>
					</div>
					<AppBreadcrumb />
				</header>
				<Outlet />
			</SidebarInset>
			{/* </div> */}
		</SidebarProvider>
	);
};

export default MainLayout;
