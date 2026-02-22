import React from "react";
import { Card, CardContent, CardTitle } from "../../ui/card";
import { Trash2 } from "lucide-react";
import { Button } from "../../ui/button";
import { RoleUtil } from "../../../auth/role.util";

const AssignedEmployeeCard = ({
  emp,
  handleRemove,
  showDesignation = true,
  disableRemove = false,
}) => {
  return (
    <Card key={emp.id} className="w-full p-1 gap-1">
      <CardContent className="">
        {/* <div className="flex gap-1">
						<span className="text-black/70">Name:</span>
						<span>{emp.name}</span>
						</div> */}

        <div className="flex justify-between items-center">
          <div>
            <div className="flex gap-1">
              <span className="text-black/70">Name:</span>
              <span>{emp.name}</span>
            </div>
            {showDesignation && (
              <div className="flex gap-1">
                <span className="text-black/70">Designation:</span>
                <span>need to add in dto</span>
              </div>
            )}
          </div>
          {!disableRemove && (RoleUtil.isAdmin || RoleUtil.isHr) && (
            <div
              onClick={() => {
                handleRemove(emp.id);
              }}
              className="w-8 h-8 grid items-center p-2 rounded-full bg-primary text-white"
            >
              <Trash2 className="h-full w-full m-0" />
            </div>
          )}
        </div>
      </CardContent>
    </Card>
  );
};

export default AssignedEmployeeCard;
