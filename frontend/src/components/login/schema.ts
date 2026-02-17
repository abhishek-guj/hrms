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

// used in creating expense
export const EmployeeExpenseSchema = z.object({
	travelPlanId: z.string(),
	// submittedById: z.string(),
	expenseTypeId: z.string(),
	// submitStatus: z.string(),
	// expenseUploadDate: z.date(),
	expenseAmount: z.number(),
	expenseDate: z.date(),
	// status: z.string(),
	// remark: z.string(),
	files: z.array(z.file()).min(1, { error: "atleast one file is requried" }),
});
// used in creating expense

export type LoginFormSchemaType = z.infer<typeof LoginFormSchema>;
export type TravelPlanSchemaType = z.infer<typeof TravelPlanSchema>;
export type EmployeeExpenseSchemaType = z.infer<typeof EmployeeExpenseSchema>;
