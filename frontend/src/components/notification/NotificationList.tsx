import React from "react";
import { useNotificationALL, useRead } from "./notification.quer";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../ui/dialog";
import { EyeIcon } from "lucide-react";
import { api } from "../../api/apiClient";
import { showInfo, showSuccess } from "../ui/toast";
import queryClient from "../../api/queryClient";
import { useQueries, useQueryClient } from "@tanstack/react-query";
import { Badge } from "../ui/badge";

const NotificationList = () => {
  const { data, isLoading, error } = useNotificationALL();
  //   //
  //   if (isLoading) {
  //     return (
  //       <div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
  //         Loading
  //       </div>
  //     );
  //   }

  //   //

  //   if (error) {
  //     return (
  //       <div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
  //         No Data Found...
  //       </div>
  //     );
  //   }
  //

  return (
    <Dialog>
      <DialogTrigger className="w-full flex justify-start h-full  ">
        <div className="flex justify-between gap-10 items-center">
          <div>Notifications</div>
          <Badge className="bg-red-500 text-white h-4 w-4 rounded-full">
            {data?.length}
          </Badge>
        </div>
      </DialogTrigger>
      <DialogContent className="w-fit min-w-96 max-w-4/5">
        <DialogHeader>
          <DialogTitle>All unread Notifications.</DialogTitle>
          <DialogDescription>
            <NotficationBox notifications={data} />
          </DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
};

export default NotificationList;

function NotficationBox({ notifications }) {
  return (
    <div className="p-4">
      {notifications?.map((noti) => {
        return <NotificationItem key={noti.id} notification={noti} />;
      })}
    </div>
  );
}

function NotificationItem({ notification }) {
  // tmp
  const readNotify = useRead();
  const handleClick = async () => {
    readNotify.mutateAsync({ id: notification?.id });
  };

  return (
    <div className="flex gap-3 px-4 py-3 border-primary font-bold w-max max-w-96 h-fit bg-primary/10 justify-center items-center">
      <div className="w-full">{notification?.content}</div>
      <div
        onClick={handleClick}
        className="cursor-pointer flex justify-center items-center w-10 h-9 p-1 bg-primary text-white rounded-full"
      >
        <EyeIcon />
      </div>
    </div>
  );
}
