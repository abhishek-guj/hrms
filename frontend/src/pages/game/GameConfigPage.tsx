import { Pencil, Plus, Trash } from "lucide-react";
import GameConfigForm from "../../components/game/forms/GameConfigForm";
import { ViewField } from "../../components/game/GameSlotDetails";
import {
  useDeleteGame,
  useGetGames,
} from "../../components/game/queries/game.queries";
import type { GameDetailsDto } from "../../components/game/types/game.types";
import { Button } from "../../components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from "../../components/ui/dialog";
import { Separator } from "../../components/ui/separator";

const GameConfigPage = () => {
  return (
    <div>
      <GamesList />
    </div>
  );
};

export default GameConfigPage;

const GamesList = () => {
  const { data, isLoading, error } = useGetGames();

  if (isLoading) {
    return (
      <div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
        Loading
      </div>
    );
  }
  if (error) {
    return (
      <div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
        No Data Found...
      </div>
    );
  }

  console.log(data);

  return (
    <div className="flex flex-col p-8 gap-4">
      <div className="grid grid-cols-1 gap-8 p-4 sm:px-10 justify-center w-full">
        <div className="flex flex-col gap-4 py-2 ">
          <div className="flex justify-between">
            <div className="text-xl font-bold">Games</div>
            <AddGameForm />
          </div>
          {data?.map((game) => {
            return <GameDetailCard key={game.gameTypeId} game={game} />;
          })}
        </div>
      </div>
    </div>
  );
};

const GameDetailCard = ({ game }: { game: GameDetailsDto }) => {
  console.log(game);
  const slotSizes = game?.slotSizes?.join(", ");

  return (
    <div className="flex flex-col gap-4 border p-4">
      <div className="flex justify-around gap-8 flex-wrap">
        <ViewField name={"Game"} value={game?.gameTypeName} />
        <ViewField
          name={"Slot Duration"}
          value={game?.maxSlotDurationMinutes}
        />
        <ViewField name={"Operational Hours Start"} value={game?.startTime} />
        <ViewField name={"Operational Hours End"} value={game?.endTime} />
        <ViewField name={"Slot Sizes"} value={slotSizes} />
      </div>
      <Separator />
      <div className="flex gap-3 justify-end w-full">
        <EditGameForm data={game} />
        <DeleteGameForm data={game} />
      </div>
    </div>
  );
};

const AddGameForm = () => {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button size="sm" className="flex items-center gap-1.5">
          <Plus className="h-4 w-4" />
          New
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-fit">
        <DialogHeader>
          <DialogTitle>Add Details</DialogTitle>
          <DialogDescription></DialogDescription>
        </DialogHeader>

        <GameConfigForm />
      </DialogContent>
    </Dialog>
  );
};

const EditGameForm = ({ data }: { data: GameDetailsDto }) => {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button
          size="sm"
          variant={"secondary"}
          className="flex items-center gap-1.5"
        >
          <Pencil className="h-4 w-4" />
          Edit
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-fit">
        <DialogHeader>
          <DialogTitle>Add Details</DialogTitle>
          <DialogDescription></DialogDescription>
        </DialogHeader>

        <GameConfigForm isEdit={true} gameData={data} />
      </DialogContent>
    </Dialog>
  );
};

const DeleteGameForm = ({ data }: { data: GameDetailsDto }) => {
  const deleteGame = useDeleteGame();
  const handleDelete = async () => {
    await deleteGame.mutateAsync({ gameId: data?.gameTypeId });
  };

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button
          size="sm"
          variant={"secondary"}
          className="flex items-center gap-1.5"
        >
          <Trash className="h-4 w-4" />
          Delete
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-fit">
        <DialogHeader>
          <DialogTitle>Delete Game</DialogTitle>
          <DialogDescription>
            Are you sure you want to delete this game?
            <br />
            {data?.gameTypeName}
          </DialogDescription>
        </DialogHeader>
        <div className="flex w-full justify-end">
          <Button onClick={handleDelete}>Delete</Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};
