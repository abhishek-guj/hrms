import React from "react";
import { useProfile } from "../components/profile/profile.queries";
import { ViewField } from "../components/game/GameSlotDetails";
import { Separator } from "../components/ui/separator";
import GameMultiSelect from "../components/profile/GameMultiSelect";
import { Badge } from "../components/ui/badge";
import {
	Dialog,
	DialogClose,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
	DialogTrigger,
} from "../components/ui/dialog";
import { Button } from "../components/ui/button";
import type { GameDetailsDto } from "../components/game/types/game.types";

const ProfilePage = () => {
	const { data: profileData, isLoading, error } = useProfile();

	if (isLoading) {
		return <div>Loading...</div>;
	}
	if (error) {
		return <div>Error loading profile</div>;
	}

	const fullName = `${profileData?.firstName} ${profileData?.lastName}`;
	const email = profileData?.userEmail || "email not provided";
	const contactNumber =
		profileData?.contactNumber?.toString() || "contact number not provided";
	const managerName = profileData?.manager
		? `${profileData.manager.firstName} ${profileData.manager.lastName}`
		: "manager not assigned";
	const birthDate = profileData?.birthDate
		? new Date(profileData.birthDate).toLocaleDateString()
		: "birth date not provided";
	const joiningDate = profileData?.joiningDate
		? new Date(profileData.joiningDate).toLocaleDateString()
		: "joining date not provided";
	return (
		<main className="flex flex-col flex-1 justify-start items-center gap-4 p-12">
			<div className="flex flex-col gap-2 sm:w-1/2 w-10/11 border rounded-2xl md:p-8 p-4">
				<div className="flex justify-start items-center text-2xl font-semibold w-full">
					Profile
				</div>
				<Separator />
				<div className="flex flex-col gap-4 w-full md:p-12 p-4">
					<div className="grid grid-cols-1 gap-4">
						<ViewField name="Full Name" value={fullName} />
					</div>
					<div className="grid md:grid-cols-2 grid-cols-1 gap-4">
						<ViewField name="Email" value={email} />
						<ViewField name="Contact Number" value={contactNumber} />
					</div>
					<div className="grid grid-cols-1 gap-4">
						<ViewField name="Manager" value={managerName} />
					</div>
					<div className="grid sm:grid-cols-2 grid-cols-1 gap-4">
						<ViewField name="Birth Date" value={birthDate} />
						<ViewField name="Joining Date" value={joiningDate} />
					</div>
				</div>
				<Separator />
				<div>
					<div className="flex justify-start items-center text-2xl font-semibold w-full">
						Game Interests
					</div>
					{/* {console.log(profileData?.interests?)} */}
					{profileData?.interests?.length ? (
						<div className="flex flex-wrap gap-2 mt-4">
							{profileData.interests.map((game) => (
								<Badge
									key={game.gameTypeId}
									className="flex gap-2 justify-center items-center text-sm"
								>
									{game.gameTypeName}
								</Badge>
							))}
						</div>
					) : (
						<div className="mt-4">
							<p className="text-gray-500">No game interests selected</p>
						</div>
					)}
				</div>
			</div>
			<InterestChangeDialog oldInterests={profileData?.interests} />
		</main>
	);
};

const InterestChangeDialog = ({
	oldInterests,
}: {
	oldInterests?: GameDetailsDto[];
}) => {
	return (
		<Dialog>
			<DialogTrigger asChild>
				<Button variant={"outline"}>Edit Interests</Button>
			</DialogTrigger>
			<DialogContent className="sm:max-w-md gap-0">
				<DialogHeader>
					<DialogTitle>Edit Interests</DialogTitle>
					<DialogDescription>Select your game interests.</DialogDescription>
				</DialogHeader>
				<GameMultiSelect
					oldInterestsIds={oldInterests?.map((g) => g.gameTypeId)}
				/>
				<DialogFooter className="p-0 m-0 px-3">
					<DialogClose asChild>
						<Button variant={"outline"} type="button">
							Close
						</Button>
					</DialogClose>
				</DialogFooter>
			</DialogContent>
		</Dialog>
	);
};

export default ProfilePage;
