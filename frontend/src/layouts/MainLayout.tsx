import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";

import { Separator } from "@/components/ui/separator";

import {
  SidebarInset,
  SidebarProvider,
  SidebarTrigger,
} from "@/components/ui/sidebar";

import { AppSidebar } from "../components/layout/AppSidebar";

const MainLayout = () => {
  return (
    <SidebarProvider className="bg-primary p-1">
      <AppSidebar />
      <SidebarInset className="rounded-2xl">
        <header className="flex h-16 shrink-0 items-center gap-2 border-b px-4">
          {/* showing hamburger only on mobile devices */}
          <div className="flex items-center gap-2 lg:hidden ">
            <SidebarTrigger className="-ml-1" />
            <Separator
              orientation="vertical"
              className="mr-2 data-[orientation=vertical]:h-6"
            />
          </div>
          <Breadcrumb>
            <BreadcrumbList>
              <BreadcrumbItem className="hidden md:block">
                <BreadcrumbLink href="#">HRMS</BreadcrumbLink>
              </BreadcrumbItem>
              <BreadcrumbSeparator className="hidden md:block" />
              <BreadcrumbItem>
                <BreadcrumbPage>Dashboard</BreadcrumbPage>
              </BreadcrumbItem>
            </BreadcrumbList>
          </Breadcrumb>
        </header>
        <div className="flex flex-1 flex-col gap-4 p-4">
          <div className="grid auto-rows-min gap-4 md:grid-cols-3">
            <div className="bg-red-100 aspect-video rounded-xl" />
            <div className="bg-green-100 aspect-video rounded-xl" />
            <div className="bg-blue-100 aspect-video rounded-xl" />
          </div>
          <div className="bg-neutral-200 min-h-[100vh] flex-1 rounded-xl md:min-h-min" />
        </div>
      </SidebarInset>
      {/* </div> */}
    </SidebarProvider>
  );
};

export default MainLayout;
