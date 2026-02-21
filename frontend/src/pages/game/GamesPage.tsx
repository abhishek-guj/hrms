import React from "react";
import { useTimeSlotsAll } from "../../components/game/queries/game.queries";

const GamesPage = () => {
	const { data, error, isLoading } = useTimeSlotsAll(); // exclamation to supress undefined error

	//
	//

	if (error) {
		return <div>{error.message}</div>;
	}
	if (isLoading) {
		return <div>Loading....</div>;
	}

	console.clear();
	Object.entries(data.gameSlots).forEach(([key, value]) => {
		console.log(`${key}:`, value);
	});

	return (
		<div className="w-full h-full bg-secondary p-10">
			{Object.entries(data.gameSlots).map(([keyof, value]) => {
				value.map((a) => {
					return (
						<div className="w-fit p-3 h-10 bg-primary flex items-center rounded-2xl text-white">
							{value.gameTypeId}
						</div>
					);
				});
			})}
		</div>
	);
};

export default GamesPage;
