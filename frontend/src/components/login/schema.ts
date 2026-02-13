import * as z from "zod";

// used in login form
export const LoginFormSchema = z.object({
	email: z.email({ message: "Please enter valid email" }),
	password: z.string().min(1, { message: "Please enter valid password" }),
});
// used in login form

// used in creating travel plan
export const TravelPlanSchema = z.object({
	purpose: z.string().min(5, { error: "Purpose must be atleast 5 characers" }),
	travelTypeId: z.string().min(1, { error: "Provide Travel Plan Type" }),
	startDate: z.any(),
	endDate: z.date(),
	destinationCity: z.any(),
	destinationState: z.any(),
	destinationCountry: z.any(),
	lastDateOfExpenseSubmission: z.date(),
	maxAmountPerDay: z.any(),
});
// used in creating travel plan

export type LoginFormSchemaType = z.infer<typeof LoginFormSchema>;
export type TravelPlanSchemaType = z.infer<typeof TravelPlanSchema>;
