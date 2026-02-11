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

// This is sample data.
const data = {
  versions: ["1.0.1", "1.1.0-alpha", "2.0.0-beta1"],
  navMain: [
    {
      title: "Travel",
      url: "#",
      items: [
        {
          title: "Travel Plans",
          url: "#",
        },
        {
          title: "Travel Documents",
          url: "#",
        },
        {
          title: "Travel Expenses",
          url: "#",
        },
      ],
    },
    {
      title: "Game",
      url: "#",
      items: [
        {
          title: "Book Game",
          url: "#",
        },
        {
          title: "Check Games",
          url: "#",
        },
      ],
    },
    {
      title: "Job",
      url: "#",
      items: [
        {
          title: "View Jobs",
          url: "#",
        },
        {
          title: "Reffrals",
          url: "#",
        },
      ],
    },
    {
      title: "Organization",
      url: "#",
      items: [
        {
          title: "Org Chart",
          url: "#",
        },
      ],
    },
  ],
};

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar {...props}>
      <SidebarHeader>
        {/* <VersionSwitcher
					versions={data.versions}
					defaultVersion={data.versions[0]}
				/> */}
        <div className="flex place-content-center text-2xl font-bold">HRMS</div>
      </SidebarHeader>
      <SidebarContent>
        {/* We create a SidebarGroup for each parent. */}
        {data.navMain.map((item) => (
          <SidebarGroup key={item.title}>
            <SidebarGroupLabel>{item.title}</SidebarGroupLabel>
            <SidebarGroupContent>
              <SidebarMenu>
                {item.items.map((item) => (
                  <SidebarMenuItem key={item.title}>
                    <SidebarMenuButton asChild isActive={item.isActive}>
                      <a href={item.url}>{item.title}</a>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                ))}
              </SidebarMenu>
            </SidebarGroupContent>
          </SidebarGroup>
        ))}
      </SidebarContent>
    </Sidebar>
  );
}
