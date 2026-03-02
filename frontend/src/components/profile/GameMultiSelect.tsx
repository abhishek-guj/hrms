import { Trash } from "lucide-react";
import { useState } from "react";
import { useGetGames } from "../game/queries/game.queries";
import { Badge } from "../ui/badge";
import { Button } from "../ui/button";
import {
	Select,
	SelectContent,
	SelectGroup,
	SelectItem,
	SelectLabel,
	SelectTrigger,
	SelectValue,
} from "../ui/select";
import { useUpdateProfile } from "./profile.queries";

const GameMultiSelect = ({
	oldInterestsIds,
}: {
	oldInterestsIds?: number[];
}) => {
	const [selectedGames, setSelectedGames] = useState<number[]>(
		oldInterestsIds?.map((id) => id) || [],
	);

	const { data: gameData, isLoading } = useGetGames();

	const updateInterests = useUpdateProfile();

	const handleChange = (value: number) => {
		setSelectedGames((prev) => {
			if (prev.includes(value)) {
				return prev;
			} else {
				return [...prev, value];
			}
		});
	};

	const handleRemove = (game: number) => {
		setSelectedGames((prev) => prev.filter((g) => g !== game));
	};

	const handleSave = async () => {
		await updateInterests.mutateAsync({
			gameTypeIds: selectedGames,
		});
	};

	if (isLoading) {
		return <div>Loading games...</div>;
	}
	return (
		<div className="flex flex-col gap-2 items-start rounded-2xl p-4">
			<Select onValueChange={handleChange}>
				<SelectTrigger className="w-[200px]">
					<SelectValue placeholder="Select games" />
				</SelectTrigger>
				<SelectContent>
					<SelectGroup>
						<SelectLabel>Games</SelectLabel>
						{gameData?.map((game) => (
							<SelectItem key={game.gameTypeId} value={game.gameTypeId}>
								{game.gameTypeName}
							</SelectItem>
						))}
					</SelectGroup>
				</SelectContent>
			</Select>
			<div className="container border p-4 mt-4">
				{selectedGames.length > 0 ? (
					<div className="flex flex-wrap gap-2">
						{selectedGames.map((game) => (
							<Badge
								key={game}
								className="flex gap-2 justify-center items-center text-sm"
							>
								{gameData?.find((g) => g.gameTypeId === game)?.gameTypeName ||
									"name not found"}
								<Button
									variant={"outline"}
									className="p-0 w-4 h-6"
									onClick={() => {
										handleRemove(game);
									}}
								>
									<Trash className="h-3 w-3 m-0 p-0 text-primary" />
								</Button>
							</Badge>
						))}
					</div>
				) : (
					<p className="text-gray-500">No games selected</p>
				)}
			</div>
			<div className="w-full text-end">
				<Button variant={"default"} size={"sm"} onClick={handleSave}>
					Save
				</Button>
			</div>
		</div>
	);
};

export default GameMultiSelect;
