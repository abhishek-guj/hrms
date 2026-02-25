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
	startDate: z.date().refine((data) => {
		const newDate = new Date(new Date().setHours(0, 0, 0))
		console.log(data, new Date().setHours(0, 0, 0), newDate, data >= newDate, data.getTime(), newDate.getTime())
		return data >= newDate
	}, { message: "Start date cant be in past" }),
	endDate: z.date(),
	destinationCity: z.string().min(5, { error: "City name must be atleast 3 characers" }),
	destinationState: z.string().min(5, { error: "State name must be atleast 3 characers" }),
	destinationCountry: z.string().min(5, { error: "Country name must be atleast 3 characers" }),
	lastDateOfExpenseSubmission: z.date(),
	maxAmountPerDay: z.string().min(0, { error: "Expense cant be negative" }),
}).refine((data) => data.lastDateOfExpenseSubmission >= data.endDate, {
	path: ["lastDateOfExpenseSubmission"],
	message: "Last Submission Date must be After to End date",
}).refine((data) => data.startDate <= data.endDate, {
	path: ["endDate"],
	message: "End date must be After to Start date",
})

// used in creating travel plan
export const TravelPlanUpdateSchema = z.object({
	id: z.string(),
	purpose: z.string().min(5, { error: "Purpose must be atleast 5 characers" }),
	travelTypeId: z.string().min(1, { error: "Provide Travel Plan Type" }),
	startDate: z.date({ error: "Start date needed" }),
	endDate: z.date({ error: "End date needed" }),
	destinationCity: z.string().min(5, { error: "City name must be atleast 3 characers" }),
	destinationState: z.string().min(5, { error: "State name must be atleast 3 characers" }),
	destinationCountry: z.string().min(5, { error: "Country name must be atleast 3 characers" }),
	lastDateOfExpenseSubmission: z.date({ error: "Last Expense Submit date needed" }),
	maxAmountPerDay: z.number().min(0, { error: "Expense cant be negative" }),
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
	// files: z.array(z.file()).min(1, { error: "atleast one file is requried" }),
	files: z.instanceof(FileList).transform((fileList) => fileList[0])
		.refine((file) => !!file, { message: "File is required" })
});
// used in creating expense



const fileSchema = z.custom<File>();
// used in referring job form
export const JobReferralSchema = z.object({
	jobId: z.string().min(1, { error: "select one job" }),
	firstName: z.string().min(1, { error: "first name must be atleast 3 letter" }),
	lastName: z.string().min(1, { error: "last name must be atleast 3 letter" }),
	email: z.email({ error: "provide valid email" }),
	contactNumber: z.string().regex(/^([+]?[\s0-9]+)?(\d{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$/, 'invalid contact number'),
	// referredById: z.string(),
	cvFile: z.instanceof(FileList)
		.refine((file) => !!file[0], { message: "File is required" })
	,
	note: z.string().optional(),
})

export const TravelDocumentSchema = z.object({
	travelPlanId: z.string(),
	uploadedForEmployeeId: z.string(),
	documentTypeId: z.string().min(1, { error: "select document type" }),
	file: z.array(fileSchema).length(1)
		.refine((file) => !!file, { message: "File is required" })
		.refine((data) => ["application/pdf", "image/png", "image/jpeg", "image/jpg"].includes(data.type), { message: "Invlaid file type" })
})

// used in referring job form


export const GameCreateSchema = z.object({
	gameTypeId: z.any(),
	gameTypeName: z.string().min(2, "need to add name here"),
	maxSlotDurationMinutes: z.coerce.number().min(5, { error: "minimium 5 minutes" }),
	slotSizes: z.string()
		.regex(/^\d+(?:\s*,\s*\d+)*$/, {
			message: "Must be a comma-separated list of numbers",
		}),
	startTime: z.string(),
	endTime: z.string(),
})



export const JobCreateSchema = z.object({
	jobTitle: z.string().min(3, "title must be of at-least 3 "),
	jobDetails: z.string().min(10, "details must be of at-least 10 characters "),
	experienceYears: z.string().regex(/^-?\d+(\.\d+)?$/, "Must be a valid number")
		.refine((num) => !isNaN(Number(num)), { message: "Invalid number" })
		.refine((num) => Number(num) <= 60, { message: "Experience cant be above 60" }),
	numberOfVaccancy: z.string().regex(/^-?\d+(\.\d+)?$/, "Must be a valid number")
		.refine((num) => !isNaN(Number(num)), { message: "Invalid number" }),
	hrIds: z.array(z.string()).min(1, { error: "At least 1 hr needed" }),
	cvReviewerIds: z.array(z.string()).min(1, { error: "At least 1 reviewer needed" }),
	jobJdFile: z.instanceof(FileList)
		.refine((file) => !!file[0], { message: "File is required" })

})


export type LoginFormSchemaType = z.infer<typeof LoginFormSchema>;
export type JobCreateSchemaType = z.infer<typeof JobCreateSchema>;
export type TravelPlanSchemaType = z.infer<typeof TravelPlanSchema>;
export type TravelPlanUpdateSchemaType = z.infer<typeof TravelPlanUpdateSchema>;
export type EmployeeExpenseSchemaType = z.infer<typeof EmployeeExpenseSchema>;
export type JobReferralSchemaType = z.infer<typeof JobReferralSchema>;
export type TravelDocumentSchemaType = z.infer<typeof TravelDocumentSchema>;
export type GameCreateSchemaType = z.infer<typeof GameCreateSchema>;
