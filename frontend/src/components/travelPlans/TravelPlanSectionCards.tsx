import { IconTrendingDown, IconTrendingUp } from "@tabler/icons-react";

import { Badge } from "../ui/badge";
import {
	Card,
	CardAction,
	CardDescription,
	CardFooter,
	CardHeader,
	CardTitle,
} from "../ui/card";

export function TravelPlanSectionCards() {
	return (
		<section className="grid gap-2 grid-cols-2 lg:grid-rows-1 lg:grid-cols-4 lg:gap-8 md:grid-cols-2 md:grid-rows-2 md:gap-4">
			<Card>
				<CardHeader>
					<CardDescription>Total Plans</CardDescription>
					<CardTitle className="text-2xl font-semibold tabular-nums @[250px]/card:text-3xl">
						60
					</CardTitle>
				</CardHeader>
			</Card>
			<Card>
				<CardHeader>
					<CardDescription>Ongoing Travels</CardDescription>
					<CardTitle className="text-2xl font-semibold tabular-nums @[250px]/card:text-3xl">
						3
					</CardTitle>
				</CardHeader>
			</Card>
			<Card>
				<CardHeader>
					<CardDescription>Upcoming Travels</CardDescription>
					<CardTitle className="text-2xl font-semibold tabular-nums @[250px]/card:text-3xl">
						2
					</CardTitle>
				</CardHeader>
			</Card>
			<Card>
				<CardHeader>
					<CardDescription>Last 30 days</CardDescription>
					<CardTitle className="text-2xl font-semibold tabular-nums @[250px]/card:text-3xl">
						7
					</CardTitle>
				</CardHeader>
			</Card>
		</section>
	);
}
