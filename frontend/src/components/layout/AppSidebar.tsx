import * as React from "react";

import {
	Sidebar,
	SidebarContent,
	SidebarGroup,
	SidebarGroupContent,
	SidebarGroupLabel,
	SidebarHeader,
	SidebarMenu,
	SidebarMenuButton,
	SidebarMenuItem,
} from "../ui/sidebar";

import { LogOut, User, UserCog } from "lucide-react";
import NotificationList from "../notification/NotificationList";
import { Button } from "../ui/button";
import { navLinksList } from "./NavLinks";
import { RoleUtil } from "../../auth/role.util";
import { Link } from "react-router-dom";
import Logo from "../../assets/R.svg";
import { useLocalStorage } from "../../hooks/useLocalStorage";

export const AppSidebar = ({
	...props
}: React.ComponentProps<typeof Sidebar>) => {
	return (
		<Sidebar {...props} collapsible={"icon"}>
			<SidebarHeader>
				<SidebarMenu>
					<SidebarMenuItem>
						<SidebarMenuButton
							asChild
							className="p-0 hover:bg-primary active:bg-primary hover:text-white"
							disabled
						>
							<div className="flex flex-row justify-start items-center px-2 m-0">
								<img
									src={Logo}
									alt="Logo"
									className="w-5 h-5 px-1 text-white"
								/>
								<div className="ml-0.5 text-lg font-bold">HRMS</div>
							</div>
						</SidebarMenuButton>
					</SidebarMenuItem>
				</SidebarMenu>
			</SidebarHeader>
			<AppSideBarContent />
		</Sidebar>
	);
};

export const AppSideBarContent = () => {
	return (
		<SidebarContent className="flex flex-col justify-between py-4">
			<div>
				{navLinksList.navMain.map((item) => (
					<SidebarGroup key={item.title}>
						<SidebarGroupLabel>{item.title}</SidebarGroupLabel>
						<SidebarGroupContent>
							<AppSidebarMenu item={item} />
						</SidebarGroupContent>
					</SidebarGroup>
				))}
			</div>
			<NotificationMenu />
			<UserLoginMenu />
		</SidebarContent>
	);
};

export const AppSidebarMenu = ({ item }) => {
	return (
		<SidebarMenu>
			{item.items.map((item) => (
				<SidebarMenuItem key={item.title}>
					<SidebarMenuButton asChild isActive={item.isActive}>
						<Link to={item.url} className="flex flex-row justify-start items-center block">
							{item.icon}
							{item.title}
						</Link>
					</SidebarMenuButton>
				</SidebarMenuItem>
			))}
		</SidebarMenu>
	);
};

export const NotificationMenu = () => {
	return (
		<SidebarGroup>
			<SidebarGroupLabel>Notification</SidebarGroupLabel>
			<SidebarGroupContent>
				<SidebarMenu>
					<SidebarMenuItem>
						<SidebarMenuButton>
							<NotificationList />
						</SidebarMenuButton>
					</SidebarMenuItem>
				</SidebarMenu>
			</SidebarGroupContent>
		</SidebarGroup>
	);
};
export const UserLoginMenu = () => {
	const { removeItem: removeEmployeeId } = useLocalStorage("employeeId")
	const { removeItem: removeToken } = useLocalStorage("token");
	const { removeItem: removeRole } = useLocalStorage("role");

	const handleLogout = () => {
		removeEmployeeId();
		removeToken();
		removeRole();

		globalThis.location.href = "/login";
	};

	return (
		<SidebarGroup>
			<SidebarGroupLabel>User</SidebarGroupLabel>
			<SidebarGroupContent>
				<SidebarMenu>
					<SidebarMenuItem>
						<SidebarMenuButton asChild>
							<Link
								to={"/my-profile"}
								className="flex flex-row items-center px-3 w-full"
							>
								<User />
								Profile
							</Link>
						</SidebarMenuButton>
						{(RoleUtil.isAdmin || RoleUtil.isHr) && (
							<SidebarMenuButton asChild>
								<Link
									to={"/admin-panel"}
									className="flex flex-row items-center px-3 w-full"
								>
									<UserCog />
									Admin Panel
								</Link>
							</SidebarMenuButton>
						)}
						<SidebarMenuButton asChild onClick={handleLogout}>
							<Button asChild className="flex justify-start cursor-pointer">
								<div className="flex flex-row justify-start items-center">
									<LogOut />
									<div>Log out</div>
								</div>
							</Button>
						</SidebarMenuButton>
					</SidebarMenuItem>
				</SidebarMenu>
			</SidebarGroupContent>
		</SidebarGroup>
	);
};

AppSidebar.AppSidebarMenu = AppSidebarMenu;
