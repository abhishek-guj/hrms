import { Button } from "../ui/button";

import { Field, FieldDescription, FieldGroup, FieldLabel } from "../ui/field";
import { Input } from "../ui/input";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm, type SubmitHandler } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth/auth.slice";
import { useAppDispatch } from "../../store/hooks";
import type { LoginFormType } from "./loginForm.types";
import { useLogin } from "./queries/auth.queries";
import { LoginFormSchema, type LoginFormSchemaType } from "./schema";

export function LoginForm({
  className,
  ...props
}: React.ComponentProps<"div">) {
  // navigate
  const navigate = useNavigate();
  // navigate

  // query hooks
  const login = useLogin();
  // query hooks

  // store
  const dispatch = useAppDispatch();
  // store

  // react form
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormSchemaType>({
    mode: "onBlur",
    defaultValues: {
      email: "abhi@exp.com",
      password: "1234",
    },
    resolver: zodResolver(LoginFormSchema),
    // defaultValues: JSON.parse(sessionStorage.getItem("prod")),
  });
  // react form

  // handlers
  const onSubmit: SubmitHandler<LoginFormType> = async (
    data: LoginFormType,
  ) => {
    const resData = await login.mutateAsync({
      email: data.email,
      password: data.password,
    });
    console.log("data update", resData);
    dispatch(authActions.authenticate(resData));
    navigate("/");
  };
  // handlers

  return (
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
  );
}
