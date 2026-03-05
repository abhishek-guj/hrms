import { Bell, EyeIcon } from "lucide-react";
import { Badge } from "../ui/badge";
import { Button } from "../ui/button";
import {
	Dialog,
	DialogContent,
	DialogDescription,
	DialogHeader,
	DialogTitle,
	DialogTrigger,
} from "../ui/dialog";
import { useNotificationALL, useRead } from "./notification.queries";
import Notification from "./Notifications";




const NotificationList = () => {
	const { data, isLoading, error } = useNotificationALL();
	if (isLoading) {
		return (
			<Button
				variant={"default"}
				className="w-full flex justify-start flex-row items-center pl-1 m-0 hover:bg-white hover:text-black">
				<div className="flex flex-row justify-between items-center w-fit p-0 m-0 ">
					<div className="flex flex-row justify-start items-center gap-1.5 p-0 m-0">
						<Bell className="h-8 w-8 p-0.5" />
						<div>Notifications</div>
					</div>
				</div>
			</Button >
		);
	}

	//

	if (error) {
		return (
			<Button
				variant={"default"}
				className="w-full flex justify-start flex-row items-center pl-1 m-0 hover:bg-white hover:text-black">
				<div className="flex flex-row justify-between items-center w-fit p-0 m-0 ">
					<div className="flex flex-row justify-start items-center gap-1.5 p-0 m-0">
						<Bell className="h-8 w-8 p-0.5" />
						<div>Notifications</div>
					</div>
				</div>
			</Button >
		);
	}

	return (
		<Dialog>
			<DialogTrigger className="w-full flex justify-start flex-col items-start p-0 m-0">
				<div className="flex flex-row justify-between items-center w-fit p-0 m-0 ">
					<div className="flex flex-row justify-start items-center gap-1.5 p-0 m-0 ">
						<Bell className="h-5 w-5 p-0.5" />
						<div>Notifications</div>
					</div>
					{data && data.length > 0 && (
						<Badge className="bg-red-500 text-white h-4 w-4 mr-1 rounded-full">
							{data?.length}
						</Badge>
					)}
				</div>
			</DialogTrigger>
			<DialogContent className="w-fit min-w-96 max-w-4/5 min-h-96">
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

function NotficationBox({ notifications }: Readonly<{ notifications: any[] }>) {
	return (
		<div className="p-4 flex flex-col gap-2 max-h-96 overflow-auto">
			{notifications?.map((noti) => {
				return (
					<Notification key={noti?.id} notification={noti} >
						<Notification.Content>
							<Notification.ReadButton />
						</Notification.Content>
					</Notification>
				)
			})}
		</div>
	);
}
