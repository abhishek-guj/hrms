import * as z from "zod";

export const LoginFormSchema = z.object({
	email: z.email({ message: "Please enter valid email" }),
	password: z.string().min(1, { message: "Please enter valid password" }),
});

export type LoginFormSchemaType = z.infer<typeof LoginFormSchema>;
