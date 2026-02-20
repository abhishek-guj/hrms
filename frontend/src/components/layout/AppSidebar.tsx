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
	SidebarRail,
} from "@/components/ui/sidebar";
import { useAppSelector } from "../../store/hooks";
import { selectIsAuthenticated } from "../../store/auth/auth.selector";
import { Button } from "../ui/button";
import { useDispatch } from "react-redux";
import { authActions } from "../../store/auth/auth.slice";
import { navLinksList } from "./NavLinks";

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
						<a href={item.url}>{item.title}</a>
					</SidebarMenuButton>
				</SidebarMenuItem>
			))}
		</SidebarMenu>
	);
};

export const UserLoginMenu = () => {
	const handleLogout = () => {
		localStorage.removeItem("token");
		localStorage.removeItem("role");
		localStorage.removeItem("employeeId");
		window.location.href = "/login";
	};

	const {} = useAppSelector(selectIsAuthenticated)

	return (
		<SidebarGroup>
			<SidebarGroupLabel>User</SidebarGroupLabel>
			<SidebarGroupContent>
				<SidebarMenu>
					<SidebarMenuItem>
						<SidebarMenuButton asChild onClick={handleLogout}>
							<Button className="flex justify-start">
								<div>Log out</div>
							</Button>
						</SidebarMenuButton>
					</SidebarMenuItem>
				</SidebarMenu>
			</SidebarGroupContent>
		</SidebarGroup>
	);
};

AppSidebar.AppSidebarMenu = AppSidebarMenu;
