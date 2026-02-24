import React from "react";
import { Link, NavLink, Outlet } from "react-router-dom";

const PublicLayout = () => {
  return (
    <div>
      <nav className="bg-primary flex justify-between items-center p-1 px-8 text-white">
        <div className="text-2xl font-bold">HRMS</div>
        <div className="flex"></div>
      </nav>
      <Outlet />
    </div>
  );
};

export default PublicLayout;
