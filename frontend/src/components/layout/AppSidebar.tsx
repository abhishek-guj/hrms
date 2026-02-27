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
	SidebarMenuItem
} from "@/components/ui/sidebar";
import { LogOut, UserCog } from "lucide-react";
import NotificationList from "../notification/NotificationList";
import { Button } from "../ui/button";
import { navLinksList } from "./NavLinks";
import { RoleUtil } from "../../auth/role.util";
import { Link } from "react-router-dom";

export const AppSidebar = ({
	...props
}: React.ComponentProps<typeof Sidebar>) => {
	return (
		<Sidebar {...props}>
			<SidebarHeader>
				<div className="flex place-content-center text-2xl font-bold">HRMS</div>
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
						<div className="flex flex-row justify-start items-center">
							{item.icon}
							<a href={item.url}>{item.title}</a>
						</div>
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
	const handleLogout = () => {
		localStorage.removeItem("token");
		localStorage.removeItem("role");
		localStorage.removeItem("employeeId");
		globalThis.location.href = "/login";
	};

	return (
		<SidebarGroup>
			<SidebarGroupLabel>User</SidebarGroupLabel>
			<SidebarGroupContent>
				<SidebarMenu>
					<SidebarMenuItem>
						{(RoleUtil.isAdmin || RoleUtil.isHr) &&
							<SidebarMenuButton asChild>
								{/* <div > */}
								<Link to={"/admin-panel"} className="flex flex-row items-center px-3 w-full">
									<UserCog />
									Admin Panel
								</Link>
								{/* </div> */}
							</SidebarMenuButton>
						}
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
