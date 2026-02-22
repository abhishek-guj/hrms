import { format } from "date-fns";
import { ViewField } from "../../components/game/GameSlotDetails";
import {
  useCancelBooking,
  useMySlotBookings,
} from "../../components/game/queries/game.queries";
import type { PlayerGroupDto } from "../../components/game/types/game.types";
import type { EmployeeProfileDto } from "../../components/job/types/job.types";
import { Badge } from "../../components/ui/badge";
import { Separator } from "../../components/ui/separator";
import { cn } from "../../lib/utils";
import { Button } from "../../components/ui/button";
import { RoleUtil } from "../../auth/role.util";

const MyBookingPage = () => {
  const { data } = useMySlotBookings();
  console.log(data);
  return (
    <div className="flex flex-col p-8 gap-4">
      <div className="text-xl font-bold">My Bookings</div>
      <div className="flex flex-col gap-8 py-4">
        {data?.map((booking) => (
          <div key={booking?.id} className="flex flex-col gap-4 p-8 border">
            <BookingDetails
              key={booking.id}
              slotDetails={booking?.gameSlot}
              slotSizes={booking?.slotSizes}
            />
            <Separator />
            <BookingGroupDetails
              playerGroup={booking?.playerGroup}
              groupOwner={booking?.groupOwner}
            />
            <Separator />
            <BookingStatus
              status={booking?.status}
              groupOwner={booking?.groupOwner}
              slotBookingId={booking?.id}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default MyBookingPage;

function BookingDetails({ slotDetails, slotSizes }) {
  const startDate = format(slotDetails?.slotStart.toString(), "dd/MM");
  const startTime = format(slotDetails?.slotStart.toString(), "HH:mm");
  const endTime = format(slotDetails?.slotEnd.toString(), "HH:mm");
  const slotSizesView = slotSizes?.join(", ");
  return (
    <div className="flex flex-col gap-1">
      <div className="font-semibold text-lg">SLot Details</div>
      <div className="grid grid-cols-5 gap-8 ">
        <ViewField name={"Slot Id"} value={slotDetails?.id} />
        <ViewField name={"Game"} value={slotDetails?.gameTypeName} />
        <ViewField name={"Date"} value={startDate} />
        <ViewField name={"Slot Start"} value={startTime} />
        <ViewField name={"Slot End"} value={endTime} />
        <ViewField name={"Slot Sizes"} value={slotSizesView} />
      </div>
    </div>
  );
}

function BookingGroupDetails({
  playerGroup,
  groupOwner,
}: {
  playerGroup: PlayerGroupDto;
  groupOwner: EmployeeProfileDto;
}) {
  return (
    <div className="flex flex-col gap-2">
      <div className="font-semibold text-lg">Players</div>
      <div className="flex flex-wrap justify-start gap-2">
        {playerGroup.players.map((player) => {
          const isOwner = groupOwner.id === player.id;
          return (
            <div
              key={player.id}
              className={`border text-white py-1 px-2 ${isOwner ? "bg-primary" : "bg-amber-500"}`}
            >{`${player.firstName} ${player.lastName}`}</div>
          );
        })}
      </div>
    </div>
  );
}

function BookingStatus({
  status,
  groupOwner,
  slotBookingId,
}: Readonly<{
  status: string;
  groupOwner: EmployeeProfileDto;
  slotBookingId: string;
}>) {
  const cancelBooking = useCancelBooking();

  const config =
    BookingStatusConfig[
      status?.toLowerCase() as keyof typeof BookingStatusConfig
    ];
  const isCancelled = "cancelled" === status?.toLowerCase();
  const isOwner = Number(groupOwner?.id) === Number(RoleUtil.myId);

  const handleCancel = () => {
    const res = cancelBooking.mutateAsync({ id: slotBookingId });
  };

  console.log(typeof groupOwner?.id, typeof RoleUtil.myId);
  return (
    <div className="flex flex-col gap-2">
      <div className="font-semibold text-lg">Status</div>
      <div className="flex justify-between">
        <Badge
          variant="outline"
          className={cn("border-0 text-base px-4 py-1", config?.className)}
        >
          {config?.label}
        </Badge>
        {!isCancelled && isOwner && (
          <Button variant={"outline"} onClick={handleCancel}>
            Cancel
          </Button>
        )}
      </div>
    </div>
  );
}

export const BookingStatusConfig = {
  confirmed: {
    label: "Confirmed",
    className:
      "bg-emerald-500/15 text-emerald-700 dark:bg-emerald-500/10 dark:text-emerald-400",
  },
  requested: {
    label: "Requested",
    className:
      "bg-blue-500/15 text-blue-700 dark:bg-blue-500/10 dark:text-blue-400",
  },
  waiting: {
    label: "Waiting",
    className:
      "bg-amber-500/15 text-amber-700 dark:bg-amber-500/10 dark:text-amber-400",
  },
  cancelled: {
    label: "Cancelled",
    className:
      "bg-rose-500/15 text-rose-700 dark:bg-rose-500/10 dark:text-rose-400",
  },
};
