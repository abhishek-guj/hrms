import { min } from "date-fns";
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
	startDate: z.date().refine((data) => data >= new Date(), { message: "Start date cant be in past" }),
	endDate: z.date(),
	destinationCity: z.string().min(5, { error: "City name must be atleast 3 characers" }),
	destinationState: z.string().min(5, { error: "State name must be atleast 3 characers" }),
	destinationCountry: z.string().min(5, { error: "Country name must be atleast 3 characers" }),
	lastDateOfExpenseSubmission: z.date(),
	maxAmountPerDay: z.string().min(0, { error: "Expense cant be negative" }),
}).refine((data) => data.lastDateOfExpenseSubmission > data.endDate, {
	path: ["lastDateOfExpenseSubmission"],
	message: "Last Submission Date must be After to End date",
}).refine((data) => data.startDate <= data.endDate, {
	path: ["endDate"],
	message: "End date must be After to Start date",
})
// used in creating travel plan

// used in creating expense
export const EmployeeExpenseSchema = z.object({
	travelPlanId: z.string().min(1, { error: "Provide Travel Plan" }),
	expenseTypeId: z.string().min(1, { error: "Provide Expense Type" }),
	expenseAmount: z.string()
		.min(0, { error: "please provide expense" }).refine((data) => Number(data) >= 0, {
			error: "please provide expense"
		}),
	expenseDate: z.date(),
	files: z.array(z.file()).min(1, { error: "atleast one file is requried" }),
});
// used in creating expense

// used in referring job form
export const JobReferralSchema = z.object({
	jobId: z.string().min(1, { error: "select one job" }),
	firstName: z.string().min(1, { error: "first name must be atleast 3 letter" }),
	lastName: z.string().min(1, { error: "last name must be atleast 3 letter" }),
	email: z.email({ error: "provide valid email" }),
	contactNumber: z.regex(/^([+]?[\s0-9]+)?(\d{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$/, 'invalid'),
	referredById: z.string(),
	cvFile: z.file(),
	note: z.string(),
})
// used in referring job form

export type LoginFormSchemaType = z.infer<typeof LoginFormSchema>;
export type TravelPlanSchemaType = z.infer<typeof TravelPlanSchema>;
export type EmployeeExpenseSchemaType = z.infer<typeof EmployeeExpenseSchema>;
export type JobReferralSchemaType = z.infer<typeof JobReferralSchema>;
