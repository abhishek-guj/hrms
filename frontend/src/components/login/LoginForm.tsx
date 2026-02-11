import { cn } from "@/lib/utils";

import { Button } from "../ui/button";

import { Field, FieldDescription, FieldGroup, FieldLabel } from "../ui/field";
import { Input } from "../ui/input";
import { LoginFormCard } from "./LoginFormCard";

import { useForm, type SubmitHandler } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import type { LoginFormType } from "./loginForm.types";
import { LoginFormSchema, type LoginFormSchemaType } from "./schema";

export function LoginForm({
  className,
  ...props
}: React.ComponentProps<"div">) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormSchemaType>({
    mode: "onBlur",
    defaultValues: {
      email: "",
      password: "",
    },
    resolver: zodResolver(LoginFormSchema),
    // defaultValues: JSON.parse(sessionStorage.getItem("prod")),
  });

  const onSubmit: SubmitHandler<LoginFormType> = (data: LoginFormType) => {
    console.log("data update", data);
  };

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <LoginFormCard>
        <form onSubmit={handleSubmit(onSubmit)}>
          <FieldGroup>
            <Field>
              <FieldLabel htmlFor="email">Email</FieldLabel>
              <Input
                id="email"
                type="email"
                placeholder="m@example.com"
                {...register("email")}
              />
              {errors.email && (
                <p className="text-red-500">{errors.email.message}</p>
              )}
            </Field>
            <Field>
              <div className="flex items-center">
                <FieldLabel htmlFor="password">Password</FieldLabel>
              </div>
              <Input id="password" type="password" {...register("password")} />
              {errors && (
                <div className="text-red-500">{errors.password?.message}</div>
              )}
            </Field>

            <Field>
              <Button type="submit">Login</Button>
              <FieldDescription className="text-center">
                {/* Don&apos;t have an account? <a href="#">Sign up</a> */}
              </FieldDescription>
            </Field>
          </FieldGroup>
        </form>
      </LoginFormCard>
    </div>
  );
}
